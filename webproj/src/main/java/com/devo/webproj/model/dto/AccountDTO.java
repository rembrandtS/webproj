package com.devo.webproj.model.dto;

import com.devo.webproj.model.entity.Company;
import lombok.Getter;


@Getter
public class AccountDTO {
    private long id;
    private String email;
    private Company companyName;
    private String userName;
    private String rank;
    private String phoneNo;
    private String mobileNo;
    private String address;
    private String photoPath;
    private Integer enabled;
}