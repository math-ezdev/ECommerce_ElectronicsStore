package android.developer.ecommerce.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.adapter.OrderPagerAdapter;
import android.developer.myteamsproject.R;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class OrderFragment extends Fragment {
    //#Widget
    private TabLayout tab;
    private ViewPager2 view_pager;
    private void initUI(View view){
        tab = view.findViewById(R.id.tab);
        view_pager = view.findViewById(R.id.view_pager);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;
    private String name;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
        name = sharedPreferences.getString("NAME","");
    }
    //#Launcher UI
    private void launcherUI(){
        //      Tab Layout + View Pager 2
        OrderPagerAdapter adapter = new OrderPagerAdapter(getActivity());
        view_pager.setAdapter(adapter);

        new TabLayoutMediator(tab, view_pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Chờ xác nhận");
                        break;
                    case 1:
                        tab.setText("Đang giao");
                        break;
                    case 2:
                        tab.setText("Giao thành công");
                        break;
                    case 3:
                        tab.setText("Hủy bỏ");
                        break;
                    default:
                }
            }
        }).attach();

        //      switch Tab ViewPager
        //      (vs Màn hình sản phẩm dc gọi từ tab 'Account')
        if (getArguments() != null) {
            view_pager.setCurrentItem(getArguments().getInt("BUNDLE_TAB"));
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_order, container, false);

        //#Widget - Var
        initUI(view);
        var();




        //#Launcher UI
        launcherUI();

        Log.e(OrderFragment.class.getSimpleName(),sharedPreferences.getBoolean("ACCOUNT_TYPE",false)+"");

        return view;
    }
}