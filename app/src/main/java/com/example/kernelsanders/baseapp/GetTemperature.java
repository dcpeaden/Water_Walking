package com.example.kernelsanders.baseapp;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetTemperature extends AsyncTask<String,Void,String> {


    @Override
    protected String doInBackground(String... weatherURL) {
        String result = "";
        URL url;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(weatherURL[0]);

            try {
                urlConnection = (HttpURLConnection) url.openConnection();
            }
            catch(IOException ioE)
            {
                ioE.printStackTrace();
            }

            try {
                InputStream in = urlConnection.getInputStream();

                InputStreamReader reader = new InputStreamReader(in);

                int data = reader.read();

                while(data != -1){

                    char current = (char) data;

                    result += current;

                    data = reader.read();
                }
            }
            catch(IOException inputStreamE){
                inputStreamE.printStackTrace();
            }

            return result;
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }



        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);

        try {
            JSONObject cityData = new JSONObject(result);

            JSONObject weather = new JSONObject(cityData.getString("main"));

            int temperature = (int) Double.parseDouble(weather.getString("temp"));

            MainActivity.temperature.setText(String.valueOf(temperature));

            String city = cityData.getString("name");
        }
        catch(JSONException weatherE)
        {
            weatherE.printStackTrace();
        }

    }
}