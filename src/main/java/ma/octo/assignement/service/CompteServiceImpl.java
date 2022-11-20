package ma.octo.assignement.service;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;
import ma.octo.assignement.exceptions.CompteExistantException;
import ma.octo.assignement.exceptions.CompteValidationException;
import ma.octo.assignement.exceptions.UtilisateurNonExistantException;
import ma.octo.assignement.mapper.CompteMapper;
import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.interfaces.CompteService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import static ma.octo.assignement.service.validators.CompteValidator.*;
import static ma.octo.assignement.service.validators.CompteValidator.ValidationResult.SUCCES;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {

    private final CompteRepository compteRepository;
    
    private final UtilisateurRepository utilisateurRepository;

    public CompteServiceImpl(CompteRepository compteRepository,
                             UtilisateurRepository utilisateurRepository) {
        this.compteRepository = compteRepository;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public CompteResponseDto save(CompteRequestDto compteRequestDto) {

        Utilisateur utilisateur = utilisateurRepository
                .findByUsername(compteRequestDto.getUtilisateurUsername());

        if (utilisateur == null) {
            throw new UtilisateurNonExistantException(
                    "Aucun utilisateur n'est existant avec cet identifiant "
                            + compteRequestDto.getUtilisateurUsername() );
        }

        ValidationResult result = isNbCompteValid()
                .and(isRibValid())
                .and(isSoldValid())
                .apply(compteRequestDto);

        if (!result.equals(SUCCES))
            throw new CompteValidationException(result.getMessage());

        Compte compteExistant = compteRepository.findByNrCompte(compteRequestDto.getNrCompte());

        if (compteExistant != null)
            throw new CompteExistantException("Ce numero de compte deja prie.");

        Compte compte = compteRepository.save(
                CompteMapper.toCompte(compteRequestDto, utilisateur));

        return CompteMapper.map(compte);
    }

    @Override
    public CompteResponseDto getCompte(String nrCompte) {
        return CompteMapper.map(compteRepository.findByNrCompte(nrCompte));
    }

    @Override
    public List<CompteResponseDto> allComptes() {
        return compteRepository.findAll()
                .stream().map(CompteMapper::map)
                .collect(Collectors.toList());
    }
}
