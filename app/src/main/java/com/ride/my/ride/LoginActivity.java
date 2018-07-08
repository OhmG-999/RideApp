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

public class LoginActivity extends AppCompatActivity {

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    // Declare private variables that will be used for each UI elements
    private TextView logEmailField;
    private TextView logPasswordField;
    private Button loginBtn;
    private Button loginRegBtn;
    private ProgressBar loginProgress;

    // Declare variables to hold the content of the email and password fields
    private String email;
    private String pwd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /*
            When onCreate is invoked, it create a connection to the FirebaseAuth instance
        */
        mAuth = FirebaseAuth.getInstance();

        logEmailField = findViewById(R.id.reg_email_text);
        logPasswordField = findViewById(R.id.reg_pwd_txt);
        loginBtn = findViewById(R.id.login_btn);
        loginRegBtn = findViewById(R.id.reg_login_btn);
        loginProgress = findViewById(R.id.login_progress_bar);

        /*
            This event will login an existing user if both email address and a matching password are
            provided and also redirect you to the main page. If the email address is not of the correct
            format or email and password do not match, an error message will display
         */
        loginBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                email = logEmailField.getText().toString();
                pwd = logPasswordField.getText().toString();

                if (!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd)) {

                    loginProgress.setVisibility(View.VISIBLE);

                    mAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {

                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            if (task.isSuccessful()) {

                                sendToMain();
                            } else {

                                Toast.makeText(LoginActivity.this, "The Email and/or Password are incorrect", Toast.LENGTH_LONG).show();
                            }

                            loginProgress.setVisibility(View.INVISIBLE);

                        }
                    });
                }
            }
        });

        loginRegBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                sendToRegister();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private void sendToMain() {

        Intent mainIntent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void sendToRegister() {

        Intent regIntent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(regIntent);
        finish();
    }
}