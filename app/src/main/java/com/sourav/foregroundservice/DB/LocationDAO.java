package com.sourav.foregroundservice.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Tanmoy Banerjee on 29-11-2018.
 */
@Dao
public interface LocationDAO {

    @Insert
    public void insert(Location... locations);

    @Update
    public void update(Location... locations);

    @Delete
    public void delete(Location location);

    @Query("SELECT * FROM location")
    public List<Location> getLocations();
}
