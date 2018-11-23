package com.example.doudy.flightapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    ArrayList<String[]> data = new ArrayList<>();

    TextView name, pass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        name = findViewById(R.id.usernameField);
        pass = findViewById(R.id.passwordField);

        try {
            FileInputStream data_in = openFileInput("database.txt");
            InputStreamReader data_read = new InputStreamReader(data_in);
            BufferedReader data_reader = new BufferedReader(data_read);

            String line = data_reader.readLine();
            while(line != null) {
                String[] entries = line.split(";");
                for(int i = 0; i < entries.length; i++) {
                    String[] single_entry = entries[i].split(",");
                    data.add(single_entry);
                }
                line = data_reader.readLine();
            }
            data_read.close();

            FileInputStream saved_in = openFileInput("saved.txt");
            InputStreamReader saved_read = new InputStreamReader(saved_in);
            BufferedReader saved_reader = new BufferedReader(saved_read);

            line = saved_reader.readLine();
            String[] split = line.split(",");
            if(split.length == 1) {
                if(split[0].charAt(0) == 'n')
                    name.setText(split[0].substring(2));
                else if(split[0].charAt(0) == 'p')
                    pass.setText(split[0].substring(2));
            }
            else if(split.length == 2) {
                name.setText(split[0].substring(2));
                pass.setText(split[1].substring(2));
            }

            saved_reader.close();
        }
        catch (Exception e) {}
    }

    public void logBtn_Click(View view) {
        // Clicking the "log in" button should check the following pieces of information:
        // - Valid username (not empty and exists in the database)
        // - Valid password (not empty and matches the username)
        // - If the username should be remembered for next time
        // - If the password should be remembered for next time
        // - If the welcome page should be skipped automatically (keep last user logged in)
        //
        // Usernames and passwords will be saved in an internal text file called "database.txt" in
        // the following format:
        //  username,password,firstname,e-mail;

        CheckBox savename = findViewById(R.id.savenameBox);
        CheckBox savepass = findViewById(R.id.savepassBox);
        CheckBox savedets = findViewById(R.id.savedetailsBox);

        if(name.length() != 0 && pass.length() != 0) {
            String name_string = name.getText().toString();
            String pass_string = pass.getText().toString();

            boolean found = false;

            for(int i = 0; i < data.size(); i++) {
                if(data.get(i)[0].equals(name_string) && data.get(i)[1].equals(pass_string)) {
                    found = true;

                    try {
                        FileOutputStream saved_out = openFileOutput("saved.txt", MODE_PRIVATE);
                        OutputStreamWriter saved_write = new OutputStreamWriter(saved_out);

                        String write_str = "";
                        if(savename.isChecked())
                            write_str += "n:" + name_string + ",";
                        if(savepass.isChecked())
                            write_str += "p:" + pass_string + ",";
                        if(savedets.isChecked())
                            write_str = "n:" + name_string + ",p:" + pass_string + ",auto";

                        if(!write_str.equals(""))
                            saved_write.write(write_str);

                        saved_write.close();
                    }
                    catch (Exception e) {}

                    break;
                }
            }

            if(found) {
                Toast success = Toast.makeText(getApplicationContext(), "Login successful", Toast.LENGTH_SHORT);
                success.show();

                // Go to some other activity here
            }
            else {
                Toast error = Toast.makeText(getApplicationContext(), "Information is incorrect. Try again.", Toast.LENGTH_SHORT);
                error.show();
            }
        }
        else {
            Toast error = Toast.makeText(getApplicationContext(), "Username or password is incorrect. Try again.", Toast.LENGTH_SHORT);
            error.show();
        }

    }

    public void cancelBtn_Click(View view) {
        finish();
    }
}
