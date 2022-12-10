package kz.alibek.solva.service;


import kz.alibek.solva.model.entity.AccountEntity;
import kz.alibek.solva.model.entity.CurrencyRateEntity;
import kz.alibek.solva.model.entity.TransactionEntity;
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

    public void transferMoney(TransactionEntity transaction) {
        double sum = transaction.getSum();
        AccountEntity sender = accountRepository.findFirstByAccountNumber(transaction.getAccountFrom());
        AccountEntity receiver = accountRepository.findFirstByAccountNumber(transaction.getAccountTo());
        if (transaction.getCurrency().equals("KZT")) {
            System.out.println(sender.getBalanceUsd());
            sender.setBalanceKzt(sender.getBalanceKzt() - sum);
            receiver.setBalanceKzt(receiver.getBalanceKzt() + sum);

            sender.setBalanceUsd(checkExchangeRate(sender.getBalanceKzt(), "KZT", "USD"));
            receiver.setBalanceUsd(checkExchangeRate(receiver.getBalanceKzt(), "KZT", "USD"));

            sender.setBalanceRub(checkExchangeRate(sender.getBalanceKzt(), "KZT", "RUB"));
            receiver.setBalanceRub(checkExchangeRate(receiver.getBalanceKzt(), "KZT", "RUB"));

        } else if (transaction.getCurrency().equals("USD")) {
            receiver.setBalanceKzt(checkExchangeRate(receiver.getBalanceUsd(), "USD", "KZT"));
            sender.setBalanceKzt(checkExchangeRate(sender.getBalanceUsd(), "USD", "KZT"));

            receiver.setBalanceRub(checkExchangeRate(receiver.getBalanceKzt(), "USD", "RUB"));
            sender.setBalanceRub(checkExchangeRate(sender.getBalanceKzt(), "USD", "RUB"));

        } else {
            receiver.setBalanceUsd(checkExchangeRate(receiver.getBalanceRub(), "RUB", "USD"));
            sender.setBalanceUsd(checkExchangeRate(sender.getBalanceRub(), "RUB", "USD"));

            receiver.setBalanceKzt(checkExchangeRate(receiver.getBalanceRub(), "RUB", "KZT"));
            sender.setBalanceKzt(checkExchangeRate(sender.getBalanceRub(), "RUB", "KZT"));
        }
        accountTransactionRepository.save(transaction);
    }

    public boolean isLimit(double limit, double sum) {
        if (limit > sum) {
            return false;
        } else return true;
    }


    public List<TransactionEntity> showAllTransactions(String accountNumber) {
        return accountTransactionRepository.getAllByAccountFromAndLimitExceeded(accountNumber, true);
    }

    public double checkExchangeRate(double receiverBalance, String fromCurrency, String toCurrency) {
        CurrencyRateEntity currencyRate = currencyRateRepository.findFirstByFromCurrencyAndToCurrency(fromCurrency, toCurrency);
        return accountService.multipleByExchangeRate(currencyRate.getExchangeRate(), receiverBalance);
    }
}
