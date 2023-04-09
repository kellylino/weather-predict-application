package com.example.weatherpredictapplication2023;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONException;
import org.json.JSONObject;
public class MainActivity extends AppCompatActivity {
    private RequestQueue queue;
    private String city = "Tampere";
    private String weather = "Click to refresh";
    private String temperature = "3 m/s";
    private String speed = "0 C";
    private int imageResource = R.drawable.weatherimage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        queue = Volley.newRequestQueue(this);

        if(savedInstanceState != null) {
            city = savedInstanceState.getString("name");
            weather = savedInstanceState.getString("weather");
            temperature = savedInstanceState.getString("temp", "");
            speed = savedInstanceState.getString("wind", "");
            imageResource = savedInstanceState.getInt("imageResource", R.drawable.weatherimage);
        }

        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(imageResource);

        TextView citytextView = findViewById(R.id.citytextView);
        citytextView.setText(city);

        TextView weathertextView = findViewById(R.id.weathertextView);
        weathertextView.setText(weather);

        TextView temperaturetextView = findViewById(R.id.temperaturetextView);
        temperaturetextView.setText(temperature);

        TextView speedtextView = findViewById(R.id.speedtextView);
        speedtextView.setText(speed);
    }
    public void fetchWeatherData(View view){
        String url = "https://api.openweathermap.org/data/2.5/weather?q=tampere&units=metric&appid=6c433438776b5be4ac86001dc88de74d";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    Log.d("WEATHER_APP", response);
                    parseJsonAndUpdateUI(response);
                }, error -> {
                    Log.d("WEATHER_APP", error.toString());
                });
        queue.add(stringRequest);
    }
    private void parseJsonAndUpdateUI(String response) {
        try {
            JSONObject weatherResponse  = new JSONObject(response);
            city = weatherResponse.getString("name");
            weather = weatherResponse.getJSONArray("weather").getJSONObject(0).getString("description");
            temperature = weatherResponse.getJSONObject("main").getString("temp") + "C";
            speed = weatherResponse.getJSONObject("wind").getString("speed") + " m/s";

            TextView citytextView = findViewById(R.id.citytextView);
            citytextView.setText(city);

            TextView weathertextView = findViewById(R.id.weathertextView);
            weathertextView.setText(weather);

            TextView temperaturetextView = findViewById(R.id.temperaturetextView);
            temperaturetextView.setText(temperature);

            TextView speedtextView = findViewById(R.id.speedtextView);
            speedtextView.setText(speed);

            if(weather.contains("snow")){
                imageResource = R.drawable.lightsnow;
            } else if (weather.contains("clear")) {
                imageResource = R.drawable.clearsky;
            } else if(weather.contains("clouds")){
                imageResource = R.drawable.overcastclouds;
            } else {
                imageResource = R.drawable.weatherimage;
            }

            ImageView imageView = findViewById(R.id.imageView);
            imageView.setImageResource(imageResource);

        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
    }

    protected  void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("name", city);
        outState.putString("weather", weather);
        outState.putString("temp", temperature);
        outState.putString("wind", speed);
        outState.putInt("imageResource", imageResource);
    }
    public void morePredict(View view) {
        Uri webpage = Uri.parse("https://www.foreca.fi");
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);
        try {
            startActivity(intent);
        }
        catch (ActivityNotFoundException e){

        }
    }
    public void secondActivity(View view) {
        Intent openSecondActivity = new Intent(this, SecondActivity.class);
        openSecondActivity.putExtra("City_Forecast", "Tampere Forecast");
        openSecondActivity.putExtra("Temperature",1);
        openSecondActivity.putExtra("Sunrise_Time", "7:15AM");
        openSecondActivity.putExtra("Day_Time", "over 10 hours recently");

        startActivity( openSecondActivity );
    }



    @Override
    protected void onResume() {
        super.onResume();
        // Restore the state of the activity when it is resumed
        SharedPreferences preferences = getSharedPreferences("Prefs", MODE_PRIVATE);
        city = preferences.getString("cityName", "Tampere");
        weather = preferences.getString("weather", "Click to refresh");
        temperature = preferences.getString("temperature", "20 C");
        speed = preferences.getString("speed", "3 m/s");
        imageResource = preferences.getInt("imageResource", imageResource);



        // Update the UI with the restored state
        ImageView imageView = findViewById(R.id.imageView);
        imageView.setImageResource(imageResource);

        TextView citytextView = findViewById(R.id.citytextView);
        citytextView.setText(city);

        TextView weathertextView = findViewById(R.id.weathertextView);
        weathertextView.setText(weather);

        TextView temperaturetextView = findViewById(R.id.temperaturetextView);
//        temperaturetextView.setText("" + String.format("%.1fC", temperature));
        temperaturetextView.setText(temperature);

        TextView speedtextView = findViewById(R.id.speedtextView);
//        speedtextView.setText("" + String.format("%.1f m/s", speed));
        speedtextView.setText(speed);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Store the state of the activity when it is paused
        SharedPreferences.Editor editor = getSharedPreferences("Prefs", MODE_PRIVATE).edit();
        editor.putString("cityName", city);
        editor.putString("weather", weather);
        editor.putString("temperature", temperature);
        editor.putString("speed", speed);
        editor.putInt("imageResource", imageResource);
        editor.apply();
    }
}