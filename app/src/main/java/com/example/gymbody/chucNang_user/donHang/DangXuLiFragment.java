package com.example.gymbody.chucNang_user.donHang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymbody.R;


public class DangXuLiFragment extends Fragment {
    public DangXuLiFragment() {
        // Required empty public constructor
    }
    public static DangXuLiFragment newInstance() {
        DangXuLiFragment fragment = new DangXuLiFragment();
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
        return inflater.inflate(R.layout.fragment_dang_xu_li, container, false);
    }
}