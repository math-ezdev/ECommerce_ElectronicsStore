package android.developer.ecommerce.adapter;

import android.developer.ecommerce.fragment.product.ComputerFragment;
import android.developer.ecommerce.fragment.product.OtherFragment;
import android.developer.ecommerce.fragment.product.PhoneFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


public class ProductPagerAdapter extends FragmentStateAdapter {
    public ProductPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ComputerFragment();
            case 1:
                return new PhoneFragment();
            case 2:
                return new OtherFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
