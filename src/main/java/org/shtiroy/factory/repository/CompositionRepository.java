package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Composition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CompositionRepository extends JpaRepository<Composition, Integer> {
}
