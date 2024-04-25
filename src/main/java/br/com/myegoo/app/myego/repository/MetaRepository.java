package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.meta.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface MetaRepository extends JpaRepository<Meta, Long> {
    Meta findByNome(String nome);
    Meta findByTipo(String tipo);

    List<Meta> findByData(LocalDate data);
    @Query("SELECT m FROM Meta m WHERE m.data BETWEEN :dataInicio AND :dataFim")
    List<Meta> findByIntervaloDeData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
