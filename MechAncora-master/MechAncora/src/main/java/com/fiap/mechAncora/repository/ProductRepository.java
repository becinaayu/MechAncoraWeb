package com.fiap.mechAncora.repository;

import com.fiap.mechAncora.entity.Product;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    public Product findByName(String name);

        @Transactional
    @Query(value = "SELECT setval(products_id_seq, (SELECT MAX(id) FROM products))", nativeQuery = true)
    void resetUserSequence();
}
