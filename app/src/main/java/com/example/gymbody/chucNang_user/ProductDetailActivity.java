package com.example.gymbody.chucNang_user;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.gymbody.R;
import com.example.gymbody.dao.StatusProductDAO;
import com.example.gymbody.model.Product;
import com.example.gymbody.model.StatusProduct;

public class ProductDetailActivity extends AppCompatActivity {

    private ImageView productDetailImageView;
    private TextView productDetailNameTextView, productDetailPriceTextView, productDetailDescriptionTextView;
    private Button addToCartButton;

    private ActivityResultLauncher<Intent> addressActivityLauncher;

    // Dữ liệu cập nhật trong Dialog
    private String userName = "";
    private String userPhone = "";
    private String userAddress = "";

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

        // Đăng ký lắng nghe dữ liệu từ DiaChiActivity
        addressActivityLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Intent data = result.getData();
                        userName = data.getStringExtra("name");
                        userPhone = data.getStringExtra("sdt");
                        userAddress = data.getStringExtra("soNha") + ", " +
                                data.getStringExtra("tinhThanh") + " - " +
                                data.getStringExtra("quanHuyen") + " - " +
                                data.getStringExtra("phuongXa");

                        Toast.makeText(this, "Dịa chỉ đã cập nhật thành công vui lòng ấn mau hàng.", Toast.LENGTH_SHORT).show();
                    }
                });

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
        TextView nameEditText = orderDialog.findViewById(R.id.nameEditText);
        TextView phoneEditText = orderDialog.findViewById(R.id.phoneEditText);
        TextView addressEditText = orderDialog.findViewById(R.id.addressEditText);
        RadioGroup paymentMethodGroup = orderDialog.findViewById(R.id.paymentMethodGroup);
        RadioButton airPayOption = orderDialog.findViewById(R.id.airPayOption);
        RadioButton cashOnDeliveryOption = orderDialog.findViewById(R.id.cashOnDeliveryOption);
        Button orderButton = orderDialog.findViewById(R.id.orderButton);
        Button txtClickOpenAddress = orderDialog.findViewById(R.id.txtClickOpenAddress);

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

        // Cập nhật địa chỉ đã chọn nếu có
        nameEditText.setText(userName);
        phoneEditText.setText(userPhone);
        addressEditText.setText(userAddress);

        // Xử lý sự kiện chọn địa chỉ
        txtClickOpenAddress.setOnClickListener(view -> {
            Intent intent = new Intent(ProductDetailActivity.this, DiaChiActivity.class);
            addressActivityLauncher.launch(intent);
            orderDialog.dismiss();
        });

        // Xử lý sự kiện nút "Đặt Hàng"
        orderButton.setOnClickListener(v -> {
            // Lấy thông tin từ EditText
            String finalUserName = nameEditText.getText().toString().trim();
            String finalUserPhone = phoneEditText.getText().toString().trim();
            String finalUserAddress = addressEditText.getText().toString().trim();

            // Kiểm tra xem thông tin đã được nhập đầy đủ hay chưa
            if (finalUserName.isEmpty() || finalUserPhone.isEmpty() || finalUserAddress.isEmpty()) {
                Toast.makeText(this, "Vui lòng chọn địa chỉ nhận hàng!", Toast.LENGTH_SHORT).show();
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

//            // Hiển thị thông báo xác nhận
//            String orderSummary = "Tên: " + finalUserName + "\n"
//                    + "Số điện thoại: " + finalUserPhone + "\n"
//                    + "Địa chỉ: " + finalUserAddress + "\n"
//                    + "Phương thức thanh toán: " + paymentMethod + "\n"
//                    + "Tổng tiền: " + String.format("%,d₫", (long) totalPrice);

//            Toast.makeText(this, "Đặt hàng thành công!\n" + orderSummary, Toast.LENGTH_LONG).show();
            StatusProductDAO statusProductDAO = new StatusProductDAO(this);

            StatusProduct statusProduct = new StatusProduct(productName, totalPrice, totalPrice, finalUserAddress, paymentMethod, "Đang xử lý");
            // Thêm vào cơ sở dữ liệu
            long result = statusProductDAO.add(statusProduct);
            if (result != -1) {
                Toast.makeText(this, "Thêm đơn hàng thành công!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Thêm đơn hàng thất bại!", Toast.LENGTH_SHORT).show();
            }
            // Đóng dialog
            orderDialog.dismiss();
        });

        // Hiển thị Dialog
        orderDialog.show();
    }

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
