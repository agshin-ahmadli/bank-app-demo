package com.example.bankinkapp.service.impl;

import com.example.bankinkapp.dto.AccountDto;
import com.example.bankinkapp.dto.ToAccountDto;
import com.example.bankinkapp.handler.ResourceNotFoundException;
import com.example.bankinkapp.mapper.AccountMapper;
import com.example.bankinkapp.models.Account;
import com.example.bankinkapp.models.ToAnotherAccount;
import com.example.bankinkapp.repository.AccountRepository;
import com.example.bankinkapp.repository.AnotherAccRepo;
import com.example.bankinkapp.service.AccountServices;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class AccountServiceImplementation implements AccountServices {
    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    private final AnotherAccRepo anotherAccRepo;

    public AccountServiceImplementation(AccountRepository accountRepository, AccountMapper accountMapper, AnotherAccRepo anotherAccRepo) {
        this.accountRepository = accountRepository;
        this.accountMapper = accountMapper;
        this.anotherAccRepo = anotherAccRepo;
    }

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        Account account = AccountMapper.mapToAccount(accountDto);
        Account dto = accountRepository.save(account);
        AccountDto accountDto1 = AccountMapper.accountDto(dto);
        return accountDto1;
    }

    @Override
    public ToAccountDto createToAccount(ToAccountDto toAccountDto) {
        ToAnotherAccount toAnotherAccount = AccountMapper.toAnotherAccount(toAccountDto);
        ToAnotherAccount saved = anotherAccRepo.save(toAnotherAccount);
        return AccountMapper.toAccountDto(saved);
    }

    @Override
    public AccountDto getAccountById(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("account not found ", 599));
        return AccountMapper.accountDto(account);
    }

    @Override
    public AccountDto deposit(Long id, Integer amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("account does not exist ", 600));
        Integer total = (account.getBalance() + amount);
        account.setBalance(total);
        accountRepository.save(account);
        return AccountMapper.accountDto(account);
    }

    @Override
    public AccountDto withdraw(Long id, Integer amount) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("account does not exist", 601));
        if (account.getBalance() < 0) {
            throw new RuntimeException("insufficient balance");
        }

        Integer changedBalance = (account.getBalance() - amount);
        account.setBalance(changedBalance);
        Account savedAccount = accountRepository.save(account);
        return AccountMapper.accountDto(savedAccount);
    }

    @Override
    public List<AccountDto> getAllAccounts() {
        return accountRepository.findAll()
                .stream().map(AccountMapper::accountDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAccountById(Long Id) {
        accountRepository.deleteById(Id);
    }

    @Override
    public List<AccountDto> searchByBalance(Integer balance) { // ????????
        return accountRepository.findAccounts(balance).stream()
                .map(AccountMapper::accountDto).collect(Collectors.toList());
    }

    @Override
    @Transactional(rollbackFor = ResourceNotFoundException.class)
    public void transferMoney(Long fromAccountId, Long toAccountId, Integer amount) {
        try {
            Account account = accountRepository.findById(fromAccountId)
                    .orElseThrow(() -> new ResourceNotFoundException("user not found", 603));
            ToAnotherAccount toAnotherAccount = anotherAccRepo.findById(toAccountId)
                    .orElseThrow(() -> new ResourceNotFoundException("user not found", 604));

            account.setBalance(account.getBalance() - amount);
            accountRepository.save(account);

            /*if (toAnotherAccount.getId().equals((BigDecimal)(-1))) {
                throw new ResourceNotFoundException("Error in processing recipient account", 605);
            }*/
            toAnotherAccount.setBalance(toAnotherAccount.getBalance() + amount);
            anotherAccRepo.save(toAnotherAccount);
        } catch (Exception exception) {
            log.error("Error occurred while transferring money {}, {}", exception, 606);

            throw exception;
        }

    }

    @Override
    public List<AccountDto> findAllSortedByCardHolder(Sort sort) {
        return accountRepository.findAll(sort).stream()
                .map(AccountMapper::accountDto).collect(Collectors.toList());
    }

    @Override
    public List<AccountDto> findUserByNameAndBalance(String name, Integer balance) {
        return accountRepository.findAccountByAccountHolderNameAndBalance(name, balance);
    }

    @Override
    public List<AccountDto> findAccountHolderNameAndBalance(String name, Integer balance) {
        return accountRepository.findByAccountHolderNameAndBalance(name, balance).stream()
                .map(account -> AccountMapper.accountDto(account)).collect(Collectors.toList());
    }

    @Override
    public List<Account> findAllAccounts(Specification<Account> specification) {
        return accountRepository.findAll(specification);

    }

    @Override
    public Page<AccountDto> findPaginated(int pageNo, int pageSize, String sortBy, String sortDirection) {
        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo - 1, pageSize, sort);
        return accountRepository.findAll(pageable)
                .map(AccountMapper::accountDto);
    }



}

