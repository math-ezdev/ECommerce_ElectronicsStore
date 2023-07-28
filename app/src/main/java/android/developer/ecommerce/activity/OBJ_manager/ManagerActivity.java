package android.developer.ecommerce.activity.OBJ_manager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.ProductDAO;
import android.developer.ecommerce.activity.LoginActivity;
import android.developer.ecommerce.database.DbHelper;
import android.developer.ecommerce.fragment.HomeFragment;
import android.developer.ecommerce.fragment.OrderFragment;
import android.developer.ecommerce.fragment.ShoppingFragment;
import android.developer.ecommerce.fragment.account.ManageAccountFragment;
import android.developer.ecommerce.model.Category;
import android.developer.ecommerce.model.Product;
import android.developer.myteamsproject.R;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;

public class ManagerActivity extends AppCompatActivity {
    //#ACCOUNTS     ID - USERNAME - PASSWORD - NAME - PHONE_NUMBER - ADDRESS
    //              ID -   EMAIL  - PASSWORD - ADMIN - NAME
    //      -> ACCOUNT_TYPE
    //          #0 - CUSTOMERS
    //          #1 - MANAGERS
    public static final String PREFS_FILE = "PREFS_FILE.MY_APP";
    //#Widget
    private BottomNavigationView bottom_nav;
    private void initUI(){
        bottom_nav = findViewById(R.id.bottom_nav);
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
    private ProductDAO productDAO;
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
        productDAO = new ProductDAO(this);
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
                        return checkLogin(new ManageAccountFragment());
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
        setContentView(R.layout.activity_manager);

        //#Widget - Var
        initUI();
        var();





        //#Launcher UI
        launcherUI();

    }

    private boolean checkLogin(Fragment fragment){
        if(id == 0){
            finish();
            startActivity(new Intent(this, LoginActivity.class));
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
        dbHelper.close();
        super.onDestroy();
    }

    public void addProduct(View view) {
        dialogInsertProduct();
    }

    int category;
    private void dialogInsertProduct() {
        //      View
        View view = getLayoutInflater().inflate(R.layout.dialog_add_prodcut,null);
        TextInputEditText product_name = view.findViewById(R.id.product_name);
        TextInputEditText product_banner = view.findViewById(R.id.product_banner);
        TextInputEditText product_description = view.findViewById(R.id.product_description);
        TextInputEditText product_config = view.findViewById(R.id.product_config);
        TextInputEditText product_original_price = view.findViewById(R.id.product_original_price);
        TextInputEditText product_price = view.findViewById(R.id.product_price);
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Button btn_ok = view.findViewById(R.id.btn_ok);
        RadioGroup rdo_category = view.findViewById(R.id.rdo_category);
        TextView tv_tip = view.findViewById(R.id.tv_tip);
        //
        tv_tip.setOnClickListener(v -> {
            goToLink("https://youtu.be/eyucrXwWphg");
        });

        //      Bottom Sheet Dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.setCancelable(false);
        bottomSheetDialog.show();
        //      Button
        category = 1;
        rdo_category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.category_computer:
                        category = 1;
                        break;
                    case R.id.category_phone:
                        category = 2;
                        break;
                    case R.id.category_other:
                        category = 3;
                        break;
                }
            }
        });
        btn_cancel.setOnClickListener(v -> bottomSheetDialog.dismiss());
        btn_ok.setOnClickListener(v -> {
            //      data
            String productName = product_name.getText().toString().trim();
            String productBanner = product_banner.getText().toString().trim();
            String productConfig = product_config.getText().toString().trim();
            String productDescription = product_description.getText().toString().trim();
            String productOriginalPrice = product_original_price.getText().toString().trim();
            String productPrice = product_price.getText().toString().trim();


            //      check
            if(productName.isEmpty() || productBanner.isEmpty() || productConfig.isEmpty() || productDescription.isEmpty() || productOriginalPrice.isEmpty() || productPrice.isEmpty()){
                return;
            }

            try {
                double originalPrice = Double.parseDouble(productOriginalPrice);
                double price = Double.parseDouble(productPrice);

                //      insert 'new Product'
                Product product = new Product(productName,productBanner,productDescription,productConfig,originalPrice,price,new Category(category));
                boolean check = productDAO.insertDatabase(product);

                Toast.makeText(this, check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

                bottomSheetDialog.dismiss();

                bottomSheetDialog.setCancelable(true);
            }catch (NumberFormatException e){
                return;
            }



        });
        //      only show one time
        if(bottomSheetDialog != null && bottomSheetDialog.isShowing()){
            return;
        }
    }
    private void goToLink(String link){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }
}