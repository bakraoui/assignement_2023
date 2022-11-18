package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.audit.Audit;

public interface AuditService {

    /*
     void auditTransfer(String message);
     void auditDeposit(String message);
    */

    void createAudit(Audit audit);
}
