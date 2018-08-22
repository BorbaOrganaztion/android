package com.example.games4all;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.drm.DrmStore;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.internal.NavigationMenu;
import android.support.design.internal.NavigationMenuItemView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;

import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    ViewPager viewPager = null;

    private Toolbar toolbar, mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference tableUser;
    private FirebaseUser user;
    private String userEmailLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupToolbarMenu();;
        setNavigationDrawerMenu();

        auth = FirebaseAuth.getInstance();
        Intent intent=getIntent();

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String userName = sharedPref.getString("userName","nothing to show");
        String userId = sharedPref.getString("userId","nothing to show");

        userEmailLogin = intent.getExtras().getString("emailLogin");

        database = FirebaseDatabase.getInstance();
        tableUser = database.getInstance().getReference("user");

        if(userEmailLogin == null){
            if(AccessToken.getCurrentAccessToken() == null)
            {
                goToLoginScreen();
            }
        }else{
            tableUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot gameSnapshot : dataSnapshot.getChildren()) {
                        User userRegistered = gameSnapshot.getValue(User.class);
                        if (userEmailLogin.equals(userRegistered.getUserEmail())) {
                            Toast.makeText(MainActivity.this, "Hello " + userRegistered.getUserName() +"", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    Toast.makeText(MainActivity.this, "Your email is " + user.getEmail() +"", Toast.LENGTH_SHORT).show();
                }
            }
        };



        viewPager = (ViewPager)findViewById(R.id.pager);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyAdapter adapter = new MyAdapter(fragmentManager);

        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.navigation_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void goToLoginScreen() {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void logout()
    {
        LoginManager.getInstance().logOut();
        goToLoginScreen();
    }



    private void setupToolbarMenu(){
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("Games4all");
    }

    private void setNavigationDrawerMenu(){
        navigationView = (NavigationView) findViewById(R.id.nagivationView);
        if (navigationView != null) {
            navigationView.setNavigationItemSelectedListener(this);
        }
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawerLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar,
                R.string.drawer_open,
                R.string.drawer_close);

        mDrawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

    }


    private void closeDrawer() {
        mDrawerLayout.closeDrawer(GravityCompat.START);
    }

    private void showDrawer(){
        mDrawerLayout.openDrawer(GravityCompat.START);
    }

    @Override
    public void onBackPressed() {
        if(mDrawerLayout.isDrawerOpen(GravityCompat.START)){
            closeDrawer();
        }else{
            super.onBackPressed();
        }

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String itemName = (String) item.getTitle();
        Toast.makeText(MainActivity.this, itemName + " Clicked", Toast.LENGTH_SHORT).show();

        closeDrawer();
        switch (item.getItemId())
        {
            case R.id.nav_account:
                startActivity(new Intent(MainActivity.this,AccountActivity.class));
                break;
            case R.id.nav_settings:
                Toast.makeText(this, "settings clicked", Toast.LENGTH_SHORT).show();
                break;
            case R.id.nav_sell:
                startActivity(new Intent(MainActivity.this,SellGame.class));
                break;
            case R.id.nav_logout:
                if (user != null) {
                    auth.signOut();
                    userEmailLogin="";
                }
                logout();
                break;
        }
        return true;
    }




    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

}
