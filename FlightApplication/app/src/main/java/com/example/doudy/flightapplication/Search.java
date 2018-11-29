package com.example.doudy.flightapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Search extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
    }

    public void searchButton(View view) {
        Intent intent = new Intent(this, DisplayActivity.class);
        if (getInputs(intent)) {
            startActivity(intent);
        }
    }

    // validate inputs and add them to the intent
    private boolean getInputs(Intent intent) {
        String toastMessage = "please fill out:";

        // origin city input
        EditText originInput = findViewById(R.id.originInput);
        String originInputText = originInput.getText().toString();
        if (!validateString(originInputText)) {
            toastMessage += " origin city";
            retreatingToast(toastMessage);
            return false;
        }
        else {
            intent.putExtra("origin", originInputText);
        }

        // destination city input
        EditText destinationInput = findViewById(R.id.destinationInput);
        String destinationInputText = destinationInput.getText().toString();
        if (!validateString(destinationInputText)) {
            toastMessage += " destination city";
            retreatingToast(toastMessage);
            return false;
        }
        else {
            intent.putExtra("destination", destinationInputText);
        }

        // departure date input
        EditText departureInput = findViewById(R.id.departureInput);
        String departureInputText = departureInput.getText().toString();
        if (!validateString(departureInputText) || !departureInputText.matches("\\d{2}/\\d{2}/\\d{4}")) {
            toastMessage += " departure date (correctly)";
            retreatingToast(toastMessage);
            return false;
        }
        else {
            intent.putExtra("departure", departureInputText);
        }

        // roundtrip checkbox
        CheckBox roundtripInput = findViewById(R.id.roundTripCheckBox);
        boolean roundtripInputBool = roundtripInput.isChecked();
        intent.putExtra("roundtrip", roundtripInputBool);

        // return date input
        EditText returnInput = findViewById(R.id.returnInput);
        String returnInputText = returnInput.getText().toString();
        if ((!validateString(returnInputText) || !departureInputText.matches("\\d{2}/\\d{2}/\\d{4}")) && roundtripInputBool) {
            toastMessage += " return date (correctly)";
            retreatingToast(toastMessage);
            return false;
        }
        else {
            intent.putExtra("return", returnInputText);
        }

        // # of passengers
        EditText passengerInput = findViewById(R.id.passengerNumberInput);
        String passengerInputText = passengerInput.getText().toString();
        if (!validateString(passengerInputText)) {
            toastMessage += " passenger #";
            retreatingToast(toastMessage);
            return false;
        }
        else {
            intent.putExtra("passengers", passengerInputText);
        }

        return true;
    }

    private void retreatingToast(String message) {
        Toast toast = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT);
        toast.show();
    }

    // check whether string is null or empty
    private boolean validateString(String someString) {
        if (someString != null && !someString.equals(""))
            return true;

        return false;
    }
}
