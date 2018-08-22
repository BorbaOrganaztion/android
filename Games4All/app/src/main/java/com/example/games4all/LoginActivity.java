package com.example.games4all;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telecom.Call;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    private CallbackManager callbackManager;
    private static final String EMAIL = "email";
    private static final String PROFILE = "public_profile";

    private LoginButton loginButton;
    Button loginButtonEmail, loginButtonSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        callbackManager = CallbackManager.Factory.create();

       // FacebookSdk.sdkInitialize(getApplicationContext());
        //AppEventsLogger.activateApp(this);

        loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButtonEmail = (Button) findViewById(R.id.login_button_email);
        loginButtonSignUp = (Button) findViewById(R.id.login_button_signup);

        loginButton.setReadPermissions(Arrays.asList(EMAIL,PROFILE));
        // If you are using in a fragment, call loginButton.setFragment(this);

        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                goToMainScreen();
                // App code

                GraphRequest graphRequest = GraphRequest.newMeRequest(loginResult.getAccessToken(),
                        new GraphRequest.GraphJSONObjectCallback() {
                            @Override
                            public void onCompleted(JSONObject object, GraphResponse response) {
                                displayUserInfo(object);

                            }
                        }
                );
                Bundle parameters = new Bundle();
                parameters.putString("fields","first_name,last_name,email,id,picture");
                graphRequest.setParameters(parameters);
                graphRequest.executeAsync();


            }

            @Override
            public void onCancel() {
                Toast.makeText(LoginActivity.this, "Your login was cancelled, Please try again",
                        Toast.LENGTH_SHORT).show();
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Toast.makeText(LoginActivity.this, "There was an error while trying to login"
                        , Toast.LENGTH_SHORT).show();
                // App code
            }
        });

        loginButtonEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, LoginActivityEmail.class);
                startActivity(intent);
            }
        });

        loginButtonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }




    /**
     * Display the user login information
     * @param object a JSONobject that contains the login details  of the user
     */
    public void displayUserInfo(JSONObject object){
        String first_name, email, id,url;
        first_name = "";
        email = "";
        id = "";
        url ="";

        try{
            first_name = object.getString("first_name");
            email = object.getString("email");
            id = object.getString("id");
            url = object.getString("picture");

            storeInfoSharedPref(first_name,id);

        }catch(JSONException e)
        {
            e.printStackTrace();
        }

    }

public void storeInfoSharedPref(String name,String id){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        //get the editor object
        SharedPreferences.Editor editor = sharedPref.edit();
        //remove already existing values
        editor.remove("userName");
        editor.remove("userId");


        //add new result string to shared preference
        editor.putString("userName",name);
        editor.putString("userId",id);


        //comit needs to be issued to save the preferences
        editor.commit();
    }



    public void goToMainScreen(){
        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        //intent.putExtra("userName", name)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
        Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
