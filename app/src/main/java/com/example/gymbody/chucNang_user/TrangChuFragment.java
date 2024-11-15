package com.example.gymbody.chucNang_user;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.gymbody.Login;
import com.example.gymbody.R;

public class TrangChuFragment extends Fragment {
    private EditText edtTimKiem;

    public TrangChuFragment() {
        // Required empty public constructor
    }

    public static TrangChuFragment newInstance() {
        TrangChuFragment fragment = new TrangChuFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        edtTimKiem = view.findViewById(R.id.edtTimKiem);
        edtTimKiem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSearchFragment();
            }
        });
        return view;
    }

    private void openSearchFragment() {
        Toast.makeText(getContext(), "Check", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(getActivity(), TimKiemActivity.class));
    }
}