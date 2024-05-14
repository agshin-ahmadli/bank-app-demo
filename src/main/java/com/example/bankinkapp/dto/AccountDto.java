package com.example.bankinkapp.dto;

import java.math.BigDecimal;

public record AccountDto(
        Long Id,
        String accountHolderName,
        Integer balance
) {

}
