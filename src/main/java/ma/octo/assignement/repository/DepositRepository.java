package ma.octo.assignement.repository;

import ma.octo.assignement.domain.operation.MoneyDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepositRepository extends JpaRepository<MoneyDeposit, Long> {
}
