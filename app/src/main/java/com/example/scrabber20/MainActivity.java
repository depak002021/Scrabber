package com.example.scrabber20;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private boolean doubleBackPressed = false;

    @Override
    public void onBackPressed() {
        if (doubleBackPressed) {
            super.onBackPressed();
        } else {
            doubleBackPressed = true;
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    doubleBackPressed = false;
                }
            }, 2000); // Reset the flag after 2 seconds
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView logo = findViewById(R.id.scrabber_logo);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeInAnimation);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                finish();
            }
        }, 2000); // change the delay time as needed

    }
}