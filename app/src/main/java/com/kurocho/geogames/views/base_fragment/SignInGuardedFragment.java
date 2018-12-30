package com.kurocho.geogames.views.base_fragment;

public class SignInGuardedFragment extends GuardedFragment {

    @Override
    public final GuardType requiredGuardType() {
        return GuardType.SIGNED_IN;
    }
}
