package com.playlist.infrastructure.persistence.repository;

import com.playlist.infrastructure.persistence.entity.ListaReproduccionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ListaReproduccionJpaRepository extends JpaRepository<ListaReproduccionEntity, Long> {

    Optional<ListaReproduccionEntity> findByNombre(String nombre);
    boolean existsByNombre(String nombre);
    void deleteByNombre(String nombre);

}