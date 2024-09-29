package com.kimngan.ComesticAdmin.controller.admin;


import com.kimngan.ComesticAdmin.entity.DanhMuc;
import com.kimngan.ComesticAdmin.entity.NguoiDungDetails;
import com.kimngan.ComesticAdmin.services.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoryController {

    @Autowired
    private DanhMucService danhMucService;

    @GetMapping("/category")
    public String index(Model model) {
        List<DanhMuc> list = danhMucService.getAll();
        model.addAttribute("list", list);

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NguoiDungDetails userDetails = (NguoiDungDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);

        return "admin/category/index";  // Trả về view hiển thị danh sách danh mục
    }

    @GetMapping("/add-category")
    public String addCategoryPage(Model model) {
        model.addAttribute("danhMuc", new DanhMuc());

        // Thêm đoạn code lấy thông tin người dùng
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NguoiDungDetails userDetails = (NguoiDungDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);

        return "admin/category/add";  // Trả về view form thêm mới danh mục
    }


}
