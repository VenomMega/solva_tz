package kz.alibek.solva.controller;

import kz.alibek.solva.model.dto.AccountDto;
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
    public ResponseEntity createAccount(@RequestBody AccountDto account) {
        accountService.createAccount(account);
        return ResponseEntity.ok("Account was created");
    }

    @GetMapping("/")
    public ResponseEntity getAll() {
        return ResponseEntity.ok(accountService.accountList());
    }

    @PostMapping("/transfer")
    @ResponseBody
    public ResponseEntity makeTransfer(@RequestParam(name = "fromAccount") String account1,
                                       @RequestParam(name = "toAccount") String account2,
                                       @RequestParam(name = "sum") float sum,
                                       @RequestParam(name = "currency") String currency) {
        accountTransactionService.transferMoney(account1, account2, sum, currency);
        return ResponseEntity.ok("Transfer good");
    }

    @PostMapping("/limit")
    @ResponseBody
    public ResponseEntity getAllLimitTransactions(@RequestParam(name = "accountNumber") String accountNumber) {
        return ResponseEntity.ok(accountTransactionService.showAllTransactions(accountNumber));
    }

    @PutMapping("/changeLimit/{accountId}")
    @ResponseBody
    public ResponseEntity addBook(@PathVariable(name = "accountId") int id, @RequestParam(name = "newLimit") int newLimit) {
        return ResponseEntity.ok(accountService.changeLimit(Long.valueOf(id), newLimit));
    }
}
