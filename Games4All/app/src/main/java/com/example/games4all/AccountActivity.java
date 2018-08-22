package com.example.games4all;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountActivity extends AppCompatActivity implements DisplayInfo.OnFragmentInteractionListener {
    CircleImageView profileImage;
    TextView nameTv;
    private Toolbar toolbar;
    private Button buttonSell;
    private  boolean displayFragment1 = true;
    TextView editTextView;
    private TextView myGamesTv;
    private FirebaseUser user;
    private FirebaseAuth auth;
    private String userEmailLogin;

    private int [] pcImages = {R.drawable.aoe,R.drawable.assassins,R.drawable.bat,
            R.drawable.callofjpg,R.drawable.crysis};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        auth = FirebaseAuth.getInstance();

        toolbar = (Toolbar)findViewById(R.id.actionBarNew);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Account");

        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sp.getString("userName","noName");
        String userID = sp.getString("userId","noId");

        nameTv = (TextView)findViewById(R.id.textView);
        myGamesTv = (TextView)findViewById(R.id.MyGames_tv);
        nameTv.setText("CSIS3375");
//


        displayInfoFragment();

        buttonSell = (Button) findViewById(R.id.sellBtn);
        buttonSell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AccountActivity.this, SellGame.class);
                startActivity(intent);
            }
        });

    }

    public void displayInfoFragment(){
        DisplayInfo displayInfo = new DisplayInfo();
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.info_fragment,displayInfo).addToBackStack(null).commit();
        displayFragment1 =true;

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.account_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.nav_home:
                startActivity(new Intent(AccountActivity.this,MainActivity.class));
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

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    private void goToLoginScreen() {
        Intent intent = new Intent(AccountActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void logout()
    {
        LoginManager.getInstance().logOut();
        goToLoginScreen();
    }


}
