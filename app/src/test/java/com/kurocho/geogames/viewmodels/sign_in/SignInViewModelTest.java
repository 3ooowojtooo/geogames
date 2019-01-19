package com.kurocho.geogames.viewmodels.sign_in;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import com.kurocho.geogames.api.SignInCredentials;
import com.kurocho.geogames.utils.sign_in.SignInUtils;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;


import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class SignInViewModelTest {

    @Rule
    public TestRule instant = new InstantTaskExecutorRule();

    @Captor
    private ArgumentCaptor<SignInLiveDataWrapper> liveDataWrapperArgumentCaptor;

    @Mock
    private SignInUtils signInUtils;

    @Mock
    private Observer<SignInLiveDataWrapper> wrapperObserver;

    @Test
    public void testResetIsSettingIdleLiveDataStatus() {
        SignInViewModel viewModel = new SignInViewModel(null);
        viewModel.getSignInLiveData().observeForever(wrapperObserver);

        // test
        viewModel.reset();

        verify(wrapperObserver, times(2)).onChanged(liveDataWrapperArgumentCaptor.capture());
        verifyNoMoreInteractions(wrapperObserver);

        assertTrue(liveDataWrapperArgumentCaptor.getValue().isIdle());

    }

    @Test
    public void Given_SuccessfulSignInCallback_When_SignInIsCalled_Then_LiveDataStatusIsSetProperly(){
        // given
        doAnswer(invocation -> {
            SignInUtils.SignInCallback callback = invocation.getArgument(1);
            callback.onSuccess();
            return null;
        }).when(signInUtils).signIn(any(SignInCredentials.class), any(SignInUtils.SignInCallback.class));

        SignInViewModel viewModel = new SignInViewModel(signInUtils);
        viewModel.getSignInLiveData().observeForever(wrapperObserver);

        // when
        viewModel.signIn("","");

        // then
        verify(wrapperObserver, times(4)).onChanged(liveDataWrapperArgumentCaptor.capture());
        verifyNoMoreInteractions(wrapperObserver);

        List<SignInLiveDataWrapper> capturedWrappers = liveDataWrapperArgumentCaptor.getAllValues();
        assertTrue(capturedWrappers.get(1).isIdle());
        assertTrue(capturedWrappers.get(2).isInProgress());
        assertTrue(capturedWrappers.get(3).isSuccessful());
    }

    @Test
    public void Given_ErrorSignInCallback_When_SignInIsCalled_Then_LiveDataStatusIsSetProperly(){
        // given
        doAnswer(invocation -> {
            SignInUtils.SignInCallback callback = invocation.getArgument(1);
            callback.onError("");
            return null;
        }).when(signInUtils).signIn(any(SignInCredentials.class), any(SignInUtils.SignInCallback.class));

        SignInViewModel viewModel = new SignInViewModel(signInUtils);
        viewModel.getSignInLiveData().observeForever(wrapperObserver);

        // when
        viewModel.signIn("","");

        // then
        verify(wrapperObserver, times(4)).onChanged(liveDataWrapperArgumentCaptor.capture());
        verifyNoMoreInteractions(wrapperObserver);

        List<SignInLiveDataWrapper> capturedWrappers = liveDataWrapperArgumentCaptor.getAllValues();
        assertTrue(capturedWrappers.get(1).isIdle());
        assertTrue(capturedWrappers.get(2).isInProgress());
        assertTrue(capturedWrappers.get(3).isError());
    }

    @Test
    public void Given_ErrorSignInCallback_When_SignInIsCalled_Then_SignInWrapperHasProperMessage(){
        final String ERROR_MESSAGE = "error message";
        // given
        doAnswer(invocation -> {
            SignInUtils.SignInCallback callback = invocation.getArgument(1);
            callback.onError(ERROR_MESSAGE);
            return null;
        }).when(signInUtils).signIn(any(SignInCredentials.class), any(SignInUtils.SignInCallback.class));

        SignInViewModel viewModel = new SignInViewModel(signInUtils);
        viewModel.getSignInLiveData().observeForever(wrapperObserver);

        // when
        viewModel.signIn("","");

        // then
        verify(wrapperObserver, atLeast(1)).onChanged(liveDataWrapperArgumentCaptor.capture());
        assertEquals(ERROR_MESSAGE, liveDataWrapperArgumentCaptor.getValue().getMessage());
    }

    /*
    @Test
    public void Given_SignInLiveDataIsNull_When_LoginIsCalled_Then_NotRetrofitLoginNorSetIdleLogInLiveDataStatusShouldBeCalled(){

        SignInViewModel viewModelWithNullLiveData = new SignInViewModel(null, null);
        SignInViewModel spyViewModelWithNullLiveData = spy(viewModelWithNullLiveData);

        spyViewModelWithNullLiveData.signIn(anyString(), anyString());

        verify(spyViewModelWithNullLiveData, times(1)).signIn(anyString(), anyString());
        verifyNoMoreInteractions(spyViewModelWithNullLiveData);
    }

    @Test
    public void Given_SignInLiveDataWithNullValue_When_LoginIsCalled_Then_NotRetrofitLoginNorSetIdleLogInLiveDataStatusShouldBeCalled(){

        MutableLiveData<SignInLiveDataWrapper> liveDataWithNullValue = mock(MutableLiveData.class);
        when(liveDataWithNullValue.getValue()).thenReturn(null);

        SignInViewModel viewModelWithLiveDataWithNullValue = new SignInViewModel(null, liveDataWithNullValue);
        SignInViewModel spyViewModelWithLiveDataWithNullValue = spy(viewModelWithLiveDataWithNullValue);

        spyViewModelWithLiveDataWithNullValue.signIn(anyString(),anyString());

        verify(spyViewModelWithLiveDataWithNullValue, times(1)).signIn(anyString(), anyString());
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

        spyViewModel.signIn(anyString(), anyString());

        verify(spyViewModel, times(1)).signIn(anyString(),anyString());
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

        spyViewModel.signIn(anyString(), anyString());

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

        spyViewModel.signIn(anyString(), anyString());

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

        spyViewModel.signIn(anyString(), anyString());

        verify(spyLiveData, times(3)).setValue(liveDataWrapperArgumentCaptor.capture());

        List<SignInLiveDataWrapper> capturedWrappers = liveDataWrapperArgumentCaptor.getAllValues();

        assertEquals(capturedWrappers.size(), 3);
        assertTrue(capturedWrappers.get(0).isIdle());
        assertTrue(capturedWrappers.get(1).isInProgress());
        assertTrue(capturedWrappers.get(2).isInternetError());
    }
    */
}