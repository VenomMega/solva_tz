package kz.alibek.solva.repository;

import kz.alibek.solva.model.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountTransactionRepository extends JpaRepository<TransactionEntity, Long> {
    List<TransactionEntity> getAllByAccountFromAndLimitExceeded(String accountNumber, boolean isLimit);
}
