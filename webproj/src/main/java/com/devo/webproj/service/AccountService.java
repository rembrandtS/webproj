package com.devo.webproj.service;

import com.devo.webproj.model.dto.AccountDTO;
import com.devo.webproj.model.entity.Account;
import com.devo.webproj.model.vo.AccountVO;
import com.devo.webproj.model.vo.SearchAccountVO;
import com.devo.webproj.repository.AccountRepository;
import com.devo.webproj.repository.dsl.AccountDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService extends AbstractService  {
    private final AccountRepository accountRepository;
    private final AccountDslRepository accountDslRepository;

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "accountList", allEntries=true),
    })
    public String deleteAccount(long id) {
        Account account = accountRepository
                .findById(id)
                .orElseGet(Account::new);

        if(account.getId() == 0)  return message.getMessage("system_accountService_deleteAccount_target.not.exist");

        accountRepository.delete(account);
        return message.getMessage("system_accountService_deleteAccount_deleted.target.and.go.to.list");
    }

    @Transactional
    public void setUserInfo(String email, String sessionId) {
        accountRepository
                .findByEmail(email)
                .map(loginAccount -> userInfo.setUserInfo(loginAccount, sessionId)
                ).orElse(userInfo);
    }

    @Cacheable(value = "accountList")
    public List<AccountDTO> findAccountDTOsBySearchCondition(SearchAccountVO searchAccountVO) {
        return accountDslRepository.findAccountDTOsBySearchCondition(searchAccountVO);
    }

    @Transactional
    public boolean setPassword(String email, String encodedPassword)
    {
        return accountRepository
                .findByEmail(email)
                .map(savedAccount -> {
                    savedAccount.setPassword(encodedPassword);
                    return true;
                })
                .orElse(false);
    }

    public AccountDTO findById(long id){
        return accountRepository
                .findById(id)
                .map(AccountDTO::new)
                .orElseGet(AccountDTO::new);
    }

    @Transactional
    @Caching(evict = {
            @CacheEvict(value = "accountList", allEntries=true),
    })
    public AccountDTO saveAccount(AccountVO accountVO){
        return new AccountDTO(
                accountRepository
                .findById(accountVO.getId())
                .map(savedAccount -> savedAccount.updateAccount(accountVO))
                .orElseGet(() -> accountRepository.save(new Account(accountVO)))
        );
    }

}
