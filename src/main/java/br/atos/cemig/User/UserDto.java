package br.atos.cemig.User;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.NotNull;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;

public record UserDto(
        Long id,
        @NotNull(message = "O nome não pode ser nulo.")
        @NotBlank(message = "O nome não pode estar em branco.")
        @NotEmpty(message = "O nome não pode estar vazio.")
        @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres.")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome só pode conter letras e espaços.")
        String name,
        @Email(message = "E-mail inválido.")
        @NotNull(message = "O e-mail não pode ser nulo.")
        @NotBlank(message = "O e-mail não pode estar em branco.")
        @NotEmpty(message = "O e-mail não pode estar vazio.")
        String email,
        @NotNull(message = "O nome não pode ser nulo.")
        @NotBlank(message = "O nome não pode estar em branco.")
        @NotEmpty(message = "O nome não pode estar vazio.")
        @Size(min = 5, message = "O nome deve ter no mínimo 5 caracteres.")
        @Pattern(regexp = "^[a-zA-ZÀ-ÿ\\s]+$", message = "O nome só pode conter letras e espaços.")
        String memberFamily,
        @CPF(message = "CPF inválido.")
        @NotNull(message = "O CPF não pode ser nulo.")
        @NotBlank(message = "O CPF não pode estar em branco.")
        @NotEmpty(message = "O CPF não pode estar vazio.")
        String cpf,

        @NotNull(message = "A data não pode ser nula.")
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
        LocalDate birthDate,
        @NotNull(message = "O celular não pode se nulo.")
        @NotBlank(message = "O celular não pode estar em branco.")
        @NotEmpty(message = "O celular não pode estar vazio.")
        @Pattern(regexp = "^\\d{11}$", message = "Número de celular inválido.")
        String cellphone,

        @Pattern(regexp = "^\\d{10}$", message = "Número de telefone inválido.")
        String telephone,
        @JsonIgnore
        @JsonProperty("password")
        @NotNull(message = "A senha não pode ser nula.")
        @NotBlank(message = "A senha não pode estar em branco.")
        @NotEmpty(message = "A senha não pode estar vazia.")
        @Size(min = 8, message = "A senha deve ter pelo menos 8 caracteres.")
        @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$",
                message = "A senha deve conter pelo menos um número, uma letra maiúscula e uma letra minúscula.")
        String password

) {
}