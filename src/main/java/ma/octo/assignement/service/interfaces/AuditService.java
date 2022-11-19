package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.audit.Audit;

public interface AuditService {
    Audit createAudit(Audit audit);
}
