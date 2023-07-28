package android.developer.ecommerce.fragment.login;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.developer.ecommerce.DAO.CustomerDAO;
import android.developer.ecommerce.activity.MainActivity;
import android.developer.ecommerce.activity.SignupActivity;
import android.developer.ecommerce.model.Customer;
import android.developer.myteamsproject.R;
import android.os.Bundle;

import androidx.annotation.Nullable;
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


public class CustomerLoginFragment extends Fragment {
    //#Widget
    private TextInputEditText customer_username,customer_password;
    private ImageButton customer_login;
    private Button customer_signup,customer_forget_password;
    private void initUI(View view){
        customer_username = view.findViewById(R.id.customer_username);
        customer_password = view.findViewById(R.id.customer_password);
        customer_login = view.findViewById(R.id.customer_login);
        customer_signup = view.findViewById(R.id.customer_signup);
        customer_forget_password = view.findViewById(R.id.customer_forget_password);
    }
    //#var
    //      Shared Preferences
    private SharedPreferences sharedPreferences;
    //      DAO
    private CustomerDAO customerDAO;
    private void var(){
        //      Shared Preferences
        sharedPreferences = getContext().getSharedPreferences(MainActivity.PREFS_FILE,MODE_PRIVATE);
        //      DAO
        customerDAO = new CustomerDAO(getContext());
    }
    //#LauncherUI
    private void launcherUI() {
        //      Login
        customer_login.setOnClickListener(v -> customerLogin());
        //      Signup
        customer_signup.setOnClickListener(v -> switchSignupUI());
        //      Forget pass
        customer_forget_password.setOnClickListener(v -> forgetPassword());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_customer_login, container, false);

        //#Widget - var
        initUI(view);
        var();

        //#LauncherUI
        launcherUI();


        return view;
    }

    private void switchSignupUI() {
        startActivityForResult(new Intent(getContext(), SignupActivity.class),10);
    }

    private void forgetPassword() {
        Snackbar.make(getActivity().findViewById(R.id.coordinator),"Chức năng này đang được phát triển", BaseTransientBottomBar.LENGTH_SHORT)
                .setAction("OK",v -> {}  )
                .show();
    }

    private void customerLogin() {
        //      get text
        String username = customer_username.getText().toString().trim();
        String password = customer_password.getText().toString().trim();

        //      check empty
        if(username.isEmpty() || password.isEmpty()){
            customer_username.requestFocus();
            Snackbar.make(getActivity().findViewById(R.id.coordinator),"Hãy điền đầy đủ thông tin!", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK",v -> {}  )
                    .show();
            return;
        }

        //      check available
        Customer customer = customerDAO.queryDatabaseAvailable(username,password);

        if(customer == null){
            customer_username.requestFocus();
            Snackbar.make(getActivity().findViewById(R.id.coordinator),"Tên tài khoản hoặc mật khẩu sai!", BaseTransientBottomBar.LENGTH_SHORT)
                    .setAction("OK",v -> {}  )
                    .show();
            return;

        }

        //      Success
        Toast.makeText(getContext(), "Thành công", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getContext(),MainActivity.class));
        getActivity().finish();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 10) {
            if (resultCode == RESULT_OK) {
                customer_username.setText(data.getStringExtra("USERNAME"));
                customer_password.setText(data.getStringExtra("PASSWORD"));
            }
        }
    }


}