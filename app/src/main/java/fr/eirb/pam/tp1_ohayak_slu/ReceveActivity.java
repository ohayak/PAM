package fr.eirb.pam.tp1_ohayak_slu;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ReceveActivity extends AppCompatActivity {
    private TextView sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receve);
        sms = (TextView) findViewById(R.id.sms);
        processIntent(getIntent());
    }

    protected void processIntent(Intent data){
        Bundle extra = data.getExtras();
        String txt = extra.getString("sms");
        sms.setText(txt);
    }
}
