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
/***************************************************************************************
 * I learned to use room Database with a local db file from the learning resources below:
 *
 *    Title: <confused about Populating AutoCompleteTextView Suggestions with a Room Database>
 *    Author: <MikeT>
 *    Date: <17/12/2021>
 *    Code version: <N/A>
 *    Availability: <https://stackoverflow.com/questions/69683821/confused-about-populating-autocompletetextview-suggestions-with-a-room-database/69684400?noredirect=1#comment123194210_69684400>
 *
 ***************************************************************************************/
