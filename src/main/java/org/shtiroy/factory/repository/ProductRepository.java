package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {

    List<Product> findAllByStatus(Boolean status);

    Product save(Product product);

    Optional<Product> findById(Integer id);
}
