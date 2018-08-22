/*

    Class that contain the AutoLoad Schedule method to generate and populate the combox boxes with
    date and time according to the DB.
   Contain methods to populate data into the db as well

 */

package com.example.pcborba.movieticketreservation_douglascollege;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class AutoloadScheduleAndDB extends AppCompatActivity {
    MyDB db;
    SQLiteDatabase wdb;
    SQLiteDatabase rdb;
    String [] movieDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_autoload_schedule_and_db);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        movieDescription=getResources().getStringArray(R.array.movieDesc);
        db = new MyDB(this);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.home:
                Intent home = new Intent(this, MainActivity.class);
                this.startActivity(home);
                return true;

            case R.id.about:
                Intent about = new Intent(this, About.class);
                this.startActivity(about);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //method to insert data to to DB
    public void insertData(View v){

        wdb = db.getWritableDatabase();

        long newRowId;
        ContentValues values = new ContentValues();

        //MOVIE

        try{

            values.clear();

            values.put("id", "1");
            values.put("name","Wonder");
            values.put("description",movieDescription[0]);
            values.put("url","ngiK1gQKgK8");

            newRowId = wdb.insert("MOVIE", null, values);

            values.put("id", "2");
            values.put("name","Despicable Me 3");
            values.put("description",movieDescription[1]);
            values.put("url","euz-KBBfAAo");

            newRowId = wdb.insert("MOVIE", null, values);

            values.put("id", "3");
            values.put("name","Justice League");
            values.put("description",movieDescription[2]);
            values.put("url","r9-DM9uBtVI");

            newRowId = wdb.insert("MOVIE", null, values);

            values.put("id", "4");
            values.put("name","Geostorm");
            values.put("description",movieDescription[3]);
            values.put("url","Qz8cjvKJLuw");

            newRowId = wdb.insert("MOVIE", null, values);

            values.put("id", "5");
            values.put("name","Blade Runner 2049");
            values.put("description",movieDescription[4]);
            values.put("url","gCcx85zbxz4");

            newRowId = wdb.insert("MOVIE", null, values);



        }catch(Exception ex){
            Log.e("error",ex.getMessage());
        }


        try {
            // ROOM

            values.clear();

            values.put("id", "1");
            values.put("number","001");
            values.put("seat_status","1");

            newRowId = wdb.insert("ROOM", null, values);

            values.put("id", "2");
            values.put("number","002");
            values.put("seat_status","1");

            newRowId = wdb.insert("ROOM", null, values);

            values.put("id", "3");
            values.put("number","003");
            values.put("seat_status","1");

            newRowId = wdb.insert("ROOM", null, values);

            values.put("id", "4");
            values.put("number","004");
            values.put("seat_status","1");

            newRowId = wdb.insert("ROOM", null, values);

            values.put("id", "5");
            values.put("number","005");
            values.put("seat_status","1");

            newRowId = wdb.insert("ROOM", null, values);

        }catch(Exception ex){
            Log.e("error", ex.getMessage());
        }


        String[] sessionHours= {"10:00 am","12:00 am","2:00 pm","4:00 pm","6:00 pm","8:00 pm"};

        try{
            //SESSION

            int idRecord = 1;

            for (int z=0;z<5;z++){ // DAY
                for (int x=0;x<5;x++) { //MOVIE
                    for (int y = 0; y < 6; y++) { //SESSION

                        values.clear();

                        values.put("id", idRecord);
                        values.put("movieID", x + 1);
                        values.put("roomID", x + 1);
                        values.put("sessionDate", "Dec, " + z+1);
                        values.put("sessionTime", sessionHours[y]);
                        values.put("seats","1,1,1,1,1,1");
                        newRowId = wdb.insert("MOVIE_SESSION", null, values);

                        idRecord++;

                        Thread.sleep(200);

                    }
                }
            }
        }catch(Exception ex){
            Log.e("error", ex.getMessage());
        }



        Log.e("ok","ok");

        Toast.makeText(this, "Data successfully inserted automatically.", Toast.LENGTH_SHORT).show();

    }

}
