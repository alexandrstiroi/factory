package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Module;
import org.shtiroy.factory.entity.ModuleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModuleTypeRepository extends JpaRepository<ModuleType, Integer> {

    List<ModuleType> findByIdModule(Module module);
}
