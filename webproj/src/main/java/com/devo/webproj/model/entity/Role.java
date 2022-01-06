package com.devo.webproj.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Role {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;
    private String code;

    @ManyToMany(fetch = LAZY)
    @JoinTable(
            name="account_role",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "account_id")
    )

    private List<Account> accounts = new ArrayList<>();
}
