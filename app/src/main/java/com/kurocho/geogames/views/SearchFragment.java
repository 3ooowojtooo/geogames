package com.kurocho.geogames.views;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.*;
import butterknife.BindString;
import butterknife.ButterKnife;
import com.kurocho.geogames.R;
import com.kurocho.geogames.views.base_fragment.SignInGuardedFragment;
import com.kurocho.geogames.views.base_fragment.UnGuardedFragment;

public class SearchFragment extends UnGuardedFragment implements SearchView.OnQueryTextListener {

    @BindString(R.string.app_bar_title_search)
    String appBarTitle;

    private MainActivity mainActivity;

    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.search, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (getActivity() instanceof MainActivity) {
            mainActivity = (MainActivity) getActivity();
        } else {
            throw new RuntimeException(this.getClass().getCanonicalName() + " can only be attached into MainActivity");
        }
        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        mainActivity.setBarTitle(appBarTitle);
    }

    @Override
    public void onCreateOptionsMenu (Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView sv = (SearchView) item.getActionView();
        sv.setOnQueryTextListener(this);
        sv.setIconifiedByDefault(false);
        sv.setOnSearchClickListener(view -> Log.v("SEARCH","Clicked: "));
        super.onCreateOptionsMenu(menu,inflater);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Log.v("SEARCH","Submitted: "+query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        Log.v("SEARCH","Changed: "+newText);
        return false;
    }

}
