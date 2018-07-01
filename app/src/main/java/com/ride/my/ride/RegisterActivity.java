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
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {

    // Declare an instance of FirebaseAuth
    private FirebaseAuth mAuth;

    // Declare private variables that will be used for each UI elements
    private TextView regEmailField;
    private TextView regPasswordField;
    private TextView regPasswordConfField;
    private Button registerBtn;
    private Button regLoginBtn;
    private ProgressBar regProgress;

    // Declare variables to hold the content of the email, password and confirm password fields
    private String email;
    private String pwd;
    private String pwdConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        /*
            When onCreate is invoked, it create a connection to the FirebaseAuth instance
        */
        mAuth = FirebaseAuth.getInstance();

        regEmailField = findViewById(R.id.reg_email_txt);
        regPasswordField = findViewById(R.id.reg_pwd_txt);
        regPasswordConfField = findViewById(R.id.reg_pwd_conf_txt);
        registerBtn = findViewById(R.id.reg_btn);
        regLoginBtn = findViewById(R.id.reg_login_btn);
        regProgress = findViewById(R.id.reg_progress_bar);

        /*
            This event will create a new user if an email and address and two matching password are
            provided and also redirect you to the main page. If the email address is not of the correct
            format or both password do not match, an error message will display
         */
        registerBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                email = regEmailField.getText().toString();
                pwd = regPasswordField.getText().toString();
                pwdConfirm = regPasswordConfField.getText().toString();

                if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(pwdConfirm)){

                    regProgress.setVisibility(View.VISIBLE);

                    if(pwd.equals(pwdConfirm)){

                        mAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(new OnCompleteListener<AuthResult>(){

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

        FirebaseUser currentUser = mAuth.getCurrentUser();

        if(currentUser != null) {

            sendToMain();

        }

    }

    private void sendToMain() {

        Intent mainIntent = new Intent(RegisterActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void sendToLogin() {

        Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

}
