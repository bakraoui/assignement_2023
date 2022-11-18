package ma.octo.assignement;

import ma.octo.assignement.repository.CompteRepository;
import ma.octo.assignement.repository.UtilisateurRepository;
import ma.octo.assignement.repository.TransferRepository;
import ma.octo.assignement.security.entity.AppRole;
import ma.octo.assignement.security.entity.AppUser;
import ma.octo.assignement.security.services.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@SpringBootApplication
public class NiceBankApplication {


	public static void main(String[] args) {
		SpringApplication.run(NiceBankApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	CommandLineRunner start(AppUserService appUserService) {
		return args -> {

			appUserService.saveRole(new AppRole(null, "ADMIN"));
			appUserService.saveRole(new AppRole(null, "USER"));

			appUserService.saveUser(new AppUser(null, "admin","1234",null));
			appUserService.saveUser(new AppUser(null, "user","1234",null));

			appUserService.addRoleToUser("USER","admin" );
			appUserService.addRoleToUser("ADMIN","admin" );

			appUserService.addRoleToUser("USER","user" );
		};
	}


}
