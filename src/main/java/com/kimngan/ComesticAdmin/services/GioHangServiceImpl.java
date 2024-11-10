package com.kimngan.ComesticAdmin.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kimngan.ComesticAdmin.entity.ChiTietGioHang;
import com.kimngan.ComesticAdmin.entity.ChiTietGioHangId;
import com.kimngan.ComesticAdmin.entity.GioHang;
import com.kimngan.ComesticAdmin.entity.NguoiDung;
import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.repository.ChiTietGioHangRepository;
import com.kimngan.ComesticAdmin.repository.GioHangRepository;

@Service
public class GioHangServiceImpl implements GioHangService {

	@Autowired
	private GioHangRepository gioHangRepository;
	@Autowired
	private ChiTietGioHangRepository chiTietGioHangRepository;

	@Autowired
	private NguoiDungService nguoiDungService;
	@Autowired
	private ChiTietGioHangService chiTietGioHangService;

	@Override
	public GioHang getOrCreateGioHang(NguoiDung nguoiDung) {
		// TODO Auto-generated method stub
		// Tìm giỏ hàng của người dùng, nếu chưa có sẽ tạo mới
		return gioHangRepository.findByNguoiDung(nguoiDung).orElseGet(() -> {
			GioHang newGioHang = new GioHang();
			newGioHang.setNguoiDung(nguoiDung);
			newGioHang.setNgayTao(LocalDate.now());
			return gioHangRepository.save(newGioHang);
		});
	}

	@Override
	public void addToCart(NguoiDung nguoiDung, SanPham sanPham, Integer soLuong) {
	    GioHang gioHang = getOrCreateGioHang(nguoiDung);
	    ChiTietGioHangId id = new ChiTietGioHangId(gioHang.getMaGioHang(), sanPham.getMaSanPham());

	    ChiTietGioHang chiTietGioHang = chiTietGioHangRepository.findById(id)
	        .orElse(new ChiTietGioHang(id, gioHang, sanPham, 0)); // Nếu chưa có thì khởi tạo mới
	    
	    chiTietGioHang.setSoLuong(chiTietGioHang.getSoLuong() + soLuong); // Cập nhật số lượng
	    chiTietGioHangRepository.save(chiTietGioHang);
	}


	@Override
	public void removeFromCart(NguoiDung nguoiDung, SanPham sanPham) {
		GioHang gioHang = getOrCreateGioHang(nguoiDung);
		chiTietGioHangRepository.findByGioHangAndSanPham(gioHang, sanPham).ifPresent(chiTietGioHangRepository::delete);

	}

	@Override
	public void clearCartAfterCheckout(NguoiDung nguoiDung) {
		GioHang gioHang = getOrCreateGioHang(nguoiDung);
		chiTietGioHangRepository.deleteByGioHang(gioHang);

	}

	@Override
	public List<ChiTietGioHang> viewCartItems(NguoiDung nguoiDung) {
		GioHang gioHang = getOrCreateGioHang(nguoiDung);
		return chiTietGioHangRepository.findByGioHang(gioHang);
	}

}
