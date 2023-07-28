package android.developer.ecommerce.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.activity.ProductActivity;
import android.developer.ecommerce.model.Product;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductHolder> implements Filterable {
    private Context context;
    private ArrayList<Product> list;
    private ArrayList<Product> listFull;
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;

    public ProductAdapter(Context context) {
        this.context = context;
        //      Shared Preferences
        sharedPreferences = context.getSharedPreferences(MainActivity.PREFS_FILE, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID", 0);
        userName = sharedPreferences.getString("USERNAME", "");
    }

    public void setData(ArrayList<Product> list) {
        this.list = list;
        this.listFull = list;

        notifyDataSetChanged();
    }

    @Override
    public ProductHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_product, parent, false);
        return new ProductHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductHolder holder, int position) {
        Product product = list.get(position);

        //#UI
        //      banner
        int banner = 3;
        switch (product.getCategory().getId()) {
            case 1:
                banner = R.drawable.ic_computer;
                break;
            case 2:
                banner = R.drawable.ic_phone;
                break;
            case 3:
                banner = R.drawable.ic_other;
                break;
        }
//        Picasso.get().load("https://drive.google.com/uc?id=" + product.getImg_banner()).error(banner).into(holder.product_banner);
        Picasso.get().load(product.getImg_banner()).error(banner).into(holder.product_banner);
        holder.product_name.setText(product.getName());
        holder.product_price.setText(String.valueOf(product.getPrice()));
        holder.product_config.setText(product.getConfig().contains("\n")?
                product.getConfig().substring(0,product.getConfig().indexOf("\n")):
                product.getConfig());

        //#Listener
        holder.product.setOnClickListener(v -> {
            Intent intent = new Intent(context, ProductActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("BUNDLE_PRODUCT", product);
            bundle.putSerializable("BUNDLE_CATEGORY", product.getCategory());
            intent.putExtras(bundle);
            context.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return list.size();
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

    public static class ProductHolder extends RecyclerView.ViewHolder {
        private LinearLayout product;
        private ImageView product_banner;
        private TextView product_name, product_price, product_config;

        public ProductHolder(@NonNull View itemView) {
            super(itemView);

            product = itemView.findViewById(R.id.product);
            product_banner = itemView.findViewById(R.id.product_banner);
            product_name = itemView.findViewById(R.id.product_name);
            product_price = itemView.findViewById(R.id.product_price);
            product_config = itemView.findViewById(R.id.product_config);
        }
    }
}
