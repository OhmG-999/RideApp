package com.ride.my.ride;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    // Declare private variables that will be used for each UI elements
    private TextView regEmailField;
    private TextView regPasswordField;
    private TextView regNameField;
    private Button registerBtn;
    private Button regLoginBtn;
    private ProgressBar regProgress;

    // Declare variables to hold the content of the email, password and confirm password fields
    private String email;
    private String pwd;
    private String name;
    private String currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*
            When onCreate is invoked, it create a connection to the FirebaseAuth instance
        */
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        regNameField = findViewById(R.id.reg_name_txt);
        regEmailField = findViewById(R.id.reg_email_text);
        regPasswordField = findViewById(R.id.reg_pwd_txt);
        registerBtn = findViewById(R.id.reg_btn);
        regLoginBtn = findViewById(R.id.reg_login_btn);
        regProgress = findViewById(R.id.reg_progress_bar);

        /*
            This event will create a new user if an email address and two matching password are
            provided and also redirect you to the main page. If the email address is not of the correct
            format or both password do not match, an error message will display
         */
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                setName(regNameField.getText().toString().trim());
                setEmail(regEmailField.getText().toString().trim());
                setPwd(regPasswordField.getText().toString().trim());

                if(!TextUtils.isEmpty(getName()) && !TextUtils.isEmpty(getEmail()) && !TextUtils.isEmpty(getPwd())){

                    regProgress.setVisibility(View.VISIBLE);

                    mAuth.createUserWithEmailAndPassword(getEmail(), getPwd()).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if(task.isSuccessful()){

                                sendToMain();
                            }
                            else{

                                Toast.makeText(RegisterActivity.this, "The Password and Confirm Password fields don't match", Toast.LENGTH_LONG).show();
                            }

                            regProgress.setVisibility(View.INVISIBLE);

                        }
                    });

                }

            }
        });

        regLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendToLogin();

            }
        });
    }

    /*
        When onStart is executed, the app will verify if the user is already logged in and the main
        screen
     */
    @Override
    protected void onStart() {
        super.onStart();

    }

    private void sendToMain() {

        setCurrentUser(mAuth.getInstance().getCurrentUser().getUid());

        HashMap<String, String> dataMap = new HashMap<>();
        dataMap.put("Name", getName());
        dataMap.put("Email", getEmail());

        mDatabase.child(getCurrentUser()).setValue(dataMap);

        Intent payIntent = new Intent(RegisterActivity.this, PaymentActivity.class);
        startActivity(payIntent);
        finish();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(String currentUser) {
        this.currentUser = currentUser;
    }

}
