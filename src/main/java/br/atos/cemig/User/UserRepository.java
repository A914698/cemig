package br.atos.cemig.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    List<UserEntity> findAllByDeletedAtIsNull();
    Optional<UserEntity> findByIdAndDeletedAtIsNull(Long id);
    Optional<UserEntity> findByEmail(String email);
    Optional<UserEntity> findByEmailAndDeletedAtIsNull(String email);
}
