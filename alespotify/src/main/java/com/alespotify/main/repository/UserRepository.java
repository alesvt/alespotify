package com.alespotify.main.repository;

import com.alespotify.main.models.entities.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends CrudRepository<Usuario, Long> {
    Optional<Usuario> findByEmail(String email);
}
