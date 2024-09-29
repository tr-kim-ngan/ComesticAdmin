package com.kimngan.ComesticAdmin.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;



@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
   

    @GetMapping
    public String index() {
        
        
        
        // Đưa danh sách sản phẩm vào model để hiển thị trên trang web
        return "admin/product/index"; // Trả về trang index
    }
}
