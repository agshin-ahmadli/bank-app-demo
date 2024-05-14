package com.example.bankinkapp.repository;

import com.example.bankinkapp.dto.AccountDto;
import com.example.bankinkapp.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account,Long> ,AccountCustomRepo,
        JpaSpecificationExecutor<Account> {

/*    @Query(nativeQuery = true, value = "select * FROM Account where balance >= :balance")
    List<AccountDto> findAccounts(@Param("balance") BigDecimal balance);*/

    @Query(value = "SELECT a FROM Account a WHERE a.balance >= :balance")
    List<Account> findAccounts(@Param("balance") Integer balance);


    @Query("SELECT a FROM Account a WHERE a.accountHolderName = :name")
    List<AccountDto> findAccountByAccountHolderName(@Param("name") String name);

    @Query(value = "SELECT u FROM Account u where u.accountHolderName=?1 and u.balance=?2")
    List<AccountDto> findAccountByAccountHolderNameAndBalance(@Param ("name")String name, @Param ("balance")Integer balance);



   /* @Query("SELECT new com.example.bankink.dto.AccountDto(a.id, a.accountHolderName, a.balance) FROM Account a WHERE a.accountHolderName = :name")
    List<AccountDto> findAccountByAccountHolderName(@Param("name") String name);*//*
*/

}
