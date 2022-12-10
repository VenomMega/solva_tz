package kz.alibek.solva.repository;

import kz.alibek.solva.model.entity.CurrencyRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CurrencyRateRepository extends JpaRepository<CurrencyRateEntity, Long> {
    CurrencyRateEntity findFirstByFromCurrencyAndToCurrency(String from, String to);
}
