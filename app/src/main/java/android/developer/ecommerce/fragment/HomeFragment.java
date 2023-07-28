package android.developer.ecommerce.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.OrderDetailDAO;
import android.developer.ecommerce.activity.CartActivity;
import android.developer.ecommerce.activity.LoginActivity;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;


import com.google.android.material.bottomnavigation.BottomNavigationView;


public class HomeFragment extends Fragment {
    //#Widget
    private LinearLayout card_computer, card_phone,card_other;
    private BottomNavigationView bottom_nav;
    private ImageButton btn_cart,btn_stats;
    private TextView account_name;
    private void initUI(View view){
        card_computer =view.findViewById(R.id.card_computer);
        card_phone =view.findViewById(R.id.card_phone);
        card_other =view.findViewById(R.id.card_other);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);
        btn_cart = view.findViewById(R.id.btn_cart);
        btn_stats = view.findViewById(R.id.btn_stats);
        account_name = view.findViewById(R.id.account_name);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;
    private String name;
    private boolean accountType;
    private boolean admin;
    //      DAO
    private OrderDetailDAO orderDetailDAO;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
        name = sharedPreferences.getString("NAME","");
        accountType = sharedPreferences.getBoolean("ACCOUNT_TYPE",false);
        admin = sharedPreferences.getBoolean("ADMIN",false);
        //      DAO
        orderDetailDAO = new OrderDetailDAO(getContext());
    }
    //#Launcher UI
    private void launcherUI(){
        //      switch Tab 'Shopping'
        card_computer.setOnClickListener(v -> {
            switchTabProduct(0);
        });
        card_phone.setOnClickListener(v -> {
            switchTabProduct(1);
        });
        card_other.setOnClickListener(v -> {
            switchTabProduct(2);
        });

        //      switch Tab 'Account'
        account_name.setText(id==0?"Xin chào!":"Xin chào, "+name+"!");
        account_name.setOnClickListener(v -> {
            bottom_nav.setSelectedItemId(R.id.account);
        });

        //      switch UI 'Cart'
        btn_cart.setVisibility(accountType?View.GONE:View.VISIBLE);
        if(id==0){
            btn_cart.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), LoginActivity.class));
            });
        }else{
            btn_cart.setOnClickListener(v -> {
                startActivity(new Intent(getActivity(), CartActivity.class));
            });
        }


        //      switch UI 'Stats'
        btn_stats.setVisibility(View.GONE);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //#Widget - Var
        initUI(view);
        var();




        //#Launcher UI
        launcherUI();

        return view;
    }

    private void switchTabProduct(int current) {
        bottom_nav.setSelectedItemId(R.id.shopping);

        try {
            Bundle bundle = new Bundle();
            bundle.putInt("BUNDLE_TAB",current);
            ShoppingFragment fragment = new ShoppingFragment();
            fragment.setArguments(bundle);
            FragmentTransaction fts = getActivity().getSupportFragmentManager().beginTransaction();
            fts.replace(R.id.frame_layout, fragment);
            fts.addToBackStack(fragment.getClass().getSimpleName());
            fts.commit();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }


}