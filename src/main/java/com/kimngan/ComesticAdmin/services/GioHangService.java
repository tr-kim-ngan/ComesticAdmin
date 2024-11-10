package com.kimngan.ComesticAdmin.services;

import java.util.List;

import com.kimngan.ComesticAdmin.entity.ChiTietGioHang;
import com.kimngan.ComesticAdmin.entity.GioHang;
import com.kimngan.ComesticAdmin.entity.NguoiDung;
import com.kimngan.ComesticAdmin.entity.SanPham;

public interface GioHangService {
    GioHang getOrCreateGioHang(NguoiDung nguoiDung);  // Lấy hoặc tạo giỏ hàng mới cho người dùng
    void addToCart(NguoiDung nguoiDung, SanPham sanPham, Integer soLuong);  // Thêm sản phẩm vào giỏ hàng
    void removeFromCart(NguoiDung nguoiDung, SanPham sanPham);  // Xóa sản phẩm khỏi giỏ hàng
    void clearCartAfterCheckout(NguoiDung nguoiDung);  // Xóa các sản phẩm đã thanh toán trong giỏ hàng
    List<ChiTietGioHang> viewCartItems(NguoiDung nguoiDung);  // Xem các sản phẩm hiện có trong giỏ hàng
}