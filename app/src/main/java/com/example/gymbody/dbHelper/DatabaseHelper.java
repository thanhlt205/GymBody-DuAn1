package com.example.gymbody.dbHelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "products.db";
    private static final int DATABASE_VERSION = 5; // Cập nhật phiên bản DB

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng Products với cột description
        db.execSQL("CREATE TABLE Products (id INTEGER PRIMARY KEY, image TEXT, name TEXT, price REAL, description TEXT)");

        // Chèn dữ liệu giả vào bảng Products
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('content://media/external/images/media/4049', 'Đôi tạ tay', 239, 'Sản phẩm tạ tay chuyên sử dụng tạ tay')");
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('content://media/external/images/media/4045', 'Lò so kháng lực', 89, 'Sản phẩm lò so tập kháng lực tay')");
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('content://media/external/images/media/4047', 'Thảm tập luyện tại nhà', 99, 'Sản phẩm thân thiện với môi trường và thoải mái chuyên dụng tại nhà')");
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('content://media/external/images/media/4048', 'Trọn bộ tạ tập luyện 24 món', 1299, 'Sản phẩm tập luyện 24 món chuyên dụng tại nhà')");
        db.execSQL("INSERT INTO Products (image, name, price, description) VALUES ('content://media/external/images/media/4044', 'Dụng cụ hồ trợ ngăn ngừa trai sạm tay chân', 99, 'Sản phẩm thân thiện thoải mái và dễ sử dụng')");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 2) {
            // Thêm cột description vào bảng Product khi nâng cấp
            db.execSQL("ALTER TABLE Products ADD COLUMN description TEXT");
        }
    }
}
