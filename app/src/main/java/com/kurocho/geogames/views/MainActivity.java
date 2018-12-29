package com.kurocho.geogames.views;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.crashlytics.android.Crashlytics;
import com.kurocho.geogames.BuildConfig;
import com.kurocho.geogames.R;
import com.kurocho.geogames.di.viewmodel_factory.ViewModelFactory;
import com.kurocho.geogames.viewmodels.main_activity.MainActivityViewModel;
import com.kurocho.geogames.views.base_fragment.GuardedFragment;
import com.kurocho.geogames.views.bottom_menu.BottomMenu;
import com.kurocho.geogames.views.bottom_menu.BottomMenuController;
import com.ncapdevi.fragnav.FragNavController;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements FragNavController.RootFragmentListener, HasSupportFragmentInjector {

    private static final int INDEX_SEARCH = 0;
    private static final int INDEX_GAMES = 1;
    private static final int INDEX_SIGN_IN = 2;
    private static final int ROOT_FRAGMENTS_NUMBER = 3;


    @BindView(R.id.navigation)
    BottomMenu navigation;

    @BindView(R.id.main_activity_progress_overlay)
    View progressOverlay;

    private FragNavController fragNavController;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Inject
    ViewModelFactory viewModelFactory;

    private MainActivityViewModel viewModel;
    private BottomMenuController bottomMenuController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainActivityViewModel.class);
        ButterKnife.bind(this);
        configureCrashlytics();
        initFragNavController(savedInstanceState);
        initBottomMenu();
    }

    private void configureCrashlytics() {
        if (!BuildConfig.DEBUG) { // only enable bug tracking in release version
            Fabric.with(this, new Crashlytics());
        }
    }

    private void initFragNavController(Bundle savedInstanceState){
        fragNavController = new FragNavController(getSupportFragmentManager(), R.id.main_activity_fragment_container);
        fragNavController.setRootFragmentListener(this);
        fragNavController.initialize(FragNavController.TAB1, savedInstanceState);
    }

    private void initBottomMenu(){
        bottomMenuController = new BottomMenuController(navigation);
        bottomMenuController.setBottomMenuEventsCallback(new BottomMenuController.BottomMenuEventsCallback() {
            @Override
            public void onSelectedMenuItemChanged(MenuItem item) {
                switch(item.getItemId()){
                    case R.id.navigation_search:
                        fragNavController.switchTab(INDEX_SEARCH);
                        break;
                    case R.id.navigation_my_games:
                        fragNavController.switchTab(INDEX_GAMES);
                        break;
                    case R.id.navigation_sign_in:
                        fragNavController.switchTab(INDEX_SIGN_IN);
                        break;
                    case R.id.navigation_sign_out:
                        processSignOut();
                        break;
                    default:
                        break;
                }
            }
        });
        viewModel.getIsSignedInLiveData().observe(this, isSignedIn -> {
            if(isSignedIn != null) {
                if (isSignedIn) {
                    bottomMenuController.onSignedIn();
                } else {
                    bottomMenuController.onSignedOut();
                }
            }
        });
    }

    @Override
    public int getNumberOfRootFragments() {
        return ROOT_FRAGMENTS_NUMBER;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (fragNavController != null) {
            fragNavController.onSaveInstanceState(outState);
        }
    }

    @NotNull
    @Override
    public Fragment getRootFragment(int index) {
        switch (index) {
            case INDEX_SIGN_IN:
                return SignInFragment.newInstance();
            case INDEX_GAMES:
                return MyGamesFragment.newInstance();
            case INDEX_SEARCH:
                return SearchFragment.newInstance();

        }
        throw new IllegalStateException("Need to send an index that we know");
    }


    void setBarTitle(String title){
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setTitle(title);
        }
    }

    void onSignInSuccess(){
        fragNavController.clearStack();
    }

    void processSignOut(){
        fragNavController.clearStack();
        setAfterSignOutMenuItem();
        viewModel.signOut();
    }

    private void setAfterSignOutMenuItem(){
        GuardedFragment currentFragment;
        try{
            currentFragment = (GuardedFragment)fragNavController.getCurrentFrag();
        } catch(ClassCastException e){
            throw new RuntimeException("All fragments managed by bottom menu must extend GuardedFragment", e);
        }

        if(currentFragment != null){
            if(currentFragment.requiresSignedInGuardType()){
                bottomMenuController.setDefaultSignOutMenuItemAsAfterSignOutMenuItem();
            } else{
                bottomMenuController.setCurrentMenuItemAsAfterSignOutMenuItem();
            }
        }

    }

    void showProgressOverlay() {
        progressOverlay.setVisibility(View.VISIBLE);
    }

    void hideProgressOverlay() {
        progressOverlay.setVisibility(View.GONE);
    }

    @Override
    public void onBackPressed() {
        if(!fragNavController.isRootFragment())
            fragNavController.popFragment();
        else
            super.onBackPressed();

    }

    FragNavController getFragNavController(){
        return fragNavController;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }

}
