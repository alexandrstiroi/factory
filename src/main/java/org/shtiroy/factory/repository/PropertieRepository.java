package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Component;
import org.shtiroy.factory.entity.Propertie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertieRepository extends JpaRepository<Propertie, Integer> {

    Propertie save(Propertie propertie);

    List<Propertie> findByIdComponent(Component idComponent);

}
