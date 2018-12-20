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
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.viewmodels.sign_up.SignUpLiveDataWrapper;
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

    @BindView(R.id.sign_up_error)
    TextView error;

    @BindView(R.id.sign_up_success)
    TextView success;

    private SignUpViewModel viewModel;

    private MainActivity mainActivity;

    @Inject
    ViewModelProvider.Factory viewModelFactory;


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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof MainActivity){
            mainActivity = (MainActivity) getActivity();
            initializeSignUpLiveDataObserver();
        } else{
            throw new RuntimeException(this.getClass().getCanonicalName() + " can only be attached into MainActivity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_up, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.sign_up_button)
    public void createAccountOnClick(){
        String email = this.email.getText().toString();
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        viewModel.signUp(username, password, email);
    }

    private void initializeSignUpLiveDataObserver(){
        viewModel.getSignUpLiveData().observe(this, (@Nullable SignUpLiveDataWrapper wrapper) -> {
            if(wrapper != null){
                if(wrapper.isIdle()){
                    processIdleSignUpLiveDataStatus();
                } else if(wrapper.isInProgress()){
                    processInProgressSignUpLiveDataStatus();
                } else if(wrapper.isSuccess()){
                    String message = wrapper.getMessage();
                    processSuccessSignUpLiveDataStatus(message);
                } else if(wrapper.isError()){
                    String message = wrapper.getMessage();
                    processErrorSignUpLiveDataStatus(message);
                }
            }
        });
    }

    private void processIdleSignUpLiveDataStatus(){
        mainActivity.hideProgressOverlay();
    }

    private void processInProgressSignUpLiveDataStatus(){
        mainActivity.showProgressOverlay();
    }

    private void processSuccessSignUpLiveDataStatus(String message){
        mainActivity.hideProgressOverlay();
        showSuccessMessage(message);

    }

    private void processErrorSignUpLiveDataStatus(String message){
        mainActivity.hideProgressOverlay();
        showErrorMessage(message);
    }


    private void showSuccessMessage(String message){
        clearMessages();
        success.setText(message);
        success.setVisibility(View.VISIBLE);
    }

    private void showErrorMessage(String message){
        clearMessages();
        error.setText(message);
        error.setVisibility(View.VISIBLE);
    }

    private void clearMessages(){
        error.setVisibility(View.GONE);
        error.setText("");
        success.setVisibility(View.GONE);
        success.setText("");
    }
}