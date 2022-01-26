package com.devo.webproj.model.dto;

import com.devo.webproj.model.entity.Account;
import com.devo.webproj.model.entity.Company;
import lombok.Getter;


@Getter
public class AccountDTO {
    private long id;
    private String email;
    private String companyName;
    private long companyId;
    private String userName;
    private String rank;
    private String phoneNo;
    private String mobileNo;
    private String address;
    private String photoPath;
    private Integer enabled;

    private String editUrl;

    public AccountDTO(Account account){
        this.id = account.getId();
        this.email = account.getEmail();
        this.companyId =account.getCompany().getId();
        this.companyName = account.getCompany().getName();
        this.userName = account.getUserName();
        this.rank = account.getRank();
        this.phoneNo = account.getPhoneNo();
        this.mobileNo = account.getMobileNo();
        this.address = account.getAddress();
        this.photoPath = account.getPhotoPath();
        this.enabled = account.getEnabled();
        this.editUrl = "/account/view/" + this.id;
    }

    public AccountDTO(){}
}