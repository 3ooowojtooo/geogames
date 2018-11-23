package com.kurocho.geogames;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.viewmodels.CreateAccountViewModel;

public class CreateAccountActivity extends AppCompatActivity {

    @BindView(R.id.create_account_mail)
    AutoCompleteTextView mail;

    @BindView(R.id.create_account_username)
    AutoCompleteTextView username;

    @BindView(R.id.create_account_password)
    EditText password;

    private CreateAccountViewModel viewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_account);
        ButterKnife.bind(this);
        viewModel = ViewModelProviders.of(this).get(CreateAccountViewModel.class);
    }

    @OnClick(R.id.create_account_button)
    public void createAccountOnClick(){

    }

}
