package com.devo.webproj.controller.api;

import com.devo.webproj.model.dto.AccountDTO;
import com.devo.webproj.model.vo.AccountVO;
import com.devo.webproj.model.vo.SearchAccountVO;
import com.devo.webproj.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accountApi")
@RequiredArgsConstructor
public class AccountApiController {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/setPassword")
    public String setPassword(String email, String password) {
        if(accountService.setPassword(email,passwordEncoder.encode(password))) return "success";
        return "fail";
    }

    @GetMapping("/accounts")
    public List<AccountDTO> getAccounts(SearchAccountVO searchAccountVO) {
        return accountService.findAccountDTOsBySearchCondition(searchAccountVO);
    }

    @PostMapping("/account")
    public AccountDTO postAccount(@RequestBody AccountVO accountVO) {
        return accountService.saveAccount(accountVO);
    }

    @PutMapping("/account")
    public AccountDTO putAccount(@RequestBody AccountVO accountVO) {
        return accountService.saveAccount(accountVO);
    }

    @DeleteMapping("/account")
    public String deleteAccount(@RequestParam long id) {
        return accountService.deleteAccount(id);
    }
}
