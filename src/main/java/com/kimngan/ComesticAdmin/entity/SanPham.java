package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Entity
@Table(name = "SanPham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maSanPham;

    @Column(length = 100, nullable = false)
    private String tenSanPham;

    @Column(length = 255)
    private String moTa;

    @Column(length = 255)
    private String hinhAnh;

    private Integer soLuong;
    
    // quan hệ với bảng DanhMuc
    @ManyToOne
    @JoinColumn(name = "maDanhMuc") // tên cột khóa ngoại
    private DanhMuc danhMuc;
    
    // quan hệ với bảng KhuyenMai
    @ManyToMany
    @JoinTable(
      name = "SanPhamCoKhuyenMai", 
      joinColumns = @JoinColumn(name = "maSanPham"), 
      inverseJoinColumns = @JoinColumn(name = "maKhuyenMai"))
    private List<KhuyenMai> khuyenMais;
    
    // quan hệ với bảng DonViTinh
    @ManyToOne
    @JoinColumn(name = "maDonVi") // tên cột khóa ngoại
    private DonViTinh donViTinh;

    
    
    
    // Constructors
    public SanPham() {
    	
    }
    
    
    
  public SanPham(Integer maSanPham, String tenSanPham, String moTa, String hinhAnh, Integer soLuong) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.moTa = moTa;
		this.hinhAnh = hinhAnh;
		this.soLuong = soLuong;
	}






//Getters and Setters
    public Integer getMaSanPham() {
        return maSanPham;
    }
    
    public void setMaSanPham(Integer maSanPham) {
        this.maSanPham = maSanPham;
    }

    public String getTenSanPham() {
        return tenSanPham;
    }

    public void setTenSanPham(String tenSanPham) {
        this.tenSanPham = tenSanPham;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }
}
