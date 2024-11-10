package com.kimngan.ComesticAdmin.controller.customer;

import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kimngan.ComesticAdmin.entity.ChiTietGioHang;
import com.kimngan.ComesticAdmin.entity.GioHang;
import com.kimngan.ComesticAdmin.entity.KhuyenMai;
import com.kimngan.ComesticAdmin.entity.NguoiDung;
import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.services.ChiTietGioHangService;
import com.kimngan.ComesticAdmin.services.GioHangService;
import com.kimngan.ComesticAdmin.services.NguoiDungService;
import com.kimngan.ComesticAdmin.services.SanPhamService;

@Controller
@RequestMapping("/customer/cart")
public class GioHangController {

	@Autowired
	private GioHangService gioHangService;

	@Autowired
	private SanPhamService sanPhamService;

	@Autowired
	private NguoiDungService nguoiDungService;

	@Autowired
	private ChiTietGioHangService chiTietGioHangService;

	@GetMapping
	public String viewCart(Model model, Principal principal) {
		if (principal == null) {
			return "redirect:/customer/login";
		}

		// Lấy thông tin người dùng hiện tại
		NguoiDung nguoiDung = getCurrentUser(principal);

		// Lấy danh sách sản phẩm trong giỏ hàng
		List<ChiTietGioHang> cartItems = gioHangService.viewCartItems(nguoiDung);

		// Tính tổng giá trị giỏ hàng
		BigDecimal totalPrice = BigDecimal.ZERO;
		Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
		Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();

		LocalDate today = LocalDate.now();
		for (ChiTietGioHang item : cartItems) {
			SanPham sanPham = item.getSanPham();
			BigDecimal giaSauGiam = sanPham.getDonGiaBan();

			Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
					.filter(km -> km.getTrangThai())
					.filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
							&& !km.getNgayKetThuc().toLocalDate().isBefore(today))
					.max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia));

			if (highestCurrentKhuyenMai.isPresent()) {
				BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
				giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
				sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
			} else {
				sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), null);
			}

			sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
			totalPrice = totalPrice.add(giaSauGiam.multiply(BigDecimal.valueOf(item.getSoLuong())));
		}

		// Đưa dữ liệu vào model để hiển thị trong cart.html
		model.addAttribute("cartItems", cartItems);
		model.addAttribute("totalPrice", totalPrice);
		model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap); // Map khuyến mãi cao nhất cho từng sản phẩm
		model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap); // Giá sau khi giảm của từng sản phẩm

		return "customer/cart";
	}

	@PostMapping("/add")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> addToCart(@RequestBody Map<String, Object> payload,
	                                                     Principal principal) {
	    Map<String, Object> response = new HashMap<>();

	    if (principal == null) {
	        response.put("success", false);
	        response.put("message", "Vui lòng đăng nhập để thêm vào giỏ hàng.");
	        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
	    }

	    try {
	        Integer productId = Integer.parseInt(payload.get("productId").toString());
	        Integer quantity = Integer.parseInt(payload.get("quantity").toString());

	        NguoiDung currentUser = nguoiDungService.findByTenNguoiDung(principal.getName());
	        SanPham sanPham = sanPhamService.findById(productId);

	        // Kiểm tra nếu sản phẩm không tìm thấy
	        if (sanPham == null) {
	            response.put("success", false);
	            response.put("message", "Sản phẩm không tồn tại.");
	            return ResponseEntity.badRequest().body(response);
	        }

	        gioHangService.addToCart(currentUser, sanPham, quantity);

	        response.put("success", true);
	        response.put("message", "Sản phẩm đã được thêm vào giỏ hàng!");
	        return ResponseEntity.ok(response);

	    } catch (NumberFormatException | NullPointerException e) {
	        response.put("success", false);
	        response.put("message", "Dữ liệu không hợp lệ.");
	        return ResponseEntity.badRequest().body(response);
	    } catch (Exception e) {
	        response.put("success", false);
	        response.put("message", "Lỗi trong quá trình thêm sản phẩm vào giỏ hàng.");
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
	    }
	}


	@GetMapping("/count")
	@ResponseBody
	public Integer getCartItemCount(Principal principal) {
		if (principal != null) {
			NguoiDung currentUser = nguoiDungService.findByTenNguoiDung(principal.getName());
			List<ChiTietGioHang> cartItems = gioHangService.viewCartItems(currentUser);
			return cartItems.size();
		}
		return 0;
	}

	@PostMapping("/remove")
	public String removeFromCart(@RequestParam("sanPhamId") Integer sanPhamId, Principal principal) {
		// Lấy người dùng hiện tại
		NguoiDung currentUser = getCurrentUser(principal);

		// Tìm sản phẩm theo ID
		SanPham sanPham = sanPhamService.findById(sanPhamId);

		// Xóa sản phẩm khỏi giỏ hàng
		gioHangService.removeFromCart(currentUser, sanPham);

		// Chuyển hướng tới trang giỏ hàng
		return "redirect:/customer/cart";
	}

	@PostMapping("/checkout")
	public String checkoutCart(Principal principal) {
		// Lấy người dùng hiện tại
		NguoiDung currentUser = getCurrentUser(principal);

		// Xóa giỏ hàng sau khi thanh toán
		gioHangService.clearCartAfterCheckout(currentUser);

		// Chuyển hướng tới trang đơn hàng sau khi thanh toán
		return "redirect:/customer/orders";
	}

	@ModelAttribute("cartItems")
	public List<ChiTietGioHang> getCartItems(Principal principal) {
		if (principal != null) {
			NguoiDung currentUser = getCurrentUser(principal);
			return gioHangService.viewCartItems(currentUser);
		}
		return new ArrayList<>();
	}

	// Phương thức tiện ích để lấy người dùng hiện tại
	private NguoiDung getCurrentUser(Principal principal) {
		return nguoiDungService.findByTenNguoiDung(principal.getName());
	}
}
