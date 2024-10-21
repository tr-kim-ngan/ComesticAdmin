package com.kimngan.ComesticAdmin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kimngan.ComesticAdmin.entity.DonNhapHang;

public interface DonNhapHangRepository extends JpaRepository<DonNhapHang, Integer> {
	@Query("SELECT d FROM DonNhapHang d WHERE d.trangThai = true")
	List<DonNhapHang> findAllActive();
	Page<DonNhapHang> findByTrangThai(boolean trangThai, Pageable pageable);

	Page<DonNhapHang> findByNgayNhapHang(LocalDate  ngayNhap, Pageable pageable);
}
