package ma.octo.assignement.service.interfaces;

public interface AuditService {
    public void auditTransfer(String message);
    public void auditDeposit(String message);
}
