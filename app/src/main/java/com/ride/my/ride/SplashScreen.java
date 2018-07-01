package com.ride.my.ride;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    // Declare an instance of FirebaseAuth and FirebaseUser
    private FirebaseAuth mAuth;
    //private FirebaseUser currentUser;

    // Declare a constant representing the display time for the login page in milliseconds
    private static int SPLASH_TIME_OUT = 3000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*
            When onCreate is invoked, it create a connection to the Firebase instances for both Auth and User.
        */
        mAuth = FirebaseAuth.getInstance();

        /*
            The Handler create a queue, check if the user is already logged in, send the user to the
            appropriate page and ultimately times out
         */
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                FirebaseUser currentUser = mAuth.getCurrentUser();

                if(currentUser != null){

                    sendToMain();

                }
                else{

                    sendToRegister();

                }
            }
        }, SPLASH_TIME_OUT);

    }
    /*
    @Override    //Only to force the user to be logout for testing purposes
    protected void onDestroy() {
        super.onDestroy();

        mAuth.signOut();
        sendToRegister();
    }*/

    /*
            this method redirect the user to the Main page
        */
    private void sendToMain(){

        Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    /*
        this method redirect the user to the Register page
    */
    private void sendToRegister(){

        Intent regIntent = new Intent(SplashScreen.this, RegisterActivity.class);
        startActivity(regIntent);
        finish();
    }

}
