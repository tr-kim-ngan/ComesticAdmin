package com.kimngan.ComesticAdmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kimngan.ComesticAdmin.entity.ThoiDiem;
import java.time.LocalDateTime;

public interface ThoiDiemRepository extends JpaRepository<ThoiDiem, LocalDateTime> {
	// Tìm kiếm ThoiDiem dựa trên NgayGio
    ThoiDiem findByNgayGio(LocalDateTime ngayGio);
}