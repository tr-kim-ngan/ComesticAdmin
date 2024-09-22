package com.kimngan.ComesticAdmin.entity;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

@Embeddable
public class DonGiaBanHangId implements Serializable {
	private static final long serialVersionUID = 123456789L;
    private Integer maSanPham;
    private Integer maThoiDiem;

    // Constructors
    public DonGiaBanHangId() {}

    public DonGiaBanHangId(Integer maSanPham, Integer maThoiDiem) {
        this.maSanPham = maSanPham;
        this.maThoiDiem = maThoiDiem;
    }

    // Getters and Setters
    public Integer getMaSanPham() {
        return maSanPham;
    }

    public void setMaSanPham(Integer maSanPham) {
        this.maSanPham = maSanPham;
    }

    public Integer getMaThoiDiem() {
        return maThoiDiem;
    }

    public void setMaThoiDiem(Integer maThoiDiem) {
        this.maThoiDiem = maThoiDiem;
    }

    // Override equals() and hashCode()
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DonGiaBanHangId that = (DonGiaBanHangId) o;
        return maSanPham.equals(that.maSanPham) && maThoiDiem.equals(that.maThoiDiem);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maSanPham, maThoiDiem);
    }
}
