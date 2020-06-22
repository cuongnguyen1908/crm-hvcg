package com.hvcg.api.crm.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hvcg.api.crm.constant.ERole;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GenerationType;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.FetchType;
import javax.persistence.Enumerated;
import javax.persistence.EnumType;
import javax.persistence.ManyToMany;
import javax.persistence.JoinTable;
import java.util.List;

@Entity
@Table(name="role")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "name", length = 20)
    private ERole name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name="user_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<User> users;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ERole getName() {
        return name;
    }

    public void setName(ERole name) {
        this.name = name;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
