package com.example.gymbody.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gymbody.dbHelperVideo.AnhVideoDBhelper;
import com.example.gymbody.model.AnhVideoModel;

import java.util.ArrayList;

public class AnhVideoDAO {
    private AnhVideoDBhelper anhVideoDBhelper;
    private SQLiteDatabase db;

    public AnhVideoDAO(Context context) {
        anhVideoDBhelper = new AnhVideoDBhelper(context);
        db = anhVideoDBhelper.getReadableDatabase();
    }

    // lấy tất cả dữ liệu trong bảng
    public ArrayList<AnhVideoModel> getAll() {
        ArrayList<AnhVideoModel> arrayList = new ArrayList<>();
        String sql = "select * from AnhVideo";
        Cursor cursor = null;
        try {
            cursor = db.rawQuery(sql, null);
            if (cursor.moveToFirst()) {
                do {
                AnhVideoModel anhVideoModel = new AnhVideoModel();
                anhVideoModel.setId(cursor.getInt(0));
                anhVideoModel.setTen(cursor.getString(1));
                anhVideoModel.setNgay(cursor.getString(2));
                anhVideoModel.setAnh(cursor.getString(3));
                arrayList.add(anhVideoModel);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("AnhVideoDAO", "Lỗi khi lấy dữ liệu từ cơ sở dữ liệu", e);
        }
        return arrayList;
    }
}
