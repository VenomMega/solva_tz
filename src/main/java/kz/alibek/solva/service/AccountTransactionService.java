package kz.alibek.solva.service;


import kz.alibek.solva.model.entity.Account;
import kz.alibek.solva.model.entity.AccountTransaction;
import kz.alibek.solva.model.entity.CurrencyRate;
import kz.alibek.solva.repository.AccountRepository;
import kz.alibek.solva.repository.AccountTransactionRepository;
import kz.alibek.solva.repository.CurrencyRateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountTransactionService {
    private final AccountTransactionRepository accountTransactionRepository;
    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final CurrencyRateRepository currencyRateRepository;

    public void transferMoney(String firstAccount, String secondAccount, float sum, String currency) {
        Account account1 = accountRepository.findFirstByAccountNumber(firstAccount);
        Account account2 = accountRepository.findFirstByAccountNumber(secondAccount);
        AccountTransaction accountTransaction = AccountTransaction.builder()
                .accountTo(firstAccount)
                .accountFrom(secondAccount)
                .sum(sum)
                .currency(currency)
                .build();
        if (currency.equals("KZT")) {
            account1.setBalanceKzt(account1.getBalanceKzt() - sum);
            account2.setBalanceKzt(account2.getBalanceKzt() + sum);
            CurrencyRate currencyRateForUsd = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("KZT", "USD");
            float limitExceeded = accountService.multipleByExchangeRate(currencyRateForUsd.getExchangeRate(), sum);
            accountTransaction.setLimitExceeded(isLimit(account1.getLimitUsd(), limitExceeded));
            float balanceUsd = accountService.multipleByExchangeRate(currencyRateForUsd.getExchangeRate(), account1.getBalanceKzt());
            account1.setBalanceUsd(balanceUsd);
            float balanceUsd2 = accountService.multipleByExchangeRate(currencyRateForUsd.getExchangeRate(), account2.getBalanceKzt());
            account2.setBalanceUsd(balanceUsd2);

            CurrencyRate currencyRateForRub = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("KZT", "RUB");
            float balanceRub = accountService.multipleByExchangeRate(currencyRateForRub.getExchangeRate(), account1.getBalanceKzt());
            account1.setBalanceRub(balanceRub);
            float balanceRub2 = accountService.multipleByExchangeRate(currencyRateForRub.getExchangeRate(), account2.getBalanceKzt());
            account2.setBalanceRub(balanceRub2);

        } else if (currency.equals("USD")) {
            accountTransaction.setLimitExceeded(isLimit(account1.getLimitUsd(), sum));

            account1.setBalanceUsd(account1.getBalanceUsd() - sum);
            account2.setBalanceUsd(account2.getBalanceUsd() + sum);

            CurrencyRate currencyRateForKzt = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("USD", "KZT");

            float balanceKzt = accountService.multipleByExchangeRate(currencyRateForKzt.getExchangeRate(), account1.getBalanceUsd());
            account1.setBalanceKzt(balanceKzt);
            float balanceKzt2 = accountService.multipleByExchangeRate(currencyRateForKzt.getExchangeRate(), account2.getBalanceUsd());
            account2.setBalanceKzt(balanceKzt2);

            CurrencyRate currencyRateForRub = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("USD", "RUB");
            float balanceRub = accountService.multipleByExchangeRate(currencyRateForRub.getExchangeRate(), account1.getBalanceUsd());
            account1.setBalanceRub(balanceRub);
            float balanceRub2 = accountService.multipleByExchangeRate(currencyRateForRub.getExchangeRate(), account2.getBalanceUsd());
            account2.setBalanceRub(balanceRub2);

        } else {
            account1.setBalanceRub(account1.getBalanceRub() - sum);
            account2.setBalanceRub(account2.getBalanceRub() + sum);


            CurrencyRate currencyRateForKzt = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("RUB", "KZT");
            float balanceKzt = accountService.multipleByExchangeRate(currencyRateForKzt.getExchangeRate(), account1.getBalanceKzt());
            account1.setBalanceKzt(balanceKzt);
            float balanceKzt2 = accountService.multipleByExchangeRate(currencyRateForKzt.getExchangeRate(), account2.getBalanceKzt());
            account2.setBalanceKzt(balanceKzt2);

            CurrencyRate currencyRateForUsd = currencyRateRepository.findFirstByFromCurrencyAndToCurrency("RUB", "USD");
            float limitExceeded = accountService.multipleByExchangeRate(currencyRateForUsd.getExchangeRate(), sum);
            accountTransaction.setLimitExceeded(isLimit(account1.getLimitUsd(), limitExceeded));
            float balanceRub = accountService.multipleByExchangeRate(currencyRateForUsd.getExchangeRate(), account1.getBalanceRub());
            account1.setBalanceUsd(balanceRub);
            float balanceRub2 = accountService.multipleByExchangeRate(currencyRateForUsd.getExchangeRate(), account2.getBalanceRub());
            account2.setBalanceUsd(balanceRub2);
        }
        accountTransactionRepository.save(accountTransaction);
    }

    public boolean isLimit(float limit, float sum){
        if (limit > sum){
            return false;
        } else return true;
    }


    public List<AccountTransaction> showAllTransactions(String accountNumber){
        return accountTransactionRepository.getAllByAccountFromAndLimitExceeded(accountNumber, true);
    }
}
