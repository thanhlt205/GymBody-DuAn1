//package com.example.gymbody;
//
//import android.database.sqlite.SQLiteDatabase;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.example.gymbody.dao.CartDao;
//import com.example.gymbody.dbHelper.DatabaseHelper;
//
//public class PaymentActivity extends AppCompatActivity {
//    private Button paymentOptionButton1, paymentOptionButton2, paymentOptionButton3;
//    private SQLiteDatabase db;
//    private CartDao cartDao;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_payment);
//
//        db = new DatabaseHelper(this).getWritableDatabase(); // Giả sử MyDatabaseHelper là lớp quản lý CSDL
//        cartDao = new CartDao(db);
//
//        paymentOptionButton1 = findViewById(R.id.paymentOptionButton1);
//        paymentOptionButton2 = findViewById(R.id.paymentOptionButton2);
//        paymentOptionButton3 = findViewById(R.id.paymentOptionButton3);
//
//        // Xử lý thanh toán qua thẻ tín dụng
//        paymentOptionButton1.setOnClickListener(v -> handlePayment("Thẻ tín dụng"));
//
//        // Xử lý thanh toán khi nhận hàng
//        paymentOptionButton2.setOnClickListener(v -> handlePayment("Thanh toán khi nhận hàng"));
//
//        // Xử lý thanh toán qua ví điện tử
//        paymentOptionButton3.setOnClickListener(v -> handlePayment("Ví điện tử"));
//    }
//
//    // Phương thức xử lý thanh toán
//    private void handlePayment(String paymentMethod) {
//        // Xử lý thanh toán (giả sử thanh toán thành công)
//        Toast.makeText(this, "Thanh toán thành công qua: " + paymentMethod, Toast.LENGTH_SHORT).show();
//
//        // Sau khi thanh toán thành công, xóa toàn bộ giỏ hàng
//        cartDao.clearCart();
//
//        // Quay lại màn hình trước hoặc thông báo thanh toán thành công
//        finish(); // Hoặc chuyển đến một Activity khác như màn hình đơn hàng thành công
//    }
//}
