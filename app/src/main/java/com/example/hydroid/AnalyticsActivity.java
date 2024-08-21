package com.example.hydroid;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;
import java.util.List;

public class AnalyticsActivity extends AppCompatActivity {

    // creating a variable
    // for our graph view.
    GraphView graphView;
    TextView header_title, curr_value;
    TextView from_text, to_text;
    ImageView header_img;
    Button go_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analytics);

        HydroDataService dataService = new HydroDataService(AnalyticsActivity.this);

        graphView = findViewById(R.id.idGraphView);
        header_title = findViewById(R.id.header_title);
        curr_value = findViewById(R.id.curr_value);
        from_text = findViewById(R.id.from_text);
        to_text = findViewById(R.id.to_text);
        header_img = findViewById(R.id.header_img);
        go_btn = findViewById(R.id.go_btn);

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
                        DataPoint[] points = new DataPoint[tdsData.size()];
                        for (int i = 0; i < tdsData.size(); i++) {
                            Date date = new Date(tdsData.get(tdsData.size()-i-1).getDate());
                            points[i] = new DataPoint(date, (double) tdsData.get(tdsData.size()-i-1).getValue());
                        }

                        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(points);
                        graphView.addSeries(series);
                        graphView.getGridLabelRenderer().setGridStyle( GridLabelRenderer.GridStyle.HORIZONTAL );
                        series.setColor(R.color.DarkBlue);
                        series.setAnimated(true);
                        series.setDrawDataPoints(true);
                    }
                });
            }
        });
    }
}