package com.kurocho.geogames;

import android.content.res.Resources;
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
import com.kurocho.geogames.utils.sign_in.SignInUtils;
import com.ncapdevi.fragnav.FragNavController;
import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;
import org.jetbrains.annotations.NotNull;

import javax.inject.Inject;


public class MainActivity extends AppCompatActivity implements FragNavController.RootFragmentListener, HasSupportFragmentInjector,
        BottomNavigationView.OnNavigationItemSelectedListener {

    private static final int INDEX_SEARCH = 0;
    private static final int INDEX_GAMES = 1;
    private static final int INDEX_SIGN_IN = 2;
    private static final int ROOT_FRAGMENTS_NUMBER = 3;


    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.main_activity_progress_overlay)
    View progressOverlay;

    private FragNavController fragNavController;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Inject
    SignInUtils signInUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        navigation.setOnNavigationItemSelectedListener(this);
        signInUtils.getIsUserSignedInLiveData().observe(this, isLoggedIn -> {
            if(isLoggedIn != null) {
                if (isLoggedIn) {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.bottom_menu_signed_in);
                } else {
                    navigation.getMenu().clear();
                    navigation.inflateMenu(R.menu.bottom_menu_signed_out);
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


    void changeDisplayedFragmentToSearch() {
        navigation.setSelectedItemId(R.id.navigation_search);
    }

    void changeDisplayedFragmentToMyGames() {
        navigation.setSelectedItemId(R.id.navigation_my_games);
    }

    void changeDisplayedFragmentToLoginFragment() {
        navigation.setSelectedItemId(R.id.navigation_sign_in);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        setBarTitle(R.string.app_name);
        switch(menuItem.getItemId()){
            case R.id.navigation_search:
                fragNavController.switchTab(INDEX_SEARCH);
                setBarTitle(R.string.app_bar_title_search);
                break;
            case R.id.navigation_my_games:
                fragNavController.switchTab(INDEX_GAMES);
                setBarTitle(R.string.app_bar_title_my_games);
                break;
            case R.id.navigation_sign_in:
                fragNavController.switchTab(INDEX_SIGN_IN);
                setBarTitle(R.string.app_bar_title_sign_in);
                break;
            case R.id.navigation_sign_out:
                processSignOut();
                break;
            default:
                return false;
        }
        return true;
    }

    private void setBarTitle(int resources){
        ActionBar bar = getSupportActionBar();
        if(bar != null){
            bar.setTitle(resources);
        }
    }

    void onSignInSuccess(){
        showProgressOverlay();
        fragNavController.clearStack();
        changeDisplayedFragmentToMyGames();
        hideProgressOverlay();
    }

    void processSignOut(){
        showProgressOverlay();
        fragNavController.clearStack();
        signInUtils.signOut();
        changeDisplayedFragmentToSearch();
        hideProgressOverlay();
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
