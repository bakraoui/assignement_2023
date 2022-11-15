package ma.octo.assignement.repository;

import ma.octo.assignement.domain.operation.Operation;
import ma.octo.assignement.domain.operation.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
}
