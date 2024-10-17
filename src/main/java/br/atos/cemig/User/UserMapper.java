package br.atos.cemig.User;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserMapper {

    // Mapeia de UserEntity para UserDto
    public UserDto toDto(UserEntity entity) {
        UserDto dto = new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getEmail(),
                entity.getMemberFamily(),
                entity.getCpf(),
                entity.getBirthDate(),
                entity.getCellphone(),
                entity.getTelephone(),
                entity.getPassword());
        return dto;
    }

    // Mapeia de UserDto para UserEntity
    public UserEntity toEntity(UserDto dto) {
        UserEntity entity = new UserEntity();
        entity.setId(dto.id());
        entity.setName(dto.name());
        entity.setEmail(dto.email());
        entity.setMemberFamily(dto.memberFamily());
        entity.setCpf(dto.cpf());
        entity.setBirthDate(dto.birthDate());
        entity.setCellphone(dto.cellphone());
        entity.setTelephone(dto.telephone());
        entity.setPassword(dto.password());
        return entity;
    }

    // Mapeia uma lista de UserEntity para uma lista de UserDto
    public List<UserDto> toDto(List<UserEntity> entities) {
        List<UserDto> result = entities.stream().map(this::toDto).collect(Collectors.toList());
        return result;
    }

    // Mapeia uma lista de UserDto para uma lista de UserEntity
    public List<UserEntity> toEntity(List<UserDto> dtos) {
        List<UserEntity> result = dtos.stream().map(this::toEntity).collect(Collectors.toList());
        return result;
    }
}
