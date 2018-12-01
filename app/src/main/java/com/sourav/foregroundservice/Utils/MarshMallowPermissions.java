package com.sourav.foregroundservice.Utils;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import com.sourav.foregroundservice.R;

import java.util.ArrayList;
import java.util.List;


public class MarshMallowPermissions {

    public static final int AUDIO_RECORD_PERMISSION_REQUEST_CODE = 1;
    public static final int EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2;
    public static final int CAMERA_PERMISSION_REQUEST_CODE = 3;
    public static final int FINE_LOCATION_PERMISSION_REQUEST_CODE = 4;
    public static final int COARSE_LOCATION_PERMISSION_REQUEST_CODE = 5;
    public static final int READ_CONTACTS_PERMISSION_REQUEST_CODE = 5;

    //
    public static final int CAMERA_AT_ONCE_REQUEST_CODE = 101;
    public static final int GPS_AT_ONCE_REQUEST_CODE = 102;

    public static int OVERLAY_PERMISSION_REQ_CODE = 1234;

    Activity activity;

    public MarshMallowPermissions(Activity activity) {
        this.activity = activity;
    }

    public boolean isBelowMarshmallow() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        return false;
    }

    /**
     * **********************************************************************************************************************
     * *********************************** CHECK PERMISSION SECTION ==> STARTS **********************************************
     * **********************************************************************************************************************
     */

    /**
     * READ CONTACTS PERMISSION CHECK
     * @return
     */
    public boolean checkPermissionForReadContacts(){
        if(isBelowMarshmallow())
            return true;

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.READ_CONTACTS);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    @TargetApi(Build.VERSION_CODES.M)
    public boolean checkOverlayPermission() {

        if(isBelowMarshmallow())
            return true;

        if (!Settings.canDrawOverlays(activity)) {
            /*Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);*/
            return  false;
        }else{
            return true;
        }
    }
    public void requestOverlayPermission(){
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                Uri.parse("package:" + activity.getPackageName()));
        activity.startActivityForResult(intent, OVERLAY_PERMISSION_REQ_CODE);

    }




    /**
     * READ CONTACTS PERMISSION REQUEST
     */
    public void requestPermissionForReadContacts(){
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.READ_CONTACTS)){
            showMessageOKCancel(activity.getResources().getString(R.string.read_contacts),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION_REQUEST_CODE);
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_CONTACTS}, READ_CONTACTS_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * AUDIO RECORD PERMISSION CHECK
     * @return
     */
    public boolean checkPermissionForAudioRecord(){
        if(isBelowMarshmallow())
            return true;

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.RECORD_AUDIO);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    //
    /**
     * AUDIO RECORD PERMISSION REQUEST
     */
    public void requestPermissionForAudioRecord(){
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.RECORD_AUDIO)){
            showMessageOKCancel(activity.getString(R.string.record_audio),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_RECORD_PERMISSION_REQUEST_CODE);
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.RECORD_AUDIO}, AUDIO_RECORD_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * FINE LOCATION PERMISSION CHECK
     * @return
     */
    public boolean checkPermissionForFineLocation(){
        if(isBelowMarshmallow())
            return true;

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    //
    /**
     * FINE LOCATION PERMISSION REQUEST
     */
    public void requestPermissionForFineLocation(){
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)){
            showMessageOKCancel(activity.getString(R.string.access_fine_location),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST_CODE);

                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, FINE_LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * COARSE LOCATION PERMISSION CHECK
     * @return
     */
    public boolean checkPermissionForCoarseLocation(){
        if(isBelowMarshmallow())
            return true;

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    //
    /**
     * COARSE LOCATION PERMISSION REQUEST
     */
    public void requestPermissionForCoarseLocation(){
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)){
            showMessageOKCancel(activity.getString(R.string.access_coarse_location),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_REQUEST_CODE);
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, COARSE_LOCATION_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * WRITE EXTERNAL STORAGE PERMISSION CHECK
     * @return
     */
    public boolean checkPermissionForWriteExternalStorage(){
        if(isBelowMarshmallow())
            return true;

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    //
    /**
     * WRITE EXTERNAL STORAGE PERMISSION REQUEST
     */
    public void requestPermissionForExternalStorage(){
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            showMessageOKCancel(activity.getString(R.string.write_external_storage),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * CAMERA PERMISSION CHECK
     * @return
     */
    public boolean checkPermissionForCamera(){
        if(isBelowMarshmallow())
            return true;

        int result = ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA);
        if (result == PackageManager.PERMISSION_GRANTED){
            return true;
        } else {
            return false;
        }
    }
    //
    /**
     * CAMERA PERMISSION REQUEST
     */
    public void requestPermissionForCamera(){
        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.CAMERA)){
            showMessageOKCancel(activity.getString(R.string.camera),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
                            }
                        }
                    });
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_REQUEST_CODE);
        }
    }

    /**
     * **********************************************************************************************************************
     * *********************************** CHECK SINGLE PERMISSION SECTION ==> ENDS ******************************************
     * **********************************************************************************************************************
     */



    /**
     * **********************************************************************************************************************
     * *********************************** GPS PERMISSION AT ONCE ==> STARTS **********************************************
     * **********************************************************************************************************************
     */

    /**
     * false ==> Some permissions not allowed
     * true ==> All permissions allowed
     * @return
     */

    public boolean isAllGpsPermissionAllowed() {
        if(isBelowMarshmallow())
            return true;

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_FINE_LOCATION))
            permissionsNeeded.add(activity.getString(R.string.tag_access_fine_location));
        if (!addPermission(permissionsList, Manifest.permission.ACCESS_COARSE_LOCATION))
            permissionsNeeded.add(activity.getString(R.string.tag_access_coarse_location));

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = activity.getString(R.string.cannot_proceed_permission_msg) + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                                            GPS_AT_ONCE_REQUEST_CODE);
                                }
                            }
                        });
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                        GPS_AT_ONCE_REQUEST_CODE);
            }
            return false;
        }

        return true;
    }
    /**
     * **********************************************************************************************************************
     * *********************************** GPS PERMISSION AT ONCE ==> ENDS **********************************************
     * **********************************************************************************************************************
     */



    /**
     * **********************************************************************************************************************
     * *********************************** CAMERA PERMISSION AT ONCE ==> STARTS **********************************************
     * **********************************************************************************************************************
     */

    /**
     * false ==> Some permissions not allowed
     * true ==> All permissions allowed
     * @return
     */

    public boolean isAllCameraPermissionAllowed() {
        if(isBelowMarshmallow())
            return true;

        List<String> permissionsNeeded = new ArrayList<String>();

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.CAMERA))
            permissionsNeeded.add(activity.getString(R.string.tag_camera));
        if (!addPermission(permissionsList, Manifest.permission.RECORD_AUDIO))
            permissionsNeeded.add(activity.getString(R.string.tag_record_audio));
        if (!addPermission(permissionsList, Manifest.permission.WRITE_EXTERNAL_STORAGE))
            permissionsNeeded.add(activity.getString(R.string.tag_write_external_storage));

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = activity.getString(R.string.cannot_proceed_permission_msg) + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                showMessageOKCancel(message,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                                            CAMERA_AT_ONCE_REQUEST_CODE);
                                }
                            }
                        });
                return false;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                ActivityCompat.requestPermissions(activity, permissionsList.toArray(new String[permissionsList.size()]),
                        CAMERA_AT_ONCE_REQUEST_CODE);
            }
            return false;
        }

        return true;
    }
    /**
     * **********************************************************************************************************************
     * *********************************** CAMERA PERMISSION AT ONCE ==> ENDS **********************************************
     * **********************************************************************************************************************
     */

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (ActivityCompat.checkSelfPermission(activity, permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission))
                    return false;
            }
        }
        return true;
    }


    public void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(activity)
                .setMessage(message)
                .setPositiveButton(activity.getString(R.string.permission_dialog_button_ok), okListener)
                //.setNegativeButton(activity.getString(R.string.permission_dialog_button_cancel), null)
                .create()
                .show();
    }
}
