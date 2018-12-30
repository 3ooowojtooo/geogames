package com.kurocho.geogames.views.bottom_menu;

import android.support.annotation.IdRes;

class AfterSignOutMenuItemManager {

    private enum IdSelector{
        DEFAULT, NOT_DEFAULT, NOT_SET
    }

    @IdRes
    private int notDefaultAfterSignOutMenuId;

    private IdSelector selector = IdSelector.NOT_SET;

    private boolean changeMenuActivated = false;


    void useNotDefaultMenuItemId(@IdRes int id){
        notDefaultAfterSignOutMenuId = id;
        selector = IdSelector.NOT_DEFAULT;
        activateMenuChanging();
    }

    void useDefaultItemId(){
        selector = IdSelector.DEFAULT;
        activateMenuChanging();
    }

    @IdRes
    int getNotDefaultAfterSignOutMenuId(){
        return notDefaultAfterSignOutMenuId;
    }

    boolean shouldUseDefaultId(){
        return (selector == IdSelector.DEFAULT);
    }

    boolean shouldUseNotDefaultId(){
        return (selector == IdSelector.NOT_DEFAULT);
    }

    boolean shouldChangeMenu(){
        return (changeMenuActivated && selector != IdSelector.NOT_SET);
    }

    void reset(){
        selector = IdSelector.NOT_SET;
    }

    private void activateMenuChanging(){
        changeMenuActivated = true;
    }
}