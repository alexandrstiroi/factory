package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Consumable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ConsumableRepository extends JpaRepository<Consumable, Integer> {

    List<Consumable> findByConsumableTypeOrderByConsumableCatAscConsumableName (Integer consumableType);
}
