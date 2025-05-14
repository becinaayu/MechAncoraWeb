package com.fiap.mechAncora.service;


import com.fiap.mechAncora.entity.Order;
import com.fiap.mechAncora.entity.User;
import com.fiap.mechAncora.repository.OrderRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAllOrder(){
        return orderRepository.findAll();
    }

    public Order getOrderById(Long id){
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public void createOrder(Order Order){
        orderRepository.save(Order);
    }

    public void updateOrder(Order Order){
        orderRepository.findById(Order.getId()).orElseThrow(() -> new RuntimeException("Pedido com Id"+ Order.getId()+" não foi encontrado."));
        orderRepository.save(Order);
    }

    public void deleteOrder(Long id){
        orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Pedido com Id"+ id +" não foi encontrado."));
        orderRepository.deleteById(id);
    }

    public List<Order> findOrdersByUser (User user) {
        return orderRepository.findByUser(user);
    }

}
