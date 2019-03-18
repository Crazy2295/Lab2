package com.example.crazy.lab2.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "DBLab";

    private static final int DATABASE_VERSION = 1;

    // Database creation sql statement
    private static final String DATABASE_USERS_CREATE = "create table Users " +
            "(id integer primary key autoincrement, login text, password text);";

    private static final String DATABASE_CITIES_CREATE = "create table Cities " +
            "( id integer primary key autoincrement,name text not null unique, atmospheric_pressure text, " +
            "temperature integer, weather_condition text, weekday text, wind integer);";

    private static final String DATABASE_SAVED_CITIES_CREATE = "create table SavedCities " +
            "( id integer primary key autoincrement,user_id integer not null, city_id integer not null);";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Method is called during creation of the database
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(DATABASE_USERS_CREATE);
        database.execSQL(DATABASE_CITIES_CREATE);
        database.execSQL(DATABASE_SAVED_CITIES_CREATE);
        database.execSQL("INSERT INTO Users (login, password) VALUES ('admin', 'admin');");
        database.execSQL("INSERT INTO Users (login, password) VALUES ('admin1', 'admin1');");
        database.execSQL("INSERT INTO Users (login, password) VALUES ('admin2', 'admin2');");
        database.execSQL("INSERT INTO Users (login, password) VALUES ('admin3', 'admin3');");
        database.execSQL("INSERT INTO Users (login, password) VALUES ('admin4', 'admin4');");
    }

    // Method is called during an upgrade of the database,
    @Override
    public void onUpgrade(SQLiteDatabase database,int oldVersion,int newVersion){
    }
}
