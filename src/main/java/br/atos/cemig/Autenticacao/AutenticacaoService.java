package br.atos.cemig.Autenticacao;

import br.atos.cemig.Jwt.JwtAutenticaticacao;
import br.atos.cemig.Jwt.JwtService;
import br.atos.cemig.User.UserEntity;
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

    public JwtAutenticaticacao logout(Logout request) {
        UserEntity user = new UserEntity();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(passwordEncoder.encode(request.password()));

        userRepository.save(user);

        var jwt = jwtService.generateToken(user);

        return new JwtAutenticaticacao(jwt);
    }

    public JwtAutenticaticacao login(Login request) {
        autenticacaoManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));

        var user = userRepository.findByEmail(request.email())
                .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        var jwt = jwtService.generateToken(user);

        return new JwtAutenticaticacao(jwt);
    }
}
