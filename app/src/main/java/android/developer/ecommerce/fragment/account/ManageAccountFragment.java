package android.developer.ecommerce.fragment.account;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.ManagerDAO;
import android.developer.ecommerce.activity.LoginActivity;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.activity.OBJ_manager.ManageHiddenProductActivity;
import android.developer.myteamsproject.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.textfield.TextInputEditText;


public class ManageAccountFragment extends Fragment {
    //#Widget
    private TextView manager_name;
    private LinearLayout linear_profile,linear_password,linear_logout,linear_stats, linear_add_manager,linear_product_hide;

    private void initUI(View view){
        manager_name = view.findViewById(R.id.manager_name);
        linear_profile = view.findViewById(R.id.linear_profile);
        linear_password = view.findViewById(R.id.linear_password);
        linear_logout = view.findViewById(R.id.linear_logout);
        linear_stats = view.findViewById(R.id.linear_stats);
        linear_add_manager = view.findViewById(R.id.linear_add_manager);
        linear_product_hide = view.findViewById(R.id.linear_product_hide);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    private int id;
    private String email;
    private String name;
    private String password;
    private boolean admin;
    //      DAO
    private ManagerDAO managerDAO;
    //      Dialog - Bottom Sheet Dialog
    private BottomSheetDialog bottomSheetProfile,bottomSheetPassword,bottomSheetManager;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE, Context.MODE_PRIVATE);
        id = sharedPreferences.getInt("ID",0);
        email = sharedPreferences.getString("EMAIL","");
        name = sharedPreferences.getString("NAME","");
        password = sharedPreferences.getString("PASSWORD","");
        admin = sharedPreferences.getBoolean("ADMIN",false);
        //      DAO
        managerDAO = new ManagerDAO(getContext());

        //      Dialog - Bottom Sheet Dialog
        dialogProfile();
        dialogPassword();
        dialogManager();
    }
    //#LauncherUI
    private void launcherUI(){
        //      Info
        manager_name.setText(name);

        //      Stats
        linear_stats.setVisibility(View.GONE);
        linear_stats.setOnClickListener(v -> {

        });

        //      add 'Manager'
        linear_add_manager.setVisibility(admin?View.VISIBLE:View.GONE);
        linear_add_manager.setOnClickListener(v -> {
            dialogAddManager();
        });

        //      manage 'products' were hide
        linear_product_hide.setOnClickListener(v -> {
            startActivity(new Intent(getContext(), ManageHiddenProductActivity.class));
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
        View view = inflater.inflate(R.layout.fragment_manage_account, container, false);

        //#Widget - var
        initUI(view);
        var();

        //#LauncherUI
        launcherUI();

        return view;
    }
    //      Dialog Manager
    private void dialogAddManager(){
        //      only show at time
        if(bottomSheetManager != null && bottomSheetManager.isShowing()){
            return;
        }

        bottomSheetManager.show();
    }
    private void dialogManager(){
        //      View
        View view = getLayoutInflater().inflate(R.layout.dialog_add_manager,null);
        TextInputEditText input_email = view.findViewById(R.id.input_email);
        TextInputEditText input_name = view.findViewById(R.id.input_name);
        TextInputEditText input_password = view.findViewById(R.id.input_password);
        CheckBox chk_admin = view.findViewById(R.id.chk_admin);
        Button btn_add_manager = view.findViewById(R.id.btn_add_manager);

        //      Bottom Sheet
        bottomSheetManager = new BottomSheetDialog(getContext());
        bottomSheetManager.setContentView(view);

        //      Button
        btn_add_manager.setOnClickListener(v -> {
            String newEmail = input_email.getText().toString().trim();
            String newName = input_name.getText().toString().trim();
            String newPassword = input_password.getText().toString().trim();
            boolean checkAdmin = chk_admin.isChecked();

            //      check empty
            if(newEmail.isEmpty() || newName.isEmpty() || newPassword.isEmpty()){
                Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            //      insert 'manager'
            boolean check = managerDAO.insertDatabase(newEmail,newPassword,checkAdmin,newName);
            Toast.makeText(getContext(), check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

            if(!check){
                return;
            }
            bottomSheetManager.dismiss();

        });
    }

    //      Dialog Password
    private void dialogChangePassword(){
        //      only show at time
        if(bottomSheetPassword != null && bottomSheetPassword.isShowing()){
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

            //      update 'manager'
            boolean check = managerDAO.updateDatabase(id,newPassword);
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
        View view = getLayoutInflater().inflate(R.layout.dialog_change_manager_profile,null);
        TextInputEditText input_email = view.findViewById(R.id.input_email);
        TextInputEditText input_name = view.findViewById(R.id.input_name);
        ImageButton btn_unlock_change = view.findViewById(R.id.btn_unlock_change);
        Button btn_change_profile = view.findViewById(R.id.btn_change_profile);
        //
        input_name.setText(name);
        input_email.setText(email);
        input_email.setOnClickListener(v -> Toast.makeText(getContext(), "Không thể thay đổi Email!", Toast.LENGTH_SHORT).show());


        //      Bottom Sheet
        bottomSheetProfile = new BottomSheetDialog(getContext());
        bottomSheetProfile.setContentView(view);

        //      Button
        btn_unlock_change.setOnClickListener(v->{
            input_name.setFocusableInTouchMode(true);
            input_name.setFocusable(true);

            btn_unlock_change.setVisibility(View.GONE);
            btn_change_profile.setVisibility(View.VISIBLE);
        });

        btn_change_profile.setOnClickListener(v -> {
            String newName = input_name.getText().toString().trim();

            if(newName.isEmpty() ){
                Toast.makeText(getContext(), "Hãy nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
                return;
            }

            //      update 'manager'
            boolean check = managerDAO.updateDatabase(newName,id);
            Toast.makeText(getContext(), check?"Thành công":"Thất bại!", Toast.LENGTH_SHORT).show();

            bottomSheetProfile.dismiss();

            //      save Shared Preferences
            if(check){
                managerDAO.saveSharedPreferences(managerDAO.queryDatabaseAvailable(email,password));

                restartFragment();
            }

        });

    }

    private void restartFragment(){
        FragmentTransaction tr = getFragmentManager().beginTransaction();
        tr.replace(R.id.frame_layout, new ManageAccountFragment());
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
}