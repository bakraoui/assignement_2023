package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.CompteDto;
import ma.octo.assignement.exceptions.CompteExistantException;
import ma.octo.assignement.exceptions.CompteNonExistantException;
import ma.octo.assignement.exceptions.CompteValidationException;
import ma.octo.assignement.exceptions.UtilisateurNonExistantException;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.interfaces.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static ma.octo.assignement.service.validators.CompteValidator.*;
import static ma.octo.assignement.service.validators.CompteValidator.ValidationResult.SUCCES;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    private final UtilisateurRepository utilisateurRepository;

    public CompteServiceImpl(CompteRepository compteRepository, UtilisateurRepository utilisateurRepository) {
        this.compteRepository = compteRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public Compte save(CompteDto compteDto) {

        Utilisateur utilisateur = utilisateurRepository
                .findByUsername(compteDto.getUtilisateurUsername());

        if (utilisateur == null) {
            throw new UtilisateurNonExistantException(
                    "Aucun utilisateur n'est existant avec un identifiant "
                            + compteDto.getUtilisateurUsername() );
        }

        ValidationResult result = isNbCompteValid()
                .and(isRibValid())
                .and(isSoldValid())
                .apply(compteDto);

        if (!result.equals(SUCCES))
            throw new CompteValidationException(result.getMessage());


        Compte compteExistant = this.getCompte(compteDto.getNrCompte());

        if (compteExistant != null)
            throw new CompteExistantException("Ce numero de compte deja prie.");

        Compte compte = CompteMapper.toCompte(compteDto, utilisateur);

        return compteRepository.save(compte);
    }

    @Override
    public Compte getCompte(String nrCompte) {
        return compteRepository.findByNrCompte(nrCompte);
    }

    @Override
    public List<Compte> allComptes() {
        return compteRepository.findAll();
    }
}
