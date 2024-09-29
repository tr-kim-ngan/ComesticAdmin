package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "DanhMuc")
public class DanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "maDanhMuc")
    private Integer maDanhMuc;

    @Column(name = "tenDanhMuc", length = 100, nullable = false)
    private String tenDanhMuc;

    // Getters and Setters
    public DanhMuc() {}

    public DanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }

    public Integer getMaDanhMuc() {
        return maDanhMuc;
    }

    public void setMaDanhMuc(Integer maDanhMuc) {
        this.maDanhMuc = maDanhMuc;
    }

    public String getTenDanhMuc() {
        return tenDanhMuc;
    }

    public void setTenDanhMuc(String tenDanhMuc) {
        this.tenDanhMuc = tenDanhMuc;
    }
}
