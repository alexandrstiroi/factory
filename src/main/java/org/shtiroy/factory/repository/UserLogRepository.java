package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.UserLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogRepository extends JpaRepository<UserLog, Long> {

    UserLog save(UserLog userLog);

}
