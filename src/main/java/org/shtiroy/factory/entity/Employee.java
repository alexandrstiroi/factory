package org.shtiroy.factory.entity;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(schema = "working_data", name = "t_employee")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "middle_name")
    private String middleName;

    @Column(name = "date_birth")
    private Date dateBirth;

    @Column(name = "works")
    private Boolean works;

    public Employee() {
    }

    public Employee(Long id, String firstName, String lastName, String middleName, Date dateBirth, Boolean works) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.dateBirth = dateBirth;
        this.works = works;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public Date getDateBirth() {
        return dateBirth;
    }

    public void setDateBirth(Date dateBirth) {
        this.dateBirth = dateBirth;
    }

    public Boolean getWorks() {
        return works;
    }

    public void setWorks(Boolean works) {
        this.works = works;
    }

    @Override
    public String toString() {
        return "Employee "  + firstName + " " + lastName;
    }
}
