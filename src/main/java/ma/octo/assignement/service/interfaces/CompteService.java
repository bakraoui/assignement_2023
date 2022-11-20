package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.dto.compteDto.CompteRequestDto;
import ma.octo.assignement.dto.compteDto.CompteResponseDto;

import java.util.List;

public interface CompteService {

    CompteResponseDto save(CompteRequestDto compteRequestDto);
    CompteResponseDto getCompte(String nrCompte);
    List<CompteResponseDto> allComptes();
}
