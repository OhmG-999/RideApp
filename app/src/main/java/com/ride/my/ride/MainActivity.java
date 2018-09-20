package com.ride.my.ride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createRide;
    private Toolbar menuToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRide = findViewById(R.id.propose_ride_btn);
        menuToolbar = findViewById(R.id.menuToolbar);
        setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle("Menu");


        createRide.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                sendToCreateRide();
            }
        });



    }

    private void sendToCreateRide() {

        Intent mainIntent = new Intent(MainActivity.this, CreateRide.class);
        startActivity(mainIntent);
        finish();
    }
}
