package com.kurocho.geogames;

import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.viewmodels.factory.DaggerViewModelComponent;
import com.kurocho.geogames.viewmodels.sign_in.SignInLiveDataWrapper;
import com.kurocho.geogames.viewmodels.sign_in.SignInViewModel;

import javax.inject.Inject;


public class SignInFragment extends Fragment {

    @BindView(R.id.sign_in_username)
    AutoCompleteTextView username;

    @BindView(R.id.sign_in_password)
    EditText password;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    private SignInViewModel viewModel;

    private MainActivity mainActivity;

    @OnClick(R.id.sign_in_button)
    public void logInOnClick(){
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        viewModel.login(username, password);
    }

    @OnClick(R.id.log_in_sign_up_button)
    public void goToSignUpFragment(){
        mainActivity.getFragNavController().pushFragment(new SignUpFragment());
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaggerViewModelComponent.create().signInFragmentBinding(this);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel.class);
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
        viewModel.getLogInLiveData().observe(this, (@Nullable SignInLiveDataWrapper wrapper) -> {
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

    private void processSuccessfulLogInViewModelStatus(@NonNull SignInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "Success. " + wrapper.getToken().getToken(), Toast.LENGTH_LONG).show();
    }

    private void processApiErrorLogInViewModelStatus(@NonNull SignInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "api error. code: " + String.valueOf(wrapper.getStatusCode()), Toast.LENGTH_LONG).show();
    }

    private void processInternetErrorLogInViewModelStatus(@NonNull SignInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "internet error. " + wrapper.getErrorThrowable().getMessage(), Toast.LENGTH_LONG).show();
    }

}
