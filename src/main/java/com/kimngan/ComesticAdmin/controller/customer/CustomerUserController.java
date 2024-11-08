package com.kimngan.ComesticAdmin.controller.customer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.kimngan.ComesticAdmin.entity.NguoiDung;
import com.kimngan.ComesticAdmin.entity.QuyenTruyCap;
import com.kimngan.ComesticAdmin.repository.QuyenTruyCapRepository;
import com.kimngan.ComesticAdmin.services.NguoiDungService;

@Controller
@RequestMapping("/customer") // Thêm @RequestMapping để tất cả các phương thức đều có tiền tố "/customer"
public class CustomerUserController {

    @Autowired
    private NguoiDungService nguoiDungService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private QuyenTruyCapRepository quyenTruyCapRepository;

    // Phương thức hiển thị trang đăng ký
    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("nguoiDung", new NguoiDung());
        return "customer/register"; // Trả về tên của file view để hiển thị form đăng ký
    }

    // Phương thức xử lý đăng ký
    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute("nguoiDung") NguoiDung nguoiDung) {
        // Mã hóa mật khẩu trước khi lưu
        nguoiDung.setMatKhau(passwordEncoder.encode(nguoiDung.getMatKhau()));
        
        // Lấy quyền CUSTOMER từ cơ sở dữ liệu
        QuyenTruyCap quyenCustomer = quyenTruyCapRepository.findByTenQuyen("CUSTOMER");
        
        // Gán quyền CUSTOMER cho người dùng
        nguoiDung.setQuyenTruyCap(quyenCustomer);

        // Lưu người dùng vào cơ sở dữ liệu
        nguoiDungService.saveCustomer(nguoiDung);
        
        // Chuyển hướng về trang đăng nhập sau khi đăng ký thành công
        return "redirect:/customer/login";
    }

    // Phương thức hiển thị trang đăng nhập
    @GetMapping("/login")
    public String showLoginForm() {
        return "customer/login";
    }

    // Phương thức điều hướng sau khi đăng xuất
    @GetMapping("/logout")
    public String logout() {
        return "redirect:/"; // Điều hướng về trang chủ sau khi đăng xuất
    }
}

