package ma.octo.assignement.service;

import ma.octo.assignement.domain.audit.Audit;
import ma.octo.assignement.exceptions.AuditNonValideException;
import ma.octo.assignement.repository.AuditRepository;
import ma.octo.assignement.service.interfaces.AuditService;

import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class AuditServiceImpl implements AuditService {

    private final AuditRepository auditRepository;

    public AuditServiceImpl( AuditRepository auditRepository) {
        this.auditRepository = auditRepository;
    }

    @Override
    public void createAudit(Audit audit) {
        String message = audit.getMessage();

        if (message == null || message.equals(""))
            throw new AuditNonValideException("Ajouter une description a l'audit");

        auditRepository.save(audit);
    }


}
