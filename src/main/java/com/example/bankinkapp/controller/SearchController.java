package com.example.bankinkapp.controller;

import com.example.bankinkapp.dto.AccountDto;
import com.example.bankinkapp.models.Account;
import com.example.bankinkapp.repository.AccountRepository;
import com.example.bankinkapp.service.AccountServices;
import com.example.bankinkapp.specifaction.AccountSpecification;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    private final AccountServices accountServices;

    private final AccountRepository accountRepository;



    public SearchController(AccountServices accountServices, AccountRepository accountRepository) {
        this.accountServices = accountServices;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/accounts/balance")
    public List<AccountDto> getAccount(@RequestParam Integer request){
        return accountServices.searchByBalance(request);
    }
    @GetMapping("/search")
    public ResponseEntity<List<AccountDto>> searchByName(){
        Sort sort = Sort.by("LENGTH(accountCardHolder)");
        return new ResponseEntity<>(accountServices.findAllSortedByCardHolder(sort), HttpStatus.CREATED);
    }

    @GetMapping("/name/{name}/balance/{balance}")
    public ResponseEntity<List<AccountDto>> getAccount(@PathVariable String name,
                                                       @PathVariable Integer balance){
        return new ResponseEntity<>(accountServices.findUserByNameAndBalance(name,balance), HttpStatus.OK);
    }

    @GetMapping("/{name}/name/money")
    public ResponseEntity<List<Account>> searchAccount(@PathVariable String name,
                                                          @RequestParam Integer amount){
      /* // Integer amount = request.get("amount");
        return new ResponseEntity<>(accountServices.findAccountHolderNameAndBalance(name,amount),HttpStatus.OK);*/
        Specification<Account>specification = Specification.where(AccountSpecification.hasAccountHolderName(name)
                .and(AccountSpecification.hasBalance(amount)));
        return new ResponseEntity<>(accountServices.findAllAccounts(specification),HttpStatus.CREATED);
    }
}
