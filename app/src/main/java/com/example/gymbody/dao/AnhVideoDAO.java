package com.example.gymbody.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.gymbody.dbHelper.AnhVideoDBhelper;
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

    // Hàm xóa dữ liệu theo id
    public void deleteById(int id) {
        try {
            // Xóa dữ liệu khỏi bảng AnhVideo dựa vào id
            int rowsDeleted = db.delete("AnhVideo", "id = ?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Log.d("AnhVideoDAO", "Xóa thành công AnhVideo với id: " + id);
            } else {
                Log.d("AnhVideoDAO", "Không tìm thấy AnhVideo với id: " + id);
            }
        } catch (Exception e) {
            Log.e("AnhVideoDAO", "Lỗi khi xóa dữ liệu từ cơ sở dữ liệu", e);
        }
    }

    public void deleteFavoriteById(int videoId) {
        try {
            // Xóa video yêu thích từ bảng favorite_videos theo video_id
            int rowsDeleted = db.delete("favorite_videos", "video_id = ?", new String[]{String.valueOf(videoId)});

            if (rowsDeleted > 0) {
                Log.d("FavoriteVideosDAO", "Xóa thành công video yêu thích với video_id: " + videoId);
            } else {
                Log.d("FavoriteVideosDAO", "Không tìm thấy video yêu thích với video_id: " + videoId);
            }
        } catch (Exception e) {
            Log.e("FavoriteVideosDAO", "Lỗi khi xóa video yêu thích", e);
        }
    }
}
