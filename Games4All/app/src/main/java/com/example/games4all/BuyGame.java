package com.example.games4all;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BuyGame extends AppCompatActivity {

    private Toolbar toolbar;

    private FirebaseUser user;
    private FirebaseAuth auth;
    private String userEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_game);

        auth = FirebaseAuth.getInstance();

        toolbar = (Toolbar)findViewById(R.id.actionBarNew);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Buy Game");


    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_game_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                startActivity(new Intent(BuyGame.this,MainActivity.class));
                break;
            case R.id.nav_account:
                startActivity(new Intent(BuyGame.this,AccountActivity.class));
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
        Intent intent = new Intent(BuyGame.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

}
