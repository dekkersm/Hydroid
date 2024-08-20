package com.example.hydroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.List;

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
                temp_value.setText(Float.toString(environmentData.getTemperature()));
                humidity_value.setText(Float.toString(environmentData.getHumidity()));
                light_value.setText(Float.toString(environmentData.getLight_intensity()));
                co2_value.setText(Float.toString(environmentData.getCo2()));
                baro_value.setText(Float.toString(environmentData.getBaro()));
            }
        });

        dataService.getCurrWater(new HydroDataService.VolleyResponseListener() {
            @Override
            public void onError(String message) {
                Toast.makeText(HomePage.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(String resp) {
                water_temp_value.setText(resp);
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ph_btn) {
            Intent i = new Intent(HomePage.this, HistoricalPhActivity.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.ec_btn) {
            Intent i = new Intent(HomePage.this, HistoricalTdsActivity.class);
            startActivity(i);
        }
        else if (v.getId() == R.id.water_temp_btn) {
        }
        else if (v.getId() == R.id.temp_btn) {
        }
        else if (v.getId() == R.id.humidity_btn) {
        }
        else if (v.getId() == R.id.light_btn) {
        }
        else if (v.getId() == R.id.co2_btn) {
        }
        else if (v.getId() == R.id.baro_btn) {
        }
    }
}