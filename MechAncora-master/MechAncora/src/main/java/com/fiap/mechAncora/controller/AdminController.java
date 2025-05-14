package com.fiap.mechAncora.controller;

import com.fiap.mechAncora.entity.Admin;
import com.fiap.mechAncora.entity.Order;
import com.fiap.mechAncora.entity.Product;
import com.fiap.mechAncora.entity.User;
import com.fiap.mechAncora.service.AdminService;
import com.fiap.mechAncora.service.OrderService;
import com.fiap.mechAncora.service.ProductService;
import com.fiap.mechAncora.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;


@Controller
public class AdminController {
    @Autowired
    private AdminService adminService;

    @Autowired
    private UserService userService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @PostMapping("/admin/product/search")
    public String adminProductSearch(@RequestParam("name") String name, Model model) {
        try {
            Product product = productService.findProductByName(name);

            // Manter todas as listas necessárias para a página admin
            model.addAttribute("adminList", adminService.getAllAdmin());
            model.addAttribute("userList", userService.getAllUser());
            model.addAttribute("orderList", orderService.getAllOrder());
            model.addAttribute("productList", productService.getAllProduct());

            if (product != null) {
                model.addAttribute("searchedProduct", product);
                model.addAttribute("messageSuccess", "Produto encontrado!");
            } else {
                model.addAttribute("messageError", "Produto não encontrado.");
            }

            return "AdminHomePage";
        } catch (Exception e) {
            model.addAttribute("messageError", "Erro ao buscar produto: " + e.getMessage());
            return "AdminHomePage";
        }
    }


    @GetMapping("/admin/verify/credentials")
    public String verifyCredentials(@ModelAttribute("admin") Admin admin, Model model) {
        if (adminService.verifyCredentials(admin.getEmail(), admin.getPassword())) {
            model.addAttribute("admin", new Admin());
            model.addAttribute("user", new User());
            model.addAttribute("product", new Product());
            return "redirect:/admin/home";
        }
        model.addAttribute("erro", "Email ou senha invalidos. Tente novamente.");
        return "LoginPage";
    }

    @GetMapping("/admin/home")
    public String adminHomePage(Model model){
        model.addAttribute("adminList", adminService.getAllAdmin());
        model.addAttribute("userList", userService.getAllUser());
        model.addAttribute("orderList", orderService.getAllOrder());
        model.addAttribute("productList", productService.getAllProduct());

        return "AdminHomePage";
    }

    @PostMapping("/add/admin")
    public String createAdmin(Admin admin){
        adminService.createUser(admin);
        return "redirect:/admin/home";
    }

    @GetMapping("/update/admin/{id}")
    public String update(@PathVariable ("id") Long id, Model model){
        model.addAttribute("admin", adminService.getAdminById(id));
        return "UpdateAdmin";
    }

    @PostMapping("/update/admin")
    public String updateAdmin(Admin admin) {
        adminService.updateAdmin(admin);

        return "redirect:/admin/home";
    }

    @GetMapping("/delete/admin/{id}")
    public String deleteAdmin(@PathVariable Long id){
        adminService.deleteAdmin(id);

        return "redirect:/admin/home";
    }

    @GetMapping("/user/home")
    public String userHome(@ModelAttribute("userId") Long userId,
                           @ModelAttribute("error") String error,
                           @ModelAttribute("messageSuccess") String messageSuccess,
                           Model model) {
        User user = userService.getUserById(userId);
        model.addAttribute("ordersList", orderService.findOrdersByUser(user));
        model.addAttribute("productList", productService.getAllProduct()); // Adiciona lista de produtos
        model.addAttribute("userId", userId);

        if (error != null && !error.isEmpty()) {
            model.addAttribute("error", error);
        }
        if (messageSuccess != null && !messageSuccess.isEmpty()) {
            model.addAttribute("messageSuccess", messageSuccess);
        }

        return "BuyProductPage";
    }


    @PostMapping("/user/login")
    public String userLogin(User user, RedirectAttributes redirectAttributes) {
        if (userService.verifyCredentials(user.getEmail(), user.getPassword())) {
            user = userService.findUserByEmail(user.getEmail());
            redirectAttributes.addAttribute("userId", user.getId());

            return "redirect:/user/home";
        }

        redirectAttributes.addAttribute("erro", "Email ou senha invalidos.");
        return "Login";
    }

    @PostMapping("/product/search")
    public String productSearch(@RequestParam("name") String name,
                                @RequestParam("userId") Long userId,
                                Model model) {
        try {
            Product product = productService.findProductByName(name);
            User user = userService.getUserById(userId);

            model.addAttribute("ordersList", orderService.findOrdersByUser(user));
            model.addAttribute("productList", productService.getAllProduct()); // Mantém a lista de produtos
            model.addAttribute("userId", userId);

            if (product != null) {
                model.addAttribute("product", product);
            } else {
                model.addAttribute("messageError", "Produto não encontrado...");
            }

            return "BuyProductPage";
        } catch (Exception e) {
            model.addAttribute("messageError", "Erro ao buscar produto: " + e.getMessage());
            return "BuyProductPage";
        }
    }


    @PostMapping("/place/order")
    public String placeOrder(Order order, Long userId, RedirectAttributes redirectAttributes) {
        try {
            User user = userService.getUserById(userId);
            order.setUser(user);
            order.setAmount(order.getPrice() * order.getQuantity());
            order.setDate(new Date());

            orderService.createOrder(order);

            redirectAttributes.addAttribute("userId", userId);
            redirectAttributes.addAttribute("messageSuccess", "Pedido realizado com sucesso!");

            return "redirect:/user/home";
        } catch (Exception e) {
            redirectAttributes.addAttribute("userId", userId);
            redirectAttributes.addAttribute("error", "Erro ao realizar o pedido: " + e.getMessage());
            return "redirect:/user/home";
        }
    }

}



