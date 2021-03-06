package com.example.weather;

import androidx.room.Dao;
import androidx.room.Query;

@Dao
public interface CityDao {


    //DataAccessObject
    @Query("SELECT distinct name FROM citytable order by Length(name)")
    String[] getAllCityNames();

    @Query("SELECT  name FROM citytable where id = 8224785")
    String getName();
    //for testing,result should be 'Waterloo'

    @Query("Select country FROM citytable where name = 'Taipei' ")
    String getCountry();
    //for testing,result should be 'TW'



}
