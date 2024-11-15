package com.example.gymbody.chucNang_user;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.example.gymbody.R;

public class TimKiemActivity extends AppCompatActivity {

    EditText edtSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tim_kiem);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtSearch = findViewById(R.id.edtSearch);
        edtSearch.requestFocus();
    }

//    @Override
//    public void onBackPressed() {
//        super.onBackPressed();
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        if (fragmentManager.getBackStackEntryCount() > 0) {
//            // Nếu có fragment trong Back Stack, quay lại fragment trước đó
//            fragmentManager.popBackStack();
//            Toast.makeText(this, "Quay lại", Toast.LENGTH_SHORT).show();
//        } else {
//            // Nếu không có fragment trong Back Stack, thực hiện hành động mặc định của nút back
//            super.onBackPressed();
//            Toast.makeText(this, "K ôs có gì", Toast.LENGTH_SHORT).show();
//        }
//    }
}