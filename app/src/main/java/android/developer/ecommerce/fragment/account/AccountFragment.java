package android.developer.ecommerce.fragment.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.CustomerDAO;
import android.developer.ecommerce.activity.LoginActivity;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.fragment.OrderFragment;
import android.developer.myteamsproject.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;


public class AccountFragment extends Fragment {
    //#Widget
    private TextView customer_name;
    private LinearLayout linear_order,linear_profile,linear_password,linear_logout;
    private BottomNavigationView bottom_nav;
    private ImageButton btn_switch_confirm ,btn_switch_delivering, btn_switch_success ,btn_switch_canceled;

    private void initUI(View view){
        customer_name = view.findViewById(R.id.customer_name);
        linear_order = view.findViewById(R.id.linear_order);
        linear_profile = view.findViewById(R.id.linear_profile);
        linear_password = view.findViewById(R.id.linear_password);
        linear_logout = view.findViewById(R.id.linear_logout);
        bottom_nav = getActivity().findViewById(R.id.bottom_nav);

        btn_switch_confirm= view.findViewById(R.id.btn_switch_confirm);
        btn_switch_delivering= view.findViewById(R.id.btn_switch_delivering);
        btn_switch_success= view.findViewById(R.id.btn_switch_success);
        btn_switch_canceled= view.findViewById(R.id.btn_switch_canceled);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String userName;
    private String name;
    private String password;
    private String phoneNumber;
    private String address;
    //      DAO
    private CustomerDAO customerDAO;
    //      Dialog - Bottom Sheet Dialog
    private BottomSheetDialog bottomSheetProfile,bottomSheetPassword;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE,Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        userName = sharedPreferences.getString("USERNAME","");
        name = sharedPreferences.getString("NAME","");
        password = sharedPreferences.getString("PASSWORD","");
        phoneNumber = sharedPreferences.getString("PHONE_NUMBER","");
        address = sharedPreferences.getString("ADDRESS","");
        //      DAO
        customerDAO = new CustomerDAO(getContext());

        //      Dialog - Bottom Sheet Dialog
        dialogProfile();
        dialogPassword();
    }
    //#LauncherUI
    private void launcherUI(){
        //      Info
        customer_name.setText(name);

        //      Order
        linear_order.setOnClickListener(v -> {
            bottom_nav.setSelectedItemId(R.id.order);
        });
        btn_switch_confirm.setOnClickListener(v -> {
            switchTabOrder(0);
        });
        btn_switch_delivering.setOnClickListener(v -> {
            switchTabOrder(1);
        });
        btn_switch_success.setOnClickListener(v -> {
            switchTabOrder(2);
        });
        btn_switch_canceled.setOnClickListener(v -> {
            switchTabOrder(3);
        });


        //      Profile
        linear_profile.setOnClickListener(v -> {
                dialogChangeProfile();
        });

        //      Password
        linear_password.setOnClickListener(v -> {
            dialogChangePassword();
        });

        //      Logout
        linear_logout.setOnClickListener(v -> {
            accountLogout();
        });


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        //#Widget - var
        initUI(view);
        var();

        //#LauncherUI
        launcherUI();

        return view;
    }

