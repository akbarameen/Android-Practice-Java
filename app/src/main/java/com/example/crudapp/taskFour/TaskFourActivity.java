package com.example.crudapp.taskFour;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.example.crudapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class TaskFourActivity extends AppCompatActivity {
    ImageView fetchButton;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_four);

        fetchButton = findViewById(R.id.imgBtnSync);
        // Inside onCreate or a similar method
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("channel_id", "Channel Name", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

        fetchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new FetchDataTask().execute();
            }
        });
    }

    private class FetchDataTask extends AsyncTask<Void, Integer, String> {
        private NotificationManager notificationManager;
        private NotificationCompat.Builder notificationBuilder;
        private final int NOTIFICATION_ID = 1;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            // Set up the notification
            notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationBuilder = new NotificationCompat.Builder(TaskFourActivity.this, "channel_id")
                    .setContentTitle("Fetching Data")
                    .setContentText("Data is being fetched and stored...")
                    .setSmallIcon(R.drawable.ic_baseline_edit_24)
                    .setProgress(100, 0, true)
                    .setOngoing(true);

            // Show the notification
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }
        @Override
        protected String doInBackground(Void... voids) {
            try {
                final String apiUrl = "http://sms1.logicslabs.com/temp/testfill";
                URL url = new URL(apiUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder data = new StringBuilder();

                while ((line = bufferedReader.readLine()) != null) {
                    data.append(line);
                }

                return data.toString();

            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }


        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);

            // Update the progress
            notificationBuilder.setProgress(100, values[0], false);

            if (notificationManager != null) {
                notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
            } else {
                Log.e("Notification", "NotificationManager is null");
            }
//            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());
        }

        @Override
        protected void onPostExecute(String data) {
            super.onPostExecute(data);
            // Update the progress to complete
            notificationBuilder.setContentText("Data fetched and stored successfully")
                    .setProgress(100, 0, false)
                    .setOngoing(false);
            notificationManager.notify(NOTIFICATION_ID, notificationBuilder.build());


            if (data != null) {
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    JSONArray dataArray = jsonObject.getJSONArray("Data");

                    // Loop through the data array
                    for (int i = 0; i < dataArray.length(); i++) {
                        JSONObject item = dataArray.getJSONObject(i);

                        int id = item.getInt("id");
                        String name = item.getString("name");
                        String fname = item.getString("fname");
                        String gender = item.getString("gender");

                        Log.e("Msg", "name is " + name);
                        // Add data to database here
                    }

                    // Notify user about completion (e.g., using a Toast)

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                Log.e("Msg", "Error");
                // Handle error (e.g., show an error message)
            }
        }
    }

}