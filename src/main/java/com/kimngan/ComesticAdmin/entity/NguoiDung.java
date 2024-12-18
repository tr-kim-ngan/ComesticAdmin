package com.kimngan.ComesticAdmin.entity;
import jakarta.persistence.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.Set;
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
    
 // Quan hệ n-1 với PhieuGiamGia
    @ManyToOne
    @JoinColumn(name = "maPhieuGiamGia", referencedColumnName = "maPhieuGiamGia",nullable = true)
    private PhieuGiamGia phieuGiamGia;
    // Quan hệ Một-Nhiều với Giỏ Hàng
    @OneToMany(mappedBy = "nguoiDung", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<GioHang> gioHangs;

    // Constructors, Getters and Setters
    public NguoiDung() {}    
   



    public NguoiDung(Integer maNguoiDung, String tenNguoiDung, String matKhau, String email, String soDienThoai,
			QuyenTruyCap quyenTruyCap, PhieuGiamGia phieuGiamGia, Set<GioHang> gioHangs) {
		super();
		this.maNguoiDung = maNguoiDung;
		this.tenNguoiDung = tenNguoiDung;
		this.matKhau = matKhau;
		this.email = email;
		this.soDienThoai = soDienThoai;
		this.quyenTruyCap = quyenTruyCap;
		this.phieuGiamGia = phieuGiamGia;
		this.gioHangs = gioHangs;
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
	public PhieuGiamGia getPhieuGiamGia() {
		return phieuGiamGia;
	}
	public void setPhieuGiamGia(PhieuGiamGia phieuGiamGia) {
		this.phieuGiamGia = phieuGiamGia;
	}

	public Set<GioHang> getGioHangs() {
		return gioHangs;
	}

	public void setGioHangs(Set<GioHang> gioHangs) {
		this.gioHangs = gioHangs;
	}
     
}
