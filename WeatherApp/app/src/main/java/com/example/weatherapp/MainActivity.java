package com.example.weatherapp;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    private EditText editTextLocation;
    private TextView textViewResult;
    private Button buttonFetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextLocation = findViewById(R.id.editTextLocation);
        textViewResult = findViewById(R.id.textViewResult);
        buttonFetch = findViewById(R.id.buttonFetch);

        buttonFetch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String location = editTextLocation.getText().toString();
                if (!location.isEmpty()) {
                    new FetchWeatherTask().execute(location);
                } else {
                    Toast.makeText(MainActivity.this, "Enter a Location", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private class FetchWeatherTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String location = params[0];
            String apiKey = "ad521eacd81225a853b5e95c4360d252"; // Replace with your actual API key
            String urlString = "https://api.openweathermap.org/data/2.5/weather?q=" + location + "&appid=" + apiKey + "&units=metric";

            StringBuilder result = new StringBuilder();
            try {
                URL url = new URL(urlString);
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.connect();

                InputStream inputStream = urlConnection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    result.append(line).append("\n");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result.toString();
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            try {
                JSONObject jsonObject = new JSONObject(result);

                // Location details
                JSONObject coord = jsonObject.getJSONObject("coord");
                double lat = coord.getDouble("lat");
                double lon = coord.getDouble("lon");

                JSONObject sys = jsonObject.getJSONObject("sys");
                String country = sys.getString("country");
                long sunriseTimestamp = sys.getLong("sunrise");
                long sunsetTimestamp = sys.getLong("sunset");

                String cityName = jsonObject.getString("name");

                // Format sunrise and sunset times
                String sunrise = formatUnixTime(sunriseTimestamp);
                String sunset = formatUnixTime(sunsetTimestamp);

                // Weather details
                JSONArray weatherArray = jsonObject.getJSONArray("weather");
                JSONObject weather = weatherArray.getJSONObject(0);
                String description = weather.getString("description");

                JSONObject main = jsonObject.getJSONObject("main");
                double temp = main.getDouble("temp");
                double feelsLike = main.getDouble("feels_like");
                double tempMin = main.getDouble("temp_min");
                double tempMax = main.getDouble("temp_max");
                int pressure = main.getInt("pressure");
                int humidity = main.getInt("humidity");

                JSONObject wind = jsonObject.getJSONObject("wind");
                double windSpeed = wind.getDouble("speed");

                StringBuilder weatherInfo = new StringBuilder();
                weatherInfo.append("City: ").append(cityName).append("\n");
                weatherInfo.append("Country: ").append(country).append("\n");
                weatherInfo.append("Coordinates: ").append("Lat: ").append(lat).append(", Lon: ").append(lon).append("\n");
                weatherInfo.append("Sunrise: ").append(sunrise).append("\n");
                weatherInfo.append("Sunset: ").append(sunset).append("\n");
                weatherInfo.append("Weather: ").append(description).append("\n");
                weatherInfo.append("Temperature: ").append(temp).append(" °C\n");
                weatherInfo.append("Feels Like: ").append(feelsLike).append(" °C\n");
                weatherInfo.append("Temperature Max: ").append(tempMax).append(" °C\n");
                weatherInfo.append("Temperature Min: ").append(tempMin).append(" °C\n");
                weatherInfo.append("Pressure: ").append(pressure).append(" hPa\n");
                weatherInfo.append("Humidity: ").append(humidity).append("%\n");
                weatherInfo.append("Wind Speed: ").append(windSpeed).append(" m/s\n");

                textViewResult.setText(weatherInfo.toString());
            } catch (Exception e) {
                e.printStackTrace();
                textViewResult.setText("Error fetching weather data.");
            }
        }

        private String formatUnixTime(long timestamp) {
            Date date = new Date(timestamp * 1000);
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(date);
        }
    }
}
