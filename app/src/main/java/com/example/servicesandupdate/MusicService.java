package com.example.servicesandupdate;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaParser;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.IBinder;
import android.provider.MediaStore;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MusicService extends Service {

    MediaPlayer mediaPlayer;
    NotificationManager notificationManager;
    AlarmManager alarmManager;

    public MusicService() {}

    @Override
    public void onCreate() {
        super.onCreate();
        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaPlayer.start();
        mediaPlayer.setLooping(true);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        alarmManager  = (AlarmManager) getSystemService(ALARM_SERVICE);

        //создем менеджер уведомлений
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        //cоздать канал для уведомлений
        NotificationChannel notificationChannel = new NotificationChannel("MyMusic", "Music",
                NotificationManager.IMPORTANCE_DEFAULT);
        //создаем pendingIntent
        notificationManager.createNotificationChannel(notificationChannel);
        Intent intent1 = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
                intent1,PendingIntent.FLAG_CANCEL_CURRENT);
        //создание уведомления
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this, "MyMusic")
                .setContentIntent(pendingIntent)
                .setChannelId("MyMusic")
                .setColor(Color.GREEN)
                .setContentTitle("Cейчас играет")
                .setSmallIcon(R.drawable.ic_launcher_background);
        //setSound для сопровождения появления уведомления звуком


        Notification notification = nBuilder.build();
        //запуск уведомления



        notificationManager.notify(134343566, notification);

        return Service.START_STICKY;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        notificationManager.cancel(134343566);
        mediaPlayer.stop();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }


}