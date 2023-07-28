package android.developer.ecommerce.adapter.OBJ_manager;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.OrderDAO;
import android.developer.ecommerce.DAO.OrderDetailDAO;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.adapter.ItemOrderTextAdapter;
import android.developer.ecommerce.model.Order;
import android.developer.ecommerce.model.OrderDetail;
import android.developer.myteamsproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class ManageOrderAdapter extends RecyclerView.Adapter<ManageOrderAdapter.ManageOrderHolder> {
    private Context context;
    private int status;
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    //      DAO
    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;
    //      Adapter
    private ArrayList<Order> list;
    private ItemOrderTextAdapter adapter;

    public ManageOrderAdapter(Context context,int status) {
        this.context = context;
        this.status = status;
        //      Shared Preferences
        sharedPreferences = context.getSharedPreferences(MainActivity.PREFS_FILE,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        //      DAO
        orderDAO = new OrderDAO(context);
        orderDetailDAO = new OrderDetailDAO(context);
    }

    public void refreshData() {
        this.list = getList();

        notifyDataSetChanged();
    }

    private ArrayList<Order> getList() {
        ArrayList<Order> listOK = new ArrayList<>();

        //      Data 'Order'
        list = orderDAO.getDatabase(status);

        //      add Data 'Order Detail' in 'Order'
        for(Order order:list){
            ArrayList<OrderDetail> orderDetails = orderDetailDAO.getDatabase(order.getId());

            order.setList(orderDetails);

            listOK.add(order);
        }

        return listOK;
    }

    @NonNull
    @Override
    public ManageOrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_manage_order, parent, false);
        return new ManageOrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageOrderHolder holder, int position) {
        Order order = list.get(position);

        //#UI
        //      Customer
        holder.customer_username.setText(order.getCustomer().getUsername());
        holder.customer_name.setText(order.getCustomer().getName());
        holder.customer_phone.setText(order.getCustomer().getPhone_number());
        holder.customer_address.setText(order.getCustomer().getAddress());
        holder.order_date.setText(textDataFormat(order.getOrder_date()));
        //      'received date'
        holder.linear_received_date.setVisibility(order.getStatus()==0?View.GONE:View.VISIBLE);
        holder.tv_received_date.setText(order.getStatus()==10?"Ngày nhận hàng":"Ngày nhận hàng dự kiến");
        holder.received_date.setText(textDataFormat(order.getReceived_date()));
        holder.order_total.setText(String.valueOf(order.getTotal()));
        //      Manager
        holder.linear_manager_email.setVisibility(order.getStatus()==0?View.GONE:View.VISIBLE);
        holder.linear_manager_name.setVisibility(order.getStatus()==0?View.GONE:View.VISIBLE);
        if(order.getManager() != null){
            holder.manager_email.setText(order.getManager().getEmail());
            holder.manager_name.setText(order.getManager().getName());
        }
        //      'order status'
        //      -1 - CHƯA ĐẶT HÀNG
        //      0 - CHỜ XÁC NHẬN
        //		1 - ĐANG GIAO
        //      10 - ĐÃ GIAO
        //      99 - ĐÃ HỦY
        holder.btn_cancel.setVisibility(order.getStatus()==0?View.VISIBLE:View.GONE);
        holder.btn_cancel.setOnClickListener(v -> {
            orderDAO.updateDatabase(order.getId(),null,99,id);

            refreshData();
        });
        holder.btn_delivering.setVisibility(order.getStatus()==0?View.VISIBLE:View.GONE);
        holder.btn_delivering.setOnClickListener(v -> {
            dialogUpdateEstimatedTime(order);


        });
        holder.btn_success.setVisibility(order.getStatus()==1?View.VISIBLE:View.GONE);
        holder.btn_success.setOnClickListener(v -> {
            orderDAO.updateDatabase(order.getId(),10);

            refreshData();
        });
        holder.btn_hide.setVisibility(order.getStatus()==10?View.VISIBLE:View.GONE);
        holder.btn_hide.setVisibility(order.getStatus()==99?View.VISIBLE:View.GONE);
        switch (order.getStatus()) {
            case 0:

                break;
            case 1:

                break;
            case 10:

                break;
            case 99:

                break;

        }
        //      RecyclerView 'Order Detail'
        adapter = new ItemOrderTextAdapter(context);
        holder.rev_order_detail.setHasFixedSize(true);
        holder.rev_order_detail.setLayoutManager(new LinearLayoutManager(context));
        adapter.refreshData(order.getList());
        holder.rev_order_detail.setAdapter(adapter);


        //#Listener
    }



    private void dialogUpdateEstimatedTime(Order order) {
        //      View
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_estimated_date,null);
        TextInputEditText edt_estimated_date = view.findViewById(R.id.edt_estimated_date);
        edt_estimated_date.setOnClickListener(v -> {
            pickDate(edt_estimated_date);
        });
        Button btn_cancel = view.findViewById(R.id.btn_cancel);
        Button btn_ok = view.findViewById(R.id.btn_ok);

        //      Bottom Sheet Dialog
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(context);
        bottomSheetDialog.setContentView(view);
        bottomSheetDialog.show();

        //      Button
        btn_cancel.setOnClickListener(v -> bottomSheetDialog.dismiss());
        btn_ok.setOnClickListener(v -> {
            String estimatedDate = edt_estimated_date.getText().toString().trim();

            if(estimatedDate.isEmpty()){
                return;
            }

            SimpleDateFormat formatDateUI = new SimpleDateFormat("dd-MM-yyyy");
            SimpleDateFormat formatDateDB = new SimpleDateFormat("yyyyMMdd");

            try {
                estimatedDate = formatDateDB.format(formatDateUI.parse(estimatedDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            orderDAO.updateDatabase(order.getId(),estimatedDate,1,id);

            refreshData();

            bottomSheetDialog.dismiss();
        });


        //      only show one time
        if(bottomSheetDialog != null && bottomSheetDialog.isShowing()){
            return;
        }
    }

    private void pickDate(TextInputEditText edt_estimated_date) {
        DatePickerDialog pickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                String myDay = "";
                myDay = dayOfMonth<10 ? "0"+dayOfMonth : String.valueOf(dayOfMonth);

                String myMonth = "";
                myMonth = dayOfMonth<10 ? "0"+(month+1) : String.valueOf((month+1));

                edt_estimated_date.setText(myDay+"-"+myMonth+"-"+year);
            }
        }, Calendar.getInstance().get(Calendar.YEAR) , Calendar.getInstance().get(Calendar.MONTH) , Calendar.getInstance().get(Calendar.DAY_OF_MONTH));

        pickerDialog.show();
    }


    private String textDataFormat(String text){
        if( text == null){
            return "";
        }
        String day = text.substring(6);
        String month = text.substring(4,6);
        String year = text.substring(0,4);

        return day+"-"+month+"-"+year;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ManageOrderHolder extends RecyclerView.ViewHolder {
        private TextView order_date, received_date, order_total,
                customer_username,customer_name,customer_phone,customer_address,
                manager_email,manager_name,
                tv_received_date;
        private RecyclerView rev_order_detail;
        private Button btn_cancel ,btn_delivering ,btn_success, btn_hide;
        private LinearLayout linear_received_date, linear_manager_email, linear_manager_name;

        public ManageOrderHolder(@NonNull View itemView) {
            super(itemView);

            order_date = itemView.findViewById(R.id.order_date);
            received_date = itemView.findViewById(R.id.received_date);
            order_total = itemView.findViewById(R.id.order_total);
            rev_order_detail = itemView.findViewById(R.id.rev_order_detail);
            customer_username = itemView.findViewById(R.id.customer_username);
            customer_name = itemView.findViewById(R.id.customer_name);
            customer_phone = itemView.findViewById(R.id.customer_phone);
            customer_address = itemView.findViewById(R.id.customer_address);
            manager_email = itemView.findViewById(R.id.manager_email);
            manager_name = itemView.findViewById(R.id.manager_name);

            btn_cancel= itemView.findViewById(R.id.btn_cancel);
            btn_delivering= itemView.findViewById(R.id.btn_delivering);
            btn_success= itemView.findViewById(R.id.btn_success);
            btn_hide= itemView.findViewById(R.id.btn_hide);

            tv_received_date= itemView.findViewById(R.id.tv_received_date);

            linear_received_date = itemView.findViewById(R.id.linear_received_date);
            linear_manager_email= itemView.findViewById(R.id.linear_manager_email);
            linear_manager_name= itemView.findViewById(R.id.linear_manager_name);
        }
    }
}

