package com.example.gymbody.adapterVideo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.example.gymbody.R;
import com.example.gymbody.chucNang_user.ShowVideoActivity;
import com.example.gymbody.model.anhVideoModel;

import java.util.ArrayList;

public class AnhVideoAdapter extends RecyclerView.Adapter<AnhVideoAdapter.ViewHolder> {

    Context context;
    ArrayList<anhVideoModel> arrayList;

    public AnhVideoAdapter(Context context, ArrayList<anhVideoModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AnhVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_rcv, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnhVideoAdapter.ViewHolder holder, int position) {
        anhVideoModel model = arrayList.get(position);

        holder.txtTieuDeRcv.setText(model.getTen());
        holder.txtNgayRcv.setText("Ngày đăng: " + model.getNgay());

        // Lấy đường dẫn ảnh từ model và tải nó vào ImageView
        String imageUrl = model.getAnh();
        Log.e("TAG", "Image URL: " + imageUrl);  // Kiểm tra đường dẫn ảnh trong log

        // Sử dụng Glide để tải ảnh vào ImageView
        Glide.with(context)
                .load(imageUrl)  // Tải ảnh từ URL hoặc URI
                .placeholder(R.drawable.edit)  // Placeholder khi ảnh đang tải
                .error(R.drawable.ic_launcher_foreground)  // Hình ảnh hiển thị khi có lỗi
                .diskCacheStrategy(DiskCacheStrategy.ALL)  // Cải thiện hiệu suất với cache
                .into(holder.imgAnhRcv);

        // Xử lý sự kiện khi người dùng click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowVideoActivity.class);
            intent.putExtra("id", String.valueOf(model.getId()));
            context.startActivity(intent);
            Log.e("ID", "ID của video: " + model.getId());
        });
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng phần tử trong danh sách
        return arrayList != null ? arrayList.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtTieuDeRcv, txtNgayRcv;
        ImageView imgYeuThichRcv, imgAnhRcv;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTieuDeRcv = itemView.findViewById(R.id.txtTieuDeRcv);
            txtNgayRcv = itemView.findViewById(R.id.txtNgayRcv);
            imgYeuThichRcv = itemView.findViewById(R.id.imgYeuThichRcv);
            imgAnhRcv = itemView.findViewById(R.id.imgAnhRcv);
        }
    }
}