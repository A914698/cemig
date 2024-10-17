package br.atos.cemig.Autenticacao;

import br.atos.cemig.Jwt.JwtAutenticaticacao;
import br.atos.cemig.Jwt.JwtService;
import br.atos.cemig.User.UserEntity; // Certifique-se de que o pacote está correto
import br.atos.cemig.User.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AutenticacaoService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final AuthenticationManager autenticacaoManager;
    private final PasswordEncoder passwordEncoder;

    // Método 'logout' para registrar um novo usuário (alterado de signup).
    public JwtAutenticaticacao logout(Logout request) {
        // Cria uma nova entidade UserEntity com os dados fornecidos.
        UserEntity user = new UserEntity();
        user.setName(request.name()); // Use o método correto do seu DTO
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password())); // Use o método correto do seu DTO
        // Se você tiver uma enumeração ou constante para o papel, você pode definir aqui
        // Ex: user.setRole(RoleEnum.USER);

        // O novo usuário é salvo no repositório.
        userRepository.save(user);

        // Um JWT é gerado para o novo usuário.
        var jwt = jwtService.generateToken(user);

        // O JWT é retornado dentro de um objeto JwtAuthenticationResponse.
        return new JwtAutenticaticacao(jwt);
    }

    // Método 'login' para autenticar um usuário existente (alterado de signin).
    public JwtAutenticaticacao login(Login request) {
        // O AuthenticationManager tenta autenticar a solicitação.
        autenticacaoManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())); // Use o método correto do seu DTO

        // Se a autenticação for bem-sucedida, encontramos o usuário no repositório.
        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        // Um JWT é gerado para o usuário autenticado.
        var jwt = jwtService.generateToken(user);

        // O JWT é retornado dentro de um objeto JwtAuthenticationResponse.
        return new JwtAutenticaticacao(jwt);
    }
}
