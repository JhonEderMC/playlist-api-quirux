package com.playlist.infrastructure.persistence.adapter;

import com.playlist.domain.model.ListaReproduccion;
import com.playlist.domain.port.ListaReproduccionRepository;
import com.playlist.infrastructure.persistence.entity.ListaReproduccionEntity;
import com.playlist.infrastructure.persistence.mapper.ListaReproduccionMapper;
import com.playlist.infrastructure.persistence.repository.ListaReproduccionJpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
public class ListaReproduccionRepositoryAdapter implements ListaReproduccionRepository {

    private final ListaReproduccionJpaRepository jpaRepository;
    private final ListaReproduccionMapper mapper;

    public ListaReproduccionRepositoryAdapter(ListaReproduccionJpaRepository jpaRepository,
                                              ListaReproduccionMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ListaReproduccion save(ListaReproduccion lista) {
        ListaReproduccionEntity entity = mapper.toEntity(lista);
        ListaReproduccionEntity savedEntity = jpaRepository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    public Optional<ListaReproduccion> findByNombre(String nombre) {
        return jpaRepository.findByNombre(nombre)
                .map(mapper::toDomain);
    }

    @Override
    public List<ListaReproduccion> findAll() {
        return jpaRepository.findAll().stream()
                .map(mapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteByNombre(String nombre) {
        jpaRepository.deleteByNombre(nombre);
    }

    @Override
    public boolean existsByNombre(String nombre) {
        return jpaRepository.existsByNombre(nombre);
    }
}
