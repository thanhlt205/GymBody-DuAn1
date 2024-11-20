package com.example.gymbody.chucNang_admin;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.adapterAdmin.AcountAdapter;
import com.example.gymbody.model.AcountModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
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

        // Cấu hình padding cho các phần tử giao diện khi có hệ thống thanh trạng thái
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // Khởi tạo các view và đối tượng
        rcvAcount = findViewById(R.id.rcvAcount);
        accountList = new ArrayList<>();
        adapter = new AcountAdapter(this);  // Tạo đối tượng adapter với context là activity này

        // Cấu hình RecyclerView
        rcvAcount.setLayoutManager(new LinearLayoutManager(this));  // Cấu hình layout là LinearLayoutManager
        rcvAcount.setAdapter(adapter);  // Gán adapter cho RecyclerView

        // Khởi tạo Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Lấy danh sách tài khoản người dùng từ Firestore
//        fetchAccountsFromFirestore();
    }

    private void fetchAccountsFromFirestore() {
        Toast.makeText(this, "....................", Toast.LENGTH_SHORT).show();
        db.collection("users")  // "users" là tên collection chứa thông tin tài khoản người dùng
                .get()
                .addOnSuccessListener(querySnapshot -> {
                    // Duyệt qua tất cả các document trong collection và thêm vào danh sách
                    for (DocumentSnapshot document : querySnapshot) {
                        // Giả sử bạn có trường "email" trong mỗi tài liệu người dùng
                        String email = document.getString("email");
                        Log.e("TAG", "fetchAccountsFromFirestore: "+ email );
                        if (email != null) {
                            AcountModel account = new AcountModel(email);  // Tạo đối tượng AcountModel
                            accountList.add(account);  // Thêm vào danh sách tài khoản
                            Log.e("TAG", "fetchAccountsFromFirestore: "+ account.getEmail() );
                            Log.e("TAG", "fetchAccountsFromFirestore: "+ accountList.size() );
                        }
                    }

                    // Cập nhật dữ liệu cho adapter và làm mới RecyclerView
                    adapter.setAcountEmail(accountList);
                })
                .addOnFailureListener(e -> {
                    // Xử lý lỗi nếu không thể lấy dữ liệu
                    Log.e("TAG", "fetchAccountsFromFirestore: "+ e.getMessage() );
                    e.printStackTrace();
                });
    }
}
