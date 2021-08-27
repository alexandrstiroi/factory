package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Shop;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ShopRepository extends JpaRepository<Shop, Integer> {

    List<Shop> findByShopType(Integer shopType);

    Optional<Shop> findById(Integer id);

}
