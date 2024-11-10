package com.example.gymbody;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.gymbody.adapterPager.ViewPagerAdapterUser;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;

    private BottomNavigationView buttonNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        viewPager = findViewById(R.id.viewPager);
        buttonNavigation = findViewById(R.id.bottomNavigation);

        ViewPagerAdapterUser pagerAdapterAdmin = new ViewPagerAdapterUser(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        viewPager.setAdapter(pagerAdapterAdmin);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        buttonNavigation.getMenu().findItem(R.id.iconTrangChu).setChecked(true);
                        break;
                    case 1:
                        buttonNavigation.getMenu().findItem(R.id.iconLoaiBaiTap).setChecked(true);
                        break;
                    case 2:
                        buttonNavigation.getMenu().findItem(R.id.iconCaiDat).setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        buttonNavigation.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.iconTrangChu) {
                    viewPager.setCurrentItem(0);
                }
                if (itemId == R.id.iconLoaiBaiTap) {
                    viewPager.setCurrentItem(1);
                }
                if (itemId == R.id.iconCaiDat) {
                    viewPager.setCurrentItem(2);
                }
                return true;
            }
        });


    }
}