package android.developer.ecommerce.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.CustomerDAO;
import android.developer.ecommerce.DAO.OrderDAO;
import android.developer.ecommerce.DAO.OrderDetailDAO;
import android.developer.ecommerce.adapter.OrderDetailAdapter;
import android.developer.ecommerce.model.OrderDetail;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    //#Widget
    private RecyclerView rev_order_detail;
    private TextView order_total;
    private CheckBox check_all;
    private void initUI(){
        rev_order_detail = findViewById(R.id.rev_order_detail);
        order_total = findViewById(R.id.order_total);
        check_all = findViewById(R.id.check_all);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;
    //      DAO
    private OrderDetailDAO orderDetailDAO;
    private CustomerDAO customerDAO;
    private OrderDAO orderDAO;
    //      Adapter
    private OrderDetailAdapter adapter;
    //
    double total;
    ActionMode actionMode;
    ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_mode_delete,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_delete:
                    deleteOrderDetailSelected();
                    mode.finish();
                    return true;
                default:
                    return false;
            }
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            actionMode = null;
        }
    };


    private ArrayList<OrderDetail> list;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getSharedPreferences(MainActivity.PREFS_FILE,MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
        //      DAO
        orderDetailDAO = new OrderDetailDAO(this);
        customerDAO = new CustomerDAO(this);
        orderDAO = new OrderDAO(this);
        //      Adapter
        list = orderDetailDAO.getDatabaseOfCustomer(id);
        adapter = new OrderDetailAdapter(this, new OrderDetailAdapter.ItemClickListener() {
            @Override
            public void onClick(View itemView, OrderDetail orderDetail, int position) {
                orderDetail.setSelected(!orderDetail.isSelected());

                ((CheckBox)itemView.findViewById(R.id.order_detail_is_selected)).setChecked(orderDetail.isSelected());

                if(orderDetail.isSelected()){
                    total+= orderDetail.getTotal();

                    //      action mode 'delete'
                    actionDelete();
                }else {
                    total-= orderDetail.getTotal();
                }

                order_total.setText(String.valueOf(total));

                adapter.notifyDataSetChanged();
            }
        });
        //
        total = 0;


    }

    //#LauncherUI
    private void launcherUI(){
        //      Style
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //      RecyclerView
        adapter.refreshData(list);
        rev_order_detail.setHasFixedSize(true);
        rev_order_detail.setLayoutManager(new LinearLayoutManager(this));
        rev_order_detail.setAdapter(adapter);

        //      Select all item
        check_all.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    selectedAll();
                } else {
                    unSelectedAll();
                }
            }
        });
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //#Widget - var
        initUI();
        var();


        //#LauncherUI
        launcherUI();
    }

    private void unSelectedAll() {
        for (OrderDetail orderDetail : list) {
            orderDetail.setSelected(false);
            total=0;
        }

        order_total.setText(String.valueOf(total));

        //      Notify - 'isSelected' change -> update UI
        adapter.notifyDataSetChanged();
    }

    private void selectedAll() {
        unSelectedAll();

        for (OrderDetail orderDetail : list) {
            orderDetail.setSelected(true);
            total+=orderDetail.getTotal();
        }

        order_total.setText(String.valueOf(total));

        //      Notify - 'isSelected' change -> update UI
        adapter.notifyDataSetChanged();
    }

    //      Action mode 'delete'
    private void actionDelete() {
        if(actionMode != null){
            return;
        }

        actionMode = startSupportActionMode(callback);
    }
    private void deleteOrderDetailSelected() {
        boolean check = false;
        for (OrderDetail orderDetail:list){
            if(orderDetail.isSelected()){
                 check = orderDetailDAO.deleteDatabase(orderDetail.getId());

            }
        }
        unSelectedAll();

        Toast.makeText(this, check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

        refreshData();
    }

    private void refreshData(){
        list = orderDetailDAO.getDatabaseOfCustomer(id);
        adapter.refreshData(list);
        rev_order_detail.setAdapter(adapter);
    }


    //      Order
    public void switchOrderUI(View view) {
        //      check selected
        int count = 0;
        for (OrderDetail orderDetail:list){
            if(orderDetail.isSelected()){
                count++;
            }
        }
        if(count == 0){
            Toast.makeText(this, "Please choose item!", Toast.LENGTH_SHORT).show();
            return;
        }

        //      insert 'new Order' contain 'item order detail' was selected
        int orderId = orderDAO.insertDatabase(total,id);
        //      update 'Order detail' was selected to 'Order' above
        updateOrderDetailSelected(orderId);

        //      reset selected item
        unSelectedAll();

        //      switch Order activity
        finish();
        Intent intent = new Intent(this,OrderActivity.class);
        intent.putExtra("INTENT_ORDER_ID",orderId);
        startActivity(intent);
    }
    private void updateOrderDetailSelected(int orderId) {
        for (OrderDetail orderDetail:list){
            if(orderDetail.isSelected()){
                orderDetailDAO.updateDatabaseOrderID(orderDetail,orderId);
                orderDetailDAO.insertDatabase(orderDetail.getQuantity(),orderDetail.getTotal(),orderDetail.getProduct().getId(), orderDetail.getOrder().getId());
            }
        }
    }


}