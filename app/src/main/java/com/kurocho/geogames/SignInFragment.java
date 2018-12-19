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
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.kurocho.geogames.utils.SignInErrorMessageUtils;
import com.kurocho.geogames.viewmodels.sign_in.SignInLiveDataWrapper;
import com.kurocho.geogames.viewmodels.sign_in.SignInViewModel;
import dagger.android.support.AndroidSupportInjection;

import javax.inject.Inject;


public class SignInFragment extends Fragment {

    @BindView(R.id.sign_in_username)
    AutoCompleteTextView username;

    @BindView(R.id.sign_in_password)
    EditText password;

    @BindView(R.id.sign_in_error)
    TextView error;

    @Inject
    ViewModelProvider.Factory viewModelFactory;

    @Inject
    SignInErrorMessageUtils errorMessageUtils;

    private SignInViewModel viewModel;

    private MainActivity mainActivity;

    @OnClick(R.id.sign_in_button)
    public void logInOnClick(){
        String username = this.username.getText().toString();
        String password = this.password.getText().toString();
        viewModel.login(username, password);
    }

    @OnClick(R.id.sign_in_sign_up_button)
    public void goToSignUpFragment(){
        mainActivity.getFragNavController().pushFragment(new SignUpFragment());
    }

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public void onAttach(Context context) {
        AndroidSupportInjection.inject(this);
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(SignInViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.sign_in, container, false);
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
               } else if(wrapper.isError()){
                   processErrorLogInViewModelStatus(wrapper);
               }
           }
        });
    }

    private void processIdleLogInViewModelStatus(){
        mainActivity.hideProgressOverlay();
        clearErrorMessage();
    }

    private void processInProgressLogInViewModelStatus(){
        mainActivity.showProgressOverlay();
        clearErrorMessage();
    }

    private void processSuccessfulLogInViewModelStatus(@NonNull SignInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        Toast.makeText(getActivity(), "Success. " + wrapper.getToken().getToken(), Toast.LENGTH_LONG).show();
    }

    private void processErrorLogInViewModelStatus(@NonNull SignInLiveDataWrapper wrapper){
        mainActivity.hideProgressOverlay();
        getAndShowErrorMessage(wrapper);
    }

    private void getAndShowErrorMessage(@NonNull SignInLiveDataWrapper wrapper){
        String errorMessage = getErrorMessage(wrapper);
        showErrorMessage(errorMessage);
    }

    @NonNull
    private String getErrorMessage(@NonNull SignInLiveDataWrapper wrapper){
        return errorMessageUtils.getErrorMessage(wrapper);
    }

    private void showErrorMessage(String message){
        error.setText(message);
        error.setVisibility(View.VISIBLE);
    }

    private void clearErrorMessage(){
        error.setVisibility(View.GONE);
        error.setText("");
    }

}