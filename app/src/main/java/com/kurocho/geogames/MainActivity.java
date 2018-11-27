package com.kurocho.geogames;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.crashlytics.android.Crashlytics;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.navigation)
    BottomNavigationView navigation;

    @BindView(R.id.main_activity_progress_overlay)
    View progressOverlay;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = (item) -> {
            switch (item.getItemId()) {
                case R.id.navigation_search:

                    return true;
                case R.id.navigation_my_games:

                    return true;
                case R.id.navigation_sign_in:

                    return true;
            }
            return false;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initializeListeners();
        setDisplayedFragmentOnActivityCreated(savedInstanceState);
        configureCrashlytics();
    }


    private void initializeListeners(){
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private void setDisplayedFragmentOnActivityCreated(Bundle savedInstanceState){
        if(savedInstanceState == null){
            changeDisplayedFragmentToLoginFragment();
        }
    }

    private void changeDisplayedFragmentToLoginFragment(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        LoginFragment loginFragment = new LoginFragment();
        transaction.replace(R.id.main_activity_fragment_container, loginFragment).commit();
        navigation.setSelectedItemId(R.id.navigation_sign_in);
    }

    private void configureCrashlytics(){
        // Stop crashlytics for developing
        CrashlyticsCore core = new CrashlyticsCore.Builder().disabled(BuildConfig.DEBUG).build();
        Fabric.with(this, new Crashlytics.Builder().core(core).build());
    }

    void showProgressOverlay(){
        progressOverlay.setVisibility(View.VISIBLE);
    }

    void hideProgressOverlay(){
        progressOverlay.setVisibility(View.GONE);
    }


}
