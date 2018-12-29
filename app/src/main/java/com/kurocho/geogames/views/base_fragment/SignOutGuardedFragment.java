package com.kurocho.geogames.views.base_fragment;

public class SignOutGuardedFragment extends GuardedFragment {

    @Override
    public final GuardType requiredGuardType() {
        return GuardType.SIGNED_OUT;
    }
}
