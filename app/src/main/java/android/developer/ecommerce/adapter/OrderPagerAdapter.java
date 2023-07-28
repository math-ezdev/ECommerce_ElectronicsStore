package android.developer.ecommerce.adapter;

import android.developer.ecommerce.fragment.order.CancelledFragment;
import android.developer.ecommerce.fragment.order.ConfirmFragment;
import android.developer.ecommerce.fragment.order.DeliveringFragment;
import android.developer.ecommerce.fragment.order.SuccessFragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class OrderPagerAdapter extends FragmentStateAdapter {
    public OrderPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new ConfirmFragment();
            case 1:
                return new DeliveringFragment();
            case 2:
                return new SuccessFragment();
            case 3:
                return new CancelledFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
