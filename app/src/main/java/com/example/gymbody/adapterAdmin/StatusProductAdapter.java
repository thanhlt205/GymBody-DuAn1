package com.example.gymbody.adapterAdmin;

import android.app.AlertDialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gymbody.R;
import com.example.gymbody.dao.StatusProductDAO;
import com.example.gymbody.model.StatusProduct;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class StatusProductAdapter extends RecyclerView.Adapter<StatusProductAdapter.ViewHolder> {
    Context context;
    List<StatusProduct> arrayStatusProducts;

    public StatusProductAdapter(Context context, List<StatusProduct> arrayStatusProducts) {
        this.context = context;
        this.arrayStatusProducts = arrayStatusProducts;
    }

    @NonNull
    @Override
    public StatusProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_trangthaidonhang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatusProductAdapter.ViewHolder holder, int position) {
        StatusProduct statusProduct = arrayStatusProducts.get(position);
        if (statusProduct == null) {
            return;
        }

        holder.tv_address.setText(statusProduct.getDiaChi());
        holder.tv_order_name.setText("Tên đơn hàng: " + statusProduct.getTenSp());
        holder.tv_tracking_number.setText("Số theo dõi: " + statusProduct.getId());
        holder.tv_pay.setText(statusProduct.getPhuongThucThanhToan());
        holder.tv_price.setText("Số tiền: " + statusProduct.getGiaSp() + "đ");
        holder.tv_total_amount.setText("Tổng tiền: " + statusProduct.getTongTien() + "đ");
        holder.tv_status.setText(statusProduct.getTrangThai());

        if (statusProduct.getTrangThai().equals("Đang xử lý"))
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.colorIcon));
        if (statusProduct.getTrangThai().equals("Đã giao"))
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.green));
        if (statusProduct.getTrangThai().equals("Đã hủy"))
            holder.tv_status.setTextColor(context.getResources().getColor(R.color.primary_color));

        String email = holder.mAuth.getCurrentUser().getEmail().trim();
        if (email.equals("admin@gmail.com")) {
            holder.tv_status.setClickable(true);
            holder.tv_status.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận");
                    builder.setMessage("Bạn có muốn cập nhật trạng thái đơn hàng không?");
                    builder.setCancelable(false);
                    builder.setPositiveButton("Có", (dialogInterface, i) -> {
                        // Xử lý khi người dùng chọn "Có"
                        showUpdateStatusDialog(statusProduct);
                        dialogInterface.dismiss();
                    });
                    builder.setNegativeButton("Không", (dialogInterface, i) -> dialogInterface.dismiss());
                    builder.create().show();
                }
            });
        } else {
            holder.tv_status.setClickable(false);
        }

    }

    private void showUpdateStatusDialog(StatusProduct statusProduct) {
        // Tạo Dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_crud_status_product, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();

        // Ánh xạ các thành phần trong Dialog
        RadioGroup radioGroup = view.findViewById(R.id.radioGroupStatus);
        Button btnXacNhan = view.findViewById(R.id.btnXacNhan);
        Button btnHuy = view.findViewById(R.id.btnHuy);

        // Gán trạng thái hiện tại
        if ("Đang xử lý".equals(statusProduct.getTrangThai())) {
            radioGroup.check(R.id.rdoDangXuLy);
        } else if ("Đã giao".equals(statusProduct.getTrangThai())) {
            radioGroup.check(R.id.rdoDangGiaoHang);
        } else if ("Đã hủy".equals(statusProduct.getTrangThai())) {
            radioGroup.check(R.id.rdoDaHuy);
        }

        // Xử lý sự kiện nút Xác nhận
        btnXacNhan.setOnClickListener(v -> {
            int selectedId = radioGroup.getCheckedRadioButtonId();
            String newStatus = null;

            if (selectedId == R.id.rdoDangXuLy) {
                newStatus = "Đang xử lý";
            } else if (selectedId == R.id.rdoDangGiaoHang) {
                newStatus = "Đã giao";
            } else if (selectedId == R.id.rdoDaHuy) {
                newStatus = "Đã hủy";
            }

            if (newStatus != null) {
                // Cập nhật trạng thái
                statusProduct.setTrangThai(newStatus);

                // Gọi DAO để cập nhật trong cơ sở dữ liệu
                StatusProductDAO statusProductDAO = new StatusProductDAO(context);
                int rowsAffected = statusProductDAO.updateStatus(statusProduct.getId(), newStatus);

                if (rowsAffected > 0) {
                    Toast.makeText(context, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    notifyDataSetChanged(); // Cập nhật giao diện

                } else {
                    Toast.makeText(context, "Không thể cập nhật trạng thái.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(context, "Vui lòng chọn trạng thái mới.", Toast.LENGTH_SHORT).show();
            }

            dialog.dismiss();
        });

        // Xử lý sự kiện nút Hủy
        btnHuy.setOnClickListener(v -> dialog.dismiss());

        // Hiển thị Dialog
        dialog.show();
    }

    @Override
    public int getItemCount() {
        if (arrayStatusProducts != null) {
            Log.e("TAG", "getItemCount: " + arrayStatusProducts.size());
            return arrayStatusProducts.size();
        }
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_address, tv_order_name, tv_tracking_number, tv_pay, tv_price, tv_total_amount, tv_status;
        FirebaseAuth mAuth;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuth = FirebaseAuth.getInstance();
            tv_address = itemView.findViewById(R.id.tv_address);
            tv_order_name = itemView.findViewById(R.id.tv_order_name);
            tv_tracking_number = itemView.findViewById(R.id.tv_tracking_number);
            tv_pay = itemView.findViewById(R.id.tv_pay);
            tv_price = itemView.findViewById(R.id.tv_price);
            tv_total_amount = itemView.findViewById(R.id.tv_total_amount);
            tv_status = itemView.findViewById(R.id.tv_status);
        }
    }
}
