package com.example.gymbody.adapterUser;

import static java.lang.System.load;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gymbody.R;
import com.example.gymbody.model.Product;

import java.net.URI;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private List<Product> productList;
    private OnProductActionListener listener;

    public interface OnProductActionListener {
        void onDelete(Product product); // Lắng nghe sự kiện xóa
        void onClick(Product product); // Lắng nghe sự kiện click
    }

    public ProductAdapter(List<Product> productList, OnProductActionListener listener) {
        this.productList = productList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(     R.layout.item_product, parent, false);
        return new ProductViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.productNameTextView.setText("Sản phâm:" + product.getName());
        Glide.with(holder.itemView.getContext()).load(product.getImage()).into(holder.productImageView);
        holder.productPriceTextView.setText("Giá:"+product.getPrice());
        // Xử lý sự kiện xóa
        holder.deleteButton.setOnClickListener(v -> listener.onDelete(product));
        // Xử lý sự kiện click
        holder.itemView.setOnClickListener(v -> listener.onClick(product));
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    static class ProductViewHolder extends RecyclerView.ViewHolder {
        TextView productNameTextView,productPriceTextView;
        ImageView productImageView;
        ImageButton deleteButton;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }
}
