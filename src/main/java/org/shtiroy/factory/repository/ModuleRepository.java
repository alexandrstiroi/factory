package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Module;
import org.shtiroy.factory.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleRepository extends JpaRepository<Module, Integer>{
    List<Module> findByIdProduct(Product product);
}
