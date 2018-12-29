package com.kurocho.geogames.views.base_fragment;

import android.support.v4.app.Fragment;

public abstract class GuardedFragment extends Fragment {

    public abstract GuardType requiredGuardType();

    public final boolean requiresSignedInGuardType(){
        return (requiredGuardType() == GuardType.SIGNED_IN);
    }

    public final boolean requiresSignedOutGuardType(){
        return (requiredGuardType() == GuardType.SIGNED_OUT);
    }

    public final boolean requiresAnyGuardType(){
        return (requiredGuardType() == GuardType.ANY);
    }

    public enum GuardType{
        SIGNED_IN, SIGNED_OUT, ANY
    }
}
