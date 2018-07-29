package com.ride.my.ride;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private Button splashEmailBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        splashEmailBtn = findViewById(R.id.splash_onboarding_email_btn);

        splashEmailBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent onboardingIntent = new Intent(SplashScreen.this, RegisterActivity.class);
                startActivity(onboardingIntent);
                finish();
            }
        });
    }

}
