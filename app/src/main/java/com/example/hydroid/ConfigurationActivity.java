package com.example.hydroid;

import android.os.Bundle;
import android.renderscript.Sampler;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ConfigurationActivity extends AppCompatActivity {

    TextView ec_v_btn, ph_v_btn;
    EditText ph_config, ec_config;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_configuration);

        ec_v_btn = findViewById(R.id.ec_v_btn);
        ph_v_btn = findViewById(R.id.ph_v_btn);

        ph_config = findViewById(R.id.ph_config);
        ec_config = findViewById(R.id.ec_config);

        ec_v_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ec_config.getText().toString().isEmpty())
                {
                    // TODO: ec_config.post(ec_config.getText())
                    Toast.makeText(ConfigurationActivity.this, ec_config.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ConfigurationActivity.this, "configuration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        ph_v_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ph_config.getText().toString().isEmpty())
                {
                    // TODO: ec_config.post(ec_config.getText())
                    Toast.makeText(ConfigurationActivity.this, ph_config.getText().toString(), Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(ConfigurationActivity.this, "configuration failed!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}