package com.example.gymbody.chucNang_user.donHang;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gymbody.R;

public class DaGiaoFragment extends Fragment {
    public DaGiaoFragment() {
        // Required empty public constructor
    }
    public static DaGiaoFragment newInstance() {
        DaGiaoFragment fragment = new DaGiaoFragment();
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
        return inflater.inflate(R.layout.fragment_da_giao, container, false);
    }
}