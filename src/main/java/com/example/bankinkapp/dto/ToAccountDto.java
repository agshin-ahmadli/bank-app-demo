package com.example.bankinkapp.dto;

import java.math.BigDecimal;

public record ToAccountDto(
        Long id,
        String cardHolder,
        Integer balance
) {
}
