package com.ride.my.ride;

import android.app.DatePickerDialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.util.Calendar;

public class SearchRide extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private EditText searchStart;
    private EditText searchFinish;
    private ImageButton searchCalendarBtn;
    private TextView searchedDate;
    private Button searchRideBtn;
    private RecyclerView mResultList;
    private ImageView userProfileImg;
    private TextView userName;
    private TextView userRide;
    private TextView userRideDate;
    private FirebaseRecyclerAdapter fbRecyclerAdapter;
    private Query query;
    private FirebaseRecyclerOptions options;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private final String TAG = "RideApp -->";
    private ProgressBar rideProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ride);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference("Users");

        searchStart = findViewById(R.id.search_starting_point_txt);
        searchFinish = findViewById(R.id.search_finishing_point_txt);
        searchCalendarBtn = findViewById(R.id.search_calendar_btn);
        searchedDate = findViewById(R.id.searched_departure_date_txtview);
        searchRideBtn = findViewById(R.id.search_ride_btn);
        mResultList = findViewById(R.id.result_list);

        searchCalendarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        String myStart = searchStart.getText().toString().trim();
        String myFinish = searchFinish.getText().toString().trim();
        String myDate = searchedDate.getText().toString().trim();

        firebaseUserSearch(myStart, myFinish, myDate);


        mResultList.setAdapter(fbRecyclerAdapter);


        searchRideBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                String begin = searchStart.getText().toString();
                String end = searchFinish.getText().toString();
                String date = searchedDate.getText().toString();

                firebaseUserSearch(begin, end, date);
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
        searchedDate.setText(dateString);
    }


    private void firebaseUserSearch(String start, String finish, String date){

        mResultList.setHasFixedSize(true);
        mResultList.setLayoutManager(new LinearLayoutManager(this));

        Log.e(TAG, "in firebaseUserSearch");

        mDatabase = FirebaseDatabase.getInstance().getReference("Rides");
        query = mDatabase.orderByChild(mDatabase.getKey()).limitToLast(50);

        Log.e(TAG,"passed the query");

        options = new FirebaseRecyclerOptions.Builder<Ride>().setQuery(query, Ride.class).build();

        fbRecyclerAdapter = new FirebaseRecyclerAdapter<Ride, RidesViewHolder>(options) {

            @Override
            protected void onBindViewHolder(@NonNull RidesViewHolder holder, int position, @NonNull Ride model) {

                holder.setDetails(getApplicationContext(), model.getRideDriverID(), model.getStartingPoint(), model.getFinishingPoint(), model.getSelectedDate(), model.getNumberSeat());

            }

            @NonNull
            @Override
            public RidesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.activity_list_layout, parent, false);

                return new RidesViewHolder(view);
            }
        };
    }

    public class RidesViewHolder extends RecyclerView.ViewHolder{

        View mView;

        public RidesViewHolder(View itemView) {
            super(itemView);

            mView = itemView;
        }

        public void setDetails(Context ctx, String img, String name, String start, String finish, String date) {

            userProfileImg = mView.findViewById(R.id.list_user_profile_img);
            userName = mView.findViewById(R.id.list_username_txt);
            userRide = mView.findViewById(R.id.list_user_ride_txt);
            userRideDate = mView.findViewById(R.id.list_user_ride_date_txt);


            Glide.with(ctx).load(img).into(userProfileImg);
            userName.setText(name);
            userRide.setText(start+" - "+finish);
            userRideDate.setText(date);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        fbRecyclerAdapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        fbRecyclerAdapter.stopListening();
    }
}
