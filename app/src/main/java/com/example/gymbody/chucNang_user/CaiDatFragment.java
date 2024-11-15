package com.example.gymbody.chucNang_user;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymbody.Login;
import com.example.gymbody.R;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaiDatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaiDatFragment extends Fragment {

    TextView txtNameCaiDat;
    Button btnChonGiaoDien, btnVideoYeuThich, btnDoiNgonNgu, btnThongBao, btnDangXuat;
    private FirebaseAuth mAuth;

    public CaiDatFragment() {
        // Required empty public constructor
    }

    public static CaiDatFragment newInstance() {
        CaiDatFragment fragment = new CaiDatFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cai_dat, container, false);
        txtNameCaiDat = view.findViewById(R.id.txtNameCaiDat);
        btnChonGiaoDien = view.findViewById(R.id.btnChonGiaoDien);
        btnVideoYeuThich = view.findViewById(R.id.btnVideoYeuThich);
        btnDoiNgonNgu = view.findViewById(R.id.btnDoiNgonNgu);
        btnThongBao = view.findViewById(R.id.btnThongBao);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);

        String email = mAuth.getCurrentUser().getEmail();
        txtNameCaiDat.setText("Hi! "+email);

        btnChonGiaoDien.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Xin lỗi! Chức năng đang được sử lý", Toast.LENGTH_SHORT).show();
        });
        btnVideoYeuThich.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Xin lỗi! Chức năng đang được sử lý", Toast.LENGTH_SHORT).show();
        });
        btnDoiNgonNgu.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Xin lỗi! Chức năng đang được sử lý", Toast.LENGTH_SHORT).show();
        });
        btnThongBao.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Xin lỗi! Chức năng đang được sử lý", Toast.LENGTH_SHORT).show();
        });

        btnDangXuat.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle("Thông báo");
            builder.setMessage("Bạn có chắc chắn muốn đăng xuất?");
            builder.setCancelable(true);
            builder.setPositiveButton("Có", (dialog, which) -> {
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
                dialog.dismiss();
            });
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        });
        return view;
    }
}