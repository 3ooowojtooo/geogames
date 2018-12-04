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
import android.widget.Toast;
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
                    processSuccessSignUpLiveDataStatus(wrapper);
                } else if(wrapper.isApiError()){
                    processApiErrorSignUpLiveDataStatus(wrapper);
                } else if(wrapper.isInternetError()){
                    processInternetErrorSignUpLiveDataStatus(wrapper);
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

    private void processSuccessSignUpLiveDataStatus(@NonNull SignUpLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "success. code: " + wrapper.getStatusCode()
            + " message: " + wrapper.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void processApiErrorSignUpLiveDataStatus(@NonNull SignUpLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "api error. code: " + wrapper.getStatusCode() +
            " message: " + wrapper.getMessage(), Toast.LENGTH_LONG).show();
    }

    private void processInternetErrorSignUpLiveDataStatus(@NonNull SignUpLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        String message = (wrapper.getErrorThrowable() != null) ? wrapper.getErrorThrowable().getMessage() : "";
        Toast.makeText(getActivity(), "internet error. message: " + message, Toast.LENGTH_LONG).show();
    }


}
