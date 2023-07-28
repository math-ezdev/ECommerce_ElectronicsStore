package android.developer.ecommerce.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.CustomerDAO;
import android.developer.ecommerce.DAO.OrderDAO;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class SignupActivity extends AppCompatActivity {
    //#Widget
    private TextInputEditText customer_username,customer_password,customer_name,customer_re_password;
    private void initUI(){
        customer_username = findViewById(R.id.customer_username);
        customer_name = findViewById(R.id.customer_name);
        customer_password = findViewById(R.id.customer_password);
        customer_re_password = findViewById(R.id.customer_re_password);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    //      DAO
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getSharedPreferences(MainActivity.PREFS_FILE,MODE_PRIVATE);
        //      DAO
        customerDAO = new CustomerDAO(this);
        orderDAO = new OrderDAO(this);
    }
    //#LauncherUI
    private void launcherUI(){
        //      Style
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //#Widget - var
        initUI();
        var();

        //#LauncherUI
        launcherUI();
    }

    public void switchLoginUI(View view) {
        onBackPressed();
    }

    public void customerSignup(View view) {
        //      get text
        String username = customer_username.getText().toString().trim();
        String name = customer_name.getText().toString().trim();
        String password = customer_password.getText().toString().trim();
        String rePassword = customer_re_password.getText().toString().trim();

        //      check empty
        if(username.isEmpty() ||name.isEmpty() || password.isEmpty() || rePassword.isEmpty()){
            customer_username.requestFocus();
            Snackbar.make(findViewById(R.id.coordinator),"Điền đầy đủ thông tin!", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK",v -> {}  )
                    .show();
            return;
        }

        //      check same input 'password again'
        if(!password.equals(rePassword)){
            customer_re_password.requestFocus();
            Snackbar.make(findViewById(R.id.coordinator),"Mật khẩu phải giống nhau!", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK",v -> {}  )
                    .show();
            return;
        }

        //      insert account 'Customer'
        boolean check = customerDAO.insertDatabase(username,password,name);

        //      check 'username' available
        if(!check){
            customer_username.requestFocus();
            Snackbar.make(findViewById(R.id.coordinator),"Tên tài khoản đã tồn tại!", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK",v -> {}  )
                    .show();
            return;

        }

        //      insert 'new Order' from 'userName' to contain 'all item cart'
        int customer_id = customerDAO.queryDatabaseID(username);

        orderDAO.insertDatabase(customer_id);

        //      Success
        Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.putExtra("USERNAME",username);
        intent.putExtra("PASSWORD",password);
        setResult(RESULT_OK,intent);
        finish();
    }
}
