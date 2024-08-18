package com.example.hydroid;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class HistoricalTdsActivity extends AppCompatActivity {

    Button tds_btn, historical_btn;
    ListView lv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tds_history_view);

        HydroDataService dataService = new HydroDataService(HistoricalTdsActivity.this);

        tds_btn = findViewById(R.id.curr_tds_btn);
        historical_btn = findViewById(R.id.historical_btn);
        lv_data = findViewById(R.id.list_historical);

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(HistoricalTdsActivity.this, "item " + position + "clicked", Toast.LENGTH_SHORT).show();
            }
        });

        tds_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataService.getCurrTDS(new HydroDataService.VolleyResponseListener() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(HistoricalTdsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResp(String resp) {
                        Toast.makeText(HistoricalTdsActivity.this, resp, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        historical_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataService.getEnvHistory(new HydroDataService.getEnvHistoryResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(HistoricalTdsActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResp(List<EnvironmentData> environmentDataList) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(HistoricalTdsActivity.this, android.R.layout.simple_list_item_1, environmentDataList);
                        lv_data.setAdapter(arrayAdapter);
                    }
                });
            }
        });
    }
}