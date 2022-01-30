package com.devo.webproj.controller;

import com.devo.webproj.model.dto.AccountDTO;
import com.devo.webproj.model.vo.SearchAccountVO;
import com.devo.webproj.service.AccountService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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

    @Secured("ADMIN")
    @GetMapping("/list")
    public String list(SearchAccountVO searchAccountVO, Model model) {
        model.addAttribute("accounts", accountService.findAccountDTOsBySearchCondition(searchAccountVO));
        return "account/list/page";
    }

    @GetMapping("/view/{id}")
    public String view(@PathVariable long id, Model model) {
        model.addAttribute("account", accountService.findById(id));
        return "account/view/page";
    }

    @GetMapping("/add")
    public String add(Model model) {
        model.addAttribute("account", new AccountDTO());
        return "account/add/page";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable long id, Model model) {
        model.addAttribute("account", accountService.findById(id));
        return "account/edit/page";
    }
}