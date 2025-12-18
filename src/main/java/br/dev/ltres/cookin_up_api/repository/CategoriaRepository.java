package br.dev.ltres.cookin_up_api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;

import br.dev.ltres.cookin_up_api.model.Categoria;

public interface CategoriaRepository extends JpaRepository<Categoria, Long> {

    Optional<Categoria> findByIdAndAtivoTrue(@NonNull Long Id);

    @NonNull
    List<Categoria> findByAtivoTrue();

    @NonNull
    Page<Categoria> findByAtivoTrue(@NonNull Pageable paginacao);

    @NonNull
    List<Categoria> findTop10ByAtivoTrueAndNomeContainingIgnoreCase(@NonNull String termo);

    @Modifying
    @Query("update Categoria c set c.ativo = false where c.id = :id and c.ativo = true")
    int desativar(@Param("id") Long id);

}
