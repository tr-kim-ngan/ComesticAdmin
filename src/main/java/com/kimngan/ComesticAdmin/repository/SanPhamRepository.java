package com.kimngan.ComesticAdmin.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kimngan.ComesticAdmin.entity.SanPham;

public interface SanPhamRepository extends JpaRepository<SanPham, Integer> {
	// Tìm sản phẩm theo tên
	Page<SanPham> findByTenSanPhamContainingIgnoreCase(String tenSanPham, Pageable pageable);

	// Kiểm tra xem sản phẩm có tồn tại và đang hoạt động không
	boolean existsByTenSanPhamAndTrangThai(String tenSanPham, boolean b);

	// Tìm tất cả sản phẩm với trạng thái đang hoạt động
	Page<SanPham> findByTrangThaiTrue(Pageable pageable);

	//List<SanPham> findByTrangThai();
	//List<SanPham> findByTrangThai(Boolean trangThai);
	List<SanPham> findByTrangThai(Boolean trangThai);

}
