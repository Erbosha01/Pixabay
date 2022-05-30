package com.geektech.pixabay.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.geektech.pixabay.databinding.ItemImageBinding;
import com.geektech.pixabay.network.model.Hit;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private ArrayList<Hit> list;

    public ImageAdapter(ArrayList<Hit> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ImageViewHolder(ItemImageBinding.inflate(LayoutInflater
                .from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        holder.onBind(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ItemImageBinding binding;

        public ImageViewHolder(ItemImageBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void onBind(Hit obj) {
            Glide.with(binding.ivImage).load(obj.getLargeImageURL()).into(binding.ivImage);
        }
    }
}
