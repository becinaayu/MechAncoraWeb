package com.fiap.mechAncora.repository;

import com.fiap.mechAncora.entity.Order;
import com.fiap.mechAncora.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    public List<Order> findByUser(User user);
}
