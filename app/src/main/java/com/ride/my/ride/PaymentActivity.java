package com.ride.my.ride;

import android.app.Fragment;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.realexpayments.hpp.HPPError;
import com.realexpayments.hpp.HPPManager;
import com.realexpayments.hpp.HPPManagerListener;

import java.security.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Random;

import static java.lang.System.currentTimeMillis;

public class PaymentActivity extends AppCompatActivity implements HPPManagerListener <Map<String, String>>{


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

                HPPManager hppManager = new HPPManager();

                hppManager.setHppRequestProducerURL("http://192.168.0.94:8080/test/test.html");
                hppManager.setHppResponseConsumerURL("https://myserver.com/hppResponseConsumer");
                hppManager.setHppURL("https://pay.sandbox.realexpayments.com/pay");

                hppManager.setMerchantId("mikego");
                hppManager.setAccount("internet2");
                hppManager.setAmount("100");
                hppManager.setCurrency("EUR");


                Fragment hppManagerFragment = hppManager.newInstance();
                getFragmentManager()
                        .beginTransaction()
                        .add(R.id.hpp_web_view,hppManagerFragment)
                        .commit();
    }


    private void sendToMain() {

        Intent mainIntent = new Intent(PaymentActivity.this, MainActivity.class);
        startActivity(mainIntent);
        finish();
    }

    private void sendToRegister() {

        Intent regIntent = new Intent(PaymentActivity.this, RegisterActivity.class);
        startActivity(regIntent);
        finish();
    }

    @Override
    public void hppManagerCompletedWithResult(Map<String, String> stringStringMap) {

        Toast.makeText(PaymentActivity.this, "Your payment was successful " + stringStringMap, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hppManagerFailedWithError(HPPError error) {

        Toast.makeText(PaymentActivity.this, "Your payment was declined " + error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void hppManagerCancelled() {

        Toast.makeText(PaymentActivity.this, "You have cancelled the payment", Toast.LENGTH_LONG).show();
    }
}
