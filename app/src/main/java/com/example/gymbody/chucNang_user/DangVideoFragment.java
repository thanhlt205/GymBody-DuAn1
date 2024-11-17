package com.example.gymbody.chucNang_user;

import static android.app.Activity.RESULT_OK;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.example.gymbody.R;
import com.example.gymbody.dbHelperVideo.AnhVideoDBhelper;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DangVideoFragment extends Fragment {

    TextView ngayDang;
    EditText tieuDeDang;
    ImageView imgAnh;
    ImageView imgAddAnh;
    ImageView imgAddVideo;
    VideoView viewVideo;
    Button btnDangAnh;

    private static final int REQUEST_CODE_PERMISSION = 1;
    private static final int REQUEST_CODE_PICK_IMAGE = 2;
    private static final int REQUEST_CODE_PICK_VIDEO = 3;
    private int currentPosition = 0;

    private String uriAnh;
    private String uriVideo;

    public DangVideoFragment() {
        // Required empty public constructor
    }

    public static DangVideoFragment newInstance() {
        DangVideoFragment fragment = new DangVideoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dang_video, container, false);
        ngayDang = view.findViewById(R.id.ngayDang);
        tieuDeDang = view.findViewById(R.id.tieuDeDang);
        imgAnh = view.findViewById(R.id.imgAnh);
        imgAddAnh = view.findViewById(R.id.imgAddAnh);
        imgAddVideo = view.findViewById(R.id.imgAddVideo);
        viewVideo = view.findViewById(R.id.viewVideo);
        btnDangAnh = view.findViewById(R.id.btnDangAnh);

        imgAddAnh.setOnClickListener(v -> {
            openMediaPicker("image");
        });
        imgAddVideo.setOnClickListener(v -> {
            openMediaPicker("video");
        });
        btnDangAnh.setOnClickListener(v -> {
            dangVideo();
        });


        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        String ngayHienTai = df.format(Calendar.getInstance().getTime());
        ngayDang.setText(ngayHienTai);


        // Tạo đối tượng AnhVideoDBhelper
        AnhVideoDBhelper dbHelper = new AnhVideoDBhelper(getContext());
        // Gọi getWritableDatabase() để kích hoạt việc tạo cơ sở dữ liệu
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        // Kiểm tra xem cơ sở dữ liệu đã được mở thành công hay chưa
        if (db != null) {
            Log.e("Database", "Database created/opened successfully");
        }
        return view;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getContext(), "Quyền truy cập bộ nhớ đã được cấp", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Quyền truy cập bộ nhớ chưa được cấp", Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void openMediaPicker(String mediaType) {
        Intent intent = new Intent(Intent.ACTION_PICK);

        if ("image".equals(mediaType)) {
            intent.setType("image/*");  // Chỉ lọc ảnh
            startActivityForResult(intent, REQUEST_CODE_PICK_IMAGE);
        } else if ("video".equals(mediaType)) {
            intent.setType("video/*");  // Chỉ lọc video
            startActivityForResult(intent, REQUEST_CODE_PICK_VIDEO);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {
            Uri selectedUri = data.getData();
            if (requestCode == REQUEST_CODE_PICK_IMAGE) {
                uriAnh = selectedUri.toString();
                imgAnh.setImageURI(selectedUri);
                imgAnh.setVisibility(View.VISIBLE);
                viewVideo.start();
                Toast.makeText(getContext(), "Ảnh đã được đẩy lên.", Toast.LENGTH_SHORT).show();
                return;
            } else {
                Toast.makeText(getContext(), "Không thể đẩy ảnh lên.", Toast.LENGTH_SHORT).show();
            }
            if (requestCode == REQUEST_CODE_PICK_VIDEO) {
                uriVideo = selectedUri.toString();
                viewVideo.setVideoURI(selectedUri);
                viewVideo.setVisibility(View.VISIBLE);
                viewVideo.start();
                Toast.makeText(getContext(), "Video đã được đẩy lên.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getContext(), "Không thể đẩy video lên.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void dangVideo() {
        // Lấy thông tin từ UI
        String ngay = ngayDang.getText().toString();
        String tieuDe = tieuDeDang.getText().toString();

        // Kiểm tra xem các trường dữ liệu có bị thiếu không
        if (ngay.isEmpty() || tieuDe.isEmpty() || uriAnh == null || uriVideo == null) {
            Toast.makeText(getContext(), "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            // Tạo đối tượng AnhVideoDBhelper
            AnhVideoDBhelper dbHelper = new AnhVideoDBhelper(getContext());
            SQLiteDatabase db = null;
            try {
                // Mở cơ sở dữ liệu để có thể ghi
                db = dbHelper.getWritableDatabase();

                // Chèn dữ liệu vào cơ sở dữ liệu
                dbHelper.insert(tieuDe, ngay, uriAnh, uriVideo);

                // Thông báo và log thông tin
                Toast.makeText(getContext(), "Đăng video thành công", Toast.LENGTH_SHORT).show();
                Log.e("Ngày đăng: ", ngay);
                Log.e("Tiêu đề: ", tieuDe);
                Log.e("URL ảnh: ", uriAnh);
                Log.e("URL video: ", uriVideo);
            } catch (Exception e) {
                Log.e("Database Error", "Error during inserting: " + e.getMessage());
            } finally {
                if (db != null && db.isOpen()) {
                    db.close(); // Đảm bảo đóng cơ sở dữ liệu khi xong
                }
            }
        }
    }

    //    // Phương thức mở bộ chọn video
//    private void openVideoPicker() {
//        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
//        intent.setType("video/*");
//        startActivityForResult(intent, REQUEST_CODE_PICK_VIDEO);
//    }
}