package com.kimngan.ComesticAdmin.controller.customer;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kimngan.ComesticAdmin.entity.DanhMuc;
import com.kimngan.ComesticAdmin.entity.KhuyenMai;
import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.services.DanhMucService;
import com.kimngan.ComesticAdmin.services.SanPhamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDate;

@Controller
@RequestMapping("/category")
public class CustomerCategoryController {

	@Autowired
	private SanPhamService sanPhamService;

	@Autowired
	private DanhMucService danhMucService;

	// Trang hiển thị sản phẩm theo danh mục
	// Trang hiển thị sản phẩm theo danh mục
//    @GetMapping("/{maDanhMuc}")
//    public String productsByCategory(@PathVariable("maDanhMuc") Integer maDanhMuc, 
//                                     @RequestParam(defaultValue = "0") int page, 
//                                     Model model) {
//    	
//    	
//    	
//        Page<SanPham> productsByCategory   = sanPhamService.findActiveProductsInOrderDetailsByCategory(maDanhMuc, PageRequest.of(page, 15));
//       
//        Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
//        Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();
//        LocalDate today = LocalDate.now();
//     // Tìm khuyến mãi và tính giá sau khi giảm cho từng sản phẩm
//     // Tính toán khuyến mãi và giá sau khi giảm cho từng sản phẩm
//        for (SanPham sanPham : productsByCategory.getContent()) {
//            Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
//                    .filter(km -> km.getTrangThai())  // Chỉ lấy khuyến mãi có trạng thái true
//                    
//                    .filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
//							&& !km.getNgayKetThuc().toLocalDate().isBefore(today)) 
//                    .max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia)); // Lấy khuyến mãi có tỷ lệ giảm giá cao nhất
//
//            BigDecimal giaSauGiam = sanPham.getDonGiaBan();
//            if (highestCurrentKhuyenMai.isPresent()) {
//                BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
//                giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
//                sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
//            } else {
//                sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), null);
//            }
//            sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
//        }
//
//        
//        
//        DanhMuc selectedCategory = danhMucService.findById(maDanhMuc); // Lấy danh mục đã chọn
//        List<DanhMuc> categories = danhMucService.getAll();
//       
//       // model.addAttribute("currentPage", page);
//       // model.addAttribute("totalPages", productsByCategory.getTotalPages());
//        
//        
//        model.addAttribute("sanPhams", productsByCategory.getContent()); 
//        model.addAttribute("maDanhMuc", maDanhMuc);
//        
//        model.addAttribute("selectedCategory", selectedCategory);
//        // Thêm danh sách categories vào model
//       
//        model.addAttribute("categories", categories);
//        
//        model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap);  // Thêm map khuyến mãi
//        model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap);
//
//        return "customer/categoryProduct"; // Trả về trang hiển thị sản phẩm theo danh mục
//    }

//    @GetMapping({"/{maDanhMuc}", "/all"})
//    public String productsByCategoryOrAll(
//    		
//
//            @PathVariable(value = "maDanhMuc", required = false) Integer maDanhMuc,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder,
//            Model model) {
//    	
//    	
//    	
//    	
//    	
//
//        Page<SanPham> products;
//        String selectedCategoryName;
//
//        if (maDanhMuc == null) {
//            // Hiển thị tất cả sản phẩm đang hoạt động
//            products = sanPhamService.findAllActive(PageRequest.of(page, 15));
//            selectedCategoryName = "Tất cả";
//        } else {
//            // Hiển thị sản phẩm theo danh mục đã chọn
//            products = sanPhamService.findActiveProductsInOrderDetailsByCategory(maDanhMuc, PageRequest.of(page, 15));
//            DanhMuc selectedCategory = danhMucService.findById(maDanhMuc);
//            selectedCategoryName = selectedCategory != null ? selectedCategory.getTenDanhMuc() : "Danh mục không tồn tại";
//        }
//
//        Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
//        Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();
//        LocalDate today = LocalDate.now();
//
//        for (SanPham sanPham : products.getContent()) {
//            Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
//                    .filter(km -> km.getTrangThai())
//                    .filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
//                            && !km.getNgayKetThuc().toLocalDate().isBefore(today))
//                    .max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia));
//
//            BigDecimal giaSauGiam = sanPham.getDonGiaBan();
//            if (highestCurrentKhuyenMai.isPresent()) {
//                BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
//                giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
//                sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
//            } else {
//                sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), null);
//            }
//            sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
//        }
//        
//     // Chuyển đổi Page<SanPham> sang List<SanPham> và thực hiện sắp xếp
//        List<SanPham> sortedProducts = new ArrayList<>(products.getContent());
//        sortedProducts.sort((p1, p2) -> {
//            BigDecimal giaSauGiam1 = sanPhamGiaSauGiamMap.getOrDefault(p1.getMaSanPham(), p1.getDonGiaBan());
//            BigDecimal giaSauGiam2 = sanPhamGiaSauGiamMap.getOrDefault(p2.getMaSanPham(), p2.getDonGiaBan());
//            return sortOrder.equals("asc") ? giaSauGiam1.compareTo(giaSauGiam2) : giaSauGiam2.compareTo(giaSauGiam1);
//        });
//
//        
//        
//
//        // Phân trang danh sách đã sắp xếp
//        int pageSize = 15;
//        int start = Math.min(page * pageSize, sortedProducts.size());
//        int end = Math.min((page + 1) * pageSize, sortedProducts.size());
//        
//        List<SanPham> paginatedProducts = sortedProducts.subList(start, end);
//        
//        
//        
//        
//        List<DanhMuc> categories = danhMucService.getAll();
//        model.addAttribute("sortOrder", sortOrder);
//        model.addAttribute("sanPhams", sortedProducts);
//        model.addAttribute("sanPhams", products);
//        model.addAttribute("sanPhams", paginatedProducts);
//        model.addAttribute("sanPhams", products.getContent());
//        model.addAttribute("maDanhMuc", maDanhMuc);
//        model.addAttribute("selectedCategory", selectedCategoryName);
//        model.addAttribute("categories", categories);
//        model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap);
//        model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap);
//       
//        return "customer/categoryProduct";
//    }

