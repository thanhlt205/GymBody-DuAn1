package com.example.gymbody.chucNang_user;

import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.adapterUser.DiaChiAdapter;
import com.example.gymbody.dao.DiaChiDAO;
import com.example.gymbody.model.DiaChiModel;

import java.util.List;

public class DiaChiActivity extends AppCompatActivity {

    ImageView imgAddDiaChi;
    RecyclerView rcvDiaChi;
    DiaChiDAO diaChiDAO;
    DiaChiAdapter diaChiAdapter;
    List<DiaChiModel> lstDiaChi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_dia_chi);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imgAddDiaChi = findViewById(R.id.imgAddDiaChi);
        rcvDiaChi = findViewById(R.id.rcvDiaChi);

        diaChiDAO = new DiaChiDAO(this);
        lstDiaChi = diaChiDAO.getAllDiaChi();
        diaChiAdapter = new DiaChiAdapter(this, lstDiaChi);
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        rcvDiaChi.setLayoutManager(layoutManager);
        rcvDiaChi.setAdapter(diaChiAdapter);
        diaChiAdapter.notifyDataSetChanged();

        imgAddDiaChi.setOnClickListener(v -> {
            View view = LayoutInflater.from(this).inflate(R.layout.layout_item_add_dia_chi, null);
            Dialog dialog = new Dialog(this);
            dialog.setContentView(view);
            dialog.setCancelable(false);

            EditText edtHoTen = view.findViewById(R.id.edtHoTen);
            EditText edtSdt = view.findViewById(R.id.edtSdt);
            EditText edtTinhThanh = view.findViewById(R.id.edtTinhThanh);
            EditText edtQuanHuyen = view.findViewById(R.id.edtQuanHuyen);
            EditText edtPhuongXa = view.findViewById(R.id.edtPhuongXa);
            EditText edtSoNha = view.findViewById(R.id.edtSoNha);
            Button btnLuu = view.findViewById(R.id.btnLuu);
            Button btnHuy = view.findViewById(R.id.btnHuy);

            btnHuy.setOnClickListener(v1 -> {
                dialog.dismiss();
            });

            btnLuu.setOnClickListener(v1 -> {
                String hoTen = edtHoTen.getText().toString().trim();
                String sdt = edtSdt.getText().toString().trim();
                String tinhThanh = edtTinhThanh.getText().toString().trim();
                String quanHuyen = edtQuanHuyen.getText().toString().trim();
                String phuongXa = edtPhuongXa.getText().toString().trim();
                String soNha = edtSoNha.getText().toString().trim();

                if (hoTen.isEmpty() || sdt.isEmpty() || tinhThanh.isEmpty() || quanHuyen.isEmpty() || phuongXa.isEmpty() || soNha.isEmpty()) {
                    Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                } else {
                    // Tạo đối tượng DiaChiDAO
                    DiaChiDAO diaChiDAO = new DiaChiDAO(this);

                    // Tạo đối tượng DiaChiModel từ dữ liệu nhập
                    DiaChiModel diaChi = new DiaChiModel(hoTen, sdt, tinhThanh, quanHuyen, phuongXa, soNha);

                    // Thêm vào cơ sở dữ liệu
                    long result = diaChiDAO.addDiaChi(diaChi);

                    if (result != -1) {
                        Toast.makeText(this, "Thêm địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                        lstDiaChi.add(diaChi);
                        diaChiAdapter.notifyDataSetChanged();
                        dialog.dismiss(); // Đóng dialog sau khi thêm thành công
                    } else {
                        Toast.makeText(this, "Thêm địa chỉ thất bại!", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            dialog.show();
        });

    }
}