package com.example.bankinkapp.mapper;

import com.example.bankinkapp.dto.AccountDto;
import com.example.bankinkapp.dto.ToAccountDto;
import com.example.bankinkapp.models.Account;
import com.example.bankinkapp.models.ToAnotherAccount;
import org.springframework.stereotype.Service;

@Service
public class AccountMapper {

    public static Account mapToAccount(AccountDto dto){
        /*account.setAccountHolderName(dto.accountHolderName());
        account.setBalance(dto.balance());*/
        return new Account(
                dto.Id(),
                dto.accountHolderName(),
                dto.balance()
        );
    }

    public static AccountDto accountDto(Account account){
        return new AccountDto(account.getId(), account.getAccountHolderName(),
                account.getBalance());
    }

    public static ToAnotherAccount toAnotherAccount (ToAccountDto toAccountDto){
        return new ToAnotherAccount(toAccountDto.id(), toAccountDto.cardHolder(),toAccountDto.balance());
    }

    public static ToAccountDto toAccountDto (ToAnotherAccount toAnotherAccount){
        return new ToAccountDto(toAnotherAccount.getId(),toAnotherAccount.getCardHolder(),toAnotherAccount.getBalance());
    }


}
