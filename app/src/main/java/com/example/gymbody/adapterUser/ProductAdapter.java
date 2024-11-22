package com.example.gymbody.adapterUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymbody.R;
import com.example.gymbody.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;  // Danh sách sản phẩm
    private OnProductClickListener listener;  // Interface xử lý sự kiện click

    // Constructor
    public ProductAdapter(List<Product> productList, OnProductClickListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate layout cho item product
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        // Lấy sản phẩm tại vị trí
        Product product = productList.get(position);

        // Bind dữ liệu vào ViewHolder
        holder.nameTextView.setText(product.getName());
        holder.priceTextView.setText(String.format("%,.0f₫", product.getPrice()));

        // Sử dụng Glide để load hình ảnh sản phẩm
        Glide.with(holder.itemView.getContext())
                .load(product.getImage())  // Đảm bảo product.getImage() là URL hoặc resource hợp lệ
                .placeholder(R.drawable.edit)  // Thêm placeholder (hình ảnh thay thế khi tải)
                .error(R.drawable.ic_launcher_background)  // Thêm hình ảnh lỗi khi tải không thành công
                .into(holder.imageView);

        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v -> {
            if (listener != null) {
                listener.onProductClick(product);  // Gọi phương thức trong listener
            }
        });
    }

    @Override
    public int getItemCount() {
        if (productList != null) {
            return productList.size();  // Trả về số lượng sản phẩm trong danh sách
        }
        return 0;
    }

    // ViewHolder để ánh xạ các view trong item
    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView, priceTextView, descriptionTextView;
        ImageView imageView;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            // Khởi tạo các view
            nameTextView = itemView.findViewById(R.id.productNameTextView);
            priceTextView = itemView.findViewById(R.id.productPriceTextView);
            imageView = itemView.findViewById(R.id.productImageView);
        }
    }

    // Interface để xử lý sự kiện click vào sản phẩm
    public interface OnProductClickListener {
        void onProductClick(Product product);  // Phương thức khi sản phẩm được click
    }
}
