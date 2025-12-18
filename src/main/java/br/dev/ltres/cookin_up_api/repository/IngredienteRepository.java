package br.dev.ltres.cookin_up_api.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import br.dev.ltres.cookin_up_api.model.Ingrediente;

public interface IngredienteRepository extends JpaRepository<Ingrediente, Long> {

    List<Ingrediente> findAllByNomeIn(List<String> ingredientes);

    List<Ingrediente> findTop10ByNomeContainingIgnoreCase(String termo);
}
