package com.devo.webproj.component;

import com.devo.webproj.model.entity.Account;
import lombok.Data;
import lombok.ToString;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;

import java.io.Serializable;

@Component
@Scope(value = WebApplicationContext.SCOPE_SESSION, proxyMode = ScopedProxyMode.TARGET_CLASS)
@Data
@ToString
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id = 0;
    private String email;
    private long companyId;
    private String userName;
    private String rank;
    private String phoneNo;
    private String mobileNo;
    private String photoPath;

    private String sessionId;

    public UserInfo setUserInfo(Account loginAccount, String sessionId) {
        this.id = loginAccount.getId();
        this.email = loginAccount.getEmail();
        this.companyId = loginAccount.getCompany().getId();
        this.userName = loginAccount.getUserName();
        this.rank = loginAccount.getRank();
        this.phoneNo = loginAccount.getPhoneNo();
        this.mobileNo = loginAccount.getMobileNo();
        this.photoPath = loginAccount.getPhotoPath();

        this.sessionId = sessionId;

        return this;
    }
}