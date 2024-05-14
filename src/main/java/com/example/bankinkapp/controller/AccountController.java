package com.example.bankinkapp.controller;

import com.example.bankinkapp.dto.AccountDto;
import com.example.bankinkapp.dto.ConsumerType;
import com.example.bankinkapp.dto.ToAccountDto;
import com.example.bankinkapp.service.AccountServices;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/accounts")
public class AccountController {

    private final AccountServices accountServices;

    @PostMapping("/add")
    public ResponseEntity<AccountDto> addAccount(@RequestBody AccountDto accountDto){
        AccountDto dto = accountServices.createAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PostMapping("/add/another")
    public ResponseEntity<ToAccountDto> addAccount(@RequestBody ToAccountDto accountDto){
        ToAccountDto dto = accountServices.createToAccount(accountDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AccountDto>getAccount(@PathVariable Long id){
        return new ResponseEntity<>(accountServices.getAccountById(id),HttpStatus.OK);
    }

    @PutMapping("/{id}/deposit")
    public ResponseEntity<AccountDto>deposit(
                                                @PathVariable Long id,
                                                @RequestBody  Map<String, Integer> request){

      AccountDto accountDto =  accountServices.deposit(id, request.get("amount"));
          return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{id}/withdraw")
    public ResponseEntity<AccountDto> withdraw(@PathVariable Long id,
                                               @RequestBody Map<String, Integer> request){
        Integer amount =request.get("amount");
        AccountDto accountDto = accountServices.withdraw(id,amount);
        return ResponseEntity.ok(accountDto);
    }

    @GetMapping("/all")
    public ResponseEntity<List<AccountDto>> getAll(){
        return new ResponseEntity<>(accountServices.getAllAccounts(),HttpStatus.OK);
    }

    @DeleteMapping("/{id}/delete")
    public void deleteAccount(@PathVariable Long id){
        accountServices.deleteAccountById(id);
    }

    @GetMapping("/paginated")
    public ResponseEntity<Page<AccountDto>> getPaginatedAccounts(
            @RequestParam(defaultValue = "0") int pageNo,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "ASC") String sortDirection
    ){
        Page<AccountDto>dtoPage = accountServices.findPaginated(pageNo,pageSize,sortBy,sortDirection);
        return ResponseEntity.ok(dtoPage);
    }



}
