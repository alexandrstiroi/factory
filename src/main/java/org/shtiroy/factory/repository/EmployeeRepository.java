package org.shtiroy.factory.repository;

import org.shtiroy.factory.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.List;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByWorks(Boolean works);

    @Modifying
    @Transactional
    @Query(value = "UPDATE working_data.t_employee SET first_name = ?1, last_name = ?2, " +
                "middle_name = ?3, date_birth = ?4 WHERE id = ?5",
            nativeQuery = true)
    void updateEmployee(String firstName, String lastName, String middleName, Date dateBirth, Long id);

    @Modifying
    @Transactional
    @Query(value = "UPDATE working_data.t_employee SET works = false WHERE id = ?1",
            nativeQuery = true)
    void employeeDeactivation(Long id);
}
