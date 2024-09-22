package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.*;

@Entity
public class DonGiaBanHang {

    @EmbeddedId
    private DonGiaBanHangId id; // Sử dụng EmbeddedId

    @Column(name = "donGiaBan", columnDefinition = "Decimal(8,2)")
    private Double donGiaBan;


    // Quan hệ với bảng SanPham
    @ManyToOne
    @MapsId("maSanPham") // Chỉ định khóa ngoại
    @JoinColumn(name = "maSanPham")
    private SanPham sanPham;

    // Quan hệ với bảng ThoiDiem
    @ManyToOne
    @MapsId("maThoiDiem") // Chỉ định khóa ngoại
    @JoinColumn(name = "maThoiDiem")
    private ThoiDiem thoiDiem;

    // Constructors
    public DonGiaBanHang() {}

    public DonGiaBanHang(DonGiaBanHangId id, Double donGiaBan, SanPham sanPham, ThoiDiem thoiDiem) {
        this.id = id;
        this.donGiaBan = donGiaBan;
        this.sanPham = sanPham;
        this.thoiDiem = thoiDiem;
    }

    // Getters and Setters
    public DonGiaBanHangId getId() {
        return id;
    }

    public void setId(DonGiaBanHangId id) {
        this.id = id;
    }

    public Double getDonGiaBan() {
        return donGiaBan;
    }

    public void setDonGiaBan(Double donGiaBan) {
        this.donGiaBan = donGiaBan;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    public ThoiDiem getThoiDiem() {
        return thoiDiem;
    }

    public void setThoiDiem(ThoiDiem thoiDiem) {
        this.thoiDiem = thoiDiem;
    }
}
