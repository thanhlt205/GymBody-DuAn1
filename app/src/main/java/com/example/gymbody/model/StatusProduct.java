package com.example.gymbody.model;

public class StatusProduct {
    private int id;
    private String tenSp;
    private double giaSp;
    private double tongTien;
    private String diaChi;
    private String phuongThucThanhToan;
    private String trangThai;

    public StatusProduct() {
    }

    public StatusProduct(String tenSp, double giaSp, double tongTien, String diaChi, String phuongThucThanhToan, String trangThai) {
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.tongTien = tongTien;
        this.diaChi = diaChi;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
    }

    public StatusProduct(int id, String tenSp, double giaSp, double tongTien, String diaChi, String phuongThucThanhToan, String trangThai) {
        this.id = id;
        this.tenSp = tenSp;
        this.giaSp = giaSp;
        this.tongTien = tongTien;
        this.diaChi = diaChi;
        this.phuongThucThanhToan = phuongThucThanhToan;
        this.trangThai = trangThai;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenSp() {
        return tenSp;
    }

    public void setTenSp(String tenSp) {
        this.tenSp = tenSp;
    }

    public double getGiaSp() {
        return giaSp;
    }

    public void setGiaSp(double giaSp) {
        this.giaSp = giaSp;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    @Override
    public String toString() {
        return "StatusProduct{" +
                "tenSp='" + tenSp + '\'' +
                ", giaSp=" + giaSp +
                ", tongTien=" + tongTien +
                ", diaChi='" + diaChi + '\'' +
                ", phuongThucThanhToan='" + phuongThucThanhToan + '\'' +
                ", trangThai='" + trangThai + '\'' +
                ", id=" + id +
                '}';
    }
}
