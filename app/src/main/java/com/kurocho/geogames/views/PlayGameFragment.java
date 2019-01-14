package com.kurocho.geogames.views;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kurocho.geogames.R;
public class PlayGameFragment extends Fragment {


    public PlayGameFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.play_game_fragment, container, false);
    }

}
