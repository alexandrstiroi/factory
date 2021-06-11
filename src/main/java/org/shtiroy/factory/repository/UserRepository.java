package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);

    List<User> findAllByUserActive(Boolean userActive);

    @Modifying
    @Transactional
    @Query(value = "UPDATE working_data.t_user SET last_login = ?2 WHERE id =?1",
            nativeQuery = true)
    void updateLastLogin(Long id, Timestamp lastLogin);

    @Modifying
    @Transactional
    @Query(value = "UPDATE working_data.t_user SET user_role = ?1,  last_name = ?2, first_name = ?3, " +
            "email = ?4 WHERE id = ?5",
           nativeQuery = true)
    void updateUser(Long userRole, String lastName, String firstName, String email, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE working_data.t_user SET user_password = ?1, user_role = ?2,  last_name = ?3, first_name = ?4, " +
            "email = ?5 WHERE id = ?6",
            nativeQuery = true)
    void updateUserWithPassword(String password, Long userRole, String lastName, String firstName, String email, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE working_data.t_user SET user_active = false WHERE id = ?1",
            nativeQuery = true)
    void userDeactivation(Long id);
}
