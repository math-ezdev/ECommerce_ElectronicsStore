package android.developer.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.CustomerDAO;
import android.developer.ecommerce.DAO.OrderDAO;
import android.developer.ecommerce.DAO.OrderDetailDAO;
import android.developer.ecommerce.adapter.ItemOrderAdapter;
import android.developer.ecommerce.channel.MyApplication;
import android.developer.ecommerce.model.Order;
import android.developer.ecommerce.model.OrderDetail;
import android.developer.myteamsproject.R;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class OrderActivity extends AppCompatActivity {
    //#Widget
    private TextInputEditText edt_address,edt_phone;
    private RecyclerView rev_order_detail;
    private TextView order_quantity,  order_total, tv_total,tv_price;

    private void initUI() {
        edt_address = findViewById(R.id.edt_address);
        edt_phone = findViewById(R.id.edt_phone);
        order_quantity = findViewById(R.id.order_quantity);
        order_total = findViewById(R.id.order_total);
        tv_total = findViewById(R.id.tv_total);
        tv_price = findViewById(R.id.tv_price);
        rev_order_detail = findViewById(R.id.rev_order_detail);
    }

    //#var
    //
    private int orderID;
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String username;
    private String password;
    private String phoneNumber;
    private String address;
    //      DAO
    private OrderDetailDAO orderDetailDAO;
    private OrderDAO orderDAO;
    private CustomerDAO customerDAO;
    private Order order;
    //      Adapter
    private ItemOrderAdapter adapter;
    private ArrayList<OrderDetail> list;
    private void var() {
        //
        orderID = getIntent().getIntExtra("INTENT_ORDER_ID",0);
        //      Shared Preferences
        sharedPreferences = getSharedPreferences(MainActivity.PREFS_FILE, MODE_PRIVATE);
        id = sharedPreferences.getInt("ID", 0);
        username = sharedPreferences.getString("USERNAME", "");
        password = sharedPreferences.getString("PASSWORD", "");
        phoneNumber = sharedPreferences.getString("PHONE_NUMBER", "");
        address = sharedPreferences.getString("ADDRESS", "");
        //      DAO
        orderDetailDAO = new OrderDetailDAO(this);
        orderDAO = new OrderDAO(this);
        customerDAO = new CustomerDAO(this);
        order = orderDAO.getItemDatabase(orderID);
        //      Adapter
        list = orderDetailDAO.getDatabaseOfCustomerByOrderID(orderID);
        adapter = new ItemOrderAdapter(this);

    }

    //#LauncherUI
    private void launcherUI() {
        //      Style
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //      Recycler View
        rev_order_detail.setHasFixedSize(true);
        rev_order_detail.setLayoutManager(new LinearLayoutManager(this));
        adapter.refreshData(list);
        rev_order_detail.setAdapter(adapter);

        //      UI
        edt_address.setText(address);
        edt_phone.setText(phoneNumber);
        tv_price.setText(String.valueOf(order.getTotal()));
        order_quantity.setText(String.valueOf(list.size()));
        order_total.setText(String.valueOf(order.getTotal()));
        tv_total.setText(String.valueOf(order.getTotal()));


    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);

        //#Widget - var
        initUI();
        var();


        //#LauncherUI
        launcherUI();
    }

    public void order(View view) {
        //      check 'Address'
        String checkAddress = edt_address.getText().toString().trim();
        String checkPhoneNumber = edt_phone.getText().toString().trim();

        if(checkPhoneNumber.isEmpty() || checkAddress.isEmpty()){
            Toast.makeText(this, "Hãy điền thông tin để giao hàng!", Toast.LENGTH_SHORT).show();
            return;
        }

        //      update 'status'
        boolean check = orderDAO.updateDatabase(orderID,0) && customerDAO.updateDatabase(id,checkPhoneNumber,checkAddress);

        //      save Shared Preferences
        if(check){
            customerDAO.saveSharedPreferences(customerDAO.queryDatabaseAvailable(username,password));

            pushNotification();

            finish();
        }

        //      Success
        Toast.makeText(this, check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();
    }

    private void pushNotification() {
        //Task Stack : Đồng bộ vs nút Back
        Intent intent = new Intent(this, MainActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(this);
        taskStackBuilder.addNextIntentWithParentStack(intent);
        //Open Regular Activity
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0,PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_IMMUTABLE);


        //create Notification
        Notification notification = new NotificationCompat.Builder(this, MyApplication.CHANNEL_ID)
                .setContentTitle("Bạn đã đặt hàng thành công")
                .setContentText("Xem chi tiết trong ứng dụng...")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(Uri.parse("android.resource://"
                        + getPackageName() + "/" + R.raw.sound_order_success))
                .setContentIntent(pendingIntent)

                .build();


        //push Notification to UI (status bar)
        NotificationManagerCompat managerCompat = NotificationManagerCompat.from(this);
        managerCompat.notify(0, notification);


        // @Note
        // #Task Stack : Đồng bộ với nút Back -> khai báo Parent Activity của các Activity trong Manifest
    }
}