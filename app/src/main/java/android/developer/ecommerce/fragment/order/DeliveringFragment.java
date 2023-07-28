package android.developer.ecommerce.fragment.order;

import android.content.Context;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.OrderDAO;
import android.developer.ecommerce.DAO.OrderDetailDAO;
import android.developer.ecommerce.adapter.OBJ_manager.ManageOrderAdapter;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.adapter.OrderAdapter;
import android.developer.myteamsproject.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class DeliveringFragment extends Fragment {
    //#Widget
    private RecyclerView rev_order_confirm;
    private void initUI(View view){
        rev_order_confirm = view.findViewById(R.id.rev_order_confirm);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;
    private String name;
    private boolean accountType;
    //      DAO
    private OrderDAO orderDAO;
    private OrderDetailDAO orderDetailDAO;
    //      Adapter
    private OrderAdapter adapter;
    private ManageOrderAdapter manageOrderAdapter;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
        name = sharedPreferences.getString("NAME","");
        accountType = sharedPreferences.getBoolean("ACCOUNT_TYPE",false);
        //      DAO
        orderDAO = new OrderDAO(getContext());
        orderDetailDAO = new OrderDetailDAO(getContext());
        //      Adapter
        adapter = new OrderAdapter(getContext(),1);
        manageOrderAdapter = new ManageOrderAdapter(getContext(),1);
    }
    //#Launcher UI
    private void launcherUI(){
        //      Recycler View
        rev_order_confirm.setHasFixedSize(true);
        rev_order_confirm.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter.refreshData();
        manageOrderAdapter.refreshData();
        rev_order_confirm.setAdapter(accountType?manageOrderAdapter:adapter);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_delivering, container, false);

        //#Widget - Var
        initUI(view);
        var();




        //#Launcher UI
        launcherUI();

        Log.e(DeliveringFragment.class.getSimpleName(),sharedPreferences.getBoolean("ACCOUNT_TYPE",false)+"");


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter.refreshData();
        manageOrderAdapter.refreshData();
    }
}