package com.kimngan.ComesticAdmin.services;

import java.util.List;

import com.kimngan.ComesticAdmin.entity.SanPham;

public interface SanPhamService {
	// định nghĩa các CRUD
    List<SanPham> getAll();
    Boolean create(SanPham sanPham);
    SanPham findById (Integer maSanPham);
    Boolean delete(Integer maSanPham);
    Boolean update (SanPham SanPham);

}
