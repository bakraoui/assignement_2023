package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.dto.comptedto.CompteRequestDto;
import ma.octo.assignement.dto.comptedto.CompteResponseDto;

import java.util.List;

public interface CompteService {

    CompteResponseDto saveCompte(CompteRequestDto compteRequestDto);
    CompteResponseDto getCompteByNrCompte(String nrCompte);
    List<CompteResponseDto> allComptes();
}
