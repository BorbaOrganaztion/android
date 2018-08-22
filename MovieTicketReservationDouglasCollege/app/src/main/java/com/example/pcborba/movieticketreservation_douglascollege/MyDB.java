/**
 * Class to exec DB and create tables
 */
package com.example.pcborba.movieticketreservation_douglascollege;

import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by offcampus on 11/22/2017.
 */

public class MyDB extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "MoviesA.db";

    public MyDB(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    //  called after the database connection has been configured
    //  and after the database schema has been created, upgraded or downgraded as necessary
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            // Enable foreign key constraints
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TableDefinitions.SQL_CREATE_MOVIE); // creates the Movie table
        db.execSQL(TableDefinitions.SQL_CREATE_SESSION); // creates the Session table
        db.execSQL(TableDefinitions.SQL_CREATE_ROOM); // creates the Room table
        db.execSQL(TableDefinitions.SQL_CREATE_TICKET); // creates the Room table

    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(TableDefinitions.SQL_DELETE_MOVIE);
        db.execSQL(TableDefinitions.SQL_DELETE_SESSION);
        db.execSQL(TableDefinitions.SQL_DELETE_ROOM);
        db.execSQL(TableDefinitions.SQL_DELETE_TICKET);
        onCreate(db);
    }
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }



    public List<String> getSessionDates(String movieID){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT sessionDate FROM MOVIE_SESSION WHERE movieID=" + movieID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }

    public List<String> getSessionTimes(String movieID){
        List<String> labels = new ArrayList<String>();

        // Select All Query
        String selectQuery = "SELECT sessionTime FROM MOVIE_SESSION WHERE movieID=" + movieID;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                labels.add(cursor.getString(1));
            } while (cursor.moveToNext());
        }

        // closing connection
        cursor.close();
        db.close();

        // returning lables
        return labels;
    }


}
