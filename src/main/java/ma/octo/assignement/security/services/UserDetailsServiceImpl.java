package ma.octo.assignement.security.services;

import ma.octo.assignement.domain.Utilisateur;
import ma.octo.assignement.exceptions.UtilisateurNonExistantException;
import ma.octo.assignement.repository.UtilisateurRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UtilisateurRepository utilisateurRepository;

    public UserDetailsServiceImpl(UtilisateurRepository utilisateurRepository) {
        this.utilisateurRepository = utilisateurRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur appUser = utilisateurRepository.findByUsername(username);

        if (appUser == null)
            throw new UtilisateurNonExistantException("Aucun utilisateur n'est trouvee avec username:" + username);

        Collection<GrantedAuthority> authorities = appUser.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoleName()))
                .collect(Collectors.toList());

        return new User(appUser.getUsername(), appUser.getPassword(), authorities);

    }
}
