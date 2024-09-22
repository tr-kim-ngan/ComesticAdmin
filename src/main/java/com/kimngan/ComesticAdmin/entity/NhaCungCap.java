package com.kimngan.ComesticAdmin.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;

@Entity
public class NhaCungCap {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer maNhaCungCap;

    @Column(length = 100, nullable = false)
    private String tenNhaCungCap;

    @Column(length = 20)
    private String sdtNhaCungCap;

    @Column(length = 255)
    private String diaChiNhaCungCap;

    @Column(length = 100)
    private String emailNhaCungCap;
    
    // hàm xây dựng
    public NhaCungCap () {
		
	}
    
    public NhaCungCap(Integer maNhaCungCap, String tenNhaCungCap, String sdtNhaCungCap, String diaChiNhaCungCap,
			String emailNhaCungCap) {
		super();
		this.maNhaCungCap = maNhaCungCap;
		this.tenNhaCungCap = tenNhaCungCap;
		this.sdtNhaCungCap = sdtNhaCungCap;
		this.diaChiNhaCungCap = diaChiNhaCungCap;
		this.emailNhaCungCap = emailNhaCungCap;
	}




	// Getters and Setters
    public Integer getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(Integer maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getSdtNhaCungCap() {
        return sdtNhaCungCap;
    }

    public void setSdtNhaCungCap(String sdtNhaCungCap) {
        this.sdtNhaCungCap = sdtNhaCungCap;
    }

    public String getDiaChiNhaCungCap() {
        return diaChiNhaCungCap;
    }

    public void setDiaChiNhaCungCap(String diaChiNhaCungCap) {
        this.diaChiNhaCungCap = diaChiNhaCungCap;
    }

    public String getEmailNhaCungCap() {
        return emailNhaCungCap;
    }

    public void setEmailNhaCungCap(String emailNhaCungCap) {
        this.emailNhaCungCap = emailNhaCungCap;
    }
}
