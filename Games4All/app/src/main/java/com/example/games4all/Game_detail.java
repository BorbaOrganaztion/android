package com.example.games4all;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Game_detail extends AppCompatActivity {

    private Toolbar toolbar;
    private TextView tvTitle,tvDescription, tvPrice, tvConsole;
    private ImageView img;
    private Button buttonBuy;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String userEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_detail);

        toolbar = (Toolbar)findViewById(R.id.actionBarNew);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Game Detail");

        auth = FirebaseAuth.getInstance();

        tvTitle=(TextView)findViewById(R.id.game_txtitle);
        tvDescription=(TextView)findViewById(R.id.game_txtdescription);
        tvPrice=(TextView)findViewById(R.id.game_txtPrice);
        tvConsole=(TextView)findViewById(R.id.game_txtConsole);
        img=(ImageView)findViewById(R.id.game_txtthumbnail);

        Intent intent=getIntent();
        String Title=intent.getExtras().getString("Title");
        String price =intent.getExtras().getString("Price");
        String console =intent.getExtras().getString("Console");
        String descrption=intent.getExtras().getString("Description");
        String imgurllocal=intent.getExtras().getString("Thumbnail");

        tvTitle.setText(Title);
        tvDescription.setText(descrption);
        tvConsole.setText(console);
        tvPrice.setText(price);
        Picasso.get().load(imgurllocal).into(img);

        buttonBuy = (Button)findViewById(R.id.buttonBuy);
        buttonBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Game_detail.this, BuyGame.class);
                startActivity(intent);
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail_game_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                startActivity(new Intent(Game_detail.this,MainActivity.class));
                break;
            case R.id.nav_account:
                startActivity(new Intent(Game_detail.this,AccountActivity.class));
                break;
            case R.id.nav_sell:
                startActivity(new Intent(Game_detail.this,SellGame.class));
                break;
            case R.id.nav_logout:
                if (user != null) {
                    auth.signOut();
                    userEmailLogin="";
                }
                logout();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout()
    {
        LoginManager.getInstance().logOut();
        goToLoginScreen();
    }


    private void goToLoginScreen() {
        Intent intent = new Intent(Game_detail.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }


}
