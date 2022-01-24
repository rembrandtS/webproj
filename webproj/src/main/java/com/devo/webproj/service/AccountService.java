package com.devo.webproj.service;

import com.devo.webproj.model.dto.AccountDTO;
import com.devo.webproj.model.vo.SearchAccountVO;
import com.devo.webproj.repository.dsl.AccountDslRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class AccountService {

    private final AccountDslRepository accountDslRepository;

    public List<AccountDTO> findAccountDTOsBySearchCondition(SearchAccountVO searchAccountVO) {
        return accountDslRepository.findAccountDTOsBySearchCondition(searchAccountVO);
    }
}
