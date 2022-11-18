package ma.octo.assignement.security.services;

import ma.octo.assignement.security.entity.AppRole;
import ma.octo.assignement.security.entity.AppUser;

import java.util.List;

public interface AppUserService {

    AppUser saveUser(AppUser appUser);
    AppUser loadUserByUsername(String username);
    AppRole saveRole(AppRole appRole);
    void addRoleToUser(String roleName, String username);

    List<AppUser> findAllUsers();
}
