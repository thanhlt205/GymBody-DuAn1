package com.example.gymbody.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.gymbody.model.CartItem;
import com.example.gymbody.model.Product;

import java.util.ArrayList;
import java.util.List;

public class CartDao {
    private SQLiteDatabase db;

    public CartDao(SQLiteDatabase db) {
        this.db = db;
    }

    // 1. Thêm sản phẩm vào giỏ hàng
    public void addToCart(int productId, int quantity) {
        Cursor cursor = db.rawQuery("SELECT id, quantity FROM Cart WHERE product_id = ?", new String[]{String.valueOf(productId)});
        if (cursor.moveToFirst()) {
            // Nếu sản phẩm đã tồn tại, cập nhật số lượng
            int cartId = cursor.getInt(0);
            int currentQuantity = cursor.getInt(1);
            ContentValues values = new ContentValues();
            values.put("quantity", currentQuantity + quantity);
            db.update("Cart", values, "id = ?", new String[]{String.valueOf(cartId)});
        } else {
            // Nếu sản phẩm chưa tồn tại, thêm mới
            ContentValues values = new ContentValues();
            values.put("product_id", productId);
            values.put("quantity", quantity);
            db.insert("Cart", null, values);
        }
        cursor.close();
    }

    // 2. Xóa sản phẩm khỏi giỏ hàng
    public void removeFromCart(int cartId) {
        db.delete("Cart", "id = ?", new String[]{String.valueOf(cartId)});
    }

    // 3. Lấy danh sách sản phẩm trong giỏ hàng
    public List<CartItem> getCartItems() {
        List<CartItem> cartItems = new ArrayList<>();
        Cursor cursor = db.rawQuery(
                "SELECT c.id, c.quantity, p.id AS productId, p.name, p.price, p.image " +
                        "FROM Cart c JOIN Products p ON c.product_id = p.id", null
        );

        while (cursor.moveToNext()) {
            int cartId = cursor.getInt(0);
            int quantity = cursor.getInt(1);
            int productId = cursor.getInt(2);
            String name = cursor.getString(3);
            double price = cursor.getDouble(4);
            String image = cursor.getString(5);

            Product product = new Product(productId, name, price, image);
            cartItems.add(new CartItem(cartId, product, quantity));
        }
        cursor.close();
        return cartItems;
    }

    // 4. Cập nhật số lượng sản phẩm trong giỏ hàng
    public void updateQuantity(int cartId, int quantity) {
        ContentValues values = new ContentValues();
        values.put("quantity", quantity);
        db.update("Cart", values, "id = ?", new String[]{String.valueOf(cartId)});
    }

    // 5. Xóa toàn bộ giỏ hàng sau khi thanh toán
    public void clearCart() {
        db.execSQL("DELETE FROM Cart");
    }

    // 6. Tính tổng giá trị giỏ hàng
    public double getTotalPrice() {
        double totalPrice = 0.0;
        Cursor cursor = db.rawQuery(
                "SELECT SUM(p.price * c.quantity) " +
                        "FROM Cart c JOIN Products p ON c.product_id = p.id", null
        );
        if (cursor.moveToFirst()) {
            totalPrice = cursor.getDouble(0);
        }
        cursor.close();
        return totalPrice;
    }
}

