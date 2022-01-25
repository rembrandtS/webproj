package com.devo.webproj.controller;

import com.devo.webproj.model.dto.AccountDTO;
import com.devo.webproj.model.vo.SearchAccountVO;
import com.devo.webproj.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/login")
    public String login() {
        return "account/login";
    }

    @GetMapping("/accounts")
    public String userList(SearchAccountVO searchAccountVO, Model model) {
        model.addAttribute("accounts", accountService.findAccountDTOsBySearchCondition(searchAccountVO));
        return "account/list/page";
    }

    @GetMapping("/account")
    public String item(Model model) {
        model.addAttribute("account", new AccountDTO());
        return "account/item/page";
    }
}