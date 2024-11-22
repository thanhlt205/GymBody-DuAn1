package com.example.gymbody.chucNang_user;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.adapterVideo.AnhVideoAdapter;
import com.example.gymbody.dao.AnhVideoDAO;
import com.example.gymbody.dbHelper.AnhVideoDBhelper;
import com.example.gymbody.model.AnhVideoModel;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class ShowVideoActivity extends AppCompatActivity {

    private String idVideo;
    private ImageView imgPlayVideo, imgScreenQuayVideo;
    private SeekBar seekBarVideo;
    FrameLayout videoFrame;
    LinearLayout thoiGian;
    private TextView thoiGianChayVideo, thoiGianVideo;
    private RecyclerView recyclerView;
    private VideoView videoView;
    private AnhVideoDAO anhVideoDAO;
    private AnhVideoAdapter adapterAnhVideo;
    ArrayList<AnhVideoModel> arrayList = new ArrayList<>();

    private Handler handler = new Handler();
    private int viTriHienTai = 0;
    private boolean isLandscape = false;
    private boolean isSeeking = false;  // Để tránh việc xung đột khi người dùng đang kéo

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

        recyclerView = findViewById(R.id.recyclerView);
        videoView = findViewById(R.id.viewVideoShow);
        imgPlayVideo = findViewById(R.id.imgPlayVideo);
        imgScreenQuayVideo = findViewById(R.id.imgScreenQuayVideo);
        seekBarVideo = findViewById(R.id.seekBarVideo);
        videoFrame = findViewById(R.id.videoFrame);
        thoiGianChayVideo = findViewById(R.id.thoiGianChayVideo);
        thoiGianVideo = findViewById(R.id.thoiGianVideo);
        thoiGian = findViewById(R.id.thoiGian);

        anhVideoDAO = new AnhVideoDAO(this);
        // Lấy dữ liệu từ cơ sở dữ liệu
        arrayList = anhVideoDAO.getAll();

        // Lấy ID video từ Intent
        idVideo = getIntent().getStringExtra("id");
        Log.e("ID", "check: " + idVideo);

        if (idVideo != null) {
            // Lấy đường dẫn video từ cơ sở dữ liệu
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

        // Tạo Runnable để ẩn imgPlayVideo
        Runnable hideImgRunnable = () -> {
            imgPlayVideo.setVisibility(View.GONE);
            seekBarVideo.setVisibility(View.GONE);
            thoiGian.setVisibility(View.GONE);
        };

        videoFrame.setOnClickListener(view -> {
            imgPlayVideo.setVisibility(View.VISIBLE);
            seekBarVideo.setVisibility(View.VISIBLE);
            thoiGian.setVisibility(View.VISIBLE);
            // Hủy bộ đếm trước đó (nếu có)
            imgPlayVideo.removeCallbacks(hideImgRunnable);
            seekBarVideo.removeCallbacks(hideImgRunnable);
            thoiGian.setVisibility(View.VISIBLE);
            // Bắt đầu lại bộ đếm ẩn sau 3 giây
            imgPlayVideo.postDelayed(hideImgRunnable, 3000);
            seekBarVideo.postDelayed(hideImgRunnable, 3000);
            thoiGian.postDelayed(hideImgRunnable, 3000);
        });

        // Khi nhấn vào imgPlayVideo
        imgPlayVideo.setOnClickListener(v -> {
            if (videoView.isPlaying()) {
                videoView.pause();
                imgPlayVideo.setImageResource(R.drawable.icon_play);
                // Hủy bộ đếm để giữ nút hiển thị khi video bị tạm dừng
                imgPlayVideo.removeCallbacks(hideImgRunnable);
                imgPlayVideo.setVisibility(View.VISIBLE);
                seekBarVideo.setVisibility(View.VISIBLE);
                thoiGian.setVisibility(View.VISIBLE);
            } else {
                videoView.start();
                imgPlayVideo.setImageResource(R.drawable.icon_pause);
                // Bắt đầu lại bộ đếm ẩn sau 3 giây
                imgPlayVideo.removeCallbacks(hideImgRunnable);
                imgPlayVideo.postDelayed(hideImgRunnable, 3000);
                seekBarVideo.postDelayed(hideImgRunnable, 3000);
                thoiGian.postDelayed(hideImgRunnable, 3000);
                updateSeekBar();
            }
        });

        // Cập nhật `SeekBar` khi video chuẩn bị
        videoView.setOnPreparedListener(mp -> {
            seekBarVideo.setMax(videoView.getDuration()); // Thiết lập `SeekBar` với thời lượng video
            videoView.seekTo(viTriHienTai); // Tua đến vị trí đã lưu (nếu có)
            videoView.start();
            thoiGianVideo.setText(formatTime(videoView.getDuration())); // Đặt thời lượng tổng cho remainingTime
            updateSeekBar(); // Gọi hàm cập nhật `SeekBar`
        });


        seekBarVideo.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {  // Chỉ cập nhật khi người dùng kéo
                    videoView.seekTo(progress);  // Di chuyển video đến vị trí mới
                    thoiGianChayVideo.setText(formatTime(progress));  // Cập nhật thời gian hiện tại
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                isSeeking = true;  // Đánh dấu người dùng bắt đầu kéo
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                isSeeking = false;  // Đánh dấu người dùng ngừng kéo
                int progress = seekBar.getProgress();  // Lấy vị trí mới từ SeekBar
                videoView.seekTo(progress);  // Đảm bảo video tua đến vị trí chính xác
                thoiGianChayVideo.setText(formatTime(progress));  // Cập nhật thời gian
            }
        });

