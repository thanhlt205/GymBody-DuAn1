package com.example.gymbody.model;

public class CartItem {
    private int id;            // ID của mục giỏ hàng (Primary Key trong bảng Cart)
    private Product product;   // Sản phẩm được thêm vào giỏ hàng
    private int quantity;      // Số lượng sản phẩm

    // Constructor
    public CartItem(int id, Product product, int quantity) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // (Optional) Tính tổng giá trị của mục giỏ hàng
    public double getTotalPrice() {
        return product.getPrice() * quantity;
    }

    // (Optional) ToString để in thông tin giỏ hàng ra log
    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", product=" + product +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}

