package com.kurocho.geogames.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.MenuRes;
import android.support.design.widget.BottomNavigationView;
import android.util.AttributeSet;
import com.kurocho.geogames.R;

public class BottomMenu extends BottomNavigationView {

    @MenuRes
    private int signedInMenuResource;

    @MenuRes
    private int signedOutMenuResource;

    public BottomMenu(Context context, AttributeSet attrs){
        super(context, attrs);
        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.BottomMenu, 0, 0);
        signedInMenuResource = a.getResourceId(R.styleable.BottomMenu_signedInMenu, 0);
        signedOutMenuResource = a.getResourceId(R.styleable.BottomMenu_signedOutMenu, 0);
        a.recycle();

        if(signedOutMenuResource == 0 || signedInMenuResource == 0)
            throw new RuntimeException("BottomMenu: attributes signInMenu or signOutMenu not set.");
    }

    public void showSignedInMenu(){
        deleteMenuItems();
        inflateMenu(signedInMenuResource);
    }

    public void showSignedOutMenu(){
        deleteMenuItems();
        inflateMenu(signedOutMenuResource);
    }

    private void deleteMenuItems(){
        getMenu().clear();
    }

}
