package org.shtiroy.factory.entity;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;

@Entity
@Table(schema = "working_data", name = "t_role")
public class Role implements GrantedAuthority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(name = "role_name")
    private String roleName;

    public Role() {
    }

    public Role(Long id, String roleName) {
        Id = id;
        this.roleName = roleName;
    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String getAuthority() {
        return getRoleName();
    }

    @Override
    public String toString() {
        return roleName;
    }
}
