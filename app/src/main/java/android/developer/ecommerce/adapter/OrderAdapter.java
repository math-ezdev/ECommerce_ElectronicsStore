package android.developer.ecommerce.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.OrderDAO;
import android.developer.ecommerce.DAO.OrderDetailDAO;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.model.Order;
import android.developer.ecommerce.model.OrderDetail;
import android.developer.myteamsproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderHolder> {
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


    public OrderAdapter(Context context,int status) {
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
        list = getList();

        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public OrderHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderHolder holder, int position) {
        Order order = list.get(position);

        //#UI
        holder.order_date.setText(textDataFormat(order.getOrder_date()));
        //      'received date'
        holder.tv_received_date.setText(order.getStatus()==10?"Ngày nhận hàng":"Ngày nhận hàng dự kiến");
        holder.received_date.setText(textDataFormat(order.getReceived_date()));
        holder.order_total.setText(String.valueOf(order.getTotal()));
        //      'order status'
        //      -1 - CHƯA ĐẶT HÀNG
        //      0 - CHỜ XÁC NHẬN
        //		1 - ĐANG GIAO
        //      10 - ĐÃ GIAO
        //      99 - ĐÃ HỦY
        holder.btn_cancel.setVisibility(order.getStatus()==0?View.VISIBLE:View.GONE);
        holder.btn_cancel.setOnClickListener(v -> {
            orderDAO.updateDatabase(order.getId(),99);

            refreshData();

            notifyDataSetChanged();
        });
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

    public ArrayList<Order> getList(){
        ArrayList<Order> listOK = new ArrayList<>();

        //      Data 'Order'
        list = orderDAO.getDatabaseOfCustomer(id,status);

        //      add Data 'Order Detail' in 'Order'
        for(Order order:list){
            ArrayList<OrderDetail> orderDetails = orderDetailDAO.getDatabaseOfCustomerByOrderID(order.getId());

            order.setList(orderDetails);

            listOK.add(order);
        }

        return listOK;
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

    static class OrderHolder extends RecyclerView.ViewHolder {
        private TextView order_date, received_date, order_total,tv_received_date;
        private RecyclerView rev_order_detail;
        private Button btn_cancel;

        public OrderHolder(@NonNull View itemView) {
            super(itemView);

            order_date = itemView.findViewById(R.id.order_date);
            received_date = itemView.findViewById(R.id.received_date);
            order_total = itemView.findViewById(R.id.order_total);
            rev_order_detail = itemView.findViewById(R.id.rev_order_detail);
            btn_cancel = itemView.findViewById(R.id.btn_cancel);
            tv_received_date = itemView.findViewById(R.id.tv_received_date);
        }
    }
}
