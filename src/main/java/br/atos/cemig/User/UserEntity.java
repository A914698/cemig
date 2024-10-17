package br.atos.cemig.User;

import br.atos.cemig.Autenticacao.RoleEnum;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
public class UserEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String memberFamily;
    private String cpf;
    private LocalDate birthDate;
    private String cellphone;
    private String telephone;

    @JsonIgnore
    private String password;
    @Enumerated(EnumType.STRING)
    private RoleEnum role;

    private LocalDateTime deletedAt;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMemberFamily() {
        return memberFamily;
    }

    public void setMemberFamily(String memberFamily) {
        this.memberFamily = memberFamily;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public RoleEnum getRole() {
        return role;
    }

    public void setRole(RoleEnum role) {
        this.role = role;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    // Métodos da interface UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER")); // Substitua conforme necessário
    }

    @Override
    public String getUsername() {
        // Retornando o email como nome de usuário
        return email; // Você pode optar por usar um campo diferente se preferir
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // A conta nunca expira neste exemplo
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // A conta nunca está bloqueada neste exemplo
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // As credenciais nunca expiram neste exemplo
    }

    @Override
    public boolean isEnabled() {
        // Verifica se a conta está habilitada (não foi deletada)
        return deletedAt == null; // Se deletedAt é null, a conta está habilitada
    }
}
