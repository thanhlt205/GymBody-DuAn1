package com.example.gymbody.model;

public class VideoFavoriteModel {
    private int id;
    private String anh;
    private String ten;
    private String ngay;

    public VideoFavoriteModel() {
    }

    public VideoFavoriteModel(int id, String anh, String ten, String ngay) {
        this.id = id;
        this.anh = anh;
        this.ten = ten;
        this.ngay = ngay;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getNgay() {
        return ngay;
    }

    public void setNgay(String ngay) {
        this.ngay = ngay;
    }
}
