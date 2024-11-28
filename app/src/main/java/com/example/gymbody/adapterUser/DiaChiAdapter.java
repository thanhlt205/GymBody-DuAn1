package com.example.gymbody.adapterUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.chucNang_user.DiaChiActivity;
import com.example.gymbody.dao.DiaChiDAO;
import com.example.gymbody.model.DiaChiModel;

import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.ViewHolder> {
    private Context context;
    private List<DiaChiModel> lstDiaChi;

    // Constructor
    public DiaChiAdapter(Context context, List<DiaChiModel> lstDiaChi) {
        this.context = context;
        this.lstDiaChi = lstDiaChi;
    }

    // Tạo ViewHolder
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_dia_chi, parent, false);
        return new ViewHolder(view);
    }

    // Gắn dữ liệu vào ViewHolder
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DiaChiModel diaChi = lstDiaChi.get(position);
        if (diaChi == null) {
            return;
        }

        // Thiết lập dữ liệu địa chỉ
        holder.txtSdt.setText("Số điện thoại: " + diaChi.getSdt());
        holder.txtSoNha.setText("Số nhà: " + diaChi.getSoNha());
        holder.ttxDiaChi.setText("Địa chỉ: " + diaChi.getTinhThanh() + " - " + diaChi.getQuanHuyen() + " - " + diaChi.getPhuongXa());

        // Sự kiện khi chọn địa chỉ
        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.putExtra("name", diaChi.getHoTen());
            intent.putExtra("sdt", diaChi.getSdt());
            intent.putExtra("soNha", diaChi.getSoNha());
            intent.putExtra("tinhThanh", diaChi.getTinhThanh());
            intent.putExtra("quanHuyen", diaChi.getQuanHuyen());
            intent.putExtra("phuongXa", diaChi.getPhuongXa());

            // Gửi kết quả về Activity gọi nó (ProductDetailActivity)
            ((DiaChiActivity) context).setResult(Activity.RESULT_OK, intent);
            ((DiaChiActivity) context).finish();
        });

        // Sự kiện xóa địa chỉ
        holder.itemView.setOnLongClickListener(view -> {
            DiaChiDAO diaChiDAO = new DiaChiDAO(context);
            String idXoa = String.valueOf(diaChi.getId());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa địa chỉ");
            builder.setMessage("Bạn có chắc chắn muốn xóa địa chỉ này?");
            builder.setCancelable(false);
            builder.setNegativeButton("Không", (dialog, which) -> dialog.dismiss());
            builder.setPositiveButton("Có", (dialog, which) -> {
                int rowsDeleted = diaChiDAO.deleteDiaChi(Integer.parseInt(idXoa));
                if (rowsDeleted > 0) {
                    lstDiaChi.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, lstDiaChi.size());
                    Toast.makeText(context, "Đã xóa địa chỉ thành công!", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context, "Xóa địa chỉ thất bại!", Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            });

            AlertDialog alertDialog = builder.create();
            alertDialog.show();
            return true;
        });
    }

    // Trả về số lượng địa chỉ
    @Override
    public int getItemCount() {
        return (lstDiaChi != null) ? lstDiaChi.size() : 0;
    }

    // ViewHolder chứa các thành phần của layout_item_dia_chi
    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView txtSoNha, txtSdt, ttxDiaChi;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtSoNha = itemView.findViewById(R.id.txtSoNha);
            txtSdt = itemView.findViewById(R.id.txtSdt);
            ttxDiaChi = itemView.findViewById(R.id.ttxDiaChi);
        }
    }
}
