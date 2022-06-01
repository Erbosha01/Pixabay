package com.geektech.pixabay;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.geektech.pixabay.adapter.ImageAdapter;
import com.geektech.pixabay.databinding.ActivityMainBinding;
import com.geektech.pixabay.network.PixabayApi;
import com.geektech.pixabay.network.RetrofitService;
import com.geektech.pixabay.network.model.Hit;
import com.geektech.pixabay.network.model.PixabayModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private RetrofitService retrofitService;
    private ImageAdapter adapter;
    public static final String KEY = "27694067-b7eab556f03350f1f1cb6fac0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        retrofitService = new RetrofitService();
        initClickListener();
    }

    private void initClickListener() {
        binding.btnApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String word = binding.etWord.getText().toString();
                getImageFromApi(word, 1, 10);
            }
        });

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                int count = 1;
                String word = binding.etWord.getText().toString();
                getImageFromApi(word, ++count, 10);
                adapter.notifyDataSetChanged();
                binding.swipeRefreshLayout.setRefreshing(false);
            }
        });

    }

    private void getImageFromApi(String word, int page, int perPage) {
        retrofitService.getApi().getImages(KEY, word, page, perPage).enqueue(new Callback<PixabayModel>() {
            @Override
            public void onResponse(Call<PixabayModel> call, Response<PixabayModel> response) {

                adapter = new ImageAdapter((ArrayList<Hit>) response.body().getHits());
                binding.recycler.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<PixabayModel> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}