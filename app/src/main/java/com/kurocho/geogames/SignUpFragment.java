package com.kurocho.geogames;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.viewmodels.sign_up.SignUpViewModel;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;

public class SignUpFragment extends Fragment {

    @BindView(R.id.sign_up_email)
    AutoCompleteTextView email;

    @BindView(R.id.sign_up_username)
    AutoCompleteTextView username;

    @BindView(R.id.sign_up_password)
    EditText password;

    private SignUpViewModel viewModel;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @OnClick(R.id.sign_up_button)
    public void createAccountOnClick(){
        String email = this.email.getText().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
//        viewModel.signUp(email, username, password);
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignUpViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
    }




}
