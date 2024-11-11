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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kimngan.ComesticAdmin.entity.ChiTietGioHang;
import com.kimngan.ComesticAdmin.entity.GioHang;
import com.kimngan.ComesticAdmin.entity.KhuyenMai;
import com.kimngan.ComesticAdmin.entity.NguoiDung;
import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.services.ChiTietGioHangService;
import com.kimngan.ComesticAdmin.services.GioHangService;
import com.kimngan.ComesticAdmin.services.NguoiDungService;
import com.kimngan.ComesticAdmin.services.SanPhamService;

import jakarta.servlet.http.HttpServletRequest;

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
	public String addToCart(@RequestParam("productId") Integer productId, @RequestParam("quantity") Integer quantity,
			Principal principal, RedirectAttributes redirectAttributes, HttpServletRequest request) {
		Map<String, Object> response = new HashMap<>();

		// Kiểm tra người dùng đã đăng nhập hay chưa
		if (principal == null) {
			redirectAttributes.addFlashAttribute("error", "Vui lòng đăng nhập để thêm vào giỏ hàng.");
			return "redirect:/login"; // Chuyển hướng đến trang đăng nhập nếu chưa đăng nhập
		}

		try {
			// Log để kiểm tra giá trị nhận từ form
			System.out.println("ProductId nhận được: " + productId);
			System.out.println("Số lượng nhận được: " + quantity);

			if (quantity < 1) {
				redirectAttributes.addFlashAttribute("error", "Số lượng không hợp lệ.");
				return "redirect:" + request.getHeader("Referer"); // Trở lại trang hiện tại
			}

			// Lấy người dùng hiện tại
			NguoiDung currentUser = nguoiDungService.findByTenNguoiDung(principal.getName());
			Optional<SanPham> optionalSanPham = sanPhamService.findByIdOptional(productId);

			if (!optionalSanPham.isPresent()) {
				redirectAttributes.addFlashAttribute("error", "Sản phẩm không tồn tại.");
				return "redirect:" + request.getHeader("Referer");
			}

			SanPham sanPham = optionalSanPham.get();
			gioHangService.addToCart(currentUser, sanPham, quantity);

			// Thông báo thành công và quay lại trang hiện tại
			redirectAttributes.addFlashAttribute("success", "Sản phẩm đã được thêm vào giỏ hàng!");
			return "redirect:" + request.getHeader("Referer");

		} catch (Exception e) {
			e.printStackTrace();
			redirectAttributes.addFlashAttribute("error", "Lỗi trong quá trình thêm sản phẩm vào giỏ hàng.");
			return "redirect:" + request.getHeader("Referer");
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

	@PostMapping("/update-quantity")
	@ResponseBody
	public ResponseEntity<Map<String, Object>> updateCartItemQuantity(@RequestParam("sanPhamId") Integer sanPhamId,
			@RequestParam("quantity") Integer newQuantity, Principal principal) {
		Map<String, Object> response = new HashMap<>();

		try {
			// Lấy người dùng hiện tại
			NguoiDung currentUser = getCurrentUser(principal);
			// Lấy sản phẩm theo ID
			SanPham sanPham = sanPhamService.findById(sanPhamId);

			// Cập nhật số lượng trong giỏ hàng
			gioHangService.updateCartItemQuantity(currentUser, sanPham, newQuantity);

			response.put("success", true);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			response.put("success", false);
			response.put("message", "Đã xảy ra lỗi khi cập nhật số lượng sản phẩm.");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
		}
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
