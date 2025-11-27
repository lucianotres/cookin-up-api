package br.dev.ltres.cookin_up_api.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.NonNull;

import br.dev.ltres.cookin_up_api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
    @NonNull
    Page<Categoria> findAll(@NonNull Pageable pageable);
}
