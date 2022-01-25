package com.devo.webproj.model.entity;

import com.devo.webproj.model.vo.AccountVO;
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

    public Account() {

    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Account (AccountVO accountVO)
    {
        this.email = accountVO.getEmail();
        this.company = new Company(accountVO.getCompanyId());
        this.userName = accountVO.getUserName();
        this.rank = accountVO.getRank();
        this.phoneNo = accountVO.getPhoneNo();
        this.mobileNo = accountVO.getMobileNo();
        this.address = accountVO.getAddress();
        this.photoPath = accountVO.getPhotoPath();
        this.enabled = 1;
        this.password = "admin4321!";
    }
}