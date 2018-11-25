package com.kurocho.geogames;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.viewmodels.LoginViewModel;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login_username)
    AutoCompleteTextView username;

    @BindView(R.id.login_password)
    EditText password;


    private LoginViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @OnClick(R.id.login_sign_in_button)
    public void logInOnClick(){

    }
}
