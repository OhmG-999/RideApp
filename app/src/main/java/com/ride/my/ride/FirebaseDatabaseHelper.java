package com.ride.my.ride;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {

    private FirebaseDatabase mRideDatabase;
    private FirebaseDatabase mUserDatabase;
    private DatabaseReference mReferenceRides;
    private DatabaseReference mReferenceUsers;
    private List <Ride> rides = new ArrayList<>();
    private List <User> users = new ArrayList<>();
    private List<String> rideKeys = new ArrayList<>();
    private List<String> userKeys = new ArrayList<>();

    public interface DataStatus{

        void dataIsLoaded(List<Ride> rides, List<String> rideKeys, List<User> users, List<String> userKeys);
        void dataIsInserted();
        void dataIsUpdated();
        void dataIsDeleted();
    }

    public FirebaseDatabaseHelper() {

        mRideDatabase = FirebaseDatabase.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance();
        mReferenceRides = mRideDatabase.getReference("Rides");
        mReferenceUsers = mUserDatabase.getReference("Users");

    }

    public void readRides(final DataStatus dataStatus){


        mReferenceRides.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                rides.clear();

                for(DataSnapshot keyNode : dataSnapshot.getChildren()){

                    rideKeys.add(keyNode.getKey());
                    Ride ride = keyNode.getValue(Ride.class);

                    rides.add(ride);
                }

                dataStatus.dataIsLoaded(rides, rideKeys, users, userKeys);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        mReferenceUsers.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                users.clear();

                for(DataSnapshot userKeyNode : dataSnapshot.getChildren()){

                    userKeys.add(userKeyNode.getKey());
                    User user = userKeyNode.getValue(User.class);

                    users.add(user);
                }
                dataStatus.dataIsLoaded(rides, rideKeys, users, userKeys);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
