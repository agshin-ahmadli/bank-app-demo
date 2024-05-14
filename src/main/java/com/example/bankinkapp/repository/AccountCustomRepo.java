package com.example.bankinkapp.repository;

import com.example.bankinkapp.models.Account;

import java.math.BigDecimal;
import java.util.List;

public interface AccountCustomRepo {
    List<Account> findByAccountHolderNameAndBalance(String name, Integer balance);
}
