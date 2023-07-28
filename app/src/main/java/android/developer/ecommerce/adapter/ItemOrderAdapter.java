package android.developer.ecommerce.adapter;

import android.content.Context;
import android.developer.ecommerce.model.OrderDetail;
import android.developer.myteamsproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ItemOrderAdapter extends RecyclerView.Adapter<ItemOrderAdapter.ItemHolder> {
    private Context context;
    private ArrayList<OrderDetail> list;

    public ItemOrderAdapter(Context context) {
        this.context = context;
    }

    public void refreshData(ArrayList<OrderDetail> list) {
        this.list = list;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        OrderDetail orderDetail = list.get(position);

        //#UI
//        Picasso.get().load("https://drive.google.com/uc?id=" + orderDetail.getProduct().getImg_banner()).into(holder.product_banner);
        Picasso.get().load(orderDetail.getProduct().getImg_banner()).into(holder.product_banner);
        holder.product_name.setText(orderDetail.getProduct().getName());
        holder.product_price.setText(String.valueOf(orderDetail.getProduct().getPrice()));
        holder.order_detail_total.setText(String.valueOf(orderDetail.getQuantity() * orderDetail.getProduct().getPrice()));
        holder.order_detail_quantity.setText(String.valueOf(orderDetail.getQuantity()));
        //      hide Widget
        holder.btn_down.setVisibility(View.INVISIBLE);
        holder.btn_up.setVisibility(View.INVISIBLE);
        holder.order_detail_is_selected.setVisibility(View.GONE);

        //#Listener


    }



    @Override
    public int getItemCount() {
        return list.size();
    }



    static class ItemHolder extends RecyclerView.ViewHolder {
        private ImageView product_banner;
        private TextView product_name, product_price, order_detail_quantity, order_detail_total;
        private ImageButton btn_down, btn_up;
        private CheckBox order_detail_is_selected;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);

            product_banner = itemView.findViewById(R.id.product_banner);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            order_detail_quantity = itemView.findViewById(R.id.order_detail_quantity);
            order_detail_total = itemView.findViewById(R.id.order_detail_total);
            order_detail_is_selected = itemView.findViewById(R.id.order_detail_is_selected);
            btn_down = itemView.findViewById(R.id.btn_down);
            btn_up = itemView.findViewById(R.id.btn_up);

        }
    }
}
