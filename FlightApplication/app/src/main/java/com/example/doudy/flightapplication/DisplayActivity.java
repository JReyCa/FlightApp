package com.example.doudy.flightapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

public class DisplayActivity extends AppCompatActivity {

    private Bundle inputs;
    private ArrayList<Flight> flights = new ArrayList<>();
    private ArrayList<Flight> displayed = new ArrayList<>();

    private int[] containers = new int[] {R.id.container0, R.id.container1, R.id.container2, R.id.container3};
    private int[] fnums = new int[] {R.id.fnum0, R.id.fnum1, R.id.fnum2, R.id.fnum3};
    private int[] cities = new int[] {R.id.cities0, R.id.cities1, R.id.cities2, R.id.cities3};
    private int[] dates = new int[] {R.id.dates0, R.id.dates1, R.id.dates2, R.id.dates3};
    private int[] prices = new int[] {R.id.price0, R.id.price1, R.id.price2, R.id.price3};
    private int[] buttons = new int[] {R.id.button0, R.id.button1, R.id.button2, R.id.button3};

    private int selectedFlightIndex;
    private int selectedReturnFlightIndex;

    private boolean selectingReturn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);

        inputs = getIntent().getExtras();
        selectingReturn = inputs.getBoolean("roundtrip");

        populateFlightList();
        findRelevantFlights(false);
        populateDisplay();
    }

    public void selectFlight0(View view) {
        selectFlightForReal(0);
    }
    public void selectFlight1(View view) {
        selectFlightForReal(1);
    }
    public void selectFlight2(View view) {
        selectFlightForReal(2);
    }
    public void selectFlight3(View view) {
        selectFlightForReal(3);
    }

    private void selectFlightForReal(int index) {
        if (!selectingReturn) {

        }
    }

    private void proceedToPayment() {

    }

    // populate a list of dummy flight information
    private void populateFlightList() {
        // between Kelowna and Vancouver
        flights.add(new Flight("d84hf9", "Kelowna", "Vancouver",
                "12/05/2018 04:35", "12/05/2018 06:05", "1:30", "$200"));
        flights.add(new Flight("d24gh7", "Kelowna", "Vancouver",
                "12/05/2018 06:20", "12/05/2018 07:55", "1:35", "$250"));
        flights.add(new Flight("a23kl0", "Kelowna", "Vancouver",
                "12/05/2018 13:05", "12/05/2018 14:30", "1:25", "$220"));
        flights.add(new Flight("q53nk7", "Vancouver", "Kelowna",
                "12/05/2018 18:55", "12/05/2018 20:25", "1:30", "$892"));

        // between Phoenix and Austin
        flights.add(new Flight("b77mk0", "Phoenix", "Austin",
                "12/05/2018 08:45", "12/05/2018 11:00", "2:15", "$213"));
        flights.add(new Flight("s33cc1", "Phoenix", "Austin",
                "12/05/2018 10:00", "12/05/2018 12:10", "2:10", "$116"));
        flights.add(new Flight("e90hj4", "Phoenix", "Austin",
                "12/05/2018 12:25", "12/05/2018 14:45", "2:20", "$117"));
        flights.add(new Flight("r65hh9", "Austin", "Phoenix",
                "12/05/2018 16:00", "12/05/2018 18:15", "2:15", "$118"));
    }

    // compile a list of flights matching the origin, destination, and date of the user's input
    private void findRelevantFlights(boolean forReturn) {
        String origin = inputs.getString(forReturn? "destination" : "origin");
        String destination = inputs.getString(forReturn? "origin" : "destination");
        String date = inputs.getString(forReturn? "return" : "departure");

        displayed.clear();
        for (Flight flight : flights) {
            if (flight.getOrigin().compareTo(origin) == 0 &&
                    flight.getDestination().compareTo(destination) == 0 &&
                    flight.getDepartureDate().split(" ")[0].compareTo(date) == 0) {
                displayed.add(flight);
            }
        }
    }

    private void populateDisplay() {
        for (int counter = 0; counter < 4; counter++) {
            if (displayed.size() > counter) {
                TextView fnum = findViewById(fnums[counter]);
                fnum.setText("Flight#: " + displayed.get(counter).getFlightNumber());

                TextView city = findViewById(cities[counter]);
                city.setText(displayed.get(counter).getOrigin() + " - " + displayed.get(counter).getDestination());

                TextView date = findViewById(dates[counter]);
                date.setText(extractConciseDate(displayed.get(counter).getDepartureDate()) + " - "
                        + extractConciseDate(displayed.get(counter).getArrivalDate()));

                TextView price = findViewById(prices[counter]);
                price.setText(displayed.get(counter).getPrice());
            }
            else {
                findViewById(containers[counter]).setVisibility(View.INVISIBLE);
            }
        }
    }

    private String extractConciseDate(String date) {
        String[] sepBySpace = date.split(" ");
        String[] sepBySlash = sepBySpace[0].split("/");
        String rejoinedSlash = sepBySlash[0] + "/" + sepBySlash[1];
        String rejoinedSpace = rejoinedSlash + " " + sepBySpace[1];

        return rejoinedSpace;
    }

    public void onPicClick(View view) {
        Intent profilePage = new Intent(this, userProfile.class);
        startActivity(profilePage);
    }
}
