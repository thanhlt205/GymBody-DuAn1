package com.example.gymbody.model;

public class anhVideoModel {
    private int id;
    private String ten;
    private String ngay;
    private String anh;
    private String video;

    public anhVideoModel() {
    }

    public anhVideoModel(int id, String ten, String ngay, String anh) {
        this.id = id;
        this.ten = ten;
        this.ngay = ngay;
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }
}
