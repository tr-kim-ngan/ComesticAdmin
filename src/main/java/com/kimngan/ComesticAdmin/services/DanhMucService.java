package com.kimngan.ComesticAdmin.services;

import com.kimngan.ComesticAdmin.entity.DanhMuc;

import java.util.List;

public interface DanhMucService {
    // định nghĩa các CRUD
    List<DanhMuc> getAll();
    Boolean create(DanhMuc danhMuc);
    DanhMuc findById (Integer maDanhMuc);
    Boolean delete(Integer maDanhMuc);

}
