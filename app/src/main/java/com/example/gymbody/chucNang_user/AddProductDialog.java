package com.example.gymbody.chucNang_user;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;

import com.example.gymbody.R;
import com.example.gymbody.model.Product;

import java.io.IOException;

public class AddProductDialog extends Dialog {

    private Uri selectedImageUri = null;
    private ImageView productImageView;
    private ActivityResultLauncher<Intent> imagePickerLauncher; // Thêm biến này

    public AddProductDialog(Context context, OnProductAddedListener listener) {
        super(context);
        setContentView(R.layout.dialog_add_product);

        // Tìm các view trong dialog
        EditText nameEditText = findViewById(R.id.nameEditText);
        EditText priceEditText = findViewById(R.id.priceEditText);
        EditText descriptionEditText = findViewById(R.id.descriptionEditText);
        productImageView = findViewById(R.id.productImageView);
        Button selectImageButton = findViewById(R.id.selectImageButton);
        Button addButton = findViewById(R.id.addButton);
        Button cancelButton = findViewById(R.id.cancelButton);

        // Nút chọn ảnh
        selectImageButton.setOnClickListener(v -> {
            // Mở thư viện ảnh khi người dùng nhấn vào nút
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            imagePickerLauncher.launch(intent); // Sử dụng ActivityResultLauncher đã được truyền vào
        });

        // Nút thêm sản phẩm
        addButton.setOnClickListener(v -> {
            String name = nameEditText.getText().toString().trim();
            String priceText = priceEditText.getText().toString().trim();
            String description = descriptionEditText.getText().toString().trim();

            if (name.isEmpty() || priceText.isEmpty() || description.isEmpty() || selectedImageUri == null) {
                Toast.makeText(context, "Vui lòng nhập đầy đủ thông tin và chọn ảnh", Toast.LENGTH_SHORT).show();
                return;
            }

            double price = Double.parseDouble(priceText);

            // Tạo đối tượng Product mới
            Product newProduct = new Product(0, name, price, selectedImageUri.toString(), description);

            // Gọi callback để thông báo sản phẩm đã được thêm
            listener.onProductAdded(newProduct);

            dismiss();
        });

        // Nút hủy
        cancelButton.setOnClickListener(v -> dismiss());
    }

    // Phương thức để nhận và sử dụng ActivityResultLauncher
    public void setImagePickerLauncher(ActivityResultLauncher<Intent> launcher) {
        this.imagePickerLauncher = launcher;
    }

    // Interface để nghe sự kiện khi sản phẩm được thêm
    public interface OnProductAddedListener {
        void onProductAdded(Product newProduct);
    }
    public void setSelectedImageUri(Uri uri) {
        this.selectedImageUri = uri;
        // Hiển thị ảnh trong ImageView
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
            productImageView.setImageBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(getContext(), "Không thể tải ảnh", Toast.LENGTH_SHORT).show();
        }
    }

}
