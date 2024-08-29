package com.example.hydroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import java.util.Locale;

public class HomePage extends AppCompatActivity implements View.OnClickListener {
    TextView ph_value,
            ec_value,
            water_temp_value,
            temp_value,
            humidity_value,
            light_value,
            co2_value,
            baro_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home_page);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        CardView ph_btn = findViewById(R.id.ph_btn);
        CardView ec_btn = findViewById(R.id.ec_btn);
        CardView water_temp_btn = findViewById(R.id.water_temp_btn);
        CardView temp_btn = findViewById(R.id.temp_btn);
        CardView humidity_btn = findViewById(R.id.humidity_btn);
        CardView light_btn = findViewById(R.id.light_btn);
        CardView co2_btn = findViewById(R.id.co2_btn);
        CardView baro_btn = findViewById(R.id.baro_btn);

        ph_value = findViewById(R.id.ph_value);
        ec_value = findViewById(R.id.ec_value);
        water_temp_value = findViewById(R.id.water_temp_value);
        temp_value = findViewById(R.id.temp_value);
        humidity_value = findViewById(R.id.humidity_value);
        light_value = findViewById(R.id.light_value);
        co2_value = findViewById(R.id.co2_value);
        baro_value = findViewById(R.id.baro_value);

        ph_btn.setOnClickListener(this);
        ec_btn.setOnClickListener(this);
        water_temp_btn.setOnClickListener(this);
        temp_btn.setOnClickListener(this);
        humidity_btn.setOnClickListener(this);
        light_btn.setOnClickListener(this);
        co2_btn.setOnClickListener(this);
        baro_btn.setOnClickListener(this);

        HydroDataService dataService = new HydroDataService(HomePage.this);

        dataService.getCurrTDS(new HydroDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(HomePage.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(String resp) {
                resp = resp + "ppm";
                ec_value.setText(resp);
            }
        });

        dataService.getCurrPH(new HydroDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(HomePage.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(String resp) {
                resp = resp + "pH";
                ph_value.setText(resp);
            }
        });

        dataService.getCurrEnv(new HydroDataService.getEnvResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(HomePage.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(EnvironmentData environmentData) {
                String temp = String.format(Locale.getDefault(), "%s", environmentData.getTemperature()) + "℃";
                String humidity = environmentData.getHumidity() + "%";
                String light = environmentData.getLight_intensity() + "lux";
                String co2 = environmentData.getCo2() + "ppm";
                String baro = environmentData.getBaro() + "mbar";
                temp_value.setText(temp);
                humidity_value.setText(humidity);
                light_value.setText(light);
                co2_value.setText(co2);
                baro_value.setText(baro);
            }
        });

        dataService.getCurrWater(new HydroDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(HomePage.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(String resp) {
                resp = resp + "℃";
                water_temp_value.setText(resp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ph_btn) {
            startAnalytics("pH", ph_value.getText(), R.drawable.ph_icon);
        }
        else if (v.getId() == R.id.ec_btn) {
            startAnalytics("Ec", ec_value.getText(), R.drawable.ec_icon);
        }
        else if (v.getId() == R.id.water_temp_btn) {
            startAnalytics("Water temp", water_temp_value.getText(), R.drawable.water_temp_icon);
        }
        else if (v.getId() == R.id.temp_btn) {
            startAnalytics("Temp", temp_value.getText(), R.drawable.air_temp_icon);
        }
        else if (v.getId() == R.id.humidity_btn) {
            startAnalytics("Humidity", humidity_value.getText(), R.drawable.humidity_icon);
        }
        else if (v.getId() == R.id.light_btn) {
            startAnalytics("Light", light_value.getText(), R.drawable.light_icon);
        }
        else if (v.getId() == R.id.co2_btn) {
            startAnalytics("Co2", co2_value.getText(), R.drawable.co2_icon);
        }
        else if (v.getId() == R.id.baro_btn) {
            startAnalytics("Baro", baro_value.getText(), R.drawable.pressure_icon);
        }
    }

    private void startAnalytics(String title, CharSequence value, int img){
        Intent i = new Intent(HomePage.this, AnalyticsActivity.class);
        i.putExtra("header_title", title);
        i.putExtra("curr_value", value);
        i.putExtra("header_img", img);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.app_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.config)
        {
            startActivity(new Intent(getApplicationContext(), ConfigurationActivity.class));
        }
        return true;
    }
}