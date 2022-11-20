package ma.octo.assignement.service;


import ma.octo.assignement.domain.AppRole;
import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurRequestDto;
import ma.octo.assignement.dto.utilisateurDto.UtilisateurResponseDto;
import ma.octo.assignement.exceptions.UtilisateurExistantException;
import ma.octo.assignement.mapper.UtilisateurMapper;
import ma.octo.assignement.repository.AppRoleRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UtilisateurServiceImpl implements UtilisateurService {

    private final AppRoleRepository appRoleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UtilisateurRepository utilisateurRepository;

    public UtilisateurServiceImpl(AppRoleRepository appRoleRepository, PasswordEncoder passwordEncoder, UtilisateurRepository utilisateurRepository) {
        this.appRoleRepository = appRoleRepository;
        this.passwordEncoder = passwordEncoder;
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public List<UtilisateurResponseDto> allUtilisateurs() {
        return utilisateurRepository.findAll()
                .stream().map(UtilisateurMapper::map).collect(Collectors.toList());
    }

    @Override
    public UtilisateurResponseDto save(UtilisateurRequestDto utilisateurRequestDto) {
        Utilisateur user = utilisateurRepository.findByUsername(utilisateurRequestDto.getUsername());

        if (user != null) {
            throw new UtilisateurExistantException("Ce nom d'utilisateur deja prie");
        }

        Utilisateur utilisateur = UtilisateurMapper.toUtilisateur(utilisateurRequestDto);

        // encoder le password
        String encodedPassword = passwordEncoder.encode(utilisateurRequestDto.getPassword());

        utilisateur.setPassword(encodedPassword);

        Utilisateur saveUtilisateur = utilisateurRepository.save(utilisateur);
        return UtilisateurMapper.map(saveUtilisateur);

    }


    @Override
    public UtilisateurResponseDto loadUserByUsername(String username) {
        return UtilisateurMapper.map(utilisateurRepository.findByUsername(username));
    }

    @Override
    public AppRole saveRole(AppRole appRole) {
        return appRoleRepository.save(appRole);
    }

    @Override
    public void addRoleToUser(String roleName, String username) {
        Utilisateur utilisateur = utilisateurRepository.findByUsername(username);
        AppRole appRole = appRoleRepository.findByRoleName(roleName);

        utilisateur.getRoles().add(appRole);
    }

}
