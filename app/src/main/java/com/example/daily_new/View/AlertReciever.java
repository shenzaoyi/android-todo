package com.example.daily_new.View;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.daily_new.R;

public class AlertReciever extends BroadcastReceiver {
    private String channelId = String.valueOf(R.string.channelid);
    private String channelName = String.valueOf(R.string.channelname);
    @Override
    public void onReceive(Context context, Intent intent) {
        sendNotification(context);
    }
    private void sendNotification(Context context) {
        // NotificationManagere
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        // channel
        NotificationChannel channel = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                channel = new NotificationChannel(channelId,channelName,NotificationManager.IMPORTANCE_DEFAULT);
                channel.setDescription("This is my channel");
            }catch (Exception e) {
                Log.d("Channel Error", "sendNotification: " + e.toString());
            }
        }
        // Create channel, important
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(channel);
        }
        // build messagfe
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context,channelId).setSmallIcon(R.drawable.img).setContentTitle("提醒").setContentText("这是定时消息").setPriority(NotificationCompat.PRIORITY_DEFAULT);
        // send
        notificationManager.notify(1,builder.build());
    }
}
