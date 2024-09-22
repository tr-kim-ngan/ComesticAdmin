package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Table;

@Entity
@Table(name = "DonViTinh")
public class DonViTinh {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maDonVi;

    @Column(length = 100, nullable = false)
    private String tenDonVi;
    
    // quan hệ với bảng SanPham
    @OneToMany(mappedBy = "donViTinh")
    private List<SanPham> sanPhams;


    // Constructors
    public DonViTinh() {
    }

    public DonViTinh(Integer maDonVi, String tenDonVi) {
        this.maDonVi = maDonVi;
        this.tenDonVi = tenDonVi;
    }

    // Getters and Setters
    public Integer getMaDonVi() {
        return maDonVi;
    }

    public void setMaDonVi(Integer maDonVi) {
        this.maDonVi = maDonVi;
    }

    public String getTenDonVi() {
        return tenDonVi;
    }

    public void setTenDonVi(String tenDonVi) {
        this.tenDonVi = tenDonVi;
    }
}
