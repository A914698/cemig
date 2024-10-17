package br.atos.cemig;

import br.atos.cemig.Autenticacao.RoleEnum;
import br.atos.cemig.User.UserEntity;
import br.atos.cemig.User.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@SpringBootApplication
public class CemigApplication {

	@Autowired
	private UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(CemigApplication.class, args);
	}

	@Bean
	public CommandLineRunner initializeDatabase() {
		return args -> {
			saveUserIfNotExists("John Doe", "john.doe@example.com", "familyMember1", "12345678901", LocalDate.of(1990, 1, 1), "123456789", "987654321", "Senha123", RoleEnum.USER);
			saveUserIfNotExists("Jane Smith", "jane.smith@example.com", "familyMember2", "23456789012", LocalDate.of(1992, 2, 2), "234567890", "876543210", "Senha456", RoleEnum.ADMIN);
			saveUserIfNotExists("Bob Johnson", "bob.johnson@example.com", "familyMember3", "34567890123", LocalDate.of(1985, 3, 3), "345678901", "765432109", "Senha789", RoleEnum.USER);
		};
	}

	private void saveUserIfNotExists(String name, String email, String memberFamily, String cpf, LocalDate birthDate, String cellphone, String telephone, String password, RoleEnum role) {
		Optional<UserEntity> existingUser = userRepository.findByEmail(email);
		if (existingUser.isEmpty()) {
			UserEntity user = new UserEntity();
			user.setName(name);
			user.setEmail(email);
			user.setMemberFamily(memberFamily);
			user.setCpf(cpf);
			user.setBirthDate(birthDate);
			user.setCellphone(cellphone);
			user.setTelephone(telephone);
			user.setPassword(password); // Certifique-se de hashear a senha antes de salvar!
			user.setRole(role);
			user.setDeletedAt(null); // ou LocalDateTime.now() se você quiser marcar como deletado
			userRepository.save(user);
		}
	}
}
