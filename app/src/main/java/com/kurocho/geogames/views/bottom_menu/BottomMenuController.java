package com.kurocho.geogames.views.bottom_menu;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.view.MenuItem;

public class BottomMenuController implements BottomNavigationView.OnNavigationItemSelectedListener {

    private BottomMenu view;
    private BottomMenuEventsCallback callback = null;

    private AfterSignOutMenuItemManager signOutMenuItemManager;

    public BottomMenuController(BottomMenu view){
        this.view = view;
        this.signOutMenuItemManager = new AfterSignOutMenuItemManager();
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
        if(signOutMenuItemManager.shouldChangeMenu()){
            if(signOutMenuItemManager.shouldUseNotDefaultId()){
                int menuItemId = signOutMenuItemManager.getNotDefaultAfterSignOutMenuId();
                view.setSelectedItemId(menuItemId);
            } else if(signOutMenuItemManager.shouldUseDefaultId()){
                view.selectAfterSignOutDefaultItem();
            }
            signOutMenuItemManager.reset();
        }
    }

    public void setCurrentMenuItemAsAfterSignOutMenuItem(){
        int currentMenuItemId = view.getSelectedItemId();
        signOutMenuItemManager.useNotDefaultMenuItemId(currentMenuItemId);
    }

    public void setDefaultSignOutMenuItemAsAfterSignOutMenuItem(){
        signOutMenuItemManager.useDefaultItemId();
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
