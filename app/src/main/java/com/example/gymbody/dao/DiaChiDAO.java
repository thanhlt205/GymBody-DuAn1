package com.example.gymbody.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymbody.dbHelper.DiaChiDBHelper;
import com.example.gymbody.model.DiaChiModel;

import java.util.ArrayList;
import java.util.List;

public class DiaChiDAO {
    SQLiteDatabase db;
    DiaChiDBHelper dbHelper;

    public DiaChiDAO(Context context) {
        dbHelper = new DiaChiDBHelper(context);
        db = dbHelper.getWritableDatabase();
    }

    // Method to add a new DiaChi
    public long addDiaChi(DiaChiModel diaChi) {
        // Mở kết nối đến database
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Chuẩn bị dữ liệu để chèn vào bảng
        ContentValues values = new ContentValues();
        values.put("hoTen", diaChi.getHoTen());
        values.put("sdt", diaChi.getSdt());
        values.put("tinhThanh", diaChi.getTinhThanh());
        values.put("quanHuyen", diaChi.getQuanHuyen());
        values.put("phuongXa", diaChi.getPhuongXa());
        values.put("soNha", diaChi.getSoNha());

        // Thực hiện chèn dữ liệu
        long result = db.insert("DiaChi", null, values);

        // Đóng database
        db.close();

        // Trả về kết quả (id bản ghi mới hoặc -1 nếu lỗi)
        return result;
    }

    public List<DiaChiModel> getAllDiaChi() {
        List<DiaChiModel> diaChiList = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM DiaChi", null);

        if (cursor.moveToFirst()) {
            do {
                DiaChiModel diaChi = new DiaChiModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow("id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("hoTen")),
                        cursor.getString(cursor.getColumnIndexOrThrow("sdt")),
                        cursor.getString(cursor.getColumnIndexOrThrow("tinhThanh")),
                        cursor.getString(cursor.getColumnIndexOrThrow("quanHuyen")),
                        cursor.getString(cursor.getColumnIndexOrThrow("phuongXa")),
                        cursor.getString(cursor.getColumnIndexOrThrow("soNha"))
                );
                diaChiList.add(diaChi);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return diaChiList;
    }

    public int deleteDiaChi(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        int rowsDeleted = db.delete("DiaChi", "id = ?", new String[]{String.valueOf(id)});
        db.close();
        // Trả về số hàng đã bị xóa (nếu thành công sẽ > 0)
        return rowsDeleted;
    }

}
