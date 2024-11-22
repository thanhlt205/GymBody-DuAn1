package com.example.gymbody.adapterPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymbody.chucNang_user.donHang.DaGiaoFragment;
import com.example.gymbody.chucNang_user.donHang.DangXuLiFragment;
import com.example.gymbody.chucNang_user.donHang.HuyBoFragment;

public class ViewPagerAdapterDonHang extends FragmentStateAdapter {

    public ViewPagerAdapterDonHang(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return DangXuLiFragment.newInstance();
            case 1:
                return DaGiaoFragment.newInstance();
            case 2:
                return HuyBoFragment.newInstance();
        }
        return new DangXuLiFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
