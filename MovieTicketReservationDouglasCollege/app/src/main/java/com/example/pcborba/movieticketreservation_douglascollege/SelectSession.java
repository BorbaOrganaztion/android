/**
 * Class that opens the activity to select the session
 */

package com.example.pcborba.movieticketreservation_douglascollege;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SelectSession extends AppCompatActivity {

    ConstraintLayout clSeats;
    Button[] btnSeats;
    int sizA;
    int[] seats= {1,1,1,1,1,1};
    TextView tvAlertSelection;
    ImageView movieS;
    int movieSelected;

    String selectedDay="";
    String selectedTime="";

    String seatsList = "";
    String sessionID = "";
    String roomID = "";


    MyDB db;
    SQLiteDatabase wdb;
    SQLiteDatabase rdb;


   Spinner spinnerDate = null;
   Spinner spinnerHour=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_session);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        tvAlertSelection = findViewById(R.id.tvAlert);
        clSeats = (ConstraintLayout) findViewById(R.id.clSeats);
        btnSeats = new Button[clSeats.getChildCount()];
        movieS = (ImageView) findViewById(R.id.ivMovieSel);
        Bundle bundle = getIntent().getExtras();
        this.movieSelected = bundle.getInt("movie");
        spinnerDate = (Spinner) findViewById(R.id.sessionDate);
        spinnerHour = (Spinner) findViewById(R.id.sessionTime);

        Log.d("Identifier movie", String.valueOf(movieSelected));

        db = new MyDB(this);
        rdb = db.getReadableDatabase();

        //switch loop to get the movie selected
        switch (movieSelected) {
            case 0:
                movieS.setImageResource(R.drawable.wonder);
                break;
            case 1:
                movieS.setImageResource(R.drawable.despicableme);
                break;
            case 2:
                movieS.setImageResource(R.drawable.justiceleage);
                break;
            case 3:
                movieS.setImageResource(R.drawable.geostorm);
                break;
            case 4:
                movieS.setImageResource(R.drawable.bladerunner2049);
                break;
        }

        for (int i = 0; i < clSeats.getChildCount(); i++) {
            btnSeats[i] = (Button) clSeats.getChildAt(i);
        }

        for (int i = 0; i < seats.length; i++) {
            if (seats[i] == 0) {
                btnSeats[i].setBackgroundResource(R.drawable.nonfreeseats);
            }
        }

        spinnerDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedDay = parent.getItemAtPosition(position).toString();

                if ((!selectedDay.isEmpty()) && (!selectedTime.isEmpty())) {
                    seatsList = getSessionList(selectedDay,selectedTime);
                    setSeatStatus(seatsList);

                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerHour.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTime = parent.getItemAtPosition(position).toString();

                if ((!selectedDay.isEmpty()) && (!selectedTime.isEmpty())) {
                    seatsList = getSessionList(selectedDay,selectedTime);
                    setSeatStatus(seatsList);
                }

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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

    //method to check if seat is already taken or not
    public void setSeatStatus(String seatList){
        int index = 0;
        for (int i = 0; i<seatList.length(); i++) {
            if (seatList.charAt(i) != ',') {
                int temp = Character.getNumericValue(seatList.charAt(i));
                if (temp==0) {
                    btnSeats[index].setBackgroundResource(R.drawable.nonfreeseats);
                    seats[index]=0;
                }else if (temp==1)
                {
                    btnSeats[index].setBackgroundResource(R.drawable.freeseats);
                    seats[index]=1;
                }else if(temp==2)
                {
                    btnSeats[index].setBackgroundResource(R.drawable.selectedseats);
                }
                index++;
            }
        }

    }

    public String getSessionList(String filterDay, String filterTime){

        String stringSession="";

        SQLiteDatabase wdb;
        SQLiteDatabase rdb;


        rdb = db.getReadableDatabase();

        String selectQuery = "SELECT id,seats,roomID FROM MOVIE_SESSION where sessionDate='"+   filterDay + "' and sessionTime='"+filterTime + "'";
        try {
            Cursor cursor = rdb.rawQuery(selectQuery, null); // 2nd arg is for where clause
            // looping through all rows and adding to list
            if (cursor != null) {
                cursor.moveToFirst();
                stringSession = cursor.getString(1);
                roomID = cursor.getString(2);
                sessionID = cursor.getString(0);
            }
        }catch(Exception ex){
            Log.e("ERROR", ex.getMessage());
        }
        return stringSession;
    }


    public void seatSelect(View v){
        int [] seatTemp = Arrays.copyOf(seats,6);
        String seatsClicked="";
         if(seats[clSeats.indexOfChild(v)]==0){
            tvAlertSelection.setText("Seat number  "+String.valueOf(clSeats.indexOfChild(v)+1)+ " is occupied.");
        }else if (seats[clSeats.indexOfChild(v)]==1)
        {
            seatTemp[clSeats.indexOfChild(v)]=2;
            seatsClicked="";
            for(int i =0;i<seatTemp.length;i++)
            {
                if(seatTemp[i]==2){
                    seatsClicked += String.valueOf(i+1)+", ";
                }
            }
            tvAlertSelection.setText("Seat(s) number   "+seatsClicked+ "were selected to you.");
            seats[clSeats.indexOfChild(v)]=2;
            btnSeats[clSeats.indexOfChild(v)].setBackgroundResource(R.drawable.selectedseats);
        }else if(seats[clSeats.indexOfChild(v)]==2)
        {
            seatTemp[clSeats.indexOfChild(v)]=1;
            seatsClicked=String.valueOf(clSeats.indexOfChild(v)+1)+", ";
            tvAlertSelection.setText("Seat number   "+seatsClicked+ "was released for other costumers.");
            seats[clSeats.indexOfChild(v)]=1;
            btnSeats[clSeats.indexOfChild(v)].setBackgroundResource(R.drawable.freeseats);
        }

    }

    public void checkout(View v){

        int tickets = 0;
        for(int i = 0;i<seats.length; i++)
        {
            if(seats[i]==2){
                tickets++;
            }
        }
        if(tickets>0)
        {
            Intent pay = new Intent(this, Payment.class);
            pay.putExtra("seats",seats);
            pay.putExtra("movie", movieSelected);
            pay.putExtra("selectedDay", selectedDay);
            pay.putExtra("selectedTime", selectedTime);
            pay.putExtra("ticketsBought", tickets);
            pay.putExtra("sessionID", sessionID);
            pay.putExtra("roomID", roomID);
            startActivity(pay);
        }

    }


}
