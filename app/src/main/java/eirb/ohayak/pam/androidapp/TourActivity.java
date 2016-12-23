package eirb.ohayak.pam.androidapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class TourActivity extends AppCompatActivity {
    private Intent callIntent;
    private TourHelper th = TourHelper.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tour);
        callIntent = getIntent();
        FloatingActionButton next = (FloatingActionButton) findViewById(R.id.btn_save_tour);
        next.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startTour(view);
            }
        });
    }

    private void startTour(View view) {
        Intent intent = new Intent(view.getContext(), LocationService.class);
        String tourName = "";
        if (((EditText) findViewById(R.id.txt_tour_name)) != null) {
            tourName = ((EditText) findViewById(R.id.txt_tour_name)).getText().toString();
        } else {
            Toast.makeText(getApplicationContext(),
                    "Enter tour name", Toast.LENGTH_SHORT).show();
            return;
        }

        String tourDetails = "";
        if (((EditText) findViewById(R.id.txt_tour_details)) != null) {
            tourDetails = ((EditText) findViewById(R.id.txt_tour_details)).getText().toString();
        }

        Tour newTour = new Tour();
        newTour.setName(tourName);
        newTour.setDetails(tourDetails);
        newTour.setActive(true);
        th.insert(newTour);
        callIntent.putExtra("newTour", newTour);
        intent.putExtra("request", LocationService.ADD_TOUR);
        intent.putExtra("new_tour", newTour);
        startService(intent);
        setResult(RESULT_OK, callIntent);
        finish();
    }

    public void stopTour(View view) {
        stopService(new Intent(TourActivity.this, LocationService.class));
    }

}
