package com.kimngan.ComesticAdmin.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.kimngan.ComesticAdmin.entity.DanhMuc;
import com.kimngan.ComesticAdmin.entity.KhuyenMai;
import com.kimngan.ComesticAdmin.entity.NguoiDungDetails;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;

import com.kimngan.ComesticAdmin.services.DanhMucService;
import com.kimngan.ComesticAdmin.services.SanPhamService;
import com.kimngan.ComesticAdmin.entity.SanPham;

import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/products")
public class CustomerProductController {

	@Autowired
	private SanPhamService sanPhamService;

	@Autowired
	private DanhMucService danhMucService;

	// Trang hiển thị tất cả sản phẩm (trạng thái = 1)
	@GetMapping("/all")
	public String viewAllProducts(Model model, @RequestParam(defaultValue = "0") int page) {
		Pageable pageable = PageRequest.of(page, 15); // Số lượng sản phẩm mỗi trang là 10
        Page<SanPham> sanPhams = sanPhamService.getProductsInOrderDetails(pageable);
		List<DanhMuc> categories = danhMucService.getAll();
		LocalDate today = LocalDate.now();
		// Sử dụng Map với maSanPham làm key
		Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
		//Map<SanPham, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();
		Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();

		// Tìm khuyến mãi và tính giá sau khi giảm cho từng sản phẩm
		for (SanPham sanPham : sanPhams) {
			// Tìm khuyến mãi cao nhất hiện tại có trạng thái true và trong khoảng thời gian
			// hợp lệ
			Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
					.filter(km -> km.getTrangThai()) // Chỉ lấy khuyến mãi có trạng thái true
					.filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
							&& !km.getNgayKetThuc().toLocalDate().isBefore(today)) // Chỉ lấy khuyến mãi còn hạn
					.max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia)); // Lấy khuyến mãi có tỷ lệ giảm giá cao
																				// nhất


	        BigDecimal giaSauGiam = sanPham.getDonGiaBan();
	        // Tính giá sau khi giảm
	        if (highestCurrentKhuyenMai.isPresent()) {
	            BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
	            giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
	            // Lưu khuyến mãi vào map
	            sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
	        } else {
	            // Nếu không có khuyến mãi, để giá gốc
	            sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), null);
	        }

	        // Lưu giá sau khi giảm vào map
	        sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
	    }

		model.addAttribute("sanPhams", sanPhams);
		// Thêm vào model sau khi hoàn thành tính toán
	    model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap); // Map khuyến mãi cao nhất cho từng sản phẩm
	    model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap); // Giá sau khi giảm

		model.addAttribute("categories", categories);
		model.addAttribute("currentPage", page);
		model.addAttribute("totalPages", sanPhams.getTotalPages());

		return "index"; // Hiển thị sản phẩm trên trang index
	}

//	// Trang hiển thị sản phẩm theo danh mục
//	@GetMapping("/category/{maDanhMuc}")
//	public String viewProductsByCategory(
//			@PathVariable("maDanhMuc") Integer maDanhMuc,
//			@RequestParam(defaultValue = "0") int page, 
//			Model model) {
//		
//		Pageable pageable = PageRequest.of(page, 15);
//	    Page<SanPham> pageSanPham = sanPhamService.findActiveProductsInOrderDetailsByCategory(maDanhMuc, pageable);
//	   
//
//	    
//	    LocalDate today = LocalDate.now();
//		// Sử dụng Map với maSanPham làm key
//		Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
//		Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();
//		List<SanPham> sanPhams = pageSanPham.getContent()
//				.stream()
//				.filter(sanPham -> !sanPham.getChiTietDonNhapHangs().isEmpty())
//		        .filter(SanPham::isTrangThai)  // Lọc chỉ những sản phẩm có trangThai = true
//		        .collect(Collectors.toList());
//		
//		
//		
//		 System.out.println("Số sản phẩm có trạng thái = 1: " + sanPhams.size());
//		// Tìm khuyến mãi và tính giá sau khi giảm cho từng sản phẩm
//		for (SanPham sanPham : sanPhams) {
//			// Tìm khuyến mãi cao nhất hiện tại có trạng thái true và trong khoảng thời gian
//			// hợp lệ
//			Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
//					.filter(km -> km.getTrangThai()) // Chỉ lấy khuyến mãi có trạng thái true
//					.filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
//							&& !km.getNgayKetThuc().toLocalDate().isBefore(today)) // Chỉ lấy khuyến mãi còn hạn
//					.max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia)); // Lấy khuyến mãi có tỷ lệ giảm giá cao
//																				// nhất
//
//
//	        BigDecimal giaSauGiam = sanPham.getDonGiaBan();
//	        // Tính giá sau khi giảm
//	        if (highestCurrentKhuyenMai.isPresent()) {
//	            BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
//	            giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
//	            // Lưu khuyến mãi vào map
//	            sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
//	        } else {
//	            sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), null); // Không có khuyến mãi, để trống
//	        }
//
//	        // Lưu giá sau khi giảm vào map
//	        sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
//	    }
//		System.out.println("Danh sách sản phẩm: " + pageSanPham.getContent().size());
//
//		// Lấy danh sách danh mục
//		List<DanhMuc> danhMucs = danhMucService.getAll();
//		int maxVisible = 4;
//		List<DanhMuc> visibleDanhMucs = danhMucs.subList(0, Math.min(danhMucs.size(), maxVisible));
//		List<DanhMuc> hiddenDanhMucs = danhMucs.size() > maxVisible ? danhMucs.subList(maxVisible, danhMucs.size())
//				: new ArrayList<>();
//		model.addAttribute("visibleDanhMucs", visibleDanhMucs);
//		model.addAttribute("hiddenDanhMucs", hiddenDanhMucs);
//		model.addAttribute("maDanhMuc", maDanhMuc);
//		
//		model.addAttribute("sanPhams", sanPhams); // Truyền danh sách sản phẩm vào model
//	    model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap); 
//	    model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap); 
//	    
//	    model.addAttribute("categories", danhMucs); // Truyền danh mục vào model
//	    model.addAttribute("currentPage", page);
//	    model.addAttribute("totalPages", pageSanPham.getTotalPages());
//		
//
//		return "customer/categoryProduct"; // Trả về trang index
//	}
}