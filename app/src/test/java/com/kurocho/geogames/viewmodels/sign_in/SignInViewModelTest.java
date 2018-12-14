package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.viewmodels.sign_up.SignUpLiveDataWrapper;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignInViewModelTest {

    @Rule
    public TestRule instant = new InstantTaskExecutorRule();

    @Mock private  Api api;


    @Test
    public void Given_SignInLiveDataIsNull_When_LoginIsCalled_Then_NotRetrofitLoginNorSetIdleLogInLiveDataStatusShouldBeCalled(){

        SignInViewModel viewModel = new SignInViewModel(api);

        SignInViewModel viewModelWithNullLiveData = new SignInViewModel(null, null);
        SignInViewModel spyViewModelWithNullLiveData = spy(viewModelWithNullLiveData);

        spyViewModelWithNullLiveData.login(anyString(), anyString());

        verify(spyViewModelWithNullLiveData, times(1)).login(anyString(), anyString());
        verifyNoMoreInteractions(spyViewModelWithNullLiveData);
    }

    @Test
    public void Given_SignInLiveDataWithNullValue_When_LoginIsCalled_Then_NotRetrofitLoginNorSetIdleLogInLiveDataStatusShouldBeCalled(){

        MutableLiveData<SignInLiveDataWrapper> liveDataWithNullValue = mock(MutableLiveData.class);
        when(liveDataWithNullValue.getValue()).thenReturn(null);

        SignInViewModel viewModelWithLiveDataWithNullValue = new SignInViewModel(null, liveDataWithNullValue);
        SignInViewModel spyViewModelWithLiveDataWithNullValue = spy(viewModelWithLiveDataWithNullValue);

        spyViewModelWithLiveDataWithNullValue.login(anyString(),anyString());

        verify(spyViewModelWithLiveDataWithNullValue, times(1)).login(anyString(), anyString());
        verifyNoMoreInteractions(spyViewModelWithLiveDataWithNullValue);
    }

    @Test
    public void Given_SignInLiveDataInProgressState_When_LoginIsCalled_Then_NotRetrofitLoginNorSetIdleLogInLiveDataStatusShouldBeCalled(){

        SignInLiveDataWrapper inProgressWrapper = mock(SignInLiveDataWrapper.class);
        when(inProgressWrapper.isInProgress()).thenReturn(true);

        MutableLiveData<SignInLiveDataWrapper> inProgressLiveData = mock(MutableLiveData.class);
        when(inProgressLiveData.getValue()).thenReturn(inProgressWrapper);

        SignInViewModel viewModel = new SignInViewModel(null, inProgressLiveData);
        SignInViewModel spyViewModel = spy(viewModel);

        spyViewModel.login(anyString(), anyString());

        verify(spyViewModel, times(1)).login(anyString(),anyString());
        verifyNoMoreInteractions(spyViewModel);
    }

    /*@Test
    public void Given_SignInLiveDataNotInProgressState_When_LoginIsCalled_Then_RetrofitLoginAndSetIdleLogInLiveDataStatusShouldBeCalled(){

        SignInLiveDataWrapper inProgressWrapper = mock(SignInLiveDataWrapper.class);
        when(inProgressWrapper.isInProgress()).thenReturn(false);

        MutableLiveData<SignInLiveDataWrapper> inProgressLiveData = mock(MutableLiveData.class);
        when(inProgressLiveData.getValue()).thenReturn(inProgressWrapper);

        SignInViewModel viewModel = new SignInViewModel(null, inProgressLiveData);
        SignInViewModel spyViewModel = spy(viewModel);
        doNothing().when(spyViewModel).retrofitLogin(anyString(),anyString());

        spyViewModel.login(anyString(), anyString());

        verify(spyViewModel, times(1)).login(anyString(), anyString());
        verify(spyViewModel, times(1)).setIdleLogInLiveDataStatus();
        verify(spyViewModel, times(1)).retrofitLogin(anyString(), anyString());
        verifyNoMoreInteractions(spyViewModel);
    }*/

}