package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Photo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {

    @Override
    Optional<Photo> findById(Integer integer);
}
