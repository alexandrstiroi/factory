package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, Integer> {

    @Query(value = "select t1.* from working_data.t_composition t1 join working_data.t_order_product t2 on t1.id_order_product = t2.id where t2.id_order = ?",
    nativeQuery = true)
    List<Composition> findAllComposition(Integer idOrder);
}
