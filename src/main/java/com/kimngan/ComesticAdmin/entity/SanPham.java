package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.*;


@Entity
@Table(name = "SanPham")
public class SanPham {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MaSanPham")
    private Integer maSanPham;

    @Column(name = "TenSanPham", length = 100, nullable = false)
    private String tenSanPham;

    @Column(name = "MoTa", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "HinhAnh", length = 255)
    private String hinhAnh;

    @Column(name = "SoLuong")
    private Integer soLuong;
    
    // quan hệ với bảng Danh Mục
    @ManyToOne
    @JoinColumn(name = "maDanhMuc", referencedColumnName ="maDanhMuc")
    private DanhMuc danhMuc;
    
    

    // xây dựng
    
    public SanPham() {
		
	}
    
    
    
 public SanPham(Integer maSanPham, String tenSanPham, String moTa, String hinhAnh, Integer soLuong,
			DanhMuc danhMuc) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.moTa = moTa;
		this.hinhAnh = hinhAnh;
		this.soLuong = soLuong;
		this.danhMuc = danhMuc;
	}


//Getters và Setters
 
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
		
		
		
		public DanhMuc getDanhMuc() {
			return danhMuc;
		}
		
		
		
		public void setDanhMuc(DanhMuc danhMuc) {
			this.danhMuc = danhMuc;
		}




  	
    
    
}
