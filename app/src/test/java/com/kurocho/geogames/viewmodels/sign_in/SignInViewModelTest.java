package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.MutableLiveData;
import com.kurocho.geogames.api.Api;
import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.viewmodels.sign_up.SignUpLiveDataWrapper;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.BufferedSource;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SignInViewModelTest {

    @Rule
    public TestRule instant = new InstantTaskExecutorRule();

    @Captor
    public ArgumentCaptor<SignInLiveDataWrapper> liveDataWrapperArgumentCaptor;


    private static final int SAMPLE_ERROR_CODE = 400;


    @Test
    public void Given_SignInLiveDataIsNull_When_LoginIsCalled_Then_NotRetrofitLoginNorSetIdleLogInLiveDataStatusShouldBeCalled(){

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

    @Test
    public void Given_SigInLiveDataNotInProgressStateAndSuccessfulResponse_When_LoginIsCalled_Then_SignInLiveDataStatusIsSetProperly(){

        SignInLiveDataWrapper notInProgressWrapper = mock(SignInLiveDataWrapper.class);
        when(notInProgressWrapper.isInProgress()).thenReturn(false);

        MutableLiveData<SignInLiveDataWrapper> liveData = new MutableLiveData<>();
        liveData.setValue(notInProgressWrapper);
        MutableLiveData<SignInLiveDataWrapper> spyLiveData = spy(liveData);

        Api mockedApi = mock(Api.class);
        Call<Void> mockedCall = mock(Call.class);

        when(mockedApi.signIn(any(SignInCredentials.class))).thenReturn(mockedCall);

        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(mockedCall, Response.success(null));
            return null;
        }).when(mockedCall).enqueue(any(Callback.class));

        SignInViewModel viewModel = new SignInViewModel(mockedApi, spyLiveData);
        SignInViewModel spyViewModel = spy(viewModel);

        spyViewModel.login(anyString(), anyString());

        verify(spyLiveData, times(3)).setValue(liveDataWrapperArgumentCaptor.capture());

        List<SignInLiveDataWrapper> capturedWrappers = liveDataWrapperArgumentCaptor.getAllValues();

        assertEquals(capturedWrappers.size(), 3);
        assertTrue(capturedWrappers.get(0).isIdle());
        assertTrue(capturedWrappers.get(1).isInProgress());
        assertTrue(capturedWrappers.get(2).isSuccessful());
    }

    @Test
    public void  Given_SigInLiveDataNotInProgressStateAndUnsuccessfulResponse_When_LoginIsCalled_Then_SignInLiveDataStatusIsSetProperly(){
        SignInLiveDataWrapper notInProgressWrapper = mock(SignInLiveDataWrapper.class);
        when(notInProgressWrapper.isInProgress()).thenReturn(false);

        MutableLiveData<SignInLiveDataWrapper> liveData = new MutableLiveData<>();
        liveData.setValue(notInProgressWrapper);
        MutableLiveData<SignInLiveDataWrapper> spyLiveData = spy(liveData);

        Api mockedApi = mock(Api.class);
        Call<Void> mockedCall = mock(Call.class);

        when(mockedApi.signIn(any(SignInCredentials.class))).thenReturn(mockedCall);

        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onResponse(mockedCall, Response.error(SAMPLE_ERROR_CODE, mock(ResponseBody.class)));
            return null;
        }).when(mockedCall).enqueue(any(Callback.class));

        SignInViewModel viewModel = new SignInViewModel(mockedApi, spyLiveData);
        SignInViewModel spyViewModel = spy(viewModel);

        spyViewModel.login(anyString(), anyString());

        verify(spyLiveData, times(3)).setValue(liveDataWrapperArgumentCaptor.capture());

        List<SignInLiveDataWrapper> capturedWrappers = liveDataWrapperArgumentCaptor.getAllValues();

        assertEquals(capturedWrappers.size(), 3);
        assertTrue(capturedWrappers.get(0).isIdle());
        assertTrue(capturedWrappers.get(1).isInProgress());
        assertTrue(capturedWrappers.get(2).isApiError());
        assertEquals(capturedWrappers.get(2).getStatusCode().intValue(), SAMPLE_ERROR_CODE);
    }

    @Test
    public void Given_SigInLiveDataNotInProgressStateAndInternetErrorResponse_When_LoginIsCalled_Then_SignInLiveDataStatusIsSetProperly(){
        SignInLiveDataWrapper notInProgressWrapper = mock(SignInLiveDataWrapper.class);
        when(notInProgressWrapper.isInProgress()).thenReturn(false);

        MutableLiveData<SignInLiveDataWrapper> liveData = new MutableLiveData<>();
        liveData.setValue(notInProgressWrapper);
        MutableLiveData<SignInLiveDataWrapper> spyLiveData = spy(liveData);

        Api mockedApi = mock(Api.class);
        Call<Void> mockedCall = mock(Call.class);

        when(mockedApi.signIn(any(SignInCredentials.class))).thenReturn(mockedCall);

        doAnswer(invocation -> {
            Callback<Void> callback = invocation.getArgument(0);
            callback.onFailure(mockedCall, new Throwable());
            return null;
        }).when(mockedCall).enqueue(any(Callback.class));


        SignInViewModel viewModel = new SignInViewModel(mockedApi, spyLiveData);
        SignInViewModel spyViewModel = spy(viewModel);

        spyViewModel.login(anyString(), anyString());

        verify(spyLiveData, times(3)).setValue(liveDataWrapperArgumentCaptor.capture());

        List<SignInLiveDataWrapper> capturedWrappers = liveDataWrapperArgumentCaptor.getAllValues();

        assertEquals(capturedWrappers.size(), 3);
        assertTrue(capturedWrappers.get(0).isIdle());
        assertTrue(capturedWrappers.get(1).isInProgress());
        assertTrue(capturedWrappers.get(2).isInternetError());
    }

}