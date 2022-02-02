package com.devo.webproj.service;

import com.devo.webproj.component.Message;
import com.devo.webproj.component.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AbstractService {
    @Resource protected UserInfo userInfo;
    @Autowired protected Message message;
}