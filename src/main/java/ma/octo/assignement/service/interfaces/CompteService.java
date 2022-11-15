package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.Compte;
import ma.octo.assignement.dto.CompteDto;

import java.util.List;

public interface CompteService {

    Compte save(CompteDto compteDto);
    Compte getCompte(String nrCompte);
    List<Compte> allComptes();
}
