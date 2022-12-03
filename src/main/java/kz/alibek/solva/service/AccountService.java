package kz.alibek.solva.service;

import kz.alibek.solva.model.entity.Account;
import kz.alibek.solva.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public void createAccount(Account account){
        LocalDate localDate = LocalDate.now();
        account.setCreatedDate(localDate);
        account.setModifiedDate(localDate);
        accountRepository.save(account);
    }


    public List<Account> accountList(){
        List<Account> accountList = accountRepository.findAll();
        return accountList;
    }
}
