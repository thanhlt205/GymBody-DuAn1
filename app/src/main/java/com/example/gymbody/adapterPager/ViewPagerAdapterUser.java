package com.example.gymbody.adapterPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.gymbody.chucNang_user.CaiDatFragment;
import com.example.gymbody.chucNang_user.LoaiBaiTapFragment;
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
                return new LoaiBaiTapFragment();
            case 2:
                return new CaiDatFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
