package kz.alibek.solva.repository;

import kz.alibek.solva.model.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<AccountEntity, Long> {
    AccountEntity findFirstByAccountNumber(String accountNumber);
}
