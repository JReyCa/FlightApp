package com.example.doudy.flightapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class user_payment_info extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner expYearSpinner;
    private Spinner expMonthSpinner;
    private Spinner countrySpinner;
    private Spinner citySpinner;
    private EditText nameOnCard;
    private EditText cardNum;
    private EditText ccv;
    private EditText billingAddress;
    private EditText postalCode;
    private TextView userName;
    private FileInputStream fis;
    private InputStreamReader isr;
    private BufferedReader br;
    private FileInputStream fisPersonal;
    private InputStreamReader isrPersonal;
    private BufferedReader brPersonal;
    private ArrayList<String[]> output;
    private ArrayList<String[]> outputPersonal;
    private int cityPos;
    private int i;
    private static final String file = "paymentInfo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment_info);

        nameOnCard = findViewById(R.id.editText);
        cardNum = findViewById(R.id.editText2);
        ccv = findViewById(R.id.editText3);
        billingAddress = findViewById(R.id.editText8);
        postalCode = findViewById(R.id.editText11);
        userName = findViewById(R.id.textView20);

        expMonthSpinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.expMonth, android.R.layout.simple_spinner_dropdown_item);
        expMonthSpinner.setAdapter(adapter);

        expYearSpinner = findViewById(R.id.spinner2);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(this, R.array.expYear, android.R.layout.simple_spinner_dropdown_item);
        expYearSpinner.setAdapter(adapter2);

        countrySpinner = findViewById(R.id.spinner3);
        ArrayAdapter<CharSequence> adapter3 = ArrayAdapter.createFromResource(this, R.array.Country, android.R.layout.simple_spinner_dropdown_item);
        countrySpinner.setAdapter(adapter3);
        countrySpinner.setOnItemSelectedListener(this);

        citySpinner = findViewById(R.id.spinner4);
        ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.cityDummy, android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(adapter4);

        try{
            fis = openFileInput("paymentInfo.txt");
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line;
            i = -1;
            output = new ArrayList<>();
            if ((line = br.readLine()) != null) {
                i++;
                output.add(line.split(","));
                String[] temp = output.get(i);
                nameOnCard.setText(temp[0]);
                billingAddress.setText(temp[3]);
                postalCode.setText(temp[4]);
                countrySpinner.setSelection(Integer.parseInt(temp[8]));
                this.cityPos = Integer.parseInt(temp[9]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        try {
            fisPersonal = openFileInput("personalInfo.txt");
            isrPersonal = new InputStreamReader(fisPersonal);
            brPersonal = new BufferedReader(isrPersonal);
            String line2;
            i = -1;
            outputPersonal = new ArrayList<>();
            if ((line2 = brPersonal.readLine()) != null) {
                i++;
                outputPersonal.add(line2.split(","));
                String[] temp2 = outputPersonal.get(i);
                userName.setText(temp2[0]);
            }

        }catch(IOException e){
            e.printStackTrace();
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        String countryString = countrySpinner.getSelectedItem().toString();
        if(countryString.equals("USA")){
            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.cityUSA, android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(adapter4);
            citySpinner.setSelection(cityPos);
        }else if(countryString.equals("Canada")){
            ArrayAdapter<CharSequence> adapter4 = ArrayAdapter.createFromResource(this, R.array.cityCanada, android.R.layout.simple_spinner_dropdown_item);
            citySpinner.setAdapter(adapter4);
            citySpinner.setSelection(cityPos);
        }
    }
    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    public void confirm2(View view) throws IOException {
        Log.d("test","confirm");

        String nameOnCardString = nameOnCard.getText().toString();
        String cardNumString = cardNum.getText().toString();
        String ccvString = ccv.getText().toString();
        String billingAddressString = billingAddress.getText().toString();
        String postalCodeString = postalCode.getText().toString();
        String expMonthString = expMonthSpinner.getSelectedItem().toString();
        String expYearString = expYearSpinner.getSelectedItem().toString();
        String countryString = countrySpinner.getSelectedItem().toString();
        String countryPos = "";
        String cityPos = "";
        String cityString = citySpinner.getSelectedItem().toString();
        String input;
        String expDate;

        boolean validName = false;
        boolean validCardNum = false;
        boolean validCCV = false;
        boolean validBillingAddress = false;
        boolean validPostalCode = false;
        boolean validexpDate = false;
        boolean validCounty = false;
        boolean validCity = false;

        if(!nameOnCardString.equals("")){
            validName = true;
        }else{
            Toast.makeText(getApplicationContext(), "Please enter the name printed on the credit card", Toast.LENGTH_LONG).show();
        }

        if(!cardNumString.equals("") && cardNumString.length() == 16){
            validCardNum = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid card number. Please do not include spaces or dashes.", Toast.LENGTH_LONG).show();
        }

        if(!ccvString.equals("") && ccvString.length() == 3){
            validCCV = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid CCV. The CCV are the 3 digits found on the back of your card.", Toast.LENGTH_LONG).show();
        }

        if(!billingAddressString.equals("")){
            validBillingAddress = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid billing address.", Toast.LENGTH_LONG).show();
        }

        if(countryString.equals("USA")){
            if(postalCodeString.length() == 5 || postalCodeString.length() == 6){
                validPostalCode = true;
            }else{
                Toast.makeText(getApplicationContext(), "Invalid ZIP code. Zip code should be 5 digits long.", Toast.LENGTH_LONG).show();
            }
        }else if(countryString.equals("Canada")){
            if(postalCodeString.length() == 6 || postalCodeString.length() == 7){
                validPostalCode = true;
            }else{
                Toast.makeText(getApplicationContext(), "Invalid Postal Code. Postal code should be 6 characters long.", Toast.LENGTH_LONG).show();
            }
        }else{
            if(!postalCodeString.equals("")){
                validPostalCode = true;
            }
        }

        if(!countryString.equals("Country")){
            validCounty = true;
            countryPos = Integer.toString(countrySpinner.getSelectedItemPosition());
        }else{
            Toast.makeText(getApplicationContext(), "Please select your country.", Toast.LENGTH_LONG).show();
        }

        if(!cityString.equals("City")){
            validCity = true;
            cityPos = Integer.toString(citySpinner.getSelectedItemPosition());
        }else{
            Toast.makeText(getApplicationContext(), "Please select your city.", Toast.LENGTH_LONG).show();
        }

        if(!expMonthString.equals("Month") && !expYearString.equals("Year")){
            validexpDate = true;
        }else{
            Toast.makeText(getApplicationContext(),"Invalid expiry date.",Toast.LENGTH_LONG).show();
        }

        if(validName && validCardNum && validCCV && validBillingAddress && validPostalCode && validCounty && validCity && validexpDate){
            expDate = expMonthString + " " +expYearString;
            input = nameOnCardString + "," + cardNumString + "," + ccvString + "," + billingAddressString + "," + postalCodeString + "," + expDate + "," + cityString + "," + countryString + "," + countryPos + "," + cityPos;
            FileOutputStream outputStream = openFileOutput(file, MODE_PRIVATE);
            outputStream.write(input.getBytes());
            outputStream.close();
            finish();
        }
    }
}

