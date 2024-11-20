package com.example.gymbody.adapterAdmin;

import android.content.Context;
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
    public AcountAdapter(Context context) {
        this.context = context;
        this.list = new ArrayList<>();  // Khởi tạo danh sách mặc định rỗng
    }

    // Phương thức này sẽ được gọi khi dữ liệu thay đổi (vd: sau khi lấy dữ liệu từ Firestore)
    public void setAcountEmail(ArrayList<AcountModel> list) {
        this.list = list;
        notifyDataSetChanged();  // Đảm bảo RecyclerView được làm mới khi dữ liệu thay đổi
    }

    @NonNull
    @Override
    public AcountAdapter.AcountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Tạo và trả về ViewHolder từ layout `activity_show_acount`
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_show_acount, parent, false);
        return new AcountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AcountAdapter.AcountViewHolder holder, int position) {
        // Lấy dữ liệu tại vị trí `position` và bind vào ViewHolder
        AcountModel acountModel = list.get(position);
        if (acountModel == null) {
            return;
        }
        holder.txtAcountEmail.setText(acountModel.getEmail());  // Hiển thị email người dùng
    }

    @Override
    public int getItemCount() {
        // Trả về số lượng item trong RecyclerView
        return (list != null) ? list.size() : 0;
    }

    // ViewHolder để ánh xạ các view trong mỗi item của RecyclerView
    public static class AcountViewHolder extends RecyclerView.ViewHolder {
        TextView txtAcountEmail;

        public AcountViewHolder(@NonNull View itemView) {
            super(itemView);
            txtAcountEmail = itemView.findViewById(R.id.txtAcountEmail);  // Tìm kiếm TextView để hiển thị email
        }
    }
}
