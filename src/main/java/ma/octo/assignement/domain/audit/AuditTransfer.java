package ma.octo.assignement.domain.audit;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "AUDIT_TRANSFER")
public class AuditTransfer extends Audit {
}
