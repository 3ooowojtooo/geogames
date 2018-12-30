package com.kurocho.geogames.views.base_fragment;

public class UnGuardedFragment extends GuardedFragment {

    @Override
    public final GuardType requiredGuardType() {
        return GuardType.ANY;
    }
}
