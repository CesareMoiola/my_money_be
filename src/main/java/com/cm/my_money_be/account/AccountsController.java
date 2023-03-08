package com.cm.my_money_be.account;

import com.cm.my_money_be.account.transaction.TransactionDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/account")
@CrossOrigin(origins = "*")
public class AccountsController {

    @Autowired
    AccountServiceImpl accountService;

    /**
     * Get all accounts owned by the user with the balance dating back to this date
     * @param userId User id
     * @param date Date to which the Balance of the accounts should refer
     * @return User's accounts
     */
    @GetMapping("/{userId}/{date}")
    public ResponseEntity<List<AccountDto>> getAccounts(@PathVariable("userId") long userId, @PathVariable("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date){
        List<AccountDto> accounts = accountService.getUserAccounts(userId,date);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    /**
     * Update account
     * @param accountDto Account updated
     * @return Http response
     */
    @PutMapping("/update")
    public ResponseEntity<String> updateAccount(@RequestBody AccountDto accountDto){
        accountService.updateAccount(accountDto);
        return ResponseEntity.ok("Account updated");
    }

    /**
     * Create new account
     * @param userId User id
     * @param accountDto New account
     * @return Http response
     */
    @PostMapping("/new{userId}")
    public ResponseEntity<String> createNewAccount(@PathVariable("userId") long userId, @RequestBody AccountDto accountDto){
        accountService.createNewAccount(userId, accountDto);
        return ResponseEntity.ok("Account created");
    }

    /**
     * Delete an account
     * @param accountId Account id
     * @return Http response
     */
    @DeleteMapping("/{accountId}")
    public ResponseEntity<String> deleteAccount(@PathVariable("accountId") long accountId){
        accountService.deleteAccount(accountId);
        return ResponseEntity.ok("Account deleted");
    }

    /**
     * Save a transaction
     * @param transactionDto transaction data
     * @return Http response
     */
    @PostMapping("/transaction")
    public ResponseEntity<String> saveTransaction(@RequestBody TransactionDto transactionDto){
        accountService.saveTransaction(transactionDto);
        return ResponseEntity.ok("Transaction saved");
    }
}
