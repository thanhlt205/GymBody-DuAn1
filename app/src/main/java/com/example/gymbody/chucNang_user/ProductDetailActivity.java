package com.example.gymbody.chucNang_user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gymbody.R;
import com.example.gymbody.model.Product;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productDetailImageView;
    private TextView productDetailNameTextView, productDetailPriceTextView, productDetailDescriptionTextView;
    private Button addToCartButton;

    @SuppressLint("CheckResult")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        // Ánh xạ các View
        productDetailImageView = findViewById(R.id.productDetailImageView);
        productDetailNameTextView = findViewById(R.id.productDetailNameTextView);
        productDetailPriceTextView = findViewById(R.id.productDetailPriceTextView);
        productDetailDescriptionTextView = findViewById(R.id.productDetailDescriptionTextView);
        addToCartButton = findViewById(R.id.addToCartButton);

        // Nhận dữ liệu sản phẩm từ Intent
        Intent intent = getIntent();
        if (intent != null && intent.hasExtra("product")) {
            Product product = (Product) intent.getSerializableExtra("product");

            if (product != null) {
                // Hiển thị thông tin sản phẩm
                productDetailNameTextView.setText(product.getName());
                productDetailPriceTextView.setText("Giá: " + product.getPrice() + "₫");
                productDetailDescriptionTextView.setText(product.getDescription());
                // Hiển thị ảnh sản phẩm nếu có
                Glide.with(this)
                        .load(product.getImage())
                        .into(productDetailImageView);
                Log.e("TAG", "getImage: " + product.getImage());

            }
        }

        // Xử lý sự kiện nút "Mua Hàng"
        addToCartButton.setOnClickListener(v -> showOrderDialog());
    }

    private void showOrderDialog() {
        // Tạo Dialog
        Dialog orderDialog = new Dialog(this);
        orderDialog.setContentView(R.layout.dialog_order_info);

        // Ánh xạ các View trong layout dialog_order_info.xml
        TextView productNameTextView = orderDialog.findViewById(R.id.orderProductNameTextView);
        TextView productPriceTextView = orderDialog.findViewById(R.id.orderPriceTextView);
        TextView totalPriceTextView = orderDialog.findViewById(R.id.totalAmountTextView);
        TextView totalPaymentTextView = orderDialog.findViewById(R.id.totalPaymentTextView);
        EditText nameEditText = orderDialog.findViewById(R.id.nameEditText);
        EditText phoneEditText = orderDialog.findViewById(R.id.phoneEditText);
        EditText addressEditText = orderDialog.findViewById(R.id.addressEditText);
        RadioGroup paymentMethodGroup = orderDialog.findViewById(R.id.paymentMethodGroup);
        RadioButton airPayOption = orderDialog.findViewById(R.id.airPayOption);
        RadioButton cashOnDeliveryOption = orderDialog.findViewById(R.id.cashOnDeliveryOption);
        Button orderButton = orderDialog.findViewById(R.id.orderButton);

        // Thiết lập thông tin sản phẩm
        String productName = productDetailNameTextView.getText().toString();
        String productPriceText = productDetailPriceTextView.getText().toString();

        productNameTextView.setText(productName);
        productPriceTextView.setText(productPriceText);

        // Tính toán tổng tiền (giả sử chỉ có 1 sản phẩm)
        double productPrice = parsePrice(productPriceText); // Chuyển giá từ String sang double
        double totalPrice = productPrice; // Nếu có thêm sản phẩm, bạn có thể nhân số lượng ở đây

        totalPriceTextView.setText(String.format("Tổng số tiền (1 sản phẩm): %d₫", (long) totalPrice));
        totalPaymentTextView.setText(String.format("Tổng thanh toán: %d₫", (long) totalPrice));

        // Xử lý sự kiện nút "Đặt Hàng"
        orderButton.setOnClickListener(v -> {
            // Lấy thông tin từ EditText
            String userName = nameEditText.getText().toString().trim();
            String userPhone = phoneEditText.getText().toString().trim();
            String userAddress = addressEditText.getText().toString().trim();

            // Kiểm tra xem thông tin đã được nhập đầy đủ hay chưa
            if (userName.isEmpty()) {
                nameEditText.setError("Vui lòng nhập tên người nhận!");
                return;
            }
            if (userPhone.isEmpty()) {
                phoneEditText.setError("Vui lòng nhập số điện thoại!");
                return;
            }
            if (userAddress.isEmpty()) {
                addressEditText.setError("Vui lòng nhập địa chỉ!");
                return;
            }

            // Kiểm tra phương thức thanh toán
            int selectedPaymentMethodId = paymentMethodGroup.getCheckedRadioButtonId();
            if (selectedPaymentMethodId == -1) {
                Toast.makeText(this, "Vui lòng chọn phương thức thanh toán!", Toast.LENGTH_SHORT).show();
                return;
            }

            String paymentMethod = selectedPaymentMethodId == airPayOption.getId()
                    ? "Ví AirPay"
                    : "Thanh toán khi nhận hàng";

            // Hiển thị thông báo xác nhận
            String orderSummary = "Tên: " + userName + "\n"
                    + "Số điện thoại: " + userPhone + "\n"
                    + "Địa chỉ: " + userAddress + "\n"
                    + "Phương thức thanh toán: " + paymentMethod + "\n"
                    + "Tổng tiền: " + String.format("%,d₫", (long) totalPrice);

            Toast.makeText(this, "Đặt hàng thành công!\n" + orderSummary, Toast.LENGTH_LONG).show();

            // Đóng dialog
            orderDialog.dismiss();

            // Chuyển sang màn hình DonHangActivity và truyền dữ liệu
            Intent intent = new Intent(ProductDetailActivity.this, DonHangActivity.class);
            intent.putExtra("productName", productName);
            intent.putExtra("productPrice", productPriceText);
            intent.putExtra("userName", userName);
            intent.putExtra("userPhone", userPhone);
            intent.putExtra("userAddress", userAddress);
            intent.putExtra("paymentMethod", paymentMethod);
            startActivity(intent);
        });


        // Hiển thị Dialog
        orderDialog.show();
    }

    /**
     * Chuyển giá từ chuỗi định dạng sang double.
     * Ví dụ: "229.000₫" -> 229000.0
     */
    /**
     * Chuyển giá từ chuỗi định dạng sang double.
     * Ví dụ: "345₫" -> 345.0
     */
    private double parsePrice(String priceText) {
        try {
            // Loại bỏ ký tự không cần thiết (dấu phân cách và đơn vị tiền tệ)
            String cleanPrice = priceText.replaceAll("[^\\d]", ""); // Chỉ giữ lại số
            return Double.parseDouble(cleanPrice);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            return 0;
        }
    }


}
