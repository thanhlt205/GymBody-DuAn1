package com.example.gymbody.model;

public class DiaChiModel {
    int id;
    String hoTen;
    String sdt;
    String tinhThanh;
    String quanHuyen;
    String phuongXa;
    String soNha;

    public DiaChiModel() {
    }

    public DiaChiModel(int id, String hoTen, String sdt, String tinhThanh, String quanHuyen, String phuongXa, String soNha) {
        this.id = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.tinhThanh = tinhThanh;
        this.quanHuyen = quanHuyen;
        this.phuongXa = phuongXa;
        this.soNha = soNha;
    }

    public DiaChiModel(String hoTen, String sdt, String tinhThanh, String quanHuyen, String phuongXa, String soNha) {
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.tinhThanh = tinhThanh;
        this.quanHuyen = quanHuyen;
        this.phuongXa = phuongXa;
        this.soNha = soNha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTinhThanh() {
        return tinhThanh;
    }

    public void setTinhThanh(String tinhThanh) {
        this.tinhThanh = tinhThanh;
    }

    public String getQuanHuyen() {
        return quanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        this.quanHuyen = quanHuyen;
    }

    public String getPhuongXa() {
        return phuongXa;
    }

    public void setPhuongXa(String phuongXa) {
        this.phuongXa = phuongXa;
    }

    public String getSoNha() {
        return soNha;
    }

    public void setSoNha(String soNha) {
        this.soNha = soNha;
    }
}
