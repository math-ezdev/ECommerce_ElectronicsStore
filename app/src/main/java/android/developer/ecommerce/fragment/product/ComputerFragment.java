package android.developer.ecommerce.fragment.product;

import android.animation.ObjectAnimator;
import android.app.SearchManager;
import android.content.Context;
import android.developer.ecommerce.DAO.ProductDAO;
import android.developer.ecommerce.adapter.ProductAdapter;
import android.developer.myteamsproject.R;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;


public class ComputerFragment extends Fragment {
    //#Widget
    private RecyclerView rev_product;
    private ImageView img_banner;
    private AppBarLayout app_bar;
    private TabLayout tab;
    private SearchView search_name;
    private AutoCompleteTextView auto_price;

    private void initUI(View view) {
        rev_product = view.findViewById(R.id.rev_product);
        img_banner = getActivity().findViewById(R.id.img_banner);
        app_bar = getActivity().findViewById(R.id.app_bar);
        tab = getActivity().findViewById(R.id.tab);
        search_name = view.findViewById(R.id.search_name);
        auto_price = view.findViewById(R.id.auto_price);

    }

    //#var
    //      DAO
    private ProductDAO productDAO;
    //      Adapter
    private ProductAdapter adapter;
    private ArrayAdapter<String> arrayAdapter;

    private void var() {
        //      DAO
        productDAO = new ProductDAO(getContext());
        //      Adapter
        adapter = new ProductAdapter(getContext());
        arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.drop_down_item, getResources().getStringArray(R.array.price));
    }

    //#Launcher UI
    private void launcherUI() {
        //      Recycler View
        refreshUI();

        //      Search
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);
        search_name.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));
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

        //      Auto Complete Text
        auto_price.setAdapter(arrayAdapter);
        auto_price.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        refreshUI();
                        break;
                    case 1:
                        //      0-499 $
                        uiPrice(0,499);
                        break;
                    case 2:
                        uiPrice(500,999);
                        //      500-999 $
                        break;
                    case 3:
                        //      1000-1999 $
                        uiPrice(1000,1999);
                        break;
                    case 4:
                        //      2000-4999 $
                        uiPrice(2000,4999);
                        break;
                }
            }
        });
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_computer, container, false);

        //#Widget - Var
        initUI(view);
        var();


        //#Launcher UI
        launcherUI();


        return view;
    }
    private void uiPrice(int priceMin,int priceMax){
        adapter.setData(productDAO.getDatabaseByCategory(1,priceMin,priceMax));
        rev_product.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rev_product.setAdapter(adapter);
    }

    private void changeBanner() {
        app_bar.setVisibility(View.INVISIBLE);
        img_banner.setImageResource(R.drawable.banner_computer);
        app_bar.setBackgroundResource(R.drawable.bg_gradient);
        tab.setBackgroundResource(R.drawable.bg_gradient);

        //      Animation
        img_banner.post(new Runnable() {
            @Override
            public void run() {
                startImageAnimation();
                app_bar.setVisibility(View.VISIBLE);
            }
        });


    }

    private void refreshUI() {
        adapter.setData(productDAO.getDatabaseByCategory(1));
        rev_product.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        rev_product.setAdapter(adapter);
    }

    private void startImageAnimation() {
        ObjectAnimator animation = ObjectAnimator.ofFloat(app_bar, "translationY", -(app_bar.getHeight()), 0);
        animation.setDuration(1100);
        animation.start();
    }

    @Override
    public void onResume() {
        Log.e(ComputerFragment.class.getSimpleName(),"onResume");
        changeBanner();
        refreshUI();
        super.onResume();
    }


}