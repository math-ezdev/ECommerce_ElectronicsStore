package android.developer.ecommerce.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.CustomerDAO;

import android.developer.ecommerce.fragment.login.CustomerLoginFragment;
import android.developer.ecommerce.fragment.login.ManagerLoginFragment;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;


public class LoginActivity extends AppCompatActivity {
    //#Widget
    private ToggleButton toggle_account;
    private void initUI(){
        toggle_account = findViewById(R.id.toggle_account);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    //      DAO
    private CustomerDAO customerDAO;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getSharedPreferences(MainActivity.PREFS_FILE,MODE_PRIVATE);
        //      DAO
        customerDAO = new CustomerDAO(this);
    }
    //#LauncherUI
    private void launcherUI(){
        //      Style
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        //      default UI
        getSupportFragmentManager().beginTransaction().replace(R.id.frame, new CustomerLoginFragment()).commit();
        //      Toggle 'Account UI'
        toggle_account.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSupportFragmentManager().beginTransaction().replace(R.id.frame, isChecked ?new ManagerLoginFragment():new CustomerLoginFragment()).commit();

            }
        });


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //#Widget - var
        initUI();
        var();

        //#LauncherUI
        launcherUI();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
    }
}
