package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import java.util.List;

@Entity
@Table(name = "DanhMuc")
public class DanhMuc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maDanhMuc;

    @Column(length = 100, nullable = false)
    private String tenDanhMuc;

    // Liên kết OneToMany với bảng SanPham
    @OneToMany(mappedBy = "danhMuc")
    private List<SanPham> sanPhams;

    // Constructors
    public DanhMuc() {
    	
    }
    
    
    
    // hàm xây dựng đầy đủ
    public DanhMuc(Integer maDanhMuc, String tenDanhMuc, List<SanPham> sanPhams) {
		super();
		this.maDanhMuc = maDanhMuc;
		this.tenDanhMuc = tenDanhMuc;
		this.sanPhams = sanPhams;
	}




	//Getters và Setters
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

    public List<SanPham> getSanPhams() {
        return sanPhams;
    }

    public void setSanPhams(List<SanPham> sanPhams) {
        this.sanPhams = sanPhams;
    }
}
