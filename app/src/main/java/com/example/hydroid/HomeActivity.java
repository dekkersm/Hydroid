package com.example.hydroid;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    Button ph_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        HydroDataService dataService = new HydroDataService(HomeActivity.this);

        ph_btn = findViewById(R.id.ph_btn);

        ph_btn.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  dataService.getCurrPH(new HydroDataService.VolleyResponseListener() {
                      @Override
                      public void onError(String message) {
                          Toast.makeText(HomeActivity.this, message, Toast.LENGTH_SHORT).show();
                      }

                      @Override
                      public void onResp(String resp) {
                          Toast.makeText(HomeActivity.this, resp, Toast.LENGTH_SHORT).show();
                      }
                  });
              }
          });
    }
}