    //      Dialog Password
    private void dialogChangePassword(){
        //      only show at time
        if(bottomSheetPassword != null && bottomSheetProfile.isShowing()){
            return;
        }

        bottomSheetPassword.show();
    }
    private void dialogPassword(){
        //      View
        View view = getLayoutInflater().inflate(R.layout.dialog_change_password,null);
        TextInputEditText input_password = view.findViewById(R.id.input_password);
        TextInputEditText input_new_password = view.findViewById(R.id.input_new_password);
        TextInputEditText input_new_password_again = view.findViewById(R.id.input_new_password_again);
        Button btn_change_password = view.findViewById(R.id.btn_change_password);

        //      Bottom Sheet
        bottomSheetPassword = new BottomSheetDialog(getContext());
        bottomSheetPassword.setContentView(view);

        //      Button
        btn_change_password.setOnClickListener(v -> {
            String password = input_password.getText().toString().trim();
            String newPassword = input_new_password.getText().toString().trim();
            String newPasswordAgain = input_new_password_again.getText().toString().trim();

            if(password.isEmpty() || newPassword.isEmpty() || newPasswordAgain.isEmpty()){
                Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!newPassword.equals(newPasswordAgain)){
                Toast.makeText(getContext(), "Mật khẩu không khớp!", Toast.LENGTH_SHORT).show();
                return;
            }

            if(!this.password.equals(password)){
                Toast.makeText(getContext(), "Sai mật khẩu!", Toast.LENGTH_SHORT).show();
                return;
            }

            //      update 'customer'
            boolean check = customerDAO.updateDatabase(id,newPassword);
            Toast.makeText(getContext(), check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

            bottomSheetProfile.dismiss();

            //      logout
            accountLogout();

        });
    }

    //      Dialog Profile
    private void dialogChangeProfile(){
        //      only show at time
        if(bottomSheetProfile != null && bottomSheetProfile.isShowing()){
            return;
        }

        bottomSheetProfile.show();
    }
    private void dialogProfile() {
        //      View
        View view = getLayoutInflater().inflate(R.layout.dialog_change_profile,null);
        TextInputEditText input_username = view.findViewById(R.id.input_username);
        TextInputEditText input_name = view.findViewById(R.id.input_name);
        TextInputEditText input_phone = view.findViewById(R.id.input_phone);
        TextInputEditText input_address = view.findViewById(R.id.input_address);
        ImageButton btn_unlock_change = view.findViewById(R.id.btn_unlock_change);
        Button btn_change_profile = view.findViewById(R.id.btn_change_profile);
        //
        input_username.setText(userName);
        input_name.setText(name);
        input_phone.setText(phoneNumber);
        input_address.setText(address);
        input_username.setOnClickListener(v -> Toast.makeText(getContext(), "Không thể thay đổi tên tài khoản!", Toast.LENGTH_SHORT).show());

        //      Bottom Sheet
        bottomSheetProfile = new BottomSheetDialog(getContext());
        bottomSheetProfile.setContentView(view);

        //      Button
        btn_unlock_change.setOnClickListener(v->{
            input_name.setFocusableInTouchMode(true);
            input_phone.setFocusableInTouchMode(true);
            input_address.setFocusableInTouchMode(true);
            input_name.setFocusable(true);
            input_phone.setFocusable(true);
            input_address.setFocusable(true);

            btn_unlock_change.setVisibility(View.GONE);
            btn_change_profile.setVisibility(View.VISIBLE);
        });

        btn_change_profile.setOnClickListener(v -> {
            String newName = input_name.getText().toString().trim();
            String newPhone = input_phone.getText().toString().trim();
            String newAddress = input_address.getText().toString().trim();

            if(newName.isEmpty() || newPhone.isEmpty() || newAddress.isEmpty()){
                Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            //      update 'customer'
            boolean check = customerDAO.updateDatabase(id,newName,newPhone,newAddress);

            Toast.makeText(getContext(), check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

            bottomSheetProfile.dismiss();

            //      save Shared Preferences
            if(check){
                customerDAO.saveSharedPreferences(customerDAO.queryDatabaseAvailable(userName,password));

                restartFragment();
            }

        });

    }

    private void restartFragment(){
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.frame_layout, new AccountFragment());
        tr.commit();
    }

    public void accountLogout() {
        //      reset 'current Activity'
        getActivity().finish();
        Intent intentReset = getActivity().getIntent();
        startActivity(intentReset);

        Intent intent = new Intent(getActivity(), LoginActivity.class);
        startActivity(intent);

        //      clear Shared preferences
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    private void switchTabOrder(int current) {
        bottom_nav.setSelectedItemId(R.id.order);

        try {
            Bundle bundle = new Bundle();
            bundle.putInt("BUNDLE_TAB",current);
            OrderFragment fragment = new OrderFragment();
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