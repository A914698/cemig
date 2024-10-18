package br.atos.cemig;

import br.atos.cemig.User.UserDto;
import br.atos.cemig.User.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class CemigApplication {

	private final UserService userService;

	// Injeção de dependência do UserService via construtor
	public CemigApplication(UserService userService) {
		this.userService = userService;
	}

	public static void main(String[] args) {
		SpringApplication.run(CemigApplication.class, args);
		System.out.println("Hello World");
	}

	// Definição do Bean CommandLineRunner fora do método main
	@Bean
	public CommandLineRunner loadData() {
		return args -> {
			// Criação de usuários de exemplo
			UserDto user1 = new UserDto(
					null,
					"João Silva",
					"joao@email.com",
					"Maria Silva",
					"12345678909",
					LocalDate.of(1985, 10, 5),
					"11987654321",
					"1134567890",
					null // Senha será gerada no service
			);

			UserDto user2 = new UserDto(
					null,
					"Ana Santos",
					"ana@email.com",
					"Pedro Santos",
					"98765432100",
					LocalDate.of(1990, 7, 12),
					"21998765432",
					"2134567890",
					null // Senha será gerada no service
			);

			// Cadastrar os usuários no banco
			userService.cadastrar(user1);
			userService.cadastrar(user2);

			System.out.println("Usuários de teste inseridos com sucesso!");
		};
	}
}
