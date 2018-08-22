/**
 * Class taht opnes payment activity so the user pays for the tickets
 */
package com.example.pcborba.movieticketreservation_douglascollege;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

public class Receipt extends AppCompatActivity {

    int[]seats;
    int tickets;
    double price = 10.00, pst = 0.07, gst=0.05;
    int movieSelected;
    TextView tvMovieName, tvRoom, tvSeats, tvTickets, tvSession, tvReceipt, tvPrice, tvPST, tvGST, tvTotalPrice, tvTicketsEmited;
    String []moviesName;
    String selectedDay, selectedTime,  sessionID,receiptNumber, roomCode;
    String seatsClicked ="";

    String strTotalPrice;
    String strPartialPrice;
    String strGst="";
    String strPst="";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receipt);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);

        moviesName=getResources().getStringArray(R.array.movieName);
        tvMovieName = (TextView)findViewById(R.id.tvMovie);
        tvReceipt = (TextView) findViewById(R.id.tvReceipt);
        tvRoom = (TextView) findViewById(R.id.tvRoom);
        tvSeats = (TextView) findViewById(R.id.tvSeats);
        tvTickets = (TextView) findViewById(R.id.tvTickets);
        tvSession =(TextView)findViewById(R.id.tvSession);
        tvTicketsEmited =(TextView)findViewById(R.id.tvTicketsEmited);
        tvPrice =(TextView)findViewById(R.id.tvPrice);
        tvPST =(TextView)findViewById(R.id.tvPst);
        tvGST =(TextView)findViewById(R.id.tvGst);
        tvTotalPrice =(TextView)findViewById(R.id.tvTotalPrice);


        Bundle bundle = getIntent().getExtras();
        this.tickets= bundle.getInt("ticketsBought");
        this.seats = bundle.getIntArray("seats");
        this.seatsClicked= bundle.getString("seatsClicked");
        this.movieSelected = bundle.getInt("movie");
        this.selectedDay = bundle.getString("selectedDay");
        this.selectedTime = bundle.getString("selectedTime");
        this.sessionID = bundle.getString("sessionID");

        strTotalPrice = bundle.getString("strTotalPrice");
        strPartialPrice = bundle.getString("strPartialPrice");
        strGst = bundle.getString("strGst");
        strPst = bundle.getString("strPst");
        roomCode = bundle.getString("roomCode");


        receiptNumber = sessionID+" "+selectedDay+" "+selectedTime+" "+seatsClicked;

        tvMovieName.setText(moviesName[movieSelected]);
        tvReceipt.append(receiptNumber);
        tvRoom.append(roomCode);
        tvSeats.setText(seatsClicked.substring(0, seatsClicked.length() - 2));
        tvTickets.setText(String.valueOf(tickets));
        tvSession.setText(selectedDay+ " - " +selectedTime);
        tvPrice.setText(String.format("$%.2f",Double.parseDouble(strPartialPrice)));
        tvGST.setText(String.format("$%.2f",Double.parseDouble(strGst)));
        tvPST.setText(String.format("$%.2f",Double.parseDouble(strPst)));
        tvTotalPrice.setText(String.format("$%.2f",Double.parseDouble(strTotalPrice)));


        for(int i = 0;i<seats.length;i++)
        {
            if(seats[i]==2){
                tvTicketsEmited.append("\n --------------------------------" +
                        "\n This is your ticket from receipt \" "+receiptNumber +
                        " \" and for the seat number "+String.valueOf(i+1)+
                        " \n --------------------------------");
            }
        }
        tvTicketsEmited.append("\n This receipt and tickets were send to you by e-mail.");

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

    public void sendMail(View v) {
        final ProgressDialog dialog = new ProgressDialog(Receipt.this);
        dialog.setTitle("Sending Email");
        dialog.setMessage("Please wait");
        dialog.show();
        Thread sender = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender("moviereservation@gmail.com", "movie123");
                    sender.sendMail("Movie Reservation",
                            "Ticket Number:" + receiptNumber + "\n" +
                            "Seat:" + tickets + "\n" +
                            "Total Price:" + strTotalPrice,
                            "moviereservation@gmail.com",
                            "moviereservation@gmail.com");
                    dialog.dismiss();
                } catch (Exception e) {
                    Log.e("ëmailLog", "Error: " + e.getMessage());
                }
            }
        });
        sender.start();
    }

}
