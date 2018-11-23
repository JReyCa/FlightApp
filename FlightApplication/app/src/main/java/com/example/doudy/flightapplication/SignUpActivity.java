package com.example.doudy.flightapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    ArrayList<String[]> data = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

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
        }
        catch (Exception e) {}
    }

    public void signupBtn2_Click(View view) {
        // User information is saved in an internal storage file called "database.txt", which is
        // accessed by the login page. The user must fill out all of the fields in order to
        // officially sign up for the application.
        //
        // Username criteria are as follows:
        // - No special characters
        // - No spaces
        //
        // Password criteria are as follows:
        // - No special characters
        // - No spaces

        TextView user = findViewById(R.id.userBox);
        TextView pass = findViewById(R.id.passBox);
        TextView name = findViewById(R.id.nameBox);
        TextView mail = findViewById(R.id.mailBox);

        if(user.length() != 0 && pass.length() != 0 && name.length() != 0 && mail.length() != 0) {
            String username = user.getText().toString();
            String password = pass.getText().toString();
            String name_str = name.getText().toString();
            String mail_str = mail.getText().toString();

            boolean invalid_user = HasSpecialCharacters(username);
            boolean invalid_pass = HasSpecialCharacters(password);
            boolean invalid_email = !IsEmail(mail.getText().toString());

            if(invalid_user || invalid_pass) {
                Toast error = Toast.makeText(getApplicationContext(), "Username or password may not contain special characters.", Toast.LENGTH_SHORT);
                error.show();
            }
            else {
                if(invalid_email) {
                    Toast error = Toast.makeText(getApplicationContext(), "E-mail is not valid.", Toast.LENGTH_SHORT);
                    error.show();
                }
                else {
                    if(WriteToFile(username, password, name_str, mail_str)) {
                        Toast success = Toast.makeText(getApplicationContext(), "Sign up successful.", Toast.LENGTH_SHORT);
                        success.show();
                        finish();
                    }
                    else {
                        Toast fail = Toast.makeText(getApplicationContext(), "That username already exists.", Toast.LENGTH_SHORT);
                        fail.show();
                    }
                }
            }
        }
        else {
            Toast error = Toast.makeText(getApplicationContext(), "Please fill in all fields.", Toast.LENGTH_SHORT);
            error.show();
        }
    }

    public void cancelBtn2_Click(View view) {
        finish();
    }

    private boolean HasSpecialCharacters(String str) {
        for(int i = 0; i < str.length(); i++) {
            if(!Character.isLetterOrDigit(str.charAt(i)))
                return true;
        }
        return false;
    }

    private boolean IsEmail(String str) {
        return str.contains("@");
    }

    private boolean WriteToFile(String username, String password, String name, String email) {

        for(int i = 0; i < data.size(); i++) {
            if(data.get(i)[0].equals(username)) {
                return false;
            }
        }

        try {
            FileOutputStream data_out = openFileOutput("database.txt", MODE_PRIVATE | MODE_APPEND);
            OutputStreamWriter data_write = new OutputStreamWriter(data_out);

            String write_str = username + "," + password + "," + name + "," + email + ";";
            data_write.write(write_str);

            data_write.close();
            return true;
        }
        catch (Exception e) {}
        return false;
    }
}
