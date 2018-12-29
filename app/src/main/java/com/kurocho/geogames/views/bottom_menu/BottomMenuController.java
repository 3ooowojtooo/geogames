package com.kurocho.geogames.views.bottom_menu;

import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public class BottomMenuController implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomMenu view;
    private BottomMenuEventsCallback callback = null;

    @IdRes private int afterSignOutMenuItemId;
    private boolean afterSignOutMenuItemIdSet = false;
    private boolean shouldOnSignOutChangeSelectedMenuItem = false;

    public BottomMenuController(BottomMenu view){
        this.view = view;
        init();
    }

    private void init(){
        view.setOnNavigationItemSelectedListener(this);
    }

    public void setBottomMenuEventsCallback(BottomMenuEventsCallback callback){
        this.callback = callback;
    }

    public void onSignedIn(){
        view.showSignedInMenu();
        view.selectAfterSignInDefaultItem();
    }

    public void onSignedOut(){
        view.showSignedOutMenu();
        if(shouldOnSignOutChangeSelectedMenuItem) {
            if (afterSignOutMenuItemIdSet) {
                view.setSelectedItemId(afterSignOutMenuItemId);
                afterSignOutMenuItemIdSet = false;
            } else {
                view.selectAfterSignOutDefaultItem();
            }
            shouldOnSignOutChangeSelectedMenuItem = false;
        }
    }

    public void setCurrentMenuItemAsAfterSignOutMenuItem(){
        this.afterSignOutMenuItemId = view.getSelectedItemId();
        this.afterSignOutMenuItemIdSet = true;
        this.shouldOnSignOutChangeSelectedMenuItem = true;
    }

    public void setDefaultSignOutMenuItemAsAfterSignOutMenuItem(){
        this.afterSignOutMenuItemIdSet = false;
        this.shouldOnSignOutChangeSelectedMenuItem = true;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        if(callback != null){
            callback.onSelectedMenuItemChanged(menuItem);
            return true;
        }
        return false;
    }



    public interface BottomMenuEventsCallback{
        void onSelectedMenuItemChanged(MenuItem item);
    }
}
