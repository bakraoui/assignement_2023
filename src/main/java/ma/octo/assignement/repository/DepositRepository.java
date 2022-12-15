package ma.octo.assignement.repository;

import lombok.Data;
import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.operation.MoneyDeposit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface DepositRepository extends JpaRepository<MoneyDeposit, Long> {

    int countByCompteBeneficiaireAndDateExecution(Compte compte, Date date);
}
