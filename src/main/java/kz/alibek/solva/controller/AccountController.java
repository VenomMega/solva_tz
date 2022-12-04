package kz.alibek.solva.controller;

import kz.alibek.solva.model.dto.AccountDto;
import kz.alibek.solva.service.AccountService;
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


}
