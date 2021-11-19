package com.example.weather;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface CityDao {
//    @Query("SELECT * FROM citytable WHERE name LIKE :cityName ")
//    List<CityEntity> getCitiesByName(String cityName);
//
//    @Query("SELECT name FROM citytable WHERE name LIKE :cityName ")
//    List<String> getCityNamesByName(String cityName);

    //DataAccessObject
    @Query("SELECT distinct name FROM citytable order by Length(name)")
    String[] getAllCityNames();
}
