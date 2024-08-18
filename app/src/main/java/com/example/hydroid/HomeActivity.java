package com.example.hydroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button ph_btn, tds_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        ph_btn = findViewById(R.id.ph_btn);
        tds_btn = findViewById(R.id.tds_btn);

        ph_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, HistoricalPhActivity.class);
                startActivity(i);
            }
        });

        tds_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomeActivity.this, HistoricalTdsActivity.class);
                startActivity(i);
            }
        });
    }
}