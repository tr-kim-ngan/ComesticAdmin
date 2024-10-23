package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.Comparator;

import java.util.Optional;

import java.util.Set;

@Entity
@Table(name = "SanPham")
public class SanPham {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MaSanPham")
	private Integer maSanPham;

	@Column(name = "TenSanPham", length = 200, nullable = false)
	private String tenSanPham;

	@Column(name = "MoTa", columnDefinition = "TEXT",nullable = false)
	private String moTa;

	@Column(name = "HinhAnh", length = 255,nullable = false)
	private String hinhAnh;

	@Column(name = "SoLuong",nullable = false)
	private Integer soLuong;
	
	
	@Column(name = "TrangThai", nullable = false)
    private boolean trangThai = true; 
	
	
	@Column(name = "DonGiaBan", precision = 20, scale = 2, nullable = false)
	private BigDecimal donGiaBan;

	// quan hệ với bảng Danh Mục
	@ManyToOne
	@JoinColumn(name = "maDanhMuc", referencedColumnName = "maDanhMuc")
	private DanhMuc danhMuc;
	// quan hệ với đơn giá

//	// xây dựng với Đơn giá bán hàng
//	@OneToMany(mappedBy = "sanPham", cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
//	private Set<DonGiaBanHang> donGiaBanHangs;
	
	// quan hệ với bảng Chi tiết đơn nhập hàng
	@OneToMany(mappedBy = "sanPham",cascade = CascadeType.ALL, fetch = FetchType.LAZY,orphanRemoval = true)
	private Set<ChiTietDonNhapHang> chiTietDonNhapHangs;

	
	
	

	// Nhà cung cấp
	@ManyToMany
	@JoinTable(name = "SanPham_NhaCungCap", // Tên của bảng trung gian
			joinColumns = @JoinColumn(name = "maSanPham"), // Khóa ngoại từ bảng SanPham
			inverseJoinColumns = @JoinColumn(name = "maNhaCungCap") // Khóa ngoại từ bảng NhaCungCap
	)
	private Set<NhaCungCap> nhaCungCaps;

	@ManyToMany
	@JoinTable(name = "SanPhamCoKhuyenMai", // Tên của bảng trung gian
	        joinColumns = @JoinColumn(name = "maSanPham"), // Khóa ngoại từ bảng SanPham
	        inverseJoinColumns = @JoinColumn(name = "maKhuyenMai") // Khóa ngoại từ bảng KhuyenMai
	)
	private Set<KhuyenMai> khuyenMais;

	
	// quan hệ với bảng đơn vị tính
	@ManyToOne
    @JoinColumn(name = "MaDonVi", nullable = false)
    private DonViTinh donViTinh;
 


	public SanPham() {

	}


	public SanPham(Integer maSanPham, String tenSanPham, String moTa, String hinhAnh, Integer soLuong,
			boolean trangThai, BigDecimal donGiaBan, DanhMuc danhMuc, Set<ChiTietDonNhapHang> chiTietDonNhapHangs,
			Set<NhaCungCap> nhaCungCaps, Set<KhuyenMai> khuyenMais, DonViTinh donViTinh) {
		super();
		this.maSanPham = maSanPham;
		this.tenSanPham = tenSanPham;
		this.moTa = moTa;
		this.hinhAnh = hinhAnh;
		this.soLuong = soLuong;
		this.trangThai = trangThai;
		this.donGiaBan = donGiaBan;
		this.danhMuc = danhMuc;
		this.chiTietDonNhapHangs = chiTietDonNhapHangs;
		this.nhaCungCaps = nhaCungCaps;
		this.khuyenMais = khuyenMais;
		this.donViTinh = donViTinh;
	}



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



	public BigDecimal getDonGiaBan() {
		return donGiaBan;
	}



	public void setDonGiaBan(BigDecimal donGiaBan) {
		this.donGiaBan = donGiaBan;
	}



	public Set<NhaCungCap> getNhaCungCaps() {
		return nhaCungCaps;
	}

	public void setNhaCungCaps(Set<NhaCungCap> nhaCungCaps) {
		this.nhaCungCaps = nhaCungCaps;
	}

	public Set<ChiTietDonNhapHang> getChiTietDonNhapHangs() {
		return chiTietDonNhapHangs;
	}

	public void setChiTietDonNhapHangs(Set<ChiTietDonNhapHang> chiTietDonNhapHangs) {
		this.chiTietDonNhapHangs = chiTietDonNhapHangs;
	}

	public DonViTinh getDonViTinh() {
		return donViTinh;
	}

	public void setDonViTinh(DonViTinh donViTinh) {
		this.donViTinh = donViTinh;
	}

	public boolean isTrangThai() {
		return trangThai;
	}

	public void setTrangThai(boolean trangThai) {
		this.trangThai = trangThai;
	}



	public Set<KhuyenMai> getKhuyenMais() {
		return khuyenMais;
	}

	public void setKhuyenMais(Set<KhuyenMai> khuyenMais) {
		this.khuyenMais = khuyenMais;
	}

	

}
