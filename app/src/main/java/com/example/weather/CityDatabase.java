package com.example.weather;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = CityEntity.class,version = 1)
public abstract class CityDatabase extends RoomDatabase {

    public abstract CityDao cityDao();

    private static volatile CityDatabase cDB;


    public static CityDatabase getDatabase(final Context context){
        if(cDB==null){
            synchronized (CityDatabase.class){
                if(cDB==null){
                    cDB = Room.databaseBuilder(context.getApplicationContext(),CityDatabase.class,"Database1")
                            .createFromAsset("finalcitydatabase.db").allowMainThreadQueries().build();
                }
            }
        }
        return cDB;
    }
}
