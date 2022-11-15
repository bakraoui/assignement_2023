package ma.octo.assignement.service;


import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.UtilisateurDto;
import ma.octo.assignement.exceptions.UtilisateurExistantException;
import ma.octo.assignement.mapper.UtilisateurMapper;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {


    private UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<Utilisateur> allUtilisateurs() {
        return utilisateurRepository.findAll();
    }

    @Override
    public Utilisateur save(UtilisateurDto utilisateurDto) {
        Utilisateur user = utilisateurRepository.findByUsername(utilisateurDto.getUsername());

        if (user != null) {
            throw new UtilisateurExistantException("Ce nom d'utilisateur deja prie");
        }

        Utilisateur utilisateur = UtilisateurMapper.toUtilisateur(utilisateurDto);
        return utilisateurRepository.save(utilisateur);

    }
}
