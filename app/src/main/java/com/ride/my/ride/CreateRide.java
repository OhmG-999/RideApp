package com.ride.my.ride;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.UUID;

public class CreateRide extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private EditText startingPoint;
    private EditText finishingPoint;
    private EditText selectedDate;
    private ImageButton calendarBtn;
    private TextView passengerCounter;
    private Button increasePassenger;
    private Button decreasePassenger;
    private Button saveMyRide;
    private Ride ride;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String user;
    private int rideUniqueID;
    private final String TAG = "RideApp -->";
    private ProgressBar rideProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_ride);

        mAuth = FirebaseAuth.getInstance();

        rideProgress = findViewById(R.id.ride_progress_bar);
        startingPoint = findViewById(R.id.starting_point_txt);
        finishingPoint = findViewById(R.id.finishing_point_txt);
        selectedDate = findViewById(R.id.departure_date_txtview);
        calendarBtn = findViewById(R.id.calendar_btn);
        passengerCounter = findViewById(R.id.number_seat_txt);
        increasePassenger = findViewById(R.id.increase_seat_btn);
        decreasePassenger = findViewById(R.id.decrease_seat_btn);
        saveMyRide = findViewById(R.id.create_ride_btn);

        ride = new Ride();

        calendarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        passengerCounter.setText(ride.getNumberSeat());
        increasePassenger.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                increaseNumberSeats();
            }
        });

        decreasePassenger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                decreaseNumberSeats();
            }
        });

        saveMyRide.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                setUser(mAuth.getCurrentUser().getUid());

                ride.prepareRideStatement(getUser(),
                        startingPoint.getText().toString().trim(),
                        finishingPoint.getText().toString().trim(),
                        selectedDate.getText().toString().trim(),
                        passengerCounter.getText().toString().trim());

                if(!TextUtils.isEmpty(ride.getStartingPoint()) && !TextUtils.isEmpty(ride.getFinishingPoint())
                        && !TextUtils.isEmpty(ride.getSelectedDate()) && !TextUtils.isEmpty(ride.getNumberSeat())
                        && !TextUtils.isEmpty(ride.getTotalFare())){

                    rideProgress.setVisibility(View.VISIBLE);

                    try{

                        mDatabase = FirebaseDatabase.getInstance().getReference();
                        DatabaseReference newMDatabase = mDatabase.child(getUser()).child("Rides").push();
                        newMDatabase.setValue(ride.writeRideDatabase());

                        sendToMain();
                    }
                    catch (Exception e){

                        Log.e(TAG, "=--->", e);
                    }
                }
                else{
                    Log.i(TAG, "There nothing to add");
                }
            }
        });
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month);
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        String dateString = DateFormat.getDateInstance().format(calendar.getTime());
        selectedDate.setText(dateString);
    }

    protected void increaseNumberSeats(){

        int numberSeats = Integer.valueOf(ride.getNumberSeat());

        if(numberSeats < 4) {
            numberSeats++;
            String numberOfSeats = String.valueOf(numberSeats);
            ride.setNumberSeat(numberOfSeats);
            passengerCounter.setText(ride.getNumberSeat());
        }
    }

    protected void decreaseNumberSeats(){

        int numberSeats = Integer.valueOf(ride.getNumberSeat());

        if (numberSeats > 1){
            --numberSeats;
            String numberOfSeats = String.valueOf(numberSeats);
            ride.setNumberSeat(numberOfSeats);
            passengerCounter.setText(ride.getNumberSeat());
        }
    }

    protected void sendToMain(){

        Intent createRide = new Intent(CreateRide.this, MainActivity.class);
        startActivity(createRide);
        finish();
    }

    protected String getUser(){

        return this.user;
    }

    protected void setUser(String usr){

        this.user = usr;
    }

}
