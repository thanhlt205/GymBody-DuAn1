package com.example.gymbody.chucNang_user;

import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.gymbody.Login;
import com.example.gymbody.R;
import com.example.gymbody.chucNang_admin.ShowAcountActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CaiDatFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CaiDatFragment extends Fragment {

    TextView txtNameCaiDat;
    Button btnDonHang, btnVideoYeuThich, btnDiaChi, btnThongBao, btnDangXuat, btnTkDaDk;
    private ConstraintLayout constraintTkDaDk;
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
        btnDonHang = view.findViewById(R.id.btnDonHang);
        btnVideoYeuThich = view.findViewById(R.id.btnVideoYeuThich);
        btnDiaChi = view.findViewById(R.id.btnDiaChi);
        btnThongBao = view.findViewById(R.id.btnThongBao);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        btnTkDaDk = view.findViewById(R.id.btnTkDaDk);
        constraintTkDaDk = view.findViewById(R.id.constraintTkDaDk);

        String email = mAuth.getCurrentUser().getEmail().trim();
        txtNameCaiDat.setText("Hi! " + email);

        btnDonHang.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DonHangActivity.class));
        });
        btnVideoYeuThich.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), ShowVideoYeuThichActivity.class));
        });
        btnDiaChi.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), DiaChiActivity.class));
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

                // Xóa trạng thái đăng nhập trong SharedPreferences khi đăng xuất
                requireContext()
                        .getSharedPreferences("USER_PREF", getActivity().MODE_PRIVATE)
                        .edit()
                        .putBoolean("isLoggedIn", false)  // Đặt lại trạng thái đăng nhập là false
                        .apply();

                // Chuyển người dùng trở lại màn hình đăng nhập
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

        if(email.equals("admin@gmail.com")){
            constraintTkDaDk.setVisibility(View.VISIBLE);
            btnTkDaDk.setOnClickListener(v -> {
                Toast.makeText(getActivity(), "Thành công click ấn", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), ShowAcountActivity.class));
            });
        }else{
            btnTkDaDk.setVisibility(View.GONE);
        }

        return view;
    }
}