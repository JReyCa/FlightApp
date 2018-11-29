package com.example.doudy.flightapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class user_personal_info extends AppCompatActivity {

    private EditText userName;
    private EditText firstName;
    private EditText lastName;
    private EditText email;
    private EditText phoneNumber;
    private RadioGroup genderGroup;
    private RadioButton radioButton;
    private FileInputStream fisPersonal;
    private InputStreamReader isr;
    private BufferedReader br;
    private ArrayList<String[]> output;
    private static final String file = "personalInfo.txt";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_personal_info);

        userName = findViewById(R.id.editText5);
        firstName = findViewById(R.id.editText6);
        lastName = findViewById(R.id.editText7);
        email = findViewById(R.id.editText9);
        phoneNumber = findViewById(R.id.editText10);
        genderGroup = findViewById(R.id.radioGroup);
        String gender = "";

        int i = -1;
        output = new ArrayList<>();
        String line;
        try {
            fisPersonal = openFileInput("personalInfo.txt");
            isr = new InputStreamReader(fisPersonal);
            br = new BufferedReader(isr);
            if ((line = br.readLine()) != null) {
                i++;
                output.add(line.split(","));
                String[] temp = output.get(i);
                userName.setText(temp[0]);
                gender = temp[2];
                String fullName = temp[1];
                String[] name = fullName.split(" ");
                firstName.setText(name[0]);
                lastName.setText(name[1]);
                email.setText(temp[3]);
                phoneNumber.setText(temp[4]);
            }
        }catch(IOException e){
            e.printStackTrace();
        }

        if(gender.equals("Male")){
            radioButton = findViewById(R.id.radioButton3);
            radioButton.setChecked(true);
        }else if(gender.equals("Female")){
            radioButton = findViewById(R.id.radioButton2);
            radioButton.setChecked(true);
        }else if(gender.equals("Other")){
            radioButton = findViewById(R.id.radioButton);
            radioButton.setChecked(true);
        }

    }

    public void radioClick(View view){
        Log.d("test", "radioClick");
        radioButton = findViewById(genderGroup.getCheckedRadioButtonId());
    }

    public void confirm(View view) throws IOException {
        Log.d("test", "confirm");

        String userNameString = userName.getText().toString();
        String firstNameString = firstName.getText().toString();
        String lastNameString = lastName.getText().toString();
        String emailString = email.getText().toString();
        String phoneNumberString = phoneNumber.getText().toString();
        String gender = "";
        String input;
        String fullName;

        boolean validUserName = false;
        boolean validFirstName = false;
        boolean validLastName = false;
        boolean validEmail = false;
        boolean validPhoneNum = false;
        boolean validGender = false;

        if(radioButton != null){
            gender = radioButton.getText().toString();
            validGender = true;
        }else{
            Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_LONG).show();
        }

        if(!userNameString.equals("")){
            validUserName = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid user name", Toast.LENGTH_LONG).show();
        }

        if(!firstNameString.equals("")){
            validFirstName = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid first name", Toast.LENGTH_LONG).show();
        }

        if(!lastNameString.equals("")){
            validLastName = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid last name", Toast.LENGTH_LONG).show();
        }

        if(!emailString.equals("") && emailString.contains("@") && (emailString.contains(".com") || emailString.contains(".ca"))){
            validEmail = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid email", Toast.LENGTH_LONG).show();
        }

        if(!phoneNumberString.equals("") && (phoneNumberString.length() == 10 || phoneNumberString.length() == 11 || phoneNumberString.length() == 12)){
            validPhoneNum = true;
        }else{
            Toast.makeText(getApplicationContext(), "Invalid Phone Number", Toast.LENGTH_LONG).show();
        }

        if(validUserName && validEmail && validFirstName && validLastName && validPhoneNum && validGender){
            fullName = firstNameString + " " + lastNameString;
            input = userNameString + "," + fullName + "," + gender + "," + emailString + "," + phoneNumberString;
            FileOutputStream outputStream = openFileOutput(file, MODE_PRIVATE);
            outputStream.write(input.getBytes());
            outputStream.close();
            finish();
        }

    }

}

