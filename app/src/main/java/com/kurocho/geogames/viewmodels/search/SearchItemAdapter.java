package com.kurocho.geogames.viewmodels.search;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import com.kurocho.geogames.R;
import com.kurocho.geogames.data.search.SearchGameDetails;
import com.kurocho.geogames.databinding.SearchItemBinding;

import java.util.ArrayList;
import java.util.List;


public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemAdapter.SearchItemViewHolder> {

    public static class SearchItemViewHolder extends RecyclerView.ViewHolder {
        SearchItemBinding binding;

        public SearchItemViewHolder(SearchItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    private List<SearchGameDetails> data;
    private DownloadGameInterface downloadGameInterface;

    public SearchItemAdapter(DownloadGameInterface downloadGameInterface){
        this.downloadGameInterface = downloadGameInterface;
        data = new ArrayList<>();
    }

    public void setData(List<SearchGameDetails> games){
        this.data.clear();
        this.data.addAll(games);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        SearchItemBinding binding = DataBindingUtil.
                inflate(LayoutInflater.from(viewGroup.getContext()), R.layout.search_item,
                        viewGroup, false);
        return new SearchItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchItemViewHolder searchItemViewHolder, int i) {
        SearchGameDetails game = data.get(i);
        searchItemViewHolder.binding.setGame(game);
        searchItemViewHolder.binding.download.setOnClickListener(v -> {
            downloadGameInterface.download(game);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }



    @FunctionalInterface
    public interface DownloadGameInterface{
        void download(SearchGameDetails gameDetails);
    }
}

