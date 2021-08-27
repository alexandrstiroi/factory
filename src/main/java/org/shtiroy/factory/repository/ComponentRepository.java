package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Component;
import org.shtiroy.factory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComponentRepository extends JpaRepository<Component, Integer> {

    Component save(Component component);

    Optional<Component> findById(Integer id);

    List<Component> findByIdProduct(Product product);


}
