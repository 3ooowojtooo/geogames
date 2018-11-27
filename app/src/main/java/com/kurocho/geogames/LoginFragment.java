package com.kurocho.geogames;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.viewmodels.login.LogInLiveDataStatus;
import com.kurocho.geogames.viewmodels.login.LogInLiveDataWrapper;
import com.kurocho.geogames.viewmodels.login.LoginViewModel;


public class LoginFragment extends Fragment {

    @BindView(R.id.login_username)
    AutoCompleteTextView username;

    @BindView(R.id.login_password)
    EditText password;

    private LoginViewModel viewModel;

    private MainActivity mainActivity;

    @OnClick(R.id.login_sign_in_button)
    public void logInOnClick(){
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        viewModel.login(username, password);
    }

    @OnClick(R.id.login_sign_up_button)
    public void goToSignUpFragment(){
        mainActivity.animateToFragment(new SignUpFragment(), "sign_up");
    }




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(LoginViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getActivity() instanceof MainActivity){
            mainActivity = (MainActivity)getActivity();
            initializeViewModelObserver();
        } else{
            throw new RuntimeException(this.getClass().getCanonicalName() + " can only be attached into MainActivity");
        }
    }

    private void initializeViewModelObserver(){
        viewModel.getLogInLiveData().observe(this, (@Nullable LogInLiveDataWrapper wrapper) -> {
           if(wrapper != null){
               if(wrapper.isIdle()){
                    processIdleLogInViewModelStatus();
               } else if(wrapper.isInProgress()){
                    processInProgressLogInViewModelStatus();
               } else if(wrapper.isSuccessful()){
                    processSuccessfulLogInViewModelStatus(wrapper);
               } else if(wrapper.isApiError()){
                    processApiErrorLogInViewModelStatus(wrapper);
               } else if(wrapper.isInternetError()){
                   processInternetErrorLogInViewModelStatus(wrapper);
               }
           }
        });
    }

    private void processIdleLogInViewModelStatus(){
        mainActivity.hideProgressOverlay();
    }

    private void processInProgressLogInViewModelStatus(){
        mainActivity.showProgressOverlay();
    }

    private void processSuccessfulLogInViewModelStatus(@NonNull LogInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "Success. " + wrapper.getToken().getToken(), Toast.LENGTH_LONG).show();
    }

    private void processApiErrorLogInViewModelStatus(@NonNull LogInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "api error. code: " + String.valueOf(wrapper.getStatusCode()), Toast.LENGTH_LONG).show();
    }

    private void processInternetErrorLogInViewModelStatus(@NonNull LogInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "internet error. " + wrapper.getErrorThrowable().getMessage(), Toast.LENGTH_LONG).show();
    }

}
