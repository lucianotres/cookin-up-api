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

import br.dev.ltres.cookin_up_api.model.Receita;

public interface ReceitaRepository extends JpaRepository<Receita, Long> {

    Optional<Receita> findByIdAndAtivoTrue(@NonNull Long Id);

    List<Receita> findByAtivoTrue();

    @NonNull
    Page<Receita> findByAtivoTrue(@NonNull Pageable paginacao);

    @Modifying
    @Query("update Receita c set c.ativo = false where c.id = :id and c.ativo = true")
    int desativar(@Param("id") Long id);

}
