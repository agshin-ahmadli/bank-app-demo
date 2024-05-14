package com.example.bankinkapp.service;

import com.example.bankinkapp.dto.AccountDto;
import com.example.bankinkapp.dto.ToAccountDto;
import com.example.bankinkapp.models.Account;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

public interface AccountServices {
    AccountDto createAccount (AccountDto accountDto);

    ToAccountDto createToAccount(ToAccountDto toAccountDto);

    AccountDto getAccountById(Long id);

    AccountDto deposit(Long id, Integer amount);

    AccountDto withdraw(Long id, Integer amount);

    List<AccountDto> getAllAccounts();

    void deleteAccountById(Long Id);

    List<AccountDto> searchByBalance(Integer balance);

    void transferMoney (Long fromAccountId, Long toAccountId, Integer amount);

    List<AccountDto> findAllSortedByCardHolder(Sort sort);

    List<AccountDto> findUserByNameAndBalance(String name, Integer balance);

    List<AccountDto> findAccountHolderNameAndBalance(String name, Integer balance);

    List<Account> findAllAccounts(Specification<Account> specification);

    Page<AccountDto>findPaginated(int pageNo, int pageSize, String sortBy, String sortDirection);
}
