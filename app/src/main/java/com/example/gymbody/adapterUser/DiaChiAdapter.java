package com.example.gymbody.adapterUser;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.dao.DiaChiDAO;
import com.example.gymbody.model.DiaChiModel;

import java.util.List;

public class DiaChiAdapter extends RecyclerView.Adapter<DiaChiAdapter.ViewHolder> {
    private Context context;
    private List<DiaChiModel> lstDiaChi;

    public DiaChiAdapter(Context context, List<DiaChiModel> lstDiaChi) {
        this.context = context;
        this.lstDiaChi = lstDiaChi;
    }

    @NonNull
    @Override
    public DiaChiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.layout_item_dia_chi, null);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiAdapter.ViewHolder holder, int position) {
        DiaChiModel diaChi = lstDiaChi.get(position);
        if (diaChi == null) {
            return;
        }
        holder.txtSdt.setText("Số điện thoại: " + diaChi.getSdt());
        holder.txtSoNha.setText("Số nhà: " + diaChi.getSoNha());
        holder.ttxDiaChi.setText("Địa chỉ: " + diaChi.getTinhThanh() + " - " + diaChi.getQuanHuyen() + " - " + diaChi.getPhuongXa());

        holder.itemView.setOnLongClickListener(view -> {
            DiaChiDAO diaChiDAO = new DiaChiDAO(context);
            String idXoa = String.valueOf(diaChi.getId());

            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Xóa địa chỉ");
            builder.setMessage("Bạn có chắc chắn muốn xóa địa chỉ này?");
            builder.setCancelable(false);
            builder.setNegativeButton("Không", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.setPositiveButton("Có", (dialog, which) -> {
                int rowsDeleted = diaChiDAO.deleteDiaChi(Integer.parseInt(idXoa));
                if (rowsDeleted > 0) {
                    lstDiaChi.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, lstDiaChi.size());
                }
                dialog.dismiss();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
                return false;
            });
        }

        @Override
        public int getItemCount () {
            if (lstDiaChi != null) {
                return lstDiaChi.size();
            }
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView txtSoNha, txtSdt, ttxDiaChi;

            public ViewHolder(@NonNull View itemView) {
                super(itemView);
                txtSoNha = itemView.findViewById(R.id.txtSoNha);
                txtSdt = itemView.findViewById(R.id.txtSdt);
                ttxDiaChi = itemView.findViewById(R.id.ttxDiaChi);
            }
        }
    }
