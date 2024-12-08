package com.example.gymbody.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymbody.dbHelper.StatusProductDbHelper;
import com.example.gymbody.model.StatusProduct;

import java.util.ArrayList;
import java.util.List;

public class StatusProductDAO {
    private SQLiteDatabase database;
    private StatusProductDbHelper dbHelper;

    public StatusProductDAO(Context context) {
        dbHelper = new StatusProductDbHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    public long add(StatusProduct statusProduct) {
        ContentValues values = new ContentValues();
        values.put(StatusProductDbHelper.COLUMN_TEN_SP, statusProduct.getTenSp());
        values.put(StatusProductDbHelper.COLUMN_GIA_SP, statusProduct.getGiaSp());
        values.put(StatusProductDbHelper.COLUMN_TONG_TIEN, statusProduct.getTongTien());
        values.put(StatusProductDbHelper.COLUMN_DIA_CHI, statusProduct.getDiaChi());
        values.put(StatusProductDbHelper.COLUMN_PHUONG_THUC_THANH_TOAN, statusProduct.getPhuongThucThanhToan());
        values.put(StatusProductDbHelper.COLUMN_TRANG_THAI, statusProduct.getTrangThai());

        return database.insert(StatusProductDbHelper.TABLE_STATUS_PRODUCT, null, values);
    }

    public List<StatusProduct> getByStatus(String trangThai) {
        List<StatusProduct> products = new ArrayList<>();

        String query = "SELECT * FROM " + StatusProductDbHelper.TABLE_STATUS_PRODUCT +
                " WHERE " + StatusProductDbHelper.COLUMN_TRANG_THAI + " = ?";
        String[] args = {trangThai};

        Cursor cursor = database.rawQuery(query, args);

        if (cursor.moveToFirst()) {
            do {
                StatusProduct product = new StatusProduct();
                product.setId(cursor.getInt(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_ID)));
                product.setTenSp(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_TEN_SP)));
                product.setGiaSp(cursor.getDouble(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_GIA_SP)));
                product.setTongTien(cursor.getDouble(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_TONG_TIEN)));
                product.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_DIA_CHI)));
                product.setPhuongThucThanhToan(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_PHUONG_THUC_THANH_TOAN)));
                product.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_TRANG_THAI)));

                products.add(product);
            } while (cursor.moveToNext());
        }

        cursor.close();

        return products;
    }

    public List<StatusProduct> getAll() {
        List<StatusProduct> products = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        // Câu lệnh SQL để lấy tất cả các bản ghi từ bảng StatusProduct
        String query = "SELECT * FROM " + StatusProductDbHelper.TABLE_STATUS_PRODUCT;

        // Thực thi truy vấn
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);

        // Duyệt qua các kết quả
        if (cursor.moveToFirst()) {
            do {
                StatusProduct product = new StatusProduct();
                cursor.getInt(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_ID));
                product.setTenSp(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_TEN_SP)));
                product.setGiaSp(cursor.getDouble(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_GIA_SP)));
                product.setTongTien(cursor.getDouble(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_TONG_TIEN)));
                product.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_DIA_CHI)));
                product.setPhuongThucThanhToan(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_PHUONG_THUC_THANH_TOAN)));
                product.setTrangThai(cursor.getString(cursor.getColumnIndexOrThrow(StatusProductDbHelper.COLUMN_TRANG_THAI)));

                // Thêm sản phẩm vào danh sách
                products.add(product);
            } while (cursor.moveToNext());
        }
        // Đóng cursor để giải phóng tài nguyên
        cursor.close();
        return products;
    }

    public int updateStatus(int id, String newStatus) {
        ContentValues values = new ContentValues();
        values.put(StatusProductDbHelper.COLUMN_TRANG_THAI, newStatus); // Cập nhật giá trị trạng thái

        // Điều kiện WHERE để xác định bản ghi cần cập nhật
        String whereClause = StatusProductDbHelper.COLUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(id)};

        // Thực hiện cập nhật
        return database.update(
                StatusProductDbHelper.TABLE_STATUS_PRODUCT, // Tên bảng
                values,                                     // Giá trị mới
                whereClause,                                // Điều kiện WHERE
                whereArgs                                   // Tham số của WHERE
        );
    }
}
