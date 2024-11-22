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
import com.example.gymbody.R;
import com.example.gymbody.chucNang_user.ShowVideoActivity;
import com.example.gymbody.model.AnhVideoModel;
import com.example.gymbody.model.VideoFavoriteModel;

import java.util.ArrayList;
import java.util.List;

public class VideoYeuThichAdapter extends RecyclerView.Adapter<VideoYeuThichAdapter.ViewHolder> {

    Context context;
    List<VideoFavoriteModel> arrayList;

    public VideoYeuThichAdapter(Context context, List<VideoFavoriteModel> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VideoYeuThichAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_video, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoYeuThichAdapter.ViewHolder holder, int position) {
        VideoFavoriteModel videoFavirite = arrayList.get(position);

        Glide.with(context).load(videoFavirite.getAnh()).into(holder.imgAnhRcv);
        holder.imgYeuThichRcv.setVisibility(View.GONE);
        holder.txtTieuDeRcv.setText(videoFavirite.getTen());
        holder.txtNgayRcv.setText(videoFavirite.getNgay());

        Log.e("Ten", "onBindViewHolder: "+ videoFavirite.getTen());
        Log.e("Ngay", "onBindViewHolder: "+ videoFavirite.getNgay());
        Log.e("Anh", "onBindViewHolder: "+ videoFavirite.getAnh());

        // Xử lý sự kiện khi người dùng click vào item
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowVideoActivity.class);
            intent.putExtra("id", String.valueOf(videoFavirite.getId()));
            context.startActivity(intent);
            Log.e("ID", "ID của video: " + videoFavirite.getId());
        });

    }

    @Override
    public int getItemCount() {
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
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
