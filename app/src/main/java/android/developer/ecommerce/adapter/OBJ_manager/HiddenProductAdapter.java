package android.developer.ecommerce.adapter.OBJ_manager;

import android.content.Context;
import android.developer.ecommerce.model.Product;
import android.developer.myteamsproject.R;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class HiddenProductAdapter extends RecyclerView.Adapter<HiddenProductAdapter.HiddenProductHolder> implements Filterable {
    private Context context;
    private ArrayList<Product> list;
    private ArrayList<Product> listFull;
    //
    private ItemClickListener itemClickListener;

    public HiddenProductAdapter(Context context, ItemClickListener itemClickListener) {
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    public void setData(ArrayList<Product> list){
        this.list = list;
        this.listFull = list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HiddenProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_hidden_product, parent,false);
        return new HiddenProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HiddenProductHolder holder, int position) {
        Product product = list.get(position);

        //#UI
//        Picasso.get().load("https://drive.google.com/uc?id=" + product.getImg_banner()).into(holder.product_banner);
        Picasso.get().load( product.getImg_banner()).into(holder.product_banner);
        holder.product_name.setText(product.getName());
        holder.product_price.setText(String.valueOf(product.getPrice()));
        holder.product_config.setText(product.getConfig().contains("\n")?
                product.getConfig().substring(0,product.getConfig().indexOf("\n")):
                product.getConfig());
        holder.product_is_selected.setChecked(product.isSelected());

        //#Listener
        holder.product_is_selected.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                itemClickListener.onClick(holder.itemView,product,position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public interface ItemClickListener{
        void onClick(View itemView , Product product, int position);
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();

                if(search.isEmpty()){
                    list = listFull;
                }else{
                    ArrayList<Product> listSearch = new ArrayList<>();
                    for(Product product : listFull){
                        if(product.getName().toLowerCase().contains(search)){
                            listSearch.add(product);
                        }
                    }

                    list = listSearch;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = list;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list = (ArrayList<Product>) results.values;
                notifyDataSetChanged();
            }
        };
    }


    static class HiddenProductHolder extends RecyclerView.ViewHolder{
        private ImageView product_banner;
        private TextView product_name, product_price, product_config;
        private CheckBox product_is_selected;

        public HiddenProductHolder(@NonNull View itemView) {
            super(itemView);

            product_banner = itemView.findViewById(R.id.product_banner);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_config = itemView.findViewById(R.id.product_config);
            product_is_selected = itemView.findViewById(R.id.product_is_selected);
        }
    }
}
