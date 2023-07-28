package android.developer.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.developer.ecommerce.activity.ProductActivity;
import android.developer.ecommerce.model.OrderDetail;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.OrderDetailHolder> {
    private Context context;
    private ArrayList<OrderDetail> list;
    //
    private ItemClickListener itemClickListener;

    public OrderDetailAdapter(Context context,ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void refreshData(ArrayList<OrderDetail> list){
        this.list = list;

        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public OrderDetailHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail,parent,false);
        return new OrderDetailHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderDetailHolder holder, int position) {
        OrderDetail orderDetail = list.get(position);

        //#UI
        //Picasso.get().load("https://drive.google.com/uc?id="+orderDetail.getProduct().getImg_banner()).into(holder.product_banner);
        Picasso.get().load(orderDetail.getProduct().getImg_banner()).into(holder.product_banner);
        holder.product_name.setText(orderDetail.getProduct().getName());
        holder.product_price.setText(String.valueOf(orderDetail.getProduct().getPrice()));
        holder.order_detail_total.setText(String.valueOf(orderDetail.getQuantity()*orderDetail.getProduct().getPrice()));
        holder.order_detail_quantity.setText(String.valueOf(orderDetail.getQuantity()));
        holder.order_detail_is_selected.setChecked(orderDetail.isSelected());

        //#Listener
        //      quantity
        holder.btn_down.setVisibility(holder.order_detail_is_selected.isChecked()?View.INVISIBLE:View.VISIBLE);
        holder.btn_up.setVisibility(holder.order_detail_is_selected.isChecked()?View.INVISIBLE:View.VISIBLE);
        holder.btn_up.setOnClickListener(v -> {
            quantityUp(holder,orderDetail);
        });
        holder.btn_down.setOnClickListener(v -> {
            quantityDown(holder,orderDetail);
        });
        holder.order_detail_is_selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemClickListener.onClick(holder.itemView,orderDetail,position);
            }
        });
        //      item click
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("BUNDLE_PRODUCT",orderDetail.getProduct());
            bundle.putSerializable("BUNDLE_CATEGORY", orderDetail.getProduct().getCategory());
            intent.putExtras(bundle);
            context.startActivity(intent);
        });

    }

    private void quantityDown(OrderDetailHolder holder, OrderDetail orderDetail) {
        int quantity = orderDetail.getQuantity();

        if(quantity==1){
            return;
        }

        quantity--;

        //      Data
        orderDetail.setQuantity(quantity);
        orderDetail.setTotal(quantity*orderDetail.getProduct().getPrice());

        //      UI
        holder.order_detail_quantity.setText(String.valueOf(orderDetail.getQuantity()));
        holder.order_detail_total.setText(String.valueOf(orderDetail.getTotal()));

    }

    private void quantityUp(OrderDetailHolder holder, OrderDetail orderDetail) {
        int quantity = orderDetail.getQuantity();

        quantity++;

        //      Data
        orderDetail.setQuantity(quantity);
        orderDetail.setTotal(quantity*orderDetail.getProduct().getPrice());

        //      UI
        holder.order_detail_quantity.setText(String.valueOf(orderDetail.getQuantity()));
        holder.order_detail_total.setText(String.valueOf(orderDetail.getTotal()));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onClick(View itemView , OrderDetail orderDetail, int position);
    }

    static class OrderDetailHolder extends RecyclerView.ViewHolder{
        private ImageView product_banner;
        private TextView product_name,product_price,order_detail_quantity,order_detail_total;
        private ImageButton btn_down,btn_up;
        private CheckBox order_detail_is_selected;

        public OrderDetailHolder(@NonNull View itemView) {
            super(itemView);

            product_banner = itemView.findViewById(R.id.product_banner);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            order_detail_quantity = itemView.findViewById(R.id.order_detail_quantity);
            order_detail_total = itemView.findViewById(R.id.order_detail_total);
            order_detail_is_selected = itemView.findViewById(R.id.order_detail_is_selected);
            btn_down = itemView.findViewById(R.id.btn_down);
            btn_up = itemView.findViewById(R.id.btn_up);

            setIsRecyclable(false);
        }
    }
}
