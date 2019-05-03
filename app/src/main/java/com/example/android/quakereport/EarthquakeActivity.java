/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EarthquakeActivity extends AppCompatActivity {
    String jsonResponse = "" ;

    ArrayList<earthquakes> earthquakes = new ArrayList<>();
    public static final String LOG_TAG = EarthquakeActivity.class.getName();
    final String USGS_REQUEST_URL =
            "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&starttime=2019-04-01&endtime=2019-04-31&minmagnitude=3";

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        EarthQuakTask task = new EarthQuakTask();
        task.execute(USGS_REQUEST_URL);



    }
    private  void updateTheUI(){
        // Find a reference to the {@link ListView} in the layout
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new {@link ArrayAdapter} of earthquakes
        viewAdapter adapter = new viewAdapter(earthquakes , this);

        // Set the adapter on the {@link ListView}
        // so the list can be populated in the user interface
        earthquakeListView.setAdapter(adapter);
    }
    private URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException exception) {
            Log.e(LOG_TAG, "Error with creating URL", exception);
            return null;
        }
        return url;
    }
    private String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
            jsonResponse = readFromStream(inputStream);
        } catch (IOException e) {
            // TODO: Handle the exception
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
                inputStream.close();
            }
        }
        return jsonResponse;
    }
    private String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    private class EarthQuakTask extends AsyncTask<String, Void, Integer> {
        protected Integer doInBackground(String... urls) {

            URL url = createUrl(USGS_REQUEST_URL);
            try {
                jsonResponse = makeHttpRequest(url);
                JSONObject earthquakeJson = new JSONObject(jsonResponse);
                JSONArray featuresArray = earthquakeJson.getJSONArray("features");
                for (int i = 0; i < featuresArray.length(); i++) {
                    // JSONObject properties;
                    try {
                        JSONObject currentEarthquake = featuresArray.getJSONObject(i);
                        JSONObject properties = currentEarthquake.getJSONObject("properties");

                        //properties = featuresArray.getJSONObject(i);
                        double magnitude = properties.getDouble("mag");
                        String place = properties.getString("place");
                        long time = properties.getLong("time");
                        Date dateObject = new Date(time);
                        SimpleDateFormat dateFormatter = new SimpleDateFormat("MMM DD, yyyy");
                        String dateToDisplay = dateFormatter.format(dateObject);
                        SimpleDateFormat timeFormatter = new SimpleDateFormat("HH:mm");
                        String timeToDisplay = timeFormatter.format(dateObject);
                        earthquakes.add(new earthquakes(magnitude ,place ,dateToDisplay , timeToDisplay));
                        Log.i("earthquake" , place +" " +dateToDisplay+" " + timeToDisplay + " " +magnitude );
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return earthquakes.size() ;
        }



        protected void onPostExecute(Integer result) {
            Log.i("result" , "the size is = " + result);
            updateTheUI();
        }
    }


}
