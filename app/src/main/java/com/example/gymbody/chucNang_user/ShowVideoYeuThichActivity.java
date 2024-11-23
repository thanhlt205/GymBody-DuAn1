package com.example.gymbody.chucNang_user;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.gymbody.R;
import com.example.gymbody.adapterVideo.VideoYeuThichAdapter;
import com.example.gymbody.dbHelper.AnhVideoDBhelper;
import com.example.gymbody.model.VideoFavoriteModel;

import java.util.List;

public class ShowVideoYeuThichActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private VideoYeuThichAdapter adapter;
    private AnhVideoDBhelper dbHelper;
    List<VideoFavoriteModel> favoriteVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_video_yeu_thich);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        recyclerView = findViewById(R.id.rcvVideoYeuThich);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dbHelper = new AnhVideoDBhelper(this);

        favoriteVideos = dbHelper.getFavoriteVideoDetails();
        // Gán Adapter
        adapter = new VideoYeuThichAdapter(this, favoriteVideos);
        recyclerView.setAdapter(adapter);

        swipeRefreshLayout.setOnRefreshListener(() -> {
            favoriteVideos.clear();
            favoriteVideos.addAll(dbHelper.getFavoriteVideoDetails());
            adapter.notifyDataSetChanged();
           swipeRefreshLayout.setRefreshing(false);
        });

    }
}