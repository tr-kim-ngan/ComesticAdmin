package com.kimngan.ComesticAdmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimngan.ComesticAdmin.entity.NguoiDung;

public interface  NguoiDungRepository extends JpaRepository<NguoiDung, Integer>{

	NguoiDung findByTenNguoiDung(String tenNguoiDung);
	
}
