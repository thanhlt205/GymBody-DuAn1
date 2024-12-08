package com.example.gymbody.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class StatusProductDbHelper extends SQLiteOpenHelper {

    // Tên cơ sở dữ liệu và phiên bản
    private static final String DATABASE_NAME = "statusProduct.db";
    private static final int DATABASE_VERSION = 1;

    // Tên bảng và các cột trong bảng
    public static final String TABLE_STATUS_PRODUCT = "StatusProduct";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEN_SP = "tenSp";
    public static final String COLUMN_GIA_SP = "giaSp";
    public static final String COLUMN_TONG_TIEN = "tongTien";
    public static final String COLUMN_DIA_CHI = "diaChi";
    public static final String COLUMN_PHUONG_THUC_THANH_TOAN = "phuongThucThanhToan";
    public static final String COLUMN_TRANG_THAI = "trangThai";

    // Câu lệnh SQL để tạo bảng
    private static final String CREATE_TABLE_STATUS_PRODUCT =
            "CREATE TABLE " + TABLE_STATUS_PRODUCT + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_TEN_SP + " TEXT NOT NULL, " +
                    COLUMN_GIA_SP + " REAL NOT NULL, " +
                    COLUMN_TONG_TIEN + " REAL NOT NULL, " +
                    COLUMN_DIA_CHI + " TEXT, " +
                    COLUMN_PHUONG_THUC_THANH_TOAN + " TEXT, " +
                    COLUMN_TRANG_THAI + " TEXT);";

    // Constructor
    public StatusProductDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Thực thi câu lệnh SQL để tạo bảng
        try {
            db.execSQL(CREATE_TABLE_STATUS_PRODUCT);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Nếu phiên bản cơ sở dữ liệu thay đổi, xóa bảng cũ và tạo lại
        try {
            // Xóa bảng cũ nếu tồn tại
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_STATUS_PRODUCT);
            // Tạo bảng mới
            onCreate(db);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
