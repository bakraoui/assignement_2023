package ma.octo.assignement.service;

import ma.octo.assignement.domain.audit.Audit;
import ma.octo.assignement.domain.audit.AuditDeposit;
import ma.octo.assignement.domain.audit.AuditTransfer;
import ma.octo.assignement.domain.util.EventType;
import ma.octo.assignement.repository.AuditRepository;
import ma.octo.assignement.service.interfaces.AuditService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    Logger LOGGER = LoggerFactory.getLogger(AuditServiceImpl.class);

    private final AuditRepository auditRepository;

    public AuditServiceImpl( AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void auditTransfer(String message) {

        LOGGER.info("Audit de l'événement {}", EventType.TRANSFER);

        Audit audit = new AuditTransfer();
        audit.setEventType(EventType.TRANSFER);
        audit.setMessage(message);
        auditRepository.save(audit);
    }

    @Override
    public void auditDeposit(String message) {

        LOGGER.info("Audit de l'événement {}", EventType.DEPOSIT);

        Audit audit = new AuditDeposit();
        audit.setEventType(EventType.DEPOSIT);
        audit.setMessage(message);
        auditRepository.save(audit);
    }
}
