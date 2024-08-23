package com.example.hydroid;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.util.Pair;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    Long startDate, endDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        HydroDataService dataService = new HydroDataService(AnalyticsActivity.this);
        //initDatePicker();

        graphView = findViewById(R.id.idGraphView);
        header_title = findViewById(R.id.header_title);
        curr_value = findViewById(R.id.curr_value);
        from_text = findViewById(R.id.from_text);
        to_text = findViewById(R.id.to_text);
        header_img = findViewById(R.id.header_img);
        go_btn = findViewById(R.id.go_btn);


        from_text.setOnClickListener(view -> DatePickerdialog());
        to_text.setOnClickListener(view -> DatePickerdialog());

        go_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
                            Date date = new Date(tdsData.get(tdsData.size()-i-1).getDate());
                            points[i] = new DataPoint(date, (double) tdsData.get(tdsData.size()-i-1).getValue());
                        }

                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
                        series.setColor(R.color.DarkBlue);
                        series.setAnimated(true);
                        series.setDrawDataPoints(true);
                        graphView.addSeries(series);
                        graphView.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.HORIZONTAL );
                        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(AnalyticsActivity.this));
                    }
                }, startDate, endDate);
            }
        });
    }

    private void DatePickerdialog() {
        // Creating a MaterialDatePicker builder for selecting a date range
        MaterialDatePicker.Builder<Pair<Long, Long>> builder = MaterialDatePicker.Builder.dateRangePicker();
        builder.setTitleText("Select a date range");

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
}