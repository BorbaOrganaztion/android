package com.example.games4all;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.login.LoginManager;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class SellGame extends AppCompatActivity {

    private FirebaseDatabase database;
    private DatabaseReference tableGame;

    private ListView listViewGame;
    private List<Game> listGame;

    private Spinner spinnerConsole;
    private Toolbar toolbar;

    private TextView addInfoTitle;
    private EditText gameName;
    private EditText gamePrice;
    private EditText furtherInfo;
    private ImageView gameImage;
    private String imageUrl,  gameId ="";

    private StorageReference mStorage;
    private static final int GALLERY_INTENT = 2;
    private ProgressDialog mProgressDialog;

    private FirebaseUser user;
    private FirebaseAuth auth;
    private String userEmailLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_game);
        addInfoTitle = (TextView)findViewById(R.id.tv_addInfoTitle);

        database = FirebaseDatabase.getInstance();
        tableGame = database.getReference("game");

        auth = FirebaseAuth.getInstance();

        listGame = new ArrayList<>();

        //instantiate and sets the  toolbar as the new action bar
        toolbar = (Toolbar)findViewById(R.id.actionBarNew);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Game");

        //initialize spinner and sets a prompt to it
        spinnerConsole = (Spinner)findViewById(R.id.consoleSpiner);
        spinnerConsole.setPrompt("     --Select a Console--");

        //Firebase storage initialization with process dialog and the imageVew
        mStorage = FirebaseStorage.getInstance().getReference();
        mProgressDialog = new ProgressDialog(this);
        gameImage = (ImageView)findViewById(R.id.gameImage);
        //setting onclick listener for image view to access the gallery
        gameImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent,GALLERY_INTENT);
            }
        });

    }
    /*
    * Method that executes the uploading and downloading of the image
    *
    * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //getting the user Id from the shared preferences to use it for storing the image
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        String userID = sp.getString("userId","noId");

        if(requestCode == GALLERY_INTENT && resultCode == RESULT_OK)
        {
            mProgressDialog.setMessage("Uploading Image...");
            mProgressDialog.show();
            Uri uri = data.getData();
            //stablishing the file path where the image will be stored
            StorageReference filePath = mStorage.child("Photos").child(userID).child(uri.getLastPathSegment());

            filePath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    mProgressDialog.dismiss();
                    Toast.makeText(SellGame.this, "Upload Done", Toast.LENGTH_SHORT).show();

                    Uri downloadUri = taskSnapshot.getDownloadUrl();
                    imageUrl = downloadUri.toString();
                    Picasso.get().load(downloadUri).fit().into(gameImage);
                }
            });
        }
    }

    public void AddGameButton(View view)
    {
        PcTab.internalCall = false;
        Play3Tab.internalCall = false;
        Play4TAb.internalCall = false;
        WiiTab.internalCall = false;
        XboxTab.internalCall = false;


        gameName = (EditText)findViewById(R.id.et_game_name);
        gamePrice = (EditText)findViewById(R.id.et_price);
        furtherInfo = (EditText)findViewById(R.id.et_aditionalInfo);
        String gameConsole = spinnerConsole.getSelectedItem().toString();
        //Toast.makeText(this, ""+imageUrl, Toast.LENGTH_SHORT).show();

        String gameName1 = gameName.getText().toString();
        String gamePrice1 = gamePrice.getText().toString();
        String furtherInfo1 = furtherInfo.getText().toString();



        if(!TextUtils.isEmpty(gameName.toString())){
            gameId = tableGame.push().getKey();
            Game game = new Game(gameName1,gamePrice1,furtherInfo1, gameConsole, imageUrl);
            tableGame.child(gameId).child("Title").setValue(gameName1);
            tableGame.child(gameId).child("Price").setValue(gamePrice1);
            tableGame.child(gameId).child("Description").setValue(furtherInfo1);
            tableGame.child(gameId).child("Console").setValue(gameConsole);
            tableGame.child(gameId).child("ImgUrl").setValue(imageUrl);

            Toast.makeText(this,"Game saved on the database",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(SellGame.this,MainActivity.class));
        }else{
            Toast.makeText(this,"Enter a title to save the game",Toast.LENGTH_SHORT).show();
        }

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
                startActivity(new Intent(SellGame.this,MainActivity.class));
                break;
            case R.id.nav_account:
                startActivity(new Intent(SellGame.this,AccountActivity.class));
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

    private void goToLoginScreen() {
        Intent intent = new Intent(SellGame.this,LoginActivity.class);
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
