package com.example.weather;


import static org.junit.Assert.assertEquals;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;


public class CityDatabaseTest {
    private CityDatabase db;
    private CityDao dao;



    @Before
    public void createDB(){
        Context context = ApplicationProvider.getApplicationContext();
        db =Room.databaseBuilder(context.getApplicationContext(),CityDatabase.class,"Database1")
                .createFromAsset("finalcitydatabase.db").allowMainThreadQueries().build();
        dao =db.cityDao();
    }


    @After
    public void closeDB() throws IOException{
        db.close();
    }

    @Test
    public void readTest() throws Exception{
         String city =dao.getName();
         assertEquals("Waterloo",city);
    }
    @Test
    public void readTest2()throws Exception{
        String ctry = dao.getCountry();
        assertEquals("TW",ctry);
    }

    //https://developer.android.com/training/data-storage/room/testing-db

}