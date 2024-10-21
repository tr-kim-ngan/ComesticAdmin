package com.kimngan.ComesticAdmin.services;

import com.kimngan.ComesticAdmin.entity.DonGiaBanHang;
import com.kimngan.ComesticAdmin.entity.DonGiaBanHangId;

import java.util.List;
import java.util.Optional;

public interface DonGiaBanHangService {
	List<DonGiaBanHang> getAll();

	DonGiaBanHang create(DonGiaBanHang donGiaBanHang); //

	DonGiaBanHang update(DonGiaBanHang donGiaBanHang); //

	void delete(DonGiaBanHangId id); //

	Optional<DonGiaBanHang> findById(DonGiaBanHangId id); //
}
