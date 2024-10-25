package com.kimngan.ComesticAdmin.controller.customer;

import java.util.ArrayList;
import java.util.List;
import org.springframework.ui.Model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.kimngan.ComesticAdmin.entity.DanhMuc;
import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.services.DanhMucService;
import com.kimngan.ComesticAdmin.services.SanPhamService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Controller
@RequestMapping("/category")
public class CustomerCategoryController {
    
    @Autowired
    private SanPhamService sanPhamService;

    @Autowired
    private DanhMucService danhMucService;

 // Trang hiển thị sản phẩm theo danh mục
    // Trang hiển thị sản phẩm theo danh mục
    @GetMapping("/{maDanhMuc}")
    public String productsByCategory(@PathVariable("maDanhMuc") Integer maDanhMuc, 
                                     @RequestParam(defaultValue = "0") int page, 
                                     Model model) {
        Page<SanPham> productsByCategory = sanPhamService.findByDanhMucAndTrangThai(maDanhMuc, true, PageRequest.of(page, 10));
        model.addAttribute("products", productsByCategory.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productsByCategory.getTotalPages());
        model.addAttribute("maDanhMuc", maDanhMuc);

        return "index"; // Trả về trang hiển thị sản phẩm theo danh mục
    }

    // Hiển thị tất cả danh mục sản phẩm
    @GetMapping("/list")
    public String listCategories(Model model) {
    	List<DanhMuc> danhMucs = danhMucService.getAll(); // Kiểm tra xem danh sách có thực sự không rỗng
        model.addAttribute("danhMucs", danhMucs);
        System.out.println("Danh sách danh mục: " + danhMucs.size());
        
        // Tự động chia danh mục thành 2 nhóm: hiển thị và ẩn
        int maxVisible = 4; 
        List<DanhMuc> visibleDanhMucs = danhMucs.subList(0, Math.min(danhMucs.size(), maxVisible)); 
        List<DanhMuc> hiddenDanhMucs = danhMucs.size() > maxVisible ? danhMucs.subList(maxVisible, danhMucs.size()) : new ArrayList<>();

        model.addAttribute("visibleDanhMucs", visibleDanhMucs);
        model.addAttribute("hiddenDanhMucs", hiddenDanhMucs);

        return "index"; // Trả về trang hiển thị tất cả danh mục
    }
}

