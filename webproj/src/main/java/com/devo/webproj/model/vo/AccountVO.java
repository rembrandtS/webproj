package com.devo.webproj.model.vo;

import lombok.Data;

@Data
public class AccountVO {
    private long id;
    private String email;
    private long companyId;
    private String userName;
    private String rank;
    private String phoneNo;
    private String mobileNo;
    private String address;
    private String photoPath;
}