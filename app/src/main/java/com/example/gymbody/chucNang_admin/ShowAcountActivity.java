package com.example.gymbody.chucNang_admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.adapterAdmin.AcountAdapter;
import com.example.gymbody.model.AcountModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ShowAcountActivity extends AppCompatActivity {

    RecyclerView rcvAcount;
    AcountAdapter adapter;
    ArrayList<AcountModel> accountList;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_show_acount);

        // Khởi tạo Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Cấu hình padding cho các phần tử giao diện khi có hệ thống thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các view và đối tượng
        rcvAcount = findViewById(R.id.rcvAcount);
        accountList = new ArrayList<>();
        adapter = new AcountAdapter(this, accountList);  // Tạo đối tượng adapter với context là activity này
        GridLayoutManager layoutManager = new GridLayoutManager(this, 1);
        // Cấu hình RecyclerView
        rcvAcount.setLayoutManager(layoutManager);
        rcvAcount.setAdapter(adapter);

        // Lấy danh sách tài khoản người dùng từ Firestore
        fetchAccountsFromFirestore();
    }

    private void fetchAccountsFromFirestore() {
        db.collection("users")
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            AcountModel user = document.toObject(AcountModel.class);
                            accountList.add(user);
                        }
                        adapter.notifyDataSetChanged();
                    } else {
                        Toast.makeText(ShowAcountActivity.this, "Lỗi khi lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
