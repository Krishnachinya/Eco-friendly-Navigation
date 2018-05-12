package com.krishnchinya.hackdfw;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;

public class AddRide extends AppCompatActivity {

    EditText toride,fromride;
    Button addride;

    String urllink = "http://ec2-54-202-171-166.us-west-2.compute.amazonaws.com:5001/hackdfw/addtrip/source/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ride);

        addride = (Button) findViewById(R.id.AddRide);

        toride = (EditText) findViewById(R.id.toride);
        fromride = (EditText) findViewById(R.id.fromride);

        addride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    AsyncTask<String, Void, String> task = new AsyncTask<String, Void, String>() {

                        @Override
                        protected String doInBackground(String[] params) {
                            try {
                                URL url = new URL(urllink + params[0] + "/destination/" + params[1] + "/date/" + new Date().getTime() + "/userid/John");
                                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                                urlConnection.connect();
                                InputStream inputStream = urlConnection.getInputStream();
                                inputStream.read();
                                OutputStream output = urlConnection.getOutputStream();

                                output.flush();
                                output.close();
                                //return null;
                            } catch (Exception ex) {
                                int a=0;
                            }
                            return null;
                        }
                    };

                task.execute(fromride.getText().toString(),toride.getText().toString());

                finish();

            }
        });
    }
}