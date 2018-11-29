package com.example.doudy.flightapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class userProfile extends AppCompatActivity {

    private String line;
    private FileInputStream fisPersonal;
    private FileInputStream fisPayment;
    private InputStreamReader isr;
    private InputStreamReader isrPayment;
    private BufferedReader br;
    private BufferedReader brPayment;
    private ArrayList<String[]> output;
    private ArrayList<String[]> outputPayment;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        TextView userName = findViewById(R.id.textView3);
        TextView fullName = findViewById(R.id.textView8);
        TextView gender = findViewById(R.id.textView15);
        TextView email = findViewById(R.id.textView9);
        TextView phoneNumber = findViewById(R.id.textView7);
        TextView address = findViewById(R.id.textView13);
        TextView city = findViewById(R.id.textView23);
        TextView country = findViewById(R.id.textView24);
        TextView nameOnCard = findViewById(R.id.textView25);
        TextView cardType = findViewById(R.id.textView26);
        TextView cardNum = findViewById(R.id.textView16);
        TextView expDate = findViewById(R.id.textView18);
        TextView postalCode = findViewById(R.id.textView27);
        i = -1;
        output = new ArrayList<>();
        outputPayment = new ArrayList<>();

        try {
            fisPersonal = openFileInput("personalInfo.txt");
            isr = new InputStreamReader(fisPersonal);
            br = new BufferedReader(isr);
            if ((line = br.readLine()) != null) {
                i++;
                output.add(line.split(","));
                String[] temp = output.get(i);
                userName.setText(temp[0]);
                fullName.setText(temp[1]);
                gender.setText(temp[2]);
                email.setText(temp[3]);
                phoneNumber.setText(temp[4]);
            }else{
                Toast.makeText(getApplicationContext(), "No Data Available.", Toast.LENGTH_LONG).show();
            }

        }catch(IOException e){
            e.printStackTrace();
        }

        i = -1;
        try{
            fisPayment = openFileInput("paymentInfo.txt");
            isrPayment = new InputStreamReader(fisPayment);
            brPayment = new BufferedReader(isrPayment);
            if ((line = brPayment.readLine()) != null) {
                i++;
                outputPayment.add(line.split(","));
                String[] temp = outputPayment.get(i);
                nameOnCard.setText(temp[0]);

                String temp2 = temp[1];
                if(temp2.charAt(0) == '3') // AMEX
                    cardType.setText("AMEX " + "Ending With:");
                else if(temp2.charAt(0) == '4') // Visa
                    cardType.setText("VISA " + "Ending With:");
                else if(temp2.charAt(0) == '5') // Master Card
                    cardType.setText("MasterCard " + "Ending With:");
                else if(temp2.charAt(0) == '6') // Discover Card
                    cardType.setText("DiscoverCard " + "Ending With:");
                else
                    cardType.setText("CreditCard " + "Ending With");

                cardNum.setText(temp2.substring(temp2.length()-4));
                address.setText(temp[3]);
                postalCode.setText(temp[4]);
                expDate.setText(temp[5]);
                city.setText(temp[6]);
                country.setText(temp[7]);
            }else{
                Toast.makeText(getApplicationContext(), "No Payment Data Available.", Toast.LENGTH_LONG).show();
            }
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void personalInfoClick(View view){
        Log.d("test","writeButton");
        startActivity(new Intent(userProfile.this,user_personal_info.class));
    }


    public void paymentInfoClick(View view){
        Log.d("test","writeButton");
        startActivity(new Intent(userProfile.this,user_payment_info.class));
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        recreate();
    }
}
