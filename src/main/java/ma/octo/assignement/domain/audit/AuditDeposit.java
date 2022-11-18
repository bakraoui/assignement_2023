package ma.octo.assignement.domain.audit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ma.octo.assignement.domain.util.EventType;

import javax.persistence.*;


@Entity
@Data
@Table(name = "AUDIT_DEPOSIT")

public class AuditDeposit extends Audit{}
