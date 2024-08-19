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

public class HistoricalPhActivity extends AppCompatActivity {

    Button ph_btn, historical_btn;
    ListView lv_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ph_history_view);

        HydroDataService dataService = new HydroDataService(HistoricalPhActivity.this);

        ph_btn = findViewById(R.id.curr_ph_btn);
        historical_btn = findViewById(R.id.historical_btn);
        lv_data = findViewById(R.id.list_historical);

        lv_data.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                           @Override
                                           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                               Toast.makeText(HistoricalPhActivity.this, "item " + position + "clicked", Toast.LENGTH_SHORT).show();
                                           }
                                       });

                ph_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dataService.getCurrPH(new HydroDataService.VolleyResponseListener() {
                            @Override
                            public void onError(String message) {
                                Toast.makeText(HistoricalPhActivity.this, message, Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onResp(String resp) {
                                Toast.makeText(HistoricalPhActivity.this, resp, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });

        historical_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dataService.getPhHistory(new HydroDataService.getPhHistoryResponse() {
                    @Override
                    public void onError(String message) {
                        Toast.makeText(HistoricalPhActivity.this, message, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onResp(List<PhData> PhData) {
                        ArrayAdapter arrayAdapter = new ArrayAdapter(HistoricalPhActivity.this, android.R.layout.simple_list_item_1, PhData);
                        lv_data.setAdapter(arrayAdapter);

                    }
                });
            }
        });
    }
}