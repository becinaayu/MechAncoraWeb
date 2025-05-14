package com.fiap.mechAncora.service;

import com.fiap.mechAncora.entity.Admin;
import com.fiap.mechAncora.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminRepository adminRepository;

    public List<Admin> getAllAdmin(){
        return adminRepository.findAll();
    }

    public Admin getAdminById(Long id){
        return adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin não encontrado"));
    }

    public void createUser(Admin admin) {
        adminRepository.save(admin);
    }

    public void updateAdmin(Admin admin) {
        adminRepository.findById(admin.getId()).orElseThrow(() -> new RuntimeException("Admin com id " + admin.getId() + " não foi encontrado."));
        adminRepository.save(admin);
    }

    public void deleteAdmin(Long id){
        adminRepository.findById(id).orElseThrow(() -> new RuntimeException("Admin com Id"+ id +" não foi encontrado."));
        adminRepository.deleteById(id);
    }

    public boolean verifyCredentials(String email, String password){
        Admin admin = adminRepository.findByEmail(email);
        if (admin.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
