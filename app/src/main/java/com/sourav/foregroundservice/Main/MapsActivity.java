package com.sourav.foregroundservice.Main;

import android.Manifest;
import android.arch.persistence.room.Room;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteConstraintException;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.sourav.foregroundservice.DB.AppDatabase;
import com.sourav.foregroundservice.DB.Location;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.sourav.foregroundservice.DB.LocationDAO;
import com.sourav.foregroundservice.R;
import com.sourav.foregroundservice.Service.LocationMonitoringService;
import com.sourav.foregroundservice.Utils.MarshMallowPermissions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {


    private MarshMallowPermissions marshMallowPermissions;
    private GoogleMap mMap;

    Marker mCurrLocationMarker;
    GoogleApiClient mGoogleApiClient;
    LocationRequest mLocationRequest;
    private LocationManager locationManager;
    private LocationCallback mLocationCallback;
    private LocationListener mLocationListener;
    private double lat;
    private double lng;
    private TextView locationTv;
    private boolean mRequestingLocationUpdates = false;
    private FusedLocationProviderClient mFusedLocationClient;
    private String provider = "";
    private SettingsClient mSettingsClient;
    private String mLastUpdateTime;
    private LocationSettingsRequest mLocationSettingsRequest;
    private boolean mAlreadyStartedService = false;
    private Marker myMarker;
    private LocationDAO mLocationDAO;
    private ArrayList<Location> locationList;
    private RecyclerView rv_positions;
    private LocationListAdapter locationListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        locationTv = (TextView) findViewById(R.id.latlongLocation);
        rv_positions = (RecyclerView) findViewById(R.id.rv_positions);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        rv_positions.setLayoutManager(mLinearLayoutManager);
        marshMallowPermissions = new MarshMallowPermissions(MapsActivity.this);
        checkLocationPermission();
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.

    }

    private void checkLocationPermission() {
        if (marshMallowPermissions.checkPermissionForFineLocation()) {
            //do your task--------------
            Log.d("Permission:", "Fine location permission is already given");
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);
            startLocationService();


        } else {
            Log.d("Permission:", "requesting Fine location permission");
            marshMallowPermissions.requestPermissionForFineLocation();
        }
    }

    private void initDB() {
        mLocationDAO = Room.databaseBuilder(this, AppDatabase.class, "db-locations")
                .allowMainThreadQueries()   //Allows room to do operation on main thread
                .build()
                .getLocationDAO();

        if (mLocationDAO.getLocations()!=null){
            locationList= (ArrayList<Location>) mLocationDAO.getLocations();
            Log.d("locationList", "onReceive: " + locationList.toString());
            locationListAdapter=new LocationListAdapter(locationList,MapsActivity.this);
            rv_positions.setAdapter(locationListAdapter);
        }

    }


    private void startLocationService() {
        if (!mAlreadyStartedService) {

            //Start location sharing service to app server.........
            Intent intent = new Intent(this, LocationMonitoringService.class);
            startService(intent);

            mAlreadyStartedService = true;
            //Ends................................................
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case MarshMallowPermissions.FINE_LOCATION_PERMISSION_REQUEST_CODE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the task you need to do.
                    Log.d("Permission:", "Fine location permission aquired");
                    SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                            .findFragmentById(R.id.map);
                    mapFragment.getMapAsync(this);
                    startLocationService();


                } else {
                    // permission denied, boo! Disable the functionality that depends on this permission.
                }
                return;
            }
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        enableMyLocationIfPermitted();
        initDB();
        mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

        LocalBroadcastManager.getInstance(this).registerReceiver(
                new BroadcastReceiver() {
                    @Override
                    public void onReceive(Context context, Intent intent) {
                        String latitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LATITUDE);
                        String longitude = intent.getStringExtra(LocationMonitoringService.EXTRA_LONGITUDE);
                        String speed = intent.getStringExtra(LocationMonitoringService.EXTRA_SPEED);
                        String time_stamp = intent.getStringExtra(LocationMonitoringService.EXTRA_TIME_STAMP);

                        if (latitude != null && longitude != null) {
                            locationTv.setText("\n Latitude : " + latitude + "\n Longitude: " + longitude);

                            LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                            MarkerOptions a = new MarkerOptions().position(latLng);
                            myMarker = mMap.addMarker(a);
                            myMarker.setPosition(latLng);
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));
                            mMap.animateCamera(CameraUpdateFactory.zoomTo(15));

                            Location location = new Location();
                            location.setLatitude(latitude);
                            location.setLongitude(longitude);
                            location.setGpsSpeed(speed);
                            location.setTimeStamp(time_stamp);
                            try {
                                mLocationDAO.insert(location);

                            } catch (SQLiteConstraintException e) {
                                e.printStackTrace();
                               // Toast.makeText(MapsActivity.this, "Same location exists.", Toast.LENGTH_SHORT).show();
                            }

                            if (mLocationDAO!=null) {

                                Log.d("locationList", "onReceive: " + mLocationDAO.getLocations().toString());
                            }

                        }
                    }
                }, new IntentFilter(LocationMonitoringService.ACTION_LOCATION_BROADCAST)
        );
    }

    private void enableMyLocationIfPermitted() {


        if (!marshMallowPermissions.checkPermissionForFineLocation()) {
            marshMallowPermissions.requestPermissionForFineLocation();
        } else {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onDestroy() {


        //Stop location sharing service to app server.........

        stopService(new Intent(this, LocationMonitoringService.class));
        mAlreadyStartedService = false;
        //Ends................................................


        super.onDestroy();
    }
}
