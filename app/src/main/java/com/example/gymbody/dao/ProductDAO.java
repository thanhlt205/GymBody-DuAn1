package com.example.gymbody.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymbody.model.Product;

import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private SQLiteDatabase db;

    public ProductDAO(SQLiteDatabase db) {
        this.db = db;
    }
    public long addProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());

        return db.insert("Products", null, values);
    }

    // 2. Cập nhật thông tin sản phẩm
    public void updateProduct(Product product) {
        ContentValues values = new ContentValues();
        values.put("name", product.getName());
        values.put("price", product.getPrice());
        values.put("image", product.getImage());

        db.update("Products", values, "id = ?", new String[]{String.valueOf(product.getId())});
    }

    // 3. Lấy tất cả các sản phẩm
    public List<Product> getAllProducts() {
        List<Product> productList = new ArrayList<>();
        Cursor cursor = db.rawQuery("SELECT id, name, price, image FROM Products", null);

        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            double price = cursor.getDouble(2);
            String image = cursor.getString(3);

            productList.add(new Product(id, name, price, image));
        }
        cursor.close();
        return productList;
    }

    // 4. Lấy sản phẩm theo ID
    public Product getProductById(int productId) {
        Product product = null;
        Cursor cursor = db.rawQuery("SELECT id, name, price, image FROM Products WHERE id = ?", new String[]{String.valueOf(productId)});

        if (cursor.moveToFirst()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            double price = cursor.getDouble(2);
            String image = cursor.getString(3);

            product = new Product(id, name, price, image);
        }
        cursor.close();
        return product;
    }

    // 5. Xóa sản phẩm theo ID
    public void deleteProduct(int productId) {
        db.delete("Products", "id = ?", new String[]{String.valueOf(productId)});
    }
}

