/**
 *
 * Class with the splash screen containing our logo that appears during 3 seconds
 *
 */
package com.example.pcborba.movieticketreservation_douglascollege;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class Logo extends AppCompatActivity {

    private ImageView ivLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);

        ivLogo = (ImageView) findViewById(R.id.ivLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ivLogo.setImageResource(R.drawable.logo);
                Intent session = new Intent(Logo.this, MainActivity.class);
                startActivity(session);
                finish();
            }
        },3000);

    }
}
