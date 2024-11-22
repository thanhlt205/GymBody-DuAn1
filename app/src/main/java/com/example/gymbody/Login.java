package com.example.gymbody;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText edtEmail, edtPassword;
    private Button btnLogin;

    private TextView txtResetPassword, txtRegister;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);

        // Khởi tạo FirebaseApp nếu chưa khởi tạo
        if (FirebaseApp.getApps(this).isEmpty()) {
            FirebaseApp.initializeApp(this);
        }
        // Sử dụng FirebaseAuth hoặc các dịch vụ khác
        mAuth = FirebaseAuth.getInstance();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        txtResetPassword = findViewById(R.id.txtResetPassword);
        txtRegister = findViewById(R.id.txtRegister);

        // Kiểm tra xem người dùng đã đăng nhập chưa
        SharedPreferences preferences = getSharedPreferences("USER_PREF", MODE_PRIVATE);
        boolean isLoggedIn = preferences.getBoolean("isLoggedIn", false);  // False là giá trị mặc định

        // Nếu đã đăng nhập, chuyển đến màn hình chính
        if (isLoggedIn) {
            // Người dùng đã đăng nhập trước đó, chuyển đến MainActivity
            startActivity(new Intent(Login.this, MainActivity.class));
            finish();  // Đóng LoginActivity
            return;  // Dừng lại, không thực hiện thêm bước đăng nhập nữa
        }
        btnLogin.setOnClickListener(view -> {
            if (edtEmail.getText().toString().isEmpty() || edtPassword.getText().toString().isEmpty()) {
                Toast.makeText(Login.this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            } else {
                mAuth.signInWithEmailAndPassword(edtEmail.getText().toString().trim(), edtPassword.getText().toString().trim())
                        .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Lưu trạng thái đăng nhập vào SharedPreferences
                                    SharedPreferences preferences = getSharedPreferences("USER_PREF", MODE_PRIVATE);
                                    SharedPreferences.Editor editor = preferences.edit();
                                    editor.putBoolean("isLoggedIn", true);  // Đánh dấu người dùng đã đăng nhập
                                    editor.apply();

                                    String getEmailEdt = edtEmail.getText().toString().trim();
                                    if (getEmailEdt.equals("admin@gmail.com")) {
                                        startActivity(new Intent(Login.this, MainActivity.class));
                                        Toast.makeText(Login.this, "Đăng nhập thành công admin", Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        startActivity(new Intent(Login.this, MainActivity.class));
                                        Toast.makeText(Login.this, "Đăng nhập thành công user", Toast.LENGTH_SHORT).show();
                                        finish();
                                    }

                                } else {
                                    Toast.makeText(Login.this, "Đăng nhập thất bại", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });

        txtRegister.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, RegisterActivity.class));
        });
    }
}