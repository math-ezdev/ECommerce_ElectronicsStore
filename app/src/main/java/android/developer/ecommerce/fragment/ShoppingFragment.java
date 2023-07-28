package android.developer.ecommerce.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.adapter.ProductPagerAdapter;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;


import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;


public class ShoppingFragment extends Fragment {
    //#Widget
    private ViewPager2 view_pager;
    private TabLayout tab;
    private ImageView img_banner;
    private AppBarLayout app_bar;
    private void initUI(View view) {
        view_pager = view.findViewById(R.id.view_pager);
        tab = view.findViewById(R.id.tab);
        img_banner = view.findViewById(R.id.img_banner);
        app_bar = view.findViewById(R.id.app_bar);
    }

    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private boolean accountType;
    private void var() {
        //      Shared preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE, Context.MODE_PRIVATE);
        accountType = sharedPreferences.getBoolean("ACCOUNT_TYPE",false);
    }

    //#Launcher UI
    private void launcherUI() {
        //      Tabs 'Product'
        ProductPagerAdapter pagerAdapter = new ProductPagerAdapter(getActivity());
        view_pager.setAdapter(pagerAdapter);

        //      Title
        new TabLayoutMediator(tab, view_pager, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText("Máy tính");
                        break;
                    case 1:
                        tab.setText("Điện thoại");
                        break;
                    case 2:
                        tab.setText("Khác");
                        break;
                    default:
                }
            }
        }).attach();



        //      switch Tab ViewPager
        //      (vs Màn hình sản phẩm dc gọi từ tab 'Home')
        if (getArguments() != null) {
            view_pager.setCurrentItem(getArguments().getInt("BUNDLE_TAB"));
        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shopping, container, false);

        //Widget - Var
        initUI(view);
        var();





        //Launcher UI
        launcherUI();


        return view;
    }




}