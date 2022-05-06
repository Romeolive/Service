package com.example.servicesandupdate;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button startBtn, stopBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startBtn = findViewById(R.id.start_btn);
        stopBtn  = findViewById(R.id.stop_btn);


        startBtn.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, MusicService.class);
            startService(intent);
        });

        stopBtn.setOnClickListener(view -> stopService(
                new Intent(MainActivity.this, MusicService.class)));


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Intent intent = new Intent(MainActivity.this, AlarmManag.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long time = 1000*4;

        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);

        sendBroadcast(intent);


    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = new Intent(MainActivity.this, AlarmManag.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(MainActivity.this,0,intent,0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        long time = 1000*4;

        alarmManager.set(AlarmManager.RTC_WAKEUP,time,pendingIntent);
    }
}