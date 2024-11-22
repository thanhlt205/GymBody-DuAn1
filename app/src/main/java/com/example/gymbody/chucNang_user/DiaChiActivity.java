package com.example.gymbody.chucNang_user;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gymbody.R;

public class DiaChiActivity extends AppCompatActivity {

    ImageView imgAddDiaChi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dia_chi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgAddDiaChi = findViewById(R.id.imgAddDiaChi);
        imgAddDiaChi.setOnClickListener(v -> {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_item_add_dia_chi, null);
            Dialog dialog = new Dialog(this);
            dialog.setContentView(view);
            dialog.setCancelable(false);
            dialog.show();
        });

    }
}