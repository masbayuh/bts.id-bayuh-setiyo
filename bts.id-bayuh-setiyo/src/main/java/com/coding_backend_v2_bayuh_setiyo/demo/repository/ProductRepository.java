package com.coding_backend_v2_bayuh_setiyo.demo.repository;

import com.coding_backend_v2_bayuh_setiyo.demo.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
}
