package com.example.weatherpredictapplication2023;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
public class SecondActivity extends AppCompatActivity {
    private String cityForecast;
    private int temperature;
    private String sunriseTime;
    private String dayTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second_activity);

        if( savedInstanceState != null ){
            cityForecast = savedInstanceState.getString("City_Forecast");
            temperature = savedInstanceState.getInt("Temperature");
            sunriseTime = savedInstanceState.getString("Sunrise_Time");
            dayTime = savedInstanceState.getString("Day_Time");
        }

        Intent intent = getIntent();
        String cityForecast = intent.getStringExtra("City_Forecast");

        int temperature = intent.getIntExtra("Temperature",0);
        String sunriseTime = intent.getStringExtra("Sunrise_Time");
        String dayTime = intent.getStringExtra("Day_Time");

        TextView forecastHeaderTextView = findViewById(R.id.forecastHeadertextView);
        if (cityForecast != null) {
            forecastHeaderTextView.setText(cityForecast);
        } else {
            forecastHeaderTextView.setText(R.string.LOCATION_NOT_KNOW);
        }

        TextView temperatureTextView = findViewById(R.id.TemperaturetextView);
        if (temperature != 0){
            temperatureTextView.setText("Temperature:\n" + temperature);
        } else {
            temperatureTextView.setText(R.string.NO_VALUE);
        }

        TextView SunriseTextView = findViewById(R.id.SunrisetextView);
        if (sunriseTime != null){
            SunriseTextView.setText("Sunrise Time: \n" + sunriseTime );
        } else {
            SunriseTextView.setText(R.string.ERROR_OCCUR);
        }

        TextView DayTimeTextView = findViewById(R.id.DayTimetextView);
        if (dayTime != null){
            DayTimeTextView.setText("Daylight Time: \n" + dayTime );
        } else {
            DayTimeTextView.setText(R.string.Oops);
        }
    }
    public void openMainActivity(View view) {
        Intent openMain = new Intent(this, MainActivity.class);
        startActivity( openMain );
    }

    protected  void onSaveInstanceState(@NonNull Bundle outState){
        super.onSaveInstanceState(outState);
        outState.putString("City_Forecast", cityForecast);
        outState.putInt("Temperature", temperature);
        outState.putString("Sunrise_Time", sunriseTime);
        outState.putString("Day_Time", dayTime);
    }
}
