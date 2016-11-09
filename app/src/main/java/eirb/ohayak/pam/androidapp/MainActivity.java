package eirb.ohayak.pam.androidapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private Button startService ;
    private Button stopService ;
    private Button getCount ;
    private boolean started;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        started = false;

        startService = (Button) findViewById(R.id.start_service);
        startService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start(view);
            }
        });

        stopService = (Button) findViewById(R.id.stop_service);
        stopService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                stop(view);
            }
        });

        getCount = (Button) findViewById(R.id.get_count);
        getCount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                get(view);
            }
        });

    }

    public void start(View view) {
        startService(new Intent(MainActivity.this, TimerService.class));
        started = true;
    }

    public void stop(View view) {
        stopService(new Intent(MainActivity.this, TimerService.class));
        started = false;

    }

    public void get(View view) {
        EditText screen = (EditText) findViewById(R.id.edit_message);
        if (started) {
            screen.setText("count = " + TimerService.getCounter());
        } else {
            screen.setText("Service Not started");
        }

    }


}
