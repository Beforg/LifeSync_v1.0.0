package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.treino.Treino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TreinoRepository extends JpaRepository<Treino, Long>{
    Treino findByNome(String nome);
    @Modifying
    @Transactional
    @Query("DELETE FROM Treino t WHERE t.nome = ?1")
    void deleteByNome(String nome);

    List<Treino> findByData(LocalDate value);
    @Query("SELECT t FROM Treino t WHERE t.data BETWEEN :dataInicio AND :dataFim")
    List<Treino> findByIntervaloDeData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    @Query("SELECT t FROM Treino t WHERE YEAR(t.data) = :ano")
    List<Treino> findByAno(@Param("ano") int ano);
    @Query("SELECT t FROM Treino t ORDER BY t.data DESC")
    List<Treino> findAllOrderByDataDesc();
    @Query("SELECT t FROM Treino t WHERE t.data > :dataAtual ORDER BY t.data ASC")
    List<Treino> findTop4ByDataAfterOrderByDataAsc(@Param("dataAtual") LocalDate dataAtual);

}
