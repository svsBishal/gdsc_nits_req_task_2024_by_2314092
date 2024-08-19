package com.example.guessmynumber;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.logo);
        TextView company_logo = findViewById(R.id.company);

        Animation company_anim = AnimationUtils.loadAnimation(SplashScreen.this, R.anim.company_logo);
        company_logo.setAnimation(company_anim);
        Animation logo_anim = AnimationUtils.loadAnimation(SplashScreen.this,R.anim.logo);
        logo.setAnimation(logo_anim);


        // Delaying for 3 seconds before starting MainActivity
        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent iUserDetail = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(iUserDetail);
                finish();
            }
        }, 2500);


    }
}