package com.kimngan.ComesticAdmin.controller.admin;


import com.kimngan.ComesticAdmin.entity.DanhMuc;
import com.kimngan.ComesticAdmin.entity.NguoiDungDetails;
import com.kimngan.ComesticAdmin.services.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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
    @PostMapping("/add-category")
    public String save (@ModelAttribute("danhMuc") DanhMuc danhMuc) {
    	if (this.danhMucService.create(danhMuc)) {
    		return "redirect:/admin/category";
    	}
    	else {
    		return "admin/category/add";  
		}
    	
    }
    @GetMapping("/edit-category/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        // Tìm danh mục theo ID
        DanhMuc danhMuc = danhMucService.findById(id);
        
        // Kiểm tra nếu danh mục không tồn tại
        if (danhMuc == null) {
            return "redirect:/admin/category";  // Nếu không tìm thấy, quay lại trang danh sách
        }

        // Đưa danh mục vào Model để hiển thị
        model.addAttribute("danhMuc", danhMuc);

        // Thêm thông tin người dùng (nếu cần)
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        NguoiDungDetails userDetails = (NguoiDungDetails) authentication.getPrincipal();
        model.addAttribute("user", userDetails);

        return "admin/category/edit";  // Trả về view form chỉnh sửa danh mục
    }
    @PostMapping("/edit-category")
    public String update (@ModelAttribute("danhMuc") DanhMuc danhMuc) {
    	if (this.danhMucService.update(danhMuc)) {
    		return "redirect:/admin/category";
    	}
    	else {
    		return "admin/category/add";  
		}
    	
    }
    @GetMapping("/delete-category/{id}")
    public String deleteCategory(@PathVariable("id") Integer id) {
        // Kiểm tra nếu danh mục tồn tại
        DanhMuc danhMuc = danhMucService.findById(id);
        if (danhMuc == null) {
            return "redirect:/admin/category"; // Nếu không tìm thấy, quay về trang danh sách
        }

        // Xóa danh mục
        danhMucService.delete(id);

        // Quay về trang danh sách sau khi xóa thành công
        return "redirect:/admin/category";
    }

    
    
    

    


}
