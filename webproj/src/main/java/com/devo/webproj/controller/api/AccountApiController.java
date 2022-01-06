package com.devo.webproj.controller.api;

import com.devo.webproj.model.entity.Account;
import com.devo.webproj.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accountApi")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/setPassword")
    public String setPassword(String email, String password) {
        accountRepository
            .findByEmail(email)
            .map(savedAccount -> {
                savedAccount.setPassword(passwordEncoder.encode(password));
                return accountRepository.save(savedAccount);
            })
            .orElseGet(Account::new);
        return "success";
    }
}
