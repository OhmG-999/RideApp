package com.ride.my.ride;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.List;

public class SearchRide extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private ImageButton searchCalendarBtn;
    private TextView searchedDate;
    private Button searchRideBtn;
    private RecyclerView mRecyclerView;

    private Context mContext;
    private RideAdapter mRideAdapter;
    private OnItemClickedListener onClicked;

    public interface OnItemClickedListener{

        void onItemClicked(Ride myRide);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_ride);

        mRecyclerView = findViewById(R.id.result_list);

        searchCalendarBtn = findViewById(R.id.search_calendar_btn);
        searchedDate = findViewById(R.id.searched_departure_date_txtview);
        searchRideBtn = findViewById(R.id.search_ride_btn);

        new FirebaseDatabaseHelper().readRides(new FirebaseDatabaseHelper.DataStatus() {
            @Override
            public void dataIsLoaded(List<Ride> rides, List<String> rideKeys, List<User> user, List<String> userKeys) {

                new SearchRide().setConfig(mRecyclerView, SearchRide.this, rides, rideKeys, user, userKeys);
            }

            @Override
            public void dataIsInserted() {

            }

            @Override
            public void dataIsUpdated() {

            }

            @Override
            public void dataIsDeleted() {

            }
        });

        searchCalendarBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                DialogFragment datePicker = new DatePickerFragment();
                datePicker.show(getSupportFragmentManager(), "Date Picker");
            }
        });

        searchRideBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {


            }
        });

        searchRideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent pickRide = new Intent(SearchRide.this, PickRideActivity.class);
                startActivity(pickRide);
                finish();

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

    public void setConfig(RecyclerView recyclerView, Context context, List<Ride> rides, List<String> keys, List<User> users, List<String> ukeys){

        mContext = context;
        mRideAdapter = new RideAdapter(rides, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mRideAdapter);
    }

    class RideItemView extends RecyclerView.ViewHolder{

        private TextView myRideUserName;
        private TextView myRideJourney;
        private TextView myRideDate;
        private TextView myNumberOfSeats;

        public String theKey;

        public RideItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext).inflate(R.layout.activity_ride_list_layout, parent, false));

            myRideUserName = itemView.findViewById(R.id.list_driver_id_txt);
            myRideJourney = itemView.findViewById(R.id.list_user_ride_journey_txt);
            myRideDate = itemView.findViewById(R.id.list_user_ride_date_txt);
            myNumberOfSeats = itemView.findViewById(R.id.list_user_ride_seats_txt);
        }

        public void bind(Ride ride, String myRideKey){

            myRideJourney.setText(ride.getStarting_Point() + " - " + ride.getFinishing_Point());
            myRideDate.setText(ride.getDate());
            myNumberOfSeats.setText(ride.getNumber_seat());
            this.theKey = myRideKey;
        }
    }

    class RideAdapter extends RecyclerView.Adapter<SearchRide.RideItemView>{

        private List<Ride> mRideList;
        private List<String> mRideKey;

        public RideAdapter(List<Ride> mRideList, List<String> mRideKey) {
            this.mRideList = mRideList;
            this.mRideKey = mRideKey;
        }

        @NonNull
        @Override
        public SearchRide.RideItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            return new RideItemView(parent);

        }

        @Override
        public void onBindViewHolder(@NonNull SearchRide.RideItemView holder, final int position) {

            holder.bind(mRideList.get(position), mRideKey.get(position));

            /* Onclick listener not working for the recyclingview
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    onClicked.onItemClicked(position);
                    Intent pickRide = new Intent(SearchRide.this, PickRideActivity.class);
                    startActivity(pickRide);
                    finish();
                }
            });*/

        }

        @Override
        public int getItemCount() {
            return mRideList.size();
        }
    }
}
