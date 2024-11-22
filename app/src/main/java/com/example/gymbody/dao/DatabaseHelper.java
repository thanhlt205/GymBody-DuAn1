package com.example.gymbody.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "GymBody.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Tạo bảng
        db.execSQL("CREATE TABLE Products (id INTEGER PRIMARY KEY, name TEXT, price REAL, image TEXT)");
        db.execSQL("CREATE TABLE Cart (id INTEGER PRIMARY KEY, product_id INTEGER, quantity INTEGER, FOREIGN KEY(product_id) REFERENCES Products(id))");

        // Insert dữ liệu giả vào bảng Products
        db.execSQL("INSERT INTO Products (name, price, image) VALUES ('Sản phẩm 1', 100.0, 'image_url_1')");
        db.execSQL("INSERT INTO Products (name, price, image) VALUES ('Sản phẩm 2', 200.0, 'image_url_2')");
        db.execSQL("INSERT INTO Products (name, price, image) VALUES ('Sản phẩm 3', 300.0, 'image_url_3')");
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS Products");
        db.execSQL("DROP TABLE IF EXISTS Cart");
        onCreate(db);
    }
}

