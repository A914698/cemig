package br.atos.cemig.User;

import br.atos.cemig.Autenticacao.RoleEnum;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private PasswordEncoder passwordEncoder;
    public final UserRepository repository;
    public final UserMapper mapper;

    public UserService(PasswordEncoder passwordEncoder, UserRepository repository, UserMapper mapper) {
        this.passwordEncoder = passwordEncoder;
        this.repository = repository;
        this.mapper = mapper;
    }

    public UserDetails loadUserByUsername(String email) {
        return repository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    static UserEntity getUsuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserEntity) authentication.getPrincipal();
    }

    @Transactional
    public UserDto cadastrar(UserDto dados) {
        UserEntity userEntity = mapper.toEntity(dados);

        String senhaCriptografada = passwordEncoder.encode(userEntity.getPassword());
        userEntity.setPassword(senhaCriptografada);

        userEntity = repository.save(userEntity);

        return mapper.toDto(userEntity);
    }

    public List<UserDto> listar() {
        List<UserEntity> entities = repository.findAllByDeletedAtIsNull();
        return mapper.toDto(entities);
    }

    public UserDto buscar(Long id) {
        Optional<UserEntity> optionalUserEntity = repository.findByIdAndDeletedAtIsNull(id);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));
        return mapper.toDto(userEntity);
    }

    @Transactional
    public UserDto atualizar(UserDto dados, Long id) {
        UserEntity usuarioLogado = getUsuarioLogado();

        Optional<UserEntity> optionalUserEntity = repository.findByIdAndDeletedAtIsNull(id);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (RoleEnum.USER.equals(usuarioLogado.getRole()) && !usuarioLogado.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não autorizado a atualizar o usuário");
        }

        userEntity.setName(dados.name());
        userEntity.setEmail(dados.email());
        userEntity.setMemberFamily(dados.memberFamily());
        userEntity.setCpf(dados.cpf());
        userEntity.setBirthDate(dados.birthDate());
        userEntity.setCellphone(dados.cellphone());
        userEntity.setTelephone(dados.telephone());

        repository.save(userEntity);

        return mapper.toDto(userEntity);
    }

    @Transactional
    public void deletar(Long id) {
        UserEntity usuarioLogado = getUsuarioLogado();

        Optional<UserEntity> optionalUserEntity = repository.findByIdAndDeletedAtIsNull(id);
        UserEntity userEntity = optionalUserEntity.orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuário não encontrado"));

        if (RoleEnum.USER.equals(usuarioLogado.getRole()) && !usuarioLogado.getId().equals(id)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Não autorizado a deletar o usuário");
        }

        userEntity.setDeletedAt(LocalDateTime.now());
        repository.save(userEntity);
    }
}
