package com.example.bankinkapp.controller;

import com.example.bankinkapp.service.AccountServices;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class MoneyTransferController {
    private final AccountServices accountServices;

    public MoneyTransferController(AccountServices accountServices) {
        this.accountServices = accountServices;
    }

    @PutMapping("/{fromId}/{toId}/transfer")
    public void transferFunds(@PathVariable Long fromId, @PathVariable Long toId, @RequestParam Integer amount){
        accountServices.transferMoney(fromId, toId, amount);
    }
}

