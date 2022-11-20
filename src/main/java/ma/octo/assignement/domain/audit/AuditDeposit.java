package ma.octo.assignement.domain.audit;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Data
@Table(name = "AUDIT_DEPOSIT")
public class AuditDeposit extends Audit{}