	@GetMapping({ "/{maDanhMuc}", "/all" })
	public String productsByCategoryOrAll(@PathVariable(value = "maDanhMuc", required = false) Integer maDanhMuc,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(value = "sortOrder", defaultValue = "asc") String sortOrder, Model model) {

		Page<SanPham> products;
		String selectedCategoryName;

		if (maDanhMuc == null) {
			// Hiển thị tất cả sản phẩm đang hoạt động
			products = sanPhamService.findAllActive(PageRequest.of(page, 15));
			selectedCategoryName = "Tất cả";
		} else {
			// Hiển thị sản phẩm theo danh mục đã chọn
			products = sanPhamService.findActiveProductsInOrderDetailsByCategory(maDanhMuc, PageRequest.of(page, 15));
			DanhMuc selectedCategory = danhMucService.findById(maDanhMuc);
			selectedCategoryName = selectedCategory != null ? selectedCategory.getTenDanhMuc()
					: "Danh mục không tồn tại";
		}

		Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
		Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();
		LocalDate today = LocalDate.now();

		// Tính toán giá sau giảm và lưu vào map
		for (SanPham sanPham : products.getContent()) {
			Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
					.filter(km -> km.getTrangThai())
					.filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
							&& !km.getNgayKetThuc().toLocalDate().isBefore(today))
					.max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia));

			BigDecimal giaSauGiam = sanPham.getDonGiaBan();
			if (highestCurrentKhuyenMai.isPresent()) {
				BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
				giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
				sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
			}
			sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
		}

		// Chuyển đổi Page<SanPham> sang List<SanPham> và thực hiện sắp xếp
		List<SanPham> sortedProducts = new ArrayList<>(products.getContent());
		sortedProducts.sort((p1, p2) -> {
			BigDecimal giaSauGiam1 = sanPhamGiaSauGiamMap.getOrDefault(p1.getMaSanPham(), p1.getDonGiaBan());
			BigDecimal giaSauGiam2 = sanPhamGiaSauGiamMap.getOrDefault(p2.getMaSanPham(), p2.getDonGiaBan());
			return sortOrder.equals("asc") ? giaSauGiam1.compareTo(giaSauGiam2) : giaSauGiam2.compareTo(giaSauGiam1);
		});

		// In ra để kiểm tra sắp xếp
		System.out.println("Sorted Products:");
		sortedProducts.forEach(
				sp -> System.out.println(sp.getMaSanPham() + " - " + sanPhamGiaSauGiamMap.get(sp.getMaSanPham())));

		// Phân trang danh sách đã sắp xếp
		int pageSize = 15;
		int start = Math.min(page * pageSize, sortedProducts.size());
		int end = Math.min((page + 1) * pageSize, sortedProducts.size());
		List<SanPham> paginatedProducts = sortedProducts.subList(start, end);

		// Thêm dữ liệu vào model
		model.addAttribute("sanPhams", paginatedProducts);
		model.addAttribute("maDanhMuc", maDanhMuc);
		model.addAttribute("selectedCategory", selectedCategoryName);
		model.addAttribute("categories", danhMucService.getAll());
		model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap);
		model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap);
		model.addAttribute("sortOrder", sortOrder); // Để giữ giá trị sắp xếp hiện tại trên giao diện

		return "customer/categoryProduct";
	}

	@GetMapping("/search")
	public String searchProducts(
			@RequestParam(value = "category", required = false) Integer category,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(defaultValue = "0") int page, Model model) {

		// Lấy kết quả tìm kiếm sản phẩm
		Page<SanPham> searchResults;
		if (category == null || category == -1) {
			// Tìm kiếm trong tất cả danh mục
			searchResults = sanPhamService.searchAllActiveProductsWithOrderDetails(keyword, PageRequest.of(page, 15));
		} else {
			// Tìm kiếm theo mã danh mục cụ thể
			searchResults = sanPhamService.searchByCategoryWithOrderDetails(category, keyword, PageRequest.of(page, 15));
		}
		 // Kiểm tra nếu không có sản phẩm nào trong kết quả tìm kiếm
	    if (searchResults.isEmpty()) {
	        model.addAttribute("noResultsMessage", "Không có sản phẩm nào được tìm thấy.");
	    } else {
	    	model.addAttribute("sanPhams", searchResults.getContent()); // Lấy danh sách sản phẩm từ Page
	        model.addAttribute("currentPage", page);
	        model.addAttribute("totalPages", searchResults.getTotalPages());
	    }

		// Khởi tạo các map cho khuyến mãi và giá sau giảm giá
		Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
		Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();
		LocalDate today = LocalDate.now();

		for (SanPham sanPham : searchResults) {
			Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
					.filter(km -> km.getTrangThai())
					.filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
							&& !km.getNgayKetThuc().toLocalDate().isBefore(today))
					.max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia));

			BigDecimal giaSauGiam = sanPham.getDonGiaBan();
			if (highestCurrentKhuyenMai.isPresent()) {
				BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
				giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
				sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
			}
			sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
		}

		// Lấy danh sách danh mục và thêm vào model
		List<DanhMuc> categories = danhMucService.getAll();
		model.addAttribute("categories", categories);
		//model.addAttribute("sanPhams", searchResults);
	    model.addAttribute("selectedCategory", category != null ? danhMucService.findById(category).getTenDanhMuc() : "Tất cả");
		model.addAttribute("keyword", keyword);
		model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap);
		model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap);

		return "customer/categoryProduct";
	}

	// Hiển thị tất cả danh mục sản phẩm
