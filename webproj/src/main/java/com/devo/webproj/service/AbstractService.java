package com.devo.webproj.service;

import com.devo.webproj.component.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AbstractService {
    @Resource protected UserInfo userInfo;
}
