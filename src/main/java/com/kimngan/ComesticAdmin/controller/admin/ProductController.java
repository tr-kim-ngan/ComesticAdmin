package com.kimngan.ComesticAdmin.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.kimngan.ComesticAdmin.entity.SanPham;
import com.kimngan.ComesticAdmin.repository.SanPhamRepository;
import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private SanPhamRepository sanPhamRepository;

    @GetMapping
    public String index(Model model) {
        List<SanPham> sanPhamList = sanPhamRepository.findAll(); // Lấy tất cả sản phẩm từ cơ sở dữ liệu
        model.addAttribute("sanPhamList", sanPhamList); // Đưa danh sách sản phẩm vào model để hiển thị trên trang web
        return "admin/product/index"; // Trả về trang index
    }
}
