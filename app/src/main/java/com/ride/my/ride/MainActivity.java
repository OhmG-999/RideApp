package com.ride.my.ride;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button createRide;
    private Button searchRide;
    private Toolbar menuToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createRide = findViewById(R.id.propose_ride_btn);
        searchRide = findViewById(R.id.search_btn);
        menuToolbar = findViewById(R.id.menuToolbar);
        setSupportActionBar(menuToolbar);
        getSupportActionBar().setTitle("Menu");

        createRide.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                sendToCreateRide();
            }
        });

        searchRide.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                sendToSearchRide();
            }
        });
    }

    private void sendToCreateRide() {

        Intent mainIntent = new Intent(MainActivity.this, CreateRide.class);
        startActivity(mainIntent);
        finish();
    }

    private void sendToSearchRide(){

        Intent searchIntent = new Intent(MainActivity.this, SearchRide.class);
        startActivity(searchIntent);
        finish();
    }
}
