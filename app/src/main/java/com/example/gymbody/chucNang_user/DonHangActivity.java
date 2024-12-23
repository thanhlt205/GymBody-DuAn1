package com.example.gymbody.chucNang_user;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.example.gymbody.R;
import com.example.gymbody.adapterPager.ViewPagerAdapterDonHang;
import com.google.android.material.tabs.TabLayout;

public class DonHangActivity extends AppCompatActivity {

    TabLayout tabLayout;
    ViewPager2 viewPagerDonHang;
    ViewPagerAdapterDonHang viewPagerAdapterDonHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_hang);

        // Xử lý hiển thị trên toàn màn hình
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Ánh xạ các View
        tabLayout = findViewById(R.id.tabLayout);
        viewPagerDonHang = findViewById(R.id.viewPagerDonHang);

//        // Nhận thông tin đơn hàng từ Intent
//        Bundle orderInfo = getIntent().getExtras();
//        if (orderInfo == null) {
//            orderInfo = new Bundle(); // Nếu không có thông tin, gửi bundle rỗng
//        }

//        // Truyền thông tin cho ViewPagerAdapterDonHang
        viewPagerAdapterDonHang = new ViewPagerAdapterDonHang(this);
        viewPagerDonHang.setAdapter(viewPagerAdapterDonHang);

        // Xử lý khi chuyển Tab
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerDonHang.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });

        // Đồng bộ giữa ViewPager và TabLayout
        viewPagerDonHang.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();
            }
        });
    }
}
