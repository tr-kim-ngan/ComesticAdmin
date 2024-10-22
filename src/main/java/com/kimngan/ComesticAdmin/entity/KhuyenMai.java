package com.kimngan.ComesticAdmin.entity;

import java.math.BigDecimal;
import java.sql.Date;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class KhuyenMai {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maKhuyenMai;

    @Column(name = "TenKhuyenMai", length = 100)
    private String tenKhuyenMai;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "PhanTramGiamGia", precision = 5, scale = 2)
    private BigDecimal phanTramGiamGia;

    @Column(name = "NgayBatDau")
    private Date ngayBatDau;

    @Column(name = "NgayKetThuc")
    private Date ngayKetThuc;

    @Column(name = "isActive", nullable = false)
    private Boolean trangThai; // Sửa tên thuộc tính thành isActive

}
