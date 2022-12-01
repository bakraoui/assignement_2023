package ma.octo.assignement;

import ma.octo.assignement.domain.AppRole;
import ma.octo.assignement.domain.util.RoleType;
import ma.octo.assignement.dto.utilisateurdto.UtilisateurRequestDto;
import ma.octo.assignement.service.interfaces.UtilisateurService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class BankApplication {


	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner start(UtilisateurService utilisateurService) {
		return args -> {

			utilisateurService.saveRole(new AppRole(null, RoleType.ADMIN.getRole()));
			utilisateurService.saveRole(new AppRole(null, RoleType.USER.getRole()));

			UtilisateurRequestDto admin = UtilisateurRequestDto.builder()
					.firstname("firstname1")
					.lastname("lastname1")
					.gender("FEMALE")
					.username("admin")
					.password("1234")
					.build();
			utilisateurService.save(admin);

			UtilisateurRequestDto utilisateur = UtilisateurRequestDto.builder()
					.firstname("firstname2")
					.lastname("lastname 2")
					.gender("MALE")
					.username("user")
					.password("1234")
					.build();
			utilisateurService.save(utilisateur);

			utilisateurService.addRoleToUser(RoleType.ADMIN.getRole(), "admin" );
			utilisateurService.addRoleToUser(RoleType.USER.getRole(), "admin" );

			utilisateurService.addRoleToUser(RoleType.USER.getRole(), "user" );
		};
	}

}
