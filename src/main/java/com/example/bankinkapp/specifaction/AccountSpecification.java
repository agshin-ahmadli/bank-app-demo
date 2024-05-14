package com.example.bankinkapp.specifaction;

import com.example.bankinkapp.dto.AccountDto;
import com.example.bankinkapp.models.Account;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

@Component
public class AccountSpecification {

    public static Specification<Account> hasAccountHolderName(String name){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("accountHolderName"),name);
        };
    }

    public static Specification<Account> hasBalance(Integer balance){
        return (root, query, criteriaBuilder) -> {
            return criteriaBuilder.equal(root.get("balance"), balance); //  % + acc + % like method??
        };
    }
}
