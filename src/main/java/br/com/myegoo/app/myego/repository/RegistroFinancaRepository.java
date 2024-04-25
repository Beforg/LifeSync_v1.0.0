package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.financas.RegistroFinanca;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface RegistroFinancaRepository extends JpaRepository<RegistroFinanca, Long> {
    @Query("SELECT r FROM RegistroFinanca r WHERE r.financa.id = ?1 GROUP BY r.categoria")
    List<RegistroFinanca> findByCategoria(Long id);
    @Query("SELECT SUM (r.valor) FROM RegistroFinanca r WHERE r.data BETWEEN :dataInicio AND :dataFim")
    Double sumByFinanca(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);

    default BigDecimal sumByFinancaAsBigDecimal(LocalDate inicio, LocalDate fim) {
        Double sum = sumByFinanca(inicio, fim);
        return sum != null ? BigDecimal.valueOf(sum) : BigDecimal.ZERO;
    }
    @Transactional
    @Modifying
    @Query("DELETE FROM RegistroFinanca r WHERE r.id = ?1")
    void deleteByFinanca(Long id);

    @Query("SELECT r FROM RegistroFinanca r WHERE r.data BETWEEN :dataInicio AND :dataFim")
    List<RegistroFinanca> findByIntervaloDeData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
}
