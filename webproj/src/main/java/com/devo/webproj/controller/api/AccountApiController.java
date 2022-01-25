package com.devo.webproj.controller.api;

import com.devo.webproj.model.dto.AccountDTO;
import com.devo.webproj.model.entity.Account;
import com.devo.webproj.model.vo.AccountVO;
import com.devo.webproj.model.vo.SearchAccountVO;
import com.devo.webproj.repository.AccountRepository;
import com.devo.webproj.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accountApi")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;
    private final AccountService accountService;

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

    @GetMapping("/accounts")
    public List<AccountDTO> accountList(SearchAccountVO searchAccountVO) {
        return accountService.findAccountDTOsBySearchCondition(searchAccountVO);
    }

    @PostMapping("/account")
    public AccountDTO postAccount(@RequestBody AccountVO accountVO) {
        return accountService.postAccount(accountVO);
    }
}
