package android.developer.ecommerce.activity.OBJ_manager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.SearchManager;
import android.content.Context;
import android.developer.ecommerce.DAO.ProductDAO;
import android.developer.ecommerce.adapter.OBJ_manager.HiddenProductAdapter;
import android.developer.ecommerce.model.Category;
import android.developer.ecommerce.model.Product;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import java.util.ArrayList;

public class ManageHiddenProductActivity extends AppCompatActivity {
    //#Widget
    private RecyclerView rev_hidden_product;
    private SearchView search_name;
    private void initUI(){
        rev_hidden_product = findViewById(R.id.rev_hidden_product);
        search_name = findViewById(R.id.search_name);
    }
    //#var
    //      DAO
    private ProductDAO productDAO;
    //      Adapter
    private HiddenProductAdapter adapter;
    private ArrayList<Product> list;
    //      Action Mode
    private ActionMode actionMode;
    private ActionMode.Callback callback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            mode.getMenuInflater().inflate(R.menu.action_mode_visible,menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.menu_visible:
                    visibleProduct(false);
                    mode.finish();
                    return true;
                case R.id.menu_delete:
                    visibleProduct(true);
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
    private void var(){
        //      DAO
        productDAO = new ProductDAO(this);
        //      Adapter
        list = productDAO.getDatabaseHidden();
        adapter = new HiddenProductAdapter(this, new HiddenProductAdapter.ItemClickListener() {
            @Override
            public void onClick(View itemView, Product product, int position) {
                product.setSelected(!product.isSelected());

                ((CheckBox)itemView.findViewById(R.id.product_is_selected)).setChecked(product.isSelected());

                if(product.isSelected()){
                    //      action mode 'delete'
                    actionModeVisible();
                }

                adapter.notifyDataSetChanged();
            }
        });

        //      Search
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        search_name.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        search_name.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                adapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.getFilter().filter(newText);
                return false;
            }
        });

    }
    //#LauncherUI
    private void launcherUI(){
        //      RecyclerView
        adapter.setData(list);
        rev_hidden_product.setHasFixedSize(true);
        rev_hidden_product.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        rev_hidden_product.setAdapter(adapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_hidden_product);

        //#Widget - var
        Log.e("Test Hidden","ui");
        initUI();
        Log.e("Test Hidden","var");
        var();
        Log.e("Test Hidden","launcher");

        //#LauncherUI
        launcherUI();
    }

    private void actionModeVisible() {
        if(actionMode != null){
            return;
        }

        actionMode = startSupportActionMode(callback);
    }

    private void visibleProduct(boolean isDelete){
        boolean check = false;
        for (Product product:list){
            if(product.isSelected()){
                product.setCategory(new Category(isDelete?0:product.getCategory().getId()/10));
                check = productDAO.updateDatabase(product);
            }
        }

        Toast.makeText(this, check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

        refreshData();
    }

    private void refreshData(){
        list = productDAO.getDatabaseHidden();
        adapter.setData(list);
        rev_hidden_product.setAdapter(adapter);
    }
}