//        imgScreenQuayVideo.setOnClickListener(view -> {
//            if (isLandscape) {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//            } else {
//                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
//            }
//        });


        // Hiển thị dữ liệu vào RecyclerView
        adapterAnhVideo = new AnhVideoAdapter(this, arrayList);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapterAnhVideo);

        // Thong bao thay doi du lieu
        adapterAnhVideo.notifyDataSetChanged();
    }


    // Phương thức định dạng thời gian từ milliseconds sang mm:ss
    private String formatTime(int millis) {
        long minutes = TimeUnit.MILLISECONDS.toMinutes(millis);
        long seconds = TimeUnit.MILLISECONDS.toSeconds(millis) -
                TimeUnit.MINUTES.toSeconds(minutes);
        return String.format("%02d:%02d", minutes, seconds);
    }

    private void updateSeekBar() {
        if (!isSeeking) {  // Đảm bảo không cập nhật khi người dùng đang kéo
            int currentPos = videoView.getCurrentPosition();  // Lấy vị trí hiện tại của video
            seekBarVideo.setProgress(currentPos);  // Cập nhật SeekBar
            thoiGianChayVideo.setText(formatTime(currentPos));  // Cập nhật thời gian chạy video
        }

        if (videoView.isPlaying()) {  // Nếu video đang phát
            handler.postDelayed(this::updateSeekBar, 100);  // Lặp lại sau 100ms
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // Lưu vị trí hiện tại của video
        viTriHienTai = videoView.getCurrentPosition();
        outState.putInt("viTriHienTai", viTriHienTai);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // Phục hồi vị trí video khi xoay màn hình
        viTriHienTai = savedInstanceState.getInt("viTriHienTai");
        videoView.seekTo(viTriHienTai);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tiếp tục phát video từ vị trí đã lưu khi quay lại
        videoView.seekTo(viTriHienTai);
        videoView.start();
        updateSeekBar(); // Cập nhật SeekBar khi quay lại
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Tạm dừng và lưu vị trí video khi rời khỏi Activity
        viTriHienTai = videoView.getCurrentPosition();
        videoView.pause();
        handler.removeCallbacksAndMessages(null); // Xóa các callback để tránh leak
    }

}