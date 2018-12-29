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
    }

    public void onSignedOut(){
        view.showSignedOutMenu();
        if(afterSignOutMenuItemIdSet){
            view.setSelectedItemId(afterSignOutMenuItemId);
            afterSignOutMenuItemIdSet = false;
        } else{
            view.selectAfterSignOutDefaultItem();
        }
    }

    public void setAfterSignOutMenuItemId(@IdRes int id){
        this.afterSignOutMenuItemId = id;
        this.afterSignOutMenuItemIdSet = true;
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
