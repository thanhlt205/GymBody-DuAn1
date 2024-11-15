package com.example.gymbody.adapterVideo;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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
//        anhVideoModel anhVideoModel = arrayList.get(holder.getAdapterPosition());
        holder.txtTieuDeRcv.setText(arrayList.get(position).getTen());
        holder.txtNgayRcv.setText("Ngày đăng: "+arrayList.get(position).getNgay());
        // Sử dụng Glide để tải ảnh từ URL
        Glide.with(context)
                .load(arrayList.get(position).getAnh()) // URL của ảnh
                .placeholder(R.drawable.edit) // Hiển thị khi đang tải
                .error(R.drawable.ic_launcher_foreground) // Hiển thị khi lỗi tải ảnh
                .into(holder.imgAnhRcv); // ImageView để hiển thị
//        Uri uri = Uri.parse(arrayList.get(position).getAnh());
//        Log.e("TAG", "urianhhhhhhhh: "+uri );
//        holder.imgAnhRcv.setImageURI(uri);

        holder.itemView.setOnClickListener(v -> {
//            Toast.makeText(context, "Xin lỗi! Đang sử lý video", Toast.LENGTH_SHORT).show();
            // Thêm xử lý khi item được nhấn
            Intent intent = new Intent(context, ShowVideoActivity.class);
            intent.putExtra("id", String.valueOf(arrayList.get(position).getId()));
            context.startActivity(intent);
            Log.e("ID", "IDDDDDDDDDDDDDDDDĐ: "+ arrayList.get(position).getId() );
        });

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