//    @GetMapping("/list")
//    public String listCategories(Model model) {
//    	List<DanhMuc> danhMucs = danhMucService.getAll();
//    	
//    	  // Lọc `sanPhams` để loại bỏ giá trị null
//        for (DanhMuc category : danhMucs) {
//            category.setSanPhams(
//                category.getSanPhams().stream()
//                    .filter(Objects::nonNull)
//                    .collect(Collectors.toSet())
//            );
//        }
//    	
//    	
//    	
//    	// Kiểm tra xem danh sách có thực sự không rỗng
//        model.addAttribute("danhMucs", danhMucs);
//        System.out.println("Danh sách danh mục: " + danhMucs.size());
//        
//        // Tự động chia danh mục thành 2 nhóm: hiển thị và ẩn
//        int maxVisible = 4; 
//        List<DanhMuc> visibleDanhMucs = danhMucs.subList(0, Math.min(danhMucs.size(), maxVisible)); 
//        List<DanhMuc> hiddenDanhMucs = danhMucs.size() > maxVisible ? danhMucs.subList(maxVisible, danhMucs.size()) : new ArrayList<>();
//
//        model.addAttribute("visibleDanhMucs", visibleDanhMucs);
//        model.addAttribute("hiddenDanhMucs", hiddenDanhMucs);
//
//        return "index"; // Trả về trang hiển thị tất cả danh mục
//    }
//    @GetMapping("/all")
//    public String allProducts(@PathVariable("maDanhMuc") Integer maDanhMuc, 
//            @RequestParam(defaultValue = "0") int page, 
//            Model model) 
//    
//    {
//        Page<SanPham> allProducts = sanPhamService.findAllActive(PageRequest.of(page, 15));
//        
//
//        Map<Integer, KhuyenMai> sanPhamKhuyenMaiMap = new HashMap<>();
//        Map<Integer, BigDecimal> sanPhamGiaSauGiamMap = new HashMap<>();
//        LocalDate today = LocalDate.now();
//     // Tìm khuyến mãi và tính giá sau khi giảm cho từng sản phẩm
//     // Tính toán khuyến mãi và giá sau khi giảm cho từng sản phẩm
//        for (SanPham sanPham : allProducts.getContent()) {
//            Optional<KhuyenMai> highestCurrentKhuyenMai = sanPham.getKhuyenMais().stream()
//                    .filter(km -> km.getTrangThai())  // Chỉ lấy khuyến mãi có trạng thái true
//                    
//                    .filter(km -> !km.getNgayBatDau().toLocalDate().isAfter(today)
//							&& !km.getNgayKetThuc().toLocalDate().isBefore(today)) 
//                    .max(Comparator.comparing(KhuyenMai::getPhanTramGiamGia)); // Lấy khuyến mãi có tỷ lệ giảm giá cao nhất
//
//            BigDecimal giaSauGiam = sanPham.getDonGiaBan();
//            if (highestCurrentKhuyenMai.isPresent()) {
//                BigDecimal phanTramGiam = highestCurrentKhuyenMai.get().getPhanTramGiamGia();
//                giaSauGiam = giaSauGiam.subtract(giaSauGiam.multiply(phanTramGiam).divide(BigDecimal.valueOf(100)));
//                sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), highestCurrentKhuyenMai.get());
//            } else {
//                sanPhamKhuyenMaiMap.put(sanPham.getMaSanPham(), null);
//            }
//            sanPhamGiaSauGiamMap.put(sanPham.getMaSanPham(), giaSauGiam);
//        }
//
//        
//        
//        DanhMuc selectedCategory = danhMucService.findById(maDanhMuc); // Lấy danh mục đã chọn
//        List<DanhMuc> categories = danhMucService.getAll();
//       
//       // model.addAttribute("currentPage", page);
//       // model.addAttribute("totalPages", productsByCategory.getTotalPages());
//        
//        
//        model.addAttribute("sanPhams", allProducts.getContent()); 
//        model.addAttribute("maDanhMuc", maDanhMuc);
//        
//        model.addAttribute("selectedCategory", selectedCategory);
//        // Thêm danh sách categories vào model
//       
//        model.addAttribute("categories", categories);
//        
//        model.addAttribute("sanPhamKhuyenMaiMap", sanPhamKhuyenMaiMap);  // Thêm map khuyến mãi
//        model.addAttribute("sanPhamGiaSauGiamMap", sanPhamGiaSauGiamMap);
//
//        return "customer/categoryProduct"; // Trả về trang hiển thị sản phẩm theo danh mục
//    }

}
