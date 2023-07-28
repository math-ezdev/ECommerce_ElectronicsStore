package android.developer.ecommerce.channel;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.developer.myteamsproject.R;
import android.net.Uri;
import android.os.Build;

public class MyApplication extends Application {
    public static final String CHANNEL_ID = "Channel_ID";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            //config Notification Channel
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,"Sound Order success",NotificationManager.IMPORTANCE_HIGH);
            channel.setSound(Uri.parse("android.resource://"
                    + getPackageName() + "/" + R.raw.sound_order_success),null);

            //create Notification Channel
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
    }
}
