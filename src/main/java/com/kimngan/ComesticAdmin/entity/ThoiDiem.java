package com.kimngan.ComesticAdmin.entity;

import java.sql.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
public class ThoiDiem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)// Tự động tăng giá trị cho maThoiDiem
    private Integer maThoiDiem;// Thuộc tính lưu thời gian

    private Date ngayGio;
    
    // hàm xây dựng
    public ThoiDiem() {
    	
    }
    
    public ThoiDiem(Integer maThoiDiem, Date ngayGio) {
		super();
		this.maThoiDiem = maThoiDiem;
		this.ngayGio = ngayGio;
	}

	// Getters and Setters
    public Integer getMaThoiDiem() {
        return maThoiDiem;
    }

    public void setMaThoiDiem(Integer maThoiDiem) {
        this.maThoiDiem = maThoiDiem;
    }

    public Date getNgayGio() {
        return ngayGio;
    }

    public void setNgayGio(Date ngayGio) {
        this.ngayGio = ngayGio;
    }
}
