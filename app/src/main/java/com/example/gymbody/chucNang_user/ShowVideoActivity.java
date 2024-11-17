package com.example.gymbody.chucNang_user;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.gymbody.R;
import com.example.gymbody.dbHelperVideo.AnhVideoDBhelper;

public class ShowVideoActivity extends AppCompatActivity {

    private String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_video);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        idVideo = getIntent().getStringExtra("id");
        Log.e("ID", "check: "+ idVideo );

        if (idVideo != null) {
            // Lấy đường dẫn video từ cơ sở dữ liệu
            VideoView videoView = findViewById(R.id.viewVideoShow);
            AnhVideoDBhelper dbHelper = new AnhVideoDBhelper(this);
            String videoUrl = dbHelper.getVideoUrlById(Integer.parseInt(idVideo));

            if (videoUrl != null && !videoUrl.isEmpty()) {
                videoView.setVideoURI(Uri.parse(videoUrl)); // Thiết lập URI của video
                videoView.start(); // Bắt đầu phát video
            } else {
                Log.e("VideoUrl", "Không tìm thấy URL video");
                Toast.makeText(this, "Không tìm thấy video", Toast.LENGTH_SHORT).show();
            }
        } else {
            // Xử lý trường hợp không có ID video
            Toast.makeText(this, "Không tìm thấy ID video", Toast.LENGTH_SHORT).show();
        }
    }
}