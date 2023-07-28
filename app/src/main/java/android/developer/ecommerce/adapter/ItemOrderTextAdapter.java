package android.developer.ecommerce.adapter;

import android.content.Context;
import android.developer.ecommerce.model.OrderDetail;
import android.developer.myteamsproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemOrderTextAdapter extends RecyclerView.Adapter<ItemOrderTextAdapter.ItemTextHolder> {
    private Context context;
    private ArrayList<OrderDetail> list;

    public ItemOrderTextAdapter(Context context) {
        this.context = context;
    }

    public void refreshData(ArrayList<OrderDetail> list) {
        this.list = list;

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public ItemTextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_detail_text, parent, false);
        return new ItemTextHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemTextHolder holder, int position) {
        OrderDetail orderDetail = list.get(position);

        //#UI
        holder.product_name.setText(orderDetail.getProduct().getName()+" ( "+orderDetail.getQuantity()+" )");
        holder.order_detail_total.setText(String.valueOf(orderDetail.getQuantity() * orderDetail.getProduct().getPrice()));


        //#Listener


    }



    @Override
    public int getItemCount() {
        return list.size();
    }



    static class ItemTextHolder extends RecyclerView.ViewHolder {
        private TextView product_name, order_detail_total;


        public ItemTextHolder(@NonNull View itemView) {
            super(itemView);

            product_name = itemView.findViewById(R.id.product_name);
            order_detail_total = itemView.findViewById(R.id.order_detail_total);


        }
    }
}

