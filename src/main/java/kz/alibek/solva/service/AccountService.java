package kz.alibek.solva.service;

import kz.alibek.solva.model.dto.AccountDto;
import kz.alibek.solva.model.entity.Account;
import kz.alibek.solva.model.entity.CurrencyRate;
import kz.alibek.solva.repository.AccountRepository;
import kz.alibek.solva.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final CurrencyRateRepository currencyRateRepository;

    public void createAccount(AccountDto accountDto){
        Account account = Account.builder()
                .accountNumber(accountDto.getAccountNumber())
                .balanceUsd(accountDto.getBalanceUsd())
                .limitUsd(accountDto.getLimitUsd())
                .build();
        CurrencyRate currencyRateForKzt = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("USD", "KZT");
        float balanceKzt = multipleByExchangeRate(currencyRateForKzt.getExchangeRate(), account.getBalanceUsd());
        account.setBalanceKzt(balanceKzt);

        CurrencyRate currencyRateForRub = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("USD", "RUB");
        float balanceRub = multipleByExchangeRate(currencyRateForRub.getExchangeRate(), account.getBalanceUsd());
        account.setBalanceRub(balanceRub);
        accountRepository.save(account);
    }


    public List<Account> accountList(){
        List<Account> accountList = accountRepository.findAll();
        return accountList;
    }


    public float multipleByExchangeRate(String currencyBalance, float exchangeRate){
        float balance = Float.parseFloat(currencyBalance);
        return balance * exchangeRate;
    }

    public Account changeLimit(Long id, int newLimit){
        Optional<Account> account = accountRepository.findById(id);
        account.get().setLimitUsd(newLimit);
        return accountRepository.save(account.get());
    }
}
