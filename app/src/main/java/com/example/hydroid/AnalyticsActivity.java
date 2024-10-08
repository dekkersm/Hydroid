package com.example.hydroid;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.util.Pair;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import com.google.android.material.datepicker.MaterialDatePicker;

public class AnalyticsActivity extends AppCompatActivity {

    GraphView graphView;
    TextView header_title, curr_value;
    TextView from_text, to_text;
    ImageView header_img;
    Button go_btn;

    Long startDate = 1724371200000L, endDate = 1724371200000L;

    HydroDataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        dataService = new HydroDataService(AnalyticsActivity.this);

        graphView = findViewById(R.id.idGraphView);
        header_title = findViewById(R.id.header_title);
        curr_value = findViewById(R.id.curr_value);
        from_text = findViewById(R.id.from_text);
        to_text = findViewById(R.id.to_text);
        header_img = findViewById(R.id.header_img);
        go_btn = findViewById(R.id.go_btn);


        Intent mIntent = getIntent();
        header_title.setText(mIntent.getStringExtra("header_title"));
        curr_value.setText(mIntent.getStringExtra("curr_value"));
        header_img.setImageDrawable(getResources().getDrawable(mIntent.getIntExtra("header_img", R.drawable.ec_icon), getApplicationContext().getTheme()));

        from_text.setOnClickListener(view -> DatePickerDialog());
        to_text.setOnClickListener(view -> DatePickerDialog());

        go_btn.setOnClickListener(v -> {
            switch (header_title.getText().toString())
            {
                case "pH":
                    phClickListener();
                    break;
                case "Ec":
                    tdsClickListener();
                    break;
                case "Water temp":
                    waterClickListener();
                    break;
                case "Temp":
                    envClickListener("Temp");
                    break;
                case "Humidity":
                    envClickListener("Humidity");
                    break;
                case "Light":
                    envClickListener("Light");
                    break;
                case "Co2":
                    envClickListener("Co2");
                    break;
                case "Baro":
                    envClickListener("Baro");
                    break;
                default: break;
            }
        });
    }

    private void DatePickerDialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");
        builder.setTheme(R.style.ThemeMaterialCalendar);

        // Building the date picker dialog
        MaterialDatePicker<Pair<Long, Long>> datePicker = builder.build();
        datePicker.addOnPositiveButtonClickListener(selection -> {

            // Retrieving the selected start and end dates
            startDate = selection.first;
            endDate = selection.second;

            // Formating the selected dates as strings
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.getDefault());
            String startDateString = sdf.format(new Date(startDate));
            String endDateString = sdf.format(new Date(endDate));

            // Displaying the selected date range in the TextView
            from_text.setText(startDateString);
            to_text.setText(endDateString);
        });

        // Showing the date picker dialog
        datePicker.show(getSupportFragmentManager(), "DATE_PICKER");
    }

    private void phClickListener() {
        dataService.getPhHistory(new HydroDataService.getPhHistoryResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(AnalyticsActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(List<PhData> PhData) {
                // traverse through all dates
                DataPoint[] points = new DataPoint[PhData.size()];
                for (int i = 0; i < PhData.size(); i++) {
                    Date date = new Date(PhData.get(i).getDate());
                    points[i] = new DataPoint(date, PhData.get(i).getValue());
                }
                drawGraph(points);
            }
        }, startDate, endDate);
    }

    private void tdsClickListener() {
        dataService.getTdsHistory(new HydroDataService.getTdsHistoryResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(AnalyticsActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(List<TdsData> tdsData) {
                // traverse through all dates
                DataPoint[] points = new DataPoint[tdsData.size()];
                for (int i = 0; i < tdsData.size(); i++) {
                    Date date = new Date(tdsData.get(i).getDate());
                    points[i] = new DataPoint(date, tdsData.get(i).getValue());
                }
                drawGraph(points);
            }
        }, startDate, endDate);
    }

    private void waterClickListener() {
        dataService.getWaterHistory(new HydroDataService.getWaterHistoryResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(AnalyticsActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(List<WaterData> waterData) {
                // traverse through all dates
                DataPoint[] points = new DataPoint[waterData.size()];
                for (int i = 0; i < waterData.size(); i++) {
                    Date date = new Date(waterData.get(i).getDate());
                    points[i] = new DataPoint(date, waterData.get(i).getTempValue());
                }
                drawGraph(points);
            }
        }, startDate, endDate);
    }

    private void envClickListener(String title){
        dataService.getEnvHistory(new HydroDataService.getEnvHistoryResponse() {
            @Override
            public void onError(String message) {
                Toast.makeText(AnalyticsActivity.this, message, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onResp(List<EnvironmentData> environmentDataList) {
                // traverse through all dates
                DataPoint[] points = new DataPoint[environmentDataList.size()];
                for (int i = 0; i < environmentDataList.size(); i++) {
                    Date date = new Date(environmentDataList.get(i).getDate());
                    switch (title)
                    {
                        case "Temp":
                            points[i] = new DataPoint(date, environmentDataList.get(i).getTemperature());
                            break;
                        case "Humidity":
                            points[i] = new DataPoint(date, environmentDataList.get(i).getHumidity());
                            break;
                        case "Light":
                            points[i] = new DataPoint(date, environmentDataList.get(i).getLight_intensity());
                            break;
                        case "Co2":
                            points[i] = new DataPoint(date, environmentDataList.get(i).getCo2());
                            break;
                        case "Baro":
                            points[i] = new DataPoint(date, environmentDataList.get(i).getBaro());
                            break;
                    }
                }
                drawGraph(points);
            }
        }, startDate, endDate);
    }

    private void drawGraph(DataPoint[] points){
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(points);
        graphView.removeAllSeries();
        graphView.addSeries(series);
        graphView.getViewport().setXAxisBoundsManual(true);
        graphView.getViewport().setMinX(points[0].getX());
        graphView.getViewport().setMaxX(points[points.length-1].getX());
        graphView.getGridLabelRenderer().setHorizontalLabelsAngle(45);
        graphView.getGridLabelRenderer().setLabelsSpace(35);
        graphView.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.HORIZONTAL );
        graphView.getGridLabelRenderer().setLabelFormatter(
                new DateAsXAxisLabelFormatter(AnalyticsActivity.this,
                        new SimpleDateFormat("dd.MM", Locale.getDefault())));

        series.setColor(R.color.DarkBlue);
        series.setAnimated(true);
        series.setDrawDataPoints(true);
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