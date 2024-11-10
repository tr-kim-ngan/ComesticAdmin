package com.kimngan.ComesticAdmin.services;
import com.kimngan.ComesticAdmin.entity.ChiTietGioHang;
import com.kimngan.ComesticAdmin.entity.ChiTietGioHangId;
import com.kimngan.ComesticAdmin.entity.GioHang;
import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.repository.ChiTietGioHangRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
public class ChiTietGioHangServiceImpl implements ChiTietGioHangService {

	 @Autowired
	    private ChiTietGioHangRepository chiTietGioHangRepository;

	@Override
	public void updateQuantity(GioHang gioHang, SanPham sanPham, int soLuong) {
		ChiTietGioHang chiTiet = findByGioHangAndSanPham(gioHang, sanPham);
        if (chiTiet != null) {
            chiTiet.setSoLuong(soLuong);
            chiTietGioHangRepository.save(chiTiet);
        }
		
	}

	@Override
	public ChiTietGioHang findByGioHangAndSanPham(GioHang gioHang, SanPham sanPham) {
		// TODO Auto-generated method stub
        return chiTietGioHangRepository.findByGioHangAndSanPham(gioHang, sanPham).orElse(null);
	}

	@Override
	public void delete(ChiTietGioHang chiTietGioHang) {
        chiTietGioHangRepository.delete(chiTietGioHang);
		
	}
	
	

}
