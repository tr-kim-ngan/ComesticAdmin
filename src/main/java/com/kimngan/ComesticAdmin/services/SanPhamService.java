package com.kimngan.ComesticAdmin.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.kimngan.ComesticAdmin.entity.SanPham;

public interface SanPhamService {
	// định nghĩa các CRUD
	List<SanPham> getAll();

	List<SanPham> findByTrangThai(Boolean trangThai);

	SanPham create(SanPham sanPham);

	SanPham findById(Integer maSanPham);

	Boolean delete(Integer maSanPham);

	Boolean update(SanPham SanPham);

	Page<SanPham> findAll(Pageable pageable);

	Page<SanPham> searchByName(String tenSanPham, Pageable pageable);

	Page<SanPham> findAllActive(Pageable pageable); // Tìm sản phẩm đang hoạt động
	// Tìm sản phẩm theo ID với Optional

	Optional<SanPham> findByIdOptional(Integer maSanPham);

	Boolean existsByTenSanPham(String tenSanPham);
	// Lấy giá bán mới nhất từ phương thức getLatestDonGiaBan()
//	BigDecimal getGiaBanHienTai(SanPham sanPham);

}
