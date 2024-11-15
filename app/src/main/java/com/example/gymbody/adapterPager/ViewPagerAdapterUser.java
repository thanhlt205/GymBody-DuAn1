package com.example.gymbody.adapterPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gymbody.chucNang_user.CaiDatFragment;
import com.example.gymbody.chucNang_user.ChatFragment;
import com.example.gymbody.chucNang_user.DangVideoFragment;
import com.example.gymbody.chucNang_user.TrangChuFragment;

public class ViewPagerAdapterUser extends FragmentPagerAdapter {

    public ViewPagerAdapterUser(@NonNull FragmentManager fm, int behaviorResumeOnlyCurrentFragment) {
        super(fm);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new TrangChuFragment();
            case 1:
                return new DangVideoFragment();
            case 2:
                return new ChatFragment();
            case 3:
                return new CaiDatFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 4;
    }
}
