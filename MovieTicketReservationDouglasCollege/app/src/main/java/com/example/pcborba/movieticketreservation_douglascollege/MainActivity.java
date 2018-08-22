package com.example.pcborba.movieticketreservation_douglascollege;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button[] btn;
    LinearLayout llBtns;
    ScrollView svDesc;
    TextView tvDesc;
    String [] movieDescription;
    String[] movieUrl;
    String movieYoutube="";
    int movieSelected = -1;

    MyDB db;
    SQLiteDatabase wdb;
    SQLiteDatabase rdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        svDesc = (ScrollView)findViewById(R.id.svDesc);
        movieDescription=getResources().getStringArray(R.array.movieDesc);
        movieUrl=getResources().getStringArray(R.array.movieYoutube);
        tvDesc =(TextView) findViewById(R.id.tvDesc);



        llBtns = (LinearLayout) findViewById(R.id.llBtn);
        btn = new Button[llBtns.getChildCount()];

        for(int i = 0;i< llBtns.getChildCount();i++){
            btn[i] = (Button) llBtns.getChildAt(i);
        }

        db = new MyDB(this);

        //insertData();

        //selectData();

    }

    public void selectData(){

        rdb = db.getReadableDatabase();
        //String selectQuery = "SELECT seats, sessionDate, sessionTime FROM MOVIE_SESSION";
        String selectQuery = "SELECT seats, sessionDate, sessionTime FROM MOVIE_SESSION WHERE sessionDate = 'Dec, 01' and sessionTime='10:00 am'";
        try {
            Cursor cursor = rdb.rawQuery(selectQuery, null); // 2nd arg is for where clause

                // looping through all rows and adding to list
            int qtd = cursor.getCount();
            int asd = cursor.getColumnCount();
                if (cursor != null) {
                    cursor.moveToFirst();

                    String teste;
                    int cnt = 0;
                    do {


                        for(int x = 0;x< cursor.getColumnCount();x++)
                        {
                         teste = cursor.getString(x);

                        }



                    } while (cursor.moveToNext());

                }
            }catch(Exception ex){
                Log.e("ERROR", ex.getMessage());

            }


    }

    public void insertData(){

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
            case R.id.about:
                Intent about = new Intent(this, About.class);
                this.startActivity(about);
                return true;

            case R.id.auto:
                Intent auto = new Intent(this, AutoloadScheduleAndDB.class);
                this.startActivity(auto);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void filmSelect(View v)
    {
        svDesc.setVisibility(View.VISIBLE);
        switch (llBtns.indexOfChild(v)){
            case 0:
                tvDesc.setText(movieDescription[0]);
                movieYoutube = "https://www.youtube.com/watch?v="+movieUrl[0];
                movieSelected = llBtns.indexOfChild(v);
                break;
            case 1:
                tvDesc.setText(movieDescription[1]);
                movieYoutube = "https://www.youtube.com/watch?v="+movieUrl[1];
                movieSelected = llBtns.indexOfChild(v);
                break;
            case 2:
                tvDesc.setText(movieDescription[2]);
                movieYoutube = "https://www.youtube.com/watch?v="+movieUrl[2];
                movieSelected = llBtns.indexOfChild(v);
                break;
            case 3:
                tvDesc.setText(movieDescription[3]);
                movieYoutube = "https://www.youtube.com/watch?v="+movieUrl[3];
                movieSelected = llBtns.indexOfChild(v);
                break;
            case 4:
                tvDesc.setText(movieDescription[4]);
                movieYoutube = "https://www.youtube.com/watch?v="+movieUrl[4];
                movieSelected = llBtns.indexOfChild(v);
                break;
        }
    }

    public void openYoutube(View v){

//        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v="+movieUrl[llBtns.indexOfChild(v)]));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//        intent.setPackage("com.google.android.youtube");
//        startActivity(intent);

        if(movieYoutube!="")
        {
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movieYoutube));
            startActivity(intent);
        }
    }

    public void hideDesc(View v){
        svDesc.setVisibility(View.INVISIBLE);
        movieSelected = -1;
    }

    public void buyTicket(View v)
    {
        if(movieSelected!=-1){
            Intent buyTicket = new Intent(MainActivity.this, SelectSession.class);
            buyTicket.putExtra("movie",movieSelected);
            startActivity(buyTicket);
        }
        else
        {
            Toast.makeText(this, "Please select your movie first before press this button", Toast.LENGTH_SHORT).show();
        }

    }
}
