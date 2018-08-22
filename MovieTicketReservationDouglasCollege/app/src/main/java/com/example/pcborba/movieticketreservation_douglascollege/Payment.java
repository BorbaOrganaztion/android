package com.example.pcborba.movieticketreservation_douglascollege;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Payment extends AppCompatActivity {

    int[]seats;
    int tickets;
    int movieSelected;
    TextView tvSeats, tvTickets, tvSession;
    ImageView ivMovie;
    String selectedDay, selectedTime, roomID;
    EditText txtAdults;
    EditText txtKids;
    EditText txtSenior;
    String sessionID;
    String seatsClicked ="";

    TextView tvPriceCalc;

    MyDB db;
    SQLiteDatabase wdb;
    SQLiteDatabase rdb;

    int qtSenior=0;
    int qtKids = 0;
    int qtAdults=0;
    int qtDiscount=0;
    String strTotalPrice;
    String strPartialPrice;
    String strGst="";
    String strPst="";

    final double GST_TAX = 0.05;
    final double PST_TAX = 0.07;

    double priceSenior = 0;
    double priceAdults = 0;
    double priceKids = 0;
    double totalPrice = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);

        db = new MyDB(this);

        tvSeats = (TextView) findViewById(R.id.tvSeats);
        tvTickets = (TextView) findViewById(R.id.tvTickets);
        ivMovie = (ImageView) findViewById(R.id.ivMovie);
        tvSession = (TextView)findViewById(R.id.tvSession);

        txtKids = (EditText) findViewById(R.id.etChild);
        txtAdults = (EditText) findViewById(R.id.etAdult);
        txtSenior = (EditText) findViewById(R.id.etSenior);

        tvPriceCalc = (TextView) findViewById(R.id.tvPriceCalc);

        Bundle bundle = getIntent().getExtras();
        this.tickets= bundle.getInt("ticketsBought");
        this.seats= bundle.getIntArray("seats");
        this.movieSelected = bundle.getInt("movie");
        this.selectedDay = bundle.getString("selectedDay");
        this.selectedTime = bundle.getString("selectedTime");
        this.sessionID = bundle.getString("sessionID");
        this.roomID = bundle.getString("roomID");

        tvTickets.setText(String.valueOf(tickets));
        tvSession.setText(selectedDay+ " "+ selectedTime);

        for(int i = 0; i<seats.length;i++){

            //tvSeats.append(String.valueOf(seats[i]));
                if(seats[i]==2){
                    seatsClicked += String.valueOf(i+1)+" - ";
                }

        }
        tvSeats.setText(seatsClicked.substring(0, seatsClicked.length() - 2));

        switch (movieSelected) {
            case 0:
                ivMovie.setImageResource(R.drawable.wonder);
                break;
            case 1:
                ivMovie.setImageResource(R.drawable.despicableme);
                break;
            case 2:
                ivMovie.setImageResource(R.drawable.justiceleage);
                break;
            case 3:
                ivMovie.setImageResource(R.drawable.geostorm);
                break;
            case 4:
                ivMovie.setImageResource(R.drawable.bladerunner2049);
                break;
        }

        tvPriceCalc.setVisibility(View.INVISIBLE);

        txtAdults.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

              if (!s.equals("")) {
                  calcPrice();
              }
            }
        });

        txtKids.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}

            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {

                if (!s.equals("")) {
                    calcPrice();
                }
            }
        });

        txtSenior.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {}
            public void beforeTextChanged(CharSequence s, int start,
                                          int count, int after) {
            }
            public void onTextChanged(CharSequence s, int start,
                                      int before, int count) {
                if (!s.equals("")) {
                    calcPrice();
                }
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

            case R.id.auto:
                Intent auto = new Intent(this, AutoloadScheduleAndDB.class);
                this.startActivity(auto);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void calcPrice() {
        try{
            qtSenior = Integer.parseInt(txtSenior.getText().toString());
        }catch(Exception exception){
            qtSenior = 0;
        }

        try{
            qtKids = Integer.parseInt(txtKids.getText().toString());
        }catch(Exception exception){
            qtKids = 0;
        }

        try{
            qtAdults = Integer.parseInt(txtAdults.getText().toString());
        }catch(Exception exception){
            qtAdults = 0;
        }

        qtDiscount = qtSenior + qtAdults + qtKids;

        priceSenior = 0;
        priceAdults = 0;
        priceKids = 0;
        totalPrice = 0;

        priceAdults = qtAdults * 10;
        priceKids = qtKids * 5;
        priceSenior = qtSenior * 5;

        //tvTotalPrice.setText(String.format("$%.2f",(price*tickets)+(price*pst*tickets)+(price*gst*tickets)));

        totalPrice = priceAdults+priceKids+priceSenior;
        strPartialPrice = String.valueOf(totalPrice);
        totalPrice = totalPrice+(totalPrice * PST_TAX) + (totalPrice * GST_TAX);
        strTotalPrice = String.valueOf(totalPrice);
        strPst = String.valueOf((totalPrice * PST_TAX));
        strGst = String.valueOf((totalPrice * GST_TAX));

        tvPriceCalc.setText(String.format("Total cost of the tickets: $%.2f",totalPrice));
        tvPriceCalc.setVisibility(View.VISIBLE);

        if (qtDiscount<1){
            tvPriceCalc.setVisibility(View.INVISIBLE);
        }

    }


    public void payment(View v){
        wdb = db.getWritableDatabase();
        ContentValues values = new ContentValues();
        long newRowId;

        String[] seatNumber={"9","9","9","9","9","9"};
        int index = 0;
        for(int y=0;y<seats.length;y++) {
            if (seats[y]!=1) {
                seatNumber[index]=String.valueOf(y);
                index++;
            }
        }

        String strDiscount_type = "";

        for(int x=0;x<tickets;x++) {
            if (qtDiscount == tickets) {
                try{
                    //"CREATE TABLE TICKET (id integer primary key, sessionID integer, price text, seat_number integer, paymentDate text, receiptCode text, roomCode text)";
                    Date date= Calendar.getInstance().getTime();

                    values.clear();
                    values.put("sessionID", sessionID);
                    values.put("price", strTotalPrice);
                    values.put("seat_number", seatNumber[x]);
                    values.put("paymentDate", date.toString());
                    String receiptNumber = sessionID+" "+selectedDay+" "+selectedTime+" "+seatsClicked;
                    values.put("receiptCode", receiptNumber);
                    values.put("roomID", roomID);

                    newRowId = wdb.insert("TICKET", null, values);
                    Thread.sleep(200);
                    if(newRowId==-1){
                        Toast.makeText(this, "Error during the paying process contact Douglas Cinemas for help!", Toast.LENGTH_LONG).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(this, "Your payment was succesfully processed!", Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception exception){
                    Log.e("insert",exception.getMessage());
                }

            }
            else{
                Toast.makeText(this, "The total amount of tickets type must be equal or less than tickets bought", Toast.LENGTH_LONG).show();
                return;
            }
        }

        String tempSeats = "";
        String newStatus = "";

        for(int z=0;z<seats.length;z++) {

            if ( seats[z] == 2){
                newStatus = "0";
            }else {
                newStatus = String.valueOf(seats[z]);
            }
            tempSeats = tempSeats + newStatus + ",";
        }

        String finalSeats = tempSeats.substring(0,tempSeats.length()-1);

        values.clear();
        values.put("seats", finalSeats);
        String selection = "id" + " = ?";
        String[] selectionArgs = {sessionID};

      try{
          long upRowId = wdb.update(
                  "MOVIE_SESSION",
                  values,
                  selection,
                  selectionArgs);

      }catch(Exception exception) {
          Log.e("update",exception.getMessage());
      }


      Intent rec = new Intent(this, Receipt.class);
        rec.putExtra("seats", seats);
        rec.putExtra("seatsClicked",seatsClicked);
        rec.putExtra("movie", movieSelected);
        rec.putExtra("selectedDay", selectedDay);
        rec.putExtra("selectedTime", selectedTime);
        rec.putExtra("ticketsBought", tickets);
        rec.putExtra("sessionID", sessionID);


        rec.putExtra("strTotalPrice",strTotalPrice);
        rec.putExtra("strPartialPrice",strPartialPrice);
        rec.putExtra("strGst",strGst);
        rec.putExtra("strPst",strPst);
        rec.putExtra("roomCode", roomID);

        startActivity(rec);


    }


}
