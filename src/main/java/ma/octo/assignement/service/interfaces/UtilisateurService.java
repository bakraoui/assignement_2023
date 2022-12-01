package ma.octo.assignement.service.interfaces;

import ma.octo.assignement.domain.AppRole;
import ma.octo.assignement.dto.utilisateurdto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurdto.UtilisateurResponseDto;

import java.util.List;

public interface UtilisateurService {


    UtilisateurResponseDto save(UtilisateurRequestDto utilisateurRequestDto);
    List<UtilisateurResponseDto> allUtilisateurs();

    UtilisateurResponseDto loadUserByUsername(String username);
    AppRole saveRole(AppRole appRole);
    void addRoleToUser(String roleName, String username);



}
