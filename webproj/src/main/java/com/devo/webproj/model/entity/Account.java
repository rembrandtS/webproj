package com.devo.webproj.model.entity;

import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String email;
    private String password;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String userName;
    private String rank;
    private String phoneNo;
    private String mobileNo;
    private String address;
    private String photoPath;
    private Integer enabled;

    private long writerId;
    private LocalDateTime writeDate;

    private LocalDateTime lastModifyDate;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name = "account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private List<Role> roles = new ArrayList<>();

    public void setPassword(String password) {
        this.password = password;
    }
}