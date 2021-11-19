package com.example.weather;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName ="citytable")
public class CityEntity {

    //define data types in this class
    //the constraints and attributes names MUST match what was defined in your .db file
    @PrimaryKey @NonNull public int id;

    public String name;
    public String state;
    public String country;
    public String lon;
    public String lat;
}
