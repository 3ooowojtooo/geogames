package com.kurocho.geogames;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.crashlytics.android.Crashlytics;
import com.kurocho.geogames.utils.sign_in.SignInTokenUtils;
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
    BottomNavigationView navigation;

    @BindView(R.id.main_activity_progress_overlay)
    View progressOverlay;

    private FragNavController fragNavController;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Inject
    SignInTokenUtils signInTokenUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AndroidInjection.inject(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        configureCrashlytics();
        initFragNavController(savedInstanceState);
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


    @OnClick(R.id.navigation_search)
    void changeDisplayedFragmentToSearch() {
        fragNavController.switchTab(INDEX_SEARCH);
        navigation.setSelectedItemId(R.id.navigation_search);
    }

    @OnClick(R.id.navigation_my_games)
    void changeDisplayedFragmentToMyGames() {
        fragNavController.switchTab(INDEX_GAMES);
        navigation.setSelectedItemId(R.id.navigation_my_games);
    }

    @OnClick(R.id.navigation_sign_in)
    void changeDisplayedFragmentToLoginFragment() {
        fragNavController.switchTab(INDEX_SIGN_IN);
        navigation.setSelectedItemId(R.id.navigation_sign_in);
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
