package android.developer.ecommerce.fragment.login;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.ManagerDAO;
import android.developer.ecommerce.activity.OBJ_manager.ManagerActivity;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.model.Manager;
import android.developer.myteamsproject.R;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;


public class ManagerLoginFragment extends Fragment {

    //#Widget
    private TextInputEditText manager_email, manager_password;
    private ImageButton manager_login;
    private Button manager_signup, manager_forget_password;
    private void initUI(View view){
        manager_email = view.findViewById(R.id.manager_email);
        manager_password = view.findViewById(R.id.manager_password);
        manager_login = view.findViewById(R.id.manager_login);
        manager_signup = view.findViewById(R.id.manager_signup);
        manager_forget_password = view.findViewById(R.id.manager_forget_password);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    //      DAO
    private ManagerDAO managerDAO;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE,MODE_PRIVATE);
        //      DAO
        managerDAO = new ManagerDAO(getContext());
    }
    //#LauncherUI
    private void launcherUI() {
        //      Login
        manager_login.setOnClickListener(v -> managerLogin());
        //      Signup
        manager_signup.setOnClickListener(v -> switchSignupUI());
        //      Forget pass
        manager_forget_password.setOnClickListener(v -> forgetPassword());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_manager_login, container, false);

        //#Widget - var
        initUI(view);
        var();

        //#LauncherUI
        launcherUI();


        return view;
    }

    private void switchSignupUI() {
        Snackbar.make(getActivity().findViewById(R.id.coordinator),"Đăng kí quản lí ứng dụng? Liên hệ: 0987654321", BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("OK",v -> {}  )
                .show();
    }

    private void forgetPassword() {
        Snackbar.make(getActivity().findViewById(R.id.coordinator),"Chức năng này đang được phát triển", BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("OK",v -> {}  )
                .show();
    }

    private void managerLogin() {
        //      get text
        String email = manager_email.getText().toString().trim();
        String password = manager_password.getText().toString().trim();

        //      check empty
        if(email.isEmpty() || password.isEmpty()){
            manager_email.requestFocus();
            Snackbar.make(getActivity().findViewById(R.id.coordinator),"Hãy điền đầy đủ thông tin!", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK",v -> {}  )
                    .show();
            return;
        }

        //      check available
        Manager manager = managerDAO.queryDatabaseAvailable(email,password);

        if(manager == null){
            manager_email.requestFocus();
            Snackbar.make(getActivity().findViewById(R.id.coordinator),"Tên tài khoản hoặc mật khẩu không đúng!", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK",v -> {}  )
                    .show();
            return;

        }

        //      Success
        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getContext(), ManagerActivity.class));
        getActivity().finish();
    }




}