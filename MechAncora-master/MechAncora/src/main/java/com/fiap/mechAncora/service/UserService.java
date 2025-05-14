package com.fiap.mechAncora.service;



import com.fiap.mechAncora.entity.User;
import com.fiap.mechAncora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public User getUserById(Long id){
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
    }

    public void createUser(User user){
        userRepository.save(user);
    }

    public void updateUser(User User){
        userRepository.findById(User.getId()).orElseThrow(() -> new RuntimeException("Usuário com Id"+ User.getId()+" não foi encontrado."));
        userRepository.save(User);
    }

    public void deleteUser(Long id){
        userRepository.findById(id).orElseThrow(() -> new RuntimeException("Usuário com Id"+ id +" não foi encontrado."));
        userRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean verifyCredentials(String email, String password){
        User user = userRepository.findByEmail(email);
        if (user.getPassword().equals(password)) {
            return true;
        }
        return false;
    }
}
