package com.example.gymbody;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;

public class Manhinhchao extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manhinhchao);
        ImageView gifImageView = findViewById(R.id.gifImageView);

        Glide.with(this)
                .asGif()
                .load(R.drawable.gym)
                .into(gifImageView);

        new Handler().postDelayed(() -> {
            Intent intent = new Intent(Manhinhchao.this, Login.class);
            startActivity(intent);
            finish();
        }, 3000);
    }
}