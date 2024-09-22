package com.kimngan.ComesticAdmin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kimngan.ComesticAdmin.entity.DonGiaBanHang;
import com.kimngan.ComesticAdmin.entity.DonGiaBanHangId;

public interface DonGiaBanHangRepository extends JpaRepository<DonGiaBanHang, DonGiaBanHangId> {
    // Các phương thức truy vấn tùy chỉnh (nếu cần)
}
