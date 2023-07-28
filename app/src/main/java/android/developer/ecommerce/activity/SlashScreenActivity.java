package android.developer.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.activity.OBJ_manager.ManagerActivity;
import android.developer.myteamsproject.R;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.WindowManager;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;

public class SlashScreenActivity extends AppCompatActivity {
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private boolean accountType;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getSharedPreferences(MainActivity.PREFS_FILE,MODE_PRIVATE);
        accountType = sharedPreferences.getBoolean("ACCOUNT_TYPE",false);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slash_screen);

        //#
        var();

        //      Style
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        if(isOnline()){
            CountDownTimer count = new CountDownTimer(1000,1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    startActivity(new Intent(SlashScreenActivity.this,accountType? ManagerActivity.class :MainActivity.class));
                    finish();
                }
            }.start();
        }else{
            Snackbar.make(findViewById(R.id.coordinator_layout),"Kiểm tra kết nối Internet của bạn!", BaseTransientBottomBar.LENGTH_LONG)
                    .setAction("OK", v -> {

                    })
                    .show();
        }
    }

    //      Check internet
    public boolean isOnline() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
}