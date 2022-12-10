package kz.alibek.solva.service;

import kz.alibek.solva.model.dto.AccountDto;
import kz.alibek.solva.model.entity.AccountEntity;
import kz.alibek.solva.model.entity.CurrencyRateEntity;
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

    public void createAccount(AccountDto accountDto) {
        AccountEntity accountEntity = AccountEntity.builder()
                .accountNumber(accountDto.getAccountNumber())
                .balanceUsd(accountDto.getBalanceUsd())
                .limitUsd(accountDto.getLimitUsd())
                .build();
        CurrencyRateEntity currencyRateEntityForKzt = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("USD", "KZT");
        double balanceKzt = multipleByExchangeRate(currencyRateEntityForKzt.getExchangeRate(), accountEntity.getBalanceUsd());
        accountEntity.setBalanceKzt(balanceKzt);

        CurrencyRateEntity currencyRateEntityForRub = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("USD", "RUB");
        double balanceRub = multipleByExchangeRate(currencyRateEntityForRub.getExchangeRate(), accountEntity.getBalanceUsd());
        accountEntity.setBalanceRub(balanceRub);
        accountRepository.save(accountEntity);
    }


    public List<AccountEntity> accountList() {
        List<AccountEntity> accountEntityList = accountRepository.findAll();
        return accountEntityList;
    }


    public double multipleByExchangeRate(String currencyBalance, double exchangeRate) {
        double balance = Double.parseDouble(currencyBalance);
        return balance * exchangeRate;
    }

    public AccountEntity changeLimit(Long id, int newLimit) {
        Optional<AccountEntity> account = accountRepository.findById(id);
        account.get().setLimitUsd(newLimit);
        return accountRepository.save(account.get());
    }
}
