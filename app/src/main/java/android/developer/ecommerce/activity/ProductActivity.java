package android.developer.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.OrderDAO;
import android.developer.ecommerce.DAO.OrderDetailDAO;
import android.developer.ecommerce.DAO.ProductDAO;
import android.developer.ecommerce.model.Category;
import android.developer.ecommerce.model.Product;
import android.developer.myteamsproject.R;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class ProductActivity extends AppCompatActivity {
    //#Widget
    private ImageView product_banner;
    private TextView product_price, product_description,product_config;
    private Toolbar tool_bar;
    private FloatingActionButton btn_add_to_cart;
    private ExtendedFloatingActionButton btn_settings;
    private FloatingActionButton btn_update, btn_hide;
    private BottomSheetDialog bottomSheetProduct;

    private void initUI() {
        product_banner = findViewById(R.id.product_banner);
        product_price = findViewById(R.id.product_price);
        product_description = findViewById(R.id.product_description);
        product_config = findViewById(R.id.product_config);
        tool_bar = findViewById(R.id.tool_bar);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        btn_settings = findViewById(R.id.btn_settings);
        btn_update = findViewById(R.id.btn_update);
        btn_hide = findViewById(R.id.btn_hide);

    }

    //#var
    //
    boolean isAllVisible;
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;
    private boolean accountType;
    //      DAO
    private OrderDetailDAO orderDetailDAO;
    private OrderDAO orderDAO;
    private ProductDAO productDAO;
    //
    Product product;
    Category category;

    private void var() {
        //      Shared Preferences
        sharedPreferences = getSharedPreferences(MainActivity.PREFS_FILE, MODE_PRIVATE);
        id = sharedPreferences.getInt("ID", 0);
        userName = sharedPreferences.getString("USERNAME", "");
        accountType = sharedPreferences.getBoolean("ACCOUNT_TYPE", false);
        //      DAO
        orderDetailDAO = new OrderDetailDAO(this);
        orderDAO = new OrderDAO(this);
        productDAO = new ProductDAO(this);
        //      OBJ 'Product'
        Intent intent = getIntent();
        product = (Product) intent.getExtras().getSerializable("BUNDLE_PRODUCT");
        category = (Category) intent.getExtras().getSerializable("BUNDLE_CATEGORY");
        //      Dialog
        dialogProduct();
    }

    //#LauncherUI
    private void launcherUI() {
        //      Style
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //      UI
//        Picasso.get().load("https://drive.google.com/uc?id=" + product.getImg_banner()).into(product_banner);
        Picasso.get().load( product.getImg_banner()).into(product_banner);
        //      price
        NumberFormat formatter = new DecimalFormat("#,###,###");
        String price = formatter.format(product.getPrice());
        product_price.setText(price.replace(",", "."));
        product_description.setText(String.valueOf(product.getDescription()));
        String config = "";
        if(product.getConfig().contains("\n")){
            config = product.getConfig().substring(product.getConfig().indexOf("\n"));
        }
        product_config.setText(config);
        btn_settings.setVisibility(accountType ? View.VISIBLE : View.GONE);
        btn_add_to_cart.setVisibility(!accountType ? View.VISIBLE : View.GONE);

        //      Tool Bar
        tool_bar.setTitle(product.getName());
        tool_bar.setNavigationIcon(R.drawable.ic_back);
        tool_bar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        setSupportActionBar(tool_bar);
        getSupportActionBar().setSubtitle(product.getConfig());

        //      task 'admin'
        btn_settings.setOnClickListener(v -> {
            if (isAllVisible) {
                hideFAB();
            } else {
                btn_update.show();
                btn_hide.show();
                btn_update.setVisibility(View.VISIBLE);
                btn_hide.setVisibility(View.VISIBLE);

                btn_settings.extend();
                isAllVisible = true;
            }
        });
        btn_update.setOnClickListener(v -> {
            bottomSheetProduct.setCancelable(false);
            dialogUpdateProduct();

        });
        btn_hide.setOnClickListener(v -> {
            productDAO.updateDatabase(product.getId(), category.getId() * 10);
            hideFAB();
            Toast.makeText(this, "Ẩn sản phẩm thành công", Toast.LENGTH_SHORT).show();
            finish();
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        //#Widget - var
        initUI();
        var();


        //#LauncherUI
        launcherUI();
    }

    //      Dialog 'Product'
    int category_id;

    private void dialogProduct() {
        //      View
        View view = getLayoutInflater().inflate(R.layout.dialog_add_prodcut, null);
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
        product_name.setText(product.getName());
        product_banner.setText(product.getImg_banner());
        product_description.setText(product.getDescription());
        product_config.setText(product.getConfig());
        product_original_price.setText(product.getOriginal_price() + "");
        product_price.setText(product.getPrice() + "");
        category_id = 1;
        rdo_category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.category_computer:
                        category_id = 1;
                        break;
                    case R.id.category_phone:
                        category_id = 2;
                        break;
                    case R.id.category_other:
                        category_id = 3;
                        break;
                }
            }
        });
        switch (category.getId()) {
            case 1:
                rdo_category.check(R.id.category_computer);
                break;
            case 2:
                rdo_category.check(R.id.category_phone);
                break;
            case 3:
                rdo_category.check(R.id.category_other);
                break;
        }

        //      Bottom Sheet Dialog
         bottomSheetProduct = new BottomSheetDialog(this);
        bottomSheetProduct.setContentView(view);

        //      Button

        btn_cancel.setOnClickListener(v -> bottomSheetProduct.dismiss());
        btn_ok.setOnClickListener(v -> {
            //      data
            String productName = product_name.getText().toString().trim();
            String productBanner = product_banner.getText().toString().trim();
            String productConfig = product_config.getText().toString().trim();
            String productDescription = product_description.getText().toString().trim();
            String productOriginalPrice = product_original_price.getText().toString().trim();
            String productPrice = product_price.getText().toString().trim();


            //      check
            if (productName.isEmpty() || productBanner.isEmpty() || productConfig.isEmpty() || productDescription.isEmpty() || productOriginalPrice.isEmpty() || productPrice.isEmpty()) {
                return;
            }

            try {
                double originalPrice = Double.parseDouble(productOriginalPrice);
                double price = Double.parseDouble(productPrice);

                //      update 'Product'
                Product product = new Product(this.product.getId(),productName, productBanner, productDescription, productConfig, originalPrice, price, new Category(category_id));
                boolean check = productDAO.updateDatabase(product);

                Toast.makeText(this, check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

                bottomSheetProduct.dismiss();
                bottomSheetProduct.setCancelable(true);

                hideFAB();
                finish();
            } catch (NumberFormatException e) {
                return;
            }


        });
    }
    private void goToLink(String link){
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
    }

    private void dialogUpdateProduct() {
        //      only show at time
        if (bottomSheetProduct != null && bottomSheetProduct.isShowing()) {
            return;
        }

        bottomSheetProduct.show();
    }

    private void hideFAB() {
        btn_update.hide();
        btn_hide.hide();
        btn_update.setVisibility(View.GONE);
        btn_hide.setVisibility(View.GONE);

        btn_settings.shrink();
        isAllVisible = false;
    }

    public void addToCart(View view) {
        //      check login
        if (id == 0) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            //      bottom sheet dialog
            dialogAddToCart();

        }
    }

    private void dialogAddToCart() {
        //      View
        View view = getLayoutInflater().inflate(R.layout.dialog_add_to_cart, null);
        TextInputEditText edt_quantity = view.findViewById(R.id.edt_quantity);
        ImageButton btn_ok = view.findViewById(R.id.btn_ok);
        Button btn_order = view.findViewById(R.id.btn_order);
        edt_quantity.setText(String.valueOf(1));

        //      Bottom Sheet
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(view);

        bottomSheetDialog.show();

        //      Button
        btn_ok.setOnClickListener(v -> {
            //      check 'this product' already in cart
            boolean check = orderDetailDAO.queryDatabaseProductAvailable(product.getId(), id);

            if (check) {
                Toast.makeText(this, "Sản phẩm này đã có trong giỏ hàng!", Toast.LENGTH_SHORT).show();
                return;
            }

            String quantity = edt_quantity.getText().toString().trim();

            addToYourCart(quantity, bottomSheetDialog);

            Toast.makeText(this, "Thành công", Toast.LENGTH_SHORT).show();
        });
        btn_order.setOnClickListener(v -> {
            String quantity = edt_quantity.getText().toString().trim();

            orderNow(quantity, bottomSheetDialog);
        });

        //
        if (bottomSheetDialog != null && bottomSheetDialog.isShowing()) {
            return;
        }
    }

    private void orderNow(String your_quantity, BottomSheetDialog bottomSheetDialog) {
        try {
            //      check 'quantity'
            int quantity = Integer.parseInt(your_quantity);

            if (quantity <= 0) {
                Toast.makeText(this, "Yêu cầu số lượng lớn hơn 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            //      insert 'new Order detail' (item cart) to cart
            int orderId = orderDAO.insertDatabase(quantity * product.getPrice(), id);
            boolean check = orderDetailDAO.insertDatabase(quantity, quantity * product.getPrice(), product.getId(), orderId);

            //      switch 'Order' UI
            finish();
            Intent intent = new Intent(this, OrderActivity.class);
            intent.putExtra("INTENT_ORDER_ID", orderId);
            startActivity(intent);

        } catch (NumberFormatException e) {
            Log.e(ProductActivity.class.getSimpleName(), e.toString());

            Toast.makeText(this, "Lỗi nhập liệu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void addToYourCart(String your_quantity, BottomSheetDialog dialog) {
        try {
            //      check 'quantity'
            int quantity = Integer.parseInt(your_quantity);

            if (quantity <= 0) {
                Toast.makeText(this, "Yêu cầu số lượng phải lớn hơn 0!", Toast.LENGTH_SHORT).show();
                return;
            }

            //      insert 'new Order detail' (item cart) to cart
            boolean check = orderDetailDAO.insertDatabase(quantity, quantity * product.getPrice(), product.getId(), orderDAO.queryDatabaseGetID(id));

            Toast.makeText(this, check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

            //      Success
            if (check) {
                dialog.dismiss();
            }

        } catch (NumberFormatException e) {
            Log.e(ProductActivity.class.getSimpleName(), e.toString());

            Toast.makeText(this, "Lỗi nhập liệu!", Toast.LENGTH_SHORT).show();
        }
    }
}