package fr.eirb.pam.tp1_ohayak_slu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button send;
    EditText phone;
    EditText sms;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = (Button) findViewById(R.id.btnSend);
        phone = (EditText) findViewById(R.id.phone);
        sms = (EditText) findViewById(R.id.sms);

        send.setOnClickListener(this);
    }

    public void onClick(View v) {
        if (TextUtils.isEmpty(phone.getText().toString())
                || TextUtils.isEmpty(sms.getText().toString())) {
            return;
        }
        String dest[] = phone.getText().toString().split(";");
        String msg = sms.getText().toString();
        for(String num : dest) {
            SmsManager.getDefault().sendTextMessage(num,null,msg,null,null);
        }
        Toast.makeText(getApplicationContext(), "SMS Envoyé!",Toast.LENGTH_LONG).show();
    }
}
