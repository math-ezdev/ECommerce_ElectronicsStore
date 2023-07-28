package android.developer.ecommerce.service;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.channel.MyApplication;
import android.developer.myteamsproject.R;
import android.os.IBinder;

import androidx.core.app.NotificationCompat;

public class SoundOrderSuccessService extends Service {
    private static final int ONGOING_NOTIFICATION_ID = 1;

    public SoundOrderSuccessService() {
    }




    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        notifyForForegroundService();

        return START_NOT_STICKY;
    }

    public void notifyForForegroundService(){
        // Nếu Notification hỗ trợ reply , dùng PendingIntent.FLAG_MUTABLE thay thế
        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(this, 0, notificationIntent,
                        PendingIntent.FLAG_IMMUTABLE);

        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Bạn đã đặt hàng thành công")
                .setContentText("Xem chi tiết trong ứng dụng...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentIntent(pendingIntent)
                .setTicker("Thế giới điện tử")
                .build();


        // Notification ID phải là số nguyên dương > 0
        startForeground(ONGOING_NOTIFICATION_ID, notification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}