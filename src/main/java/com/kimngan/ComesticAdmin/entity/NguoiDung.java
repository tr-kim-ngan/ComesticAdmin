package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="NguoiDung")
public class NguoiDung {

    @Id
    @Column(name="maNguoiDung")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNguoiDung;

    private String tenNguoiDung;
    private String matKhau;
    private String email;
    private String soDienThoai;
    
    // quan hệ với bảng Quyền Truy Cập
    @ManyToOne
    @JoinColumn(name = "maQuyen", referencedColumnName ="maQuyen") // Khóa ngoại trỏ đến bảng QuyenTruyCap
    private QuyenTruyCap quyenTruyCap;

    // Constructors, Getters and Setters
    public NguoiDung() {}

    public NguoiDung(String tenNguoiDung, String matKhau, String email, String soDienThoai) {
        this.tenNguoiDung = tenNguoiDung;
        this.matKhau = matKhau;
        this.email = email;
        this.soDienThoai = soDienThoai;
    }

    public Integer getMaNguoiDung() {
        return maNguoiDung;
    }

    public void setMaNguoiDung(Integer maNguoiDung) {
        this.maNguoiDung = maNguoiDung;
    }

    public String getTenNguoiDung() {
        return tenNguoiDung;
    }

    public void setTenNguoiDung(String tenNguoiDung) {
        this.tenNguoiDung = tenNguoiDung;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }
    public QuyenTruyCap getQuyenTruyCap() {
        return quyenTruyCap;
    }

    public void setQuyenTruyCap(QuyenTruyCap quyenTruyCap) {
        this.quyenTruyCap = quyenTruyCap;
    }
}
