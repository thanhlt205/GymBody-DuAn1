package com.example.gymbody.model;

public class Product {
    private int id;           // ID sản phẩm
    private String name;      // Tên sản phẩm
    private double price;     // Giá sản phẩm
    private String image;     // URL hoặc đường dẫn hình ảnh sản phẩm

    // Constructor
    public Product(int id, String name, double price, String image) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.image = image;
    }

    // Getter và Setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    // (Optional) ToString để in thông tin sản phẩm ra log
    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", image='" + image + '\'' +
                '}';
    }
}

