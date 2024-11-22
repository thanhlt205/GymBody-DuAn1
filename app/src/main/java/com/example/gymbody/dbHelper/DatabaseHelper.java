package com.example.gymbody.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GymBody.db";
    private static final int DATABASE_VERSION = 2; // Cập nhật phiên bản DB

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Products với cột description
        db.execSQL("CREATE TABLE Products (id INTEGER PRIMARY KEY, image TEXT, name TEXT, price REAL, description TEXT)");

        // Chèn dữ liệu giả vào bảng Products
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('https://khoinguonsangtao.vn/wp-content/uploads/2022/10/hinh-nen-trai-dat.jpg', 'Sản phẩm 1', 100.0, 'Mô tả sản phẩm 1')");
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('https://media-cdn-v2.laodong.vn/Storage/NewsPortal/2022/8/23/1084256/295497942_2922294937.jpg', 'Sản phẩm 2', 200.0, 'Mô tả sản phẩm 2')");
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('https://baohanam.com.vn/DATA/IMAGES/2022/09/09/20220909085625-56niem-vui-dc-mua.jpg', 'Sản phẩm 3', 300.0, 'Mô tả sản phẩm 3')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Thêm cột description vào bảng Products khi nâng cấp
            db.execSQL("ALTER TABLE Products ADD COLUMN description TEXT");
        }
    }
}
