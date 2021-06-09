package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    @Modifying
    @Transactional
    @Query(value = "UPDATE working_data.t_user SET last_login = ?2 WHERE id =?1",
            nativeQuery = true)
    void updateLastLogin(Long id, Timestamp lastLogin);

}
