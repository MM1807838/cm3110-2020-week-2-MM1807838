package uk.ac.rgu.rguweather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import uk.ac.rgu.rguweather.data.ForecastRepository;
import uk.ac.rgu.rguweather.data.LocationForecast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, AdapterView.OnItemSelectedListener {

    private static final String TAG = "MyActivity";
    private int days_ahead = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Add on click listener to the get forecast button
        Button btnGetForecast = findViewById(R.id.btn_get_forecast);
        btnGetForecast.setOnClickListener(this);

        // Add on checked change listener to the favourite check box
        CheckBox cb_favourite = findViewById(R.id.cb_favourite);
        cb_favourite.setOnCheckedChangeListener(this);

        // Add on Item Selected listener to the favourite check box
        Spinner sp_days_ahead = findViewById(R.id.sp_days_ahead);
        sp_days_ahead.setOnItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_get_forecast){

            //get all relevant elements by id
            EditText et_location = findViewById(R.id.et_location);
            TextView tv_location_forecast = findViewById(R.id.tv_location_forecast);
            TextView tv_dates = findViewById(R.id.tv_dates);
            TextView tv_max_temp = findViewById(R.id.tv_max_temp);
            TextView tv_min_temp = findViewById(R.id.tv_min_temp);
            TextView tv_weather = findViewById(R.id.tv_weather);

            //get weather forecast
            ForecastRepository fr = ForecastRepository.getRepository(getApplicationContext());
            LocationForecast forecast = fr.getForecastFor(String.valueOf(et_location.getText()), days_ahead);

            //edit elements to match forecast
            tv_location_forecast.setText(et_location.getText() + getString(R.string.tv_location_forecast_2));
            tv_dates.setText(forecast.getDate());
            tv_max_temp.setText(forecast.getMaxTemp() + getString(R.string.tv_temp_units));
            tv_min_temp.setText(forecast.getMinTemp() + getString(R.string.tv_temp_units));
            tv_weather.setText(forecast.getWeather());

        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView.getId() == R.id.cb_favourite) {

            //get all relevant elements by id
            CheckBox cb_favourite = findViewById(R.id.cb_favourite);
            EditText et_location = findViewById(R.id.et_location);

            //add/remove location to/from favourites
            if (isChecked) {
                Log.d(TAG, "User has favourited the location " + et_location.getText());
            } else {
                Log.d(TAG, "User has removed " + et_location.getText() + " from their favourites");
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        days_ahead = position;
        Log.d(TAG, "spinner days ahead = " + String.valueOf(position));

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        //do nothing
    }
}