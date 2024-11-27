package com.example.gymbody.adapterPager;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.gymbody.chucNang_user.donHang.DaGiaoFragment;
import com.example.gymbody.chucNang_user.donHang.DangXuLiFragment;
import com.example.gymbody.chucNang_user.donHang.HuyBoFragment;

public class ViewPagerAdapterDonHang extends FragmentStateAdapter {

    private Bundle orderInfo; // Lưu thông tin đơn hàng

    public ViewPagerAdapterDonHang(@NonNull FragmentActivity fragmentActivity, Bundle orderInfo) {
        super(fragmentActivity);
        this.orderInfo = orderInfo; // Lưu thông tin nhận được từ Activity
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = DangXuLiFragment.newInstance(orderInfo); // Truyền thông tin cho "Đang xử lý"
                break;
            case 1:
                fragment = new DaGiaoFragment(); // Không truyền dữ liệu, giữ trống
                break;
            case 2:
                fragment = new HuyBoFragment(); // Không truyền dữ liệu, giữ trống
                break;
        }
        return fragment;
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
