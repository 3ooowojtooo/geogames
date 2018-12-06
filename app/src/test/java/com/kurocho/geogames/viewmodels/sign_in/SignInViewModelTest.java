package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.lifecycle.MutableLiveData;
import com.kurocho.geogames.api.Api;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SignInViewModelTest {

    @Mock
    private Api api;

    private SignInViewModel viewModel;

    private SignInViewModel spyViewModel;

    /*@BeforeClass
    public static void initializeObjects(){

        viewModel = new SignInViewModel(api);
        spyViewModel = spy(viewModel);
    }*/

    @Test
    public void Given_SignInLiveDataIsNull_When_LoginIsCalled_Then_NotRetrofitLoginNorSetIdleLogInLiveDataStatusShouldBeCalled(){

        SignInViewModel viewModelWithNullLiveData = new SignInViewModel(api, null);
        SignInViewModel spyViewModelWithNullLiveData = spy(viewModelWithNullLiveData);

        spyViewModelWithNullLiveData.login(anyString(), anyString());

        verify(spyViewModelWithNullLiveData, times(1)).login(anyString(), anyString());
        verifyNoMoreInteractions(spyViewModelWithNullLiveData);

    }


}
