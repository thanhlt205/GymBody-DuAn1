package com.example.gymbody.adapterAdmin;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.model.AcountModel;

import java.util.ArrayList;

public class AcountAdapter extends RecyclerView.Adapter<AcountAdapter.AcountViewHolder> {

    private Context context;
    private ArrayList<AcountModel> list;

    // Constructor để khởi tạo context
    public AcountAdapter(Context context, ArrayList<AcountModel> list) {
        this.context = context;
        this.list = list;  // Khởi tạo danh sách mặc định rỗng
    }

    @NonNull
    @Override
    public AcountAdapter.AcountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo và trả về ViewHolder từ layout `activity_show_acount`
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_acount, parent, false);
        return new AcountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcountAdapter.AcountViewHolder holder, int position) {
        // Lấy dữ liệu tại vị trí `position` và bind vào ViewHolder
        AcountModel acountModel = list.get(position);
        holder.txtAcountEmail.setText("Email: "+acountModel.getEmail());
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng item trong RecyclerView
        if(list != null){
            return list.size();
        }
        return 0;
    }

    // ViewHolder để ánh xạ các view trong mỗi item của RecyclerView
    public static class AcountViewHolder extends RecyclerView.ViewHolder {
        TextView txtAcountEmail;

        public AcountViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAcountEmail = itemView.findViewById(R.id.txtAcountEmail);
        }
    }
}
