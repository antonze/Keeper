/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.antonnazareth.keeper.data;

//import com.example.antonnazareth.keeper.data.KeeperContract.Team2UserEntry;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;

import com.example.antonnazareth.keeper.EntityClasses.UserEntity;
import com.example.antonnazareth.keeper.backend.myApi.MyApi;
import com.example.antonnazareth.keeper.backend.myApi.model.MyBean;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Manages a local database for weather data.
 */
public class dbHelper extends SQLiteOpenHelper {
    private static final Logger logger = Logger.getLogger(dbHelper.class
            .getName());

    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "keeper.db";

    private static MyApi myApiService = null;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        logger.info("onCreate");
        // Create a table to hold locations.  A location consists of the string supplied in the
        // location setting, the city name, and the latitude and longitude
        final String SQL_CREATE_USER_TABLE = "CREATE TABLE " + KeeperContract.UserEntry.TABLE_NAME + " (" +
                KeeperContract.UserEntry.COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                KeeperContract.UserEntry.COLUMN_FIRST_NAME + " TEXT NOT NULL, " +
                KeeperContract.UserEntry.COLUMN_LAST_NAME + " TEXT NOT NULL, " +
                KeeperContract.UserEntry.COLUMN_NICKNAME + " REAL NOT NULL " +
                " );";

        final String SQL_CREATE_TEAM_TABLE = "CREATE TABLE " + KeeperContract.TeamEntry.TABLE_NAME + " (" +

                KeeperContract.TeamEntry.COLUMN_TEAM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                KeeperContract.TeamEntry.COLUMN_TEAM_NAME + " TEXT UNIQUE NOT NULL " +
                " );";

        sqLiteDatabase.execSQL(SQL_CREATE_USER_TABLE);
        sqLiteDatabase.execSQL(SQL_CREATE_TEAM_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        logger.info("onUpgrade");

        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        // Note that this only fires if you change the version number for your database.
        // It does NOT depend on the version number for your application.
        // If you want to update the schema without wiping data, commenting out the next 2 lines
        // should be your top priority before modifying this method.
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KeeperContract.UserEntry.TABLE_NAME);
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + KeeperContract.TeamEntry.TABLE_NAME);

        onCreate(sqLiteDatabase);
    }

}
