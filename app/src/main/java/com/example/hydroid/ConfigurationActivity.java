package com.example.hydroid;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.json.JSONArray;
import org.json.JSONException;

public class ConfigurationActivity extends AppCompatActivity {

    TextView ec_v_btn, ph_v_btn;
    EditText ph_max_config, ph_min_config, ec_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuration);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ec_v_btn = findViewById(R.id.ec_v_btn);
        ph_v_btn = findViewById(R.id.ph_v_btn);

        ph_max_config = findViewById(R.id.ph_max_config);
        ph_min_config = findViewById(R.id.ph_min_config);
        ec_config = findViewById(R.id.ec_config);

        HydroDataService dataService = new HydroDataService(ConfigurationActivity.this);

        dataService.getCurrConfig(new HydroDataService.GetCurrConfigResp() {
            @Override
            public void onError(String message) {
                Toast.makeText(ConfigurationActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(JSONArray resp) {
                try {
                    ec_config.setText(resp.getJSONObject(0).getString("value"));
                    ph_min_config.setText(resp.getJSONObject(1).getString("value"));
                    ph_max_config.setText(resp.getJSONObject(2).getString("value"));
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        ec_v_btn.setOnClickListener(v -> {
            if (!ec_config.getText().toString().isEmpty())
            {
                dataService.setCurrConfig("tdsMinLimit", Float.parseFloat(ec_config.getText().toString()));
                Toast.makeText(ConfigurationActivity.this, "ec configuration successful!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(ConfigurationActivity.this, "configuration failed!", Toast.LENGTH_SHORT).show();
            }
        });

        ph_v_btn.setOnClickListener(v -> {
            if (!ph_max_config.getText().toString().isEmpty() && !ph_min_config.getText().toString().isEmpty())
            {
                dataService.setCurrConfig("phMinLimit", Float.parseFloat(ph_min_config.getText().toString()));
                dataService.setCurrConfig("phMaxLimit", Float.parseFloat(ph_max_config.getText().toString()));
                Toast.makeText(ConfigurationActivity.this, "ph configuration successful!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(ConfigurationActivity.this, "configuration failed!", Toast.LENGTH_SHORT).show();
            }
        });
    }
}