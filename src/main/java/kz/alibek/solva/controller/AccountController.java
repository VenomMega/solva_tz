package kz.alibek.solva.controller;

import kz.alibek.solva.model.dto.AccountDto;
import kz.alibek.solva.model.entity.Account;
import kz.alibek.solva.service.AccountService;
import kz.alibek.solva.service.AccountTransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountService accountService;
    private final AccountTransactionService accountTransactionService;

    @PostMapping("/add")
    @ResponseBody
    public ResponseEntity createAccount(@RequestBody AccountDto account){
        try{
            accountService.createAccount(account);
            return ResponseEntity.ok("Account was created");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/")
    public ResponseEntity getAll(){
        try{
            return ResponseEntity.ok(accountService.accountList());
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity makeTransfer(@RequestParam(name = "fromAccount") String account1,
                                       @RequestParam(name = "toAccount") String account2,
                                       @RequestParam(name = "sum") float sum,
                                       @RequestParam(name = "currency") String currency){
        try{
            accountTransactionService.transferMoney(account1, account2, sum, currency);
            return ResponseEntity.ok("Transfer good");
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/limit")
    @ResponseBody
    public ResponseEntity getAllLimitTransactions(@RequestParam(name = "accountNumber") String accountNumber){
        try{
            return ResponseEntity.ok(accountTransactionService.showAllTransactions(accountNumber));
        } catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/changeLimit/{accountId}")
    @ResponseBody
    public ResponseEntity addBook(@PathVariable(name = "accountId") int id, @RequestParam(name = "newLimit") int newLimit) {
        try {
            return ResponseEntity.ok(accountService.changeLimit(Long.valueOf(id), newLimit));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error");
        }
    }
}
