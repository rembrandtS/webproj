package com.devo.webproj.repository.dsl;

import com.devo.webproj.model.dto.AccountDTO;
import com.devo.webproj.model.vo.SearchAccountVO;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.devo.webproj.model.entity.QAccount.account;
import static com.devo.webproj.model.entity.QCompany.company;
import static com.devo.webproj.model.entity.QRole.role;

@Repository
@RequiredArgsConstructor
public class AccountDslRepository {

    private final JPAQueryFactory queryFactory;

    public List<AccountDTO> findAccountDTOsBySearchCondition(SearchAccountVO searchAccountVO) {
        return queryFactory
                .select(Projections.constructor(AccountDTO.class, account))
                .distinct()
                .from(account)
                .join(account.company, company)
                .where(
                    getWhereCompanyNameLike(searchAccountVO.getCompanyName()),
                    getWhereUserNameLike(searchAccountVO.getUserName())
                ).orderBy(
                    account.id.desc()
                ).fetch();
    }

    private BooleanExpression getWhereCompanyNameLike(String companyName) {
        if (!StringUtils.isEmpty(companyName))  return account.company.name.containsIgnoreCase(companyName);
        return null;
    }

    private BooleanExpression getWhereUserNameLike(String userName) {
        if (!StringUtils.isEmpty(userName))  return account.userName.containsIgnoreCase(userName);
        return null;
    }
}
