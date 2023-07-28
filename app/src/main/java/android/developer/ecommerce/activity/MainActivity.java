package android.developer.ecommerce.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.OrderDetailDAO;

import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.fragment.account.AccountFragment;
import android.developer.ecommerce.fragment.HomeFragment;
import android.developer.ecommerce.fragment.OrderFragment;
import android.developer.ecommerce.fragment.ShoppingFragment;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    //#ACCOUNTS     ID - USERNAME - PASSWORD - NAME - PHONE_NUMBER - ADDRESS
    //              ID -   EMAIL  - PASSWORD - ADMIN - NAME
    //      -> ACCOUNT_TYPE
    //          #0 - CUSTOMERS
    //          #1 - MANAGERS
    public static final String PREFS_FILE = "PREFS_FILE.MY_APP";
    //#Widget
    private BottomNavigationView bottom_nav;
    private FloatingActionButton fab_cart;
    private void initUI(){
        bottom_nav = findViewById(R.id.bottom_nav);
        fab_cart = findViewById(R.id.fab_cart);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;
    private String name;
    private String phoneNumber;
    private String address;
    //      Database
    private DbHelper dbHelper;
    //      DAO
    private OrderDetailDAO orderDetailDAO;
    private void var(){
        //      Database
        dbHelper = new DbHelper(this);
        //      Shared Preferences
        sharedPreferences = getSharedPreferences(PREFS_FILE,MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
        name = sharedPreferences.getString("NAME","");
        phoneNumber = sharedPreferences.getString("PHONE_NUMBER","");
        address = sharedPreferences.getString("ADDRESS","");
        //      DAO
        orderDetailDAO = new OrderDetailDAO(this);
    }
    //#Launcher UI
    private void launcherUI(){
        //      Full Screen
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //      Switch Fragment screen
        bottom_nav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                        switchFragment (new HomeFragment());
                        return true;
                    case R.id.shopping:
                        switchFragment (new ShoppingFragment());
                        return true;
                    case R.id.order:
                        return checkLogin(new OrderFragment());
                    case R.id.account:
                        return checkLogin(new AccountFragment());
                    default:
                        return false;
                }
            }
        });

        //      Tab 'Home' is Launcher Screen
        switchFragment(new HomeFragment());




    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //#Widget - Var
        initUI();
        var();





        //#Launcher UI
        launcherUI();

    }

    private boolean checkLogin(Fragment fragment){
        if(id == 0){
            finish();
            startActivity(new Intent(MainActivity.this,LoginActivity.class));
            return false;
        }else{
            switchFragment (fragment);
            return true;
        }
    }

    private void switchFragment(Fragment fragment) {
        getSupportFragmentManager().beginTransaction().replace(R.id.frame_layout,fragment).commit();
    }





    @Override
    protected void onDestroy() {
        Log.e(MainActivity.class.getSimpleName(),"onDestroy");
        dbHelper.close();
        super.onDestroy();
    }


    public void switchCartUI(View view) {
        if(id==0){
                startActivity(new Intent(this, LoginActivity.class));
        }else{
                startActivity(new Intent(this, CartActivity.class));

        }
    }
}