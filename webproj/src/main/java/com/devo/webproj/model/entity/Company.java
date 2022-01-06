package com.devo.webproj.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Getter
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    private String name;
    private String roleType;

    private String status;
    private String telNo;
    private boolean isDisplay;

    private long writerId;
    private LocalDateTime writeDate;

    private long lastModifierId;
    private LocalDateTime lastModifyDate;
}
