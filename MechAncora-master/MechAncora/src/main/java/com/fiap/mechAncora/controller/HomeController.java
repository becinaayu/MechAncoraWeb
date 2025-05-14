package com.fiap.mechAncora.controller;

import com.fiap.mechAncora.entity.Admin;
import com.fiap.mechAncora.entity.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import com.fiap.mechAncora.service.ProductService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class HomeController {
    @Autowired
    private ProductService productService;

    @GetMapping({"/", "/home"})
    public String homePage() {
        return "homePage";
    }

    @GetMapping("/products")
    public String productPage(Model model) {
        model.addAttribute("productList", productService.getAllProduct());

        return "Products";
    }

    @GetMapping("/aboutUs")
    public String aboutUs(Model model) {
        return "aboutUs";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("admin", new Admin());
        return "LoginPage";
    }
}
