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

    @PutMapping("/changeLimit/{accountId}")
    @ResponseBody
    public ResponseEntity addBook(@PathVariable(name = "accountId") int id, @RequestParam(name = "newLimit") int newLimit) {
        return ResponseEntity.ok(accountService.changeLimit(Long.valueOf(id), newLimit));
    }
}
