package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface TarefaRepository extends JpaRepository<Tarefa, Long> {
    @Query("SELECT t FROM Tarefa t WHERE t.conta.id = :contaId")
    List<Tarefa> findByContaId(Long contaId);
    @Query("SELECT t FROM Tarefa t WHERE t.nome = :nome AND t.conta.id = :id")
    Tarefa findByNomeAndId(String nome, Long id);
    @Modifying
    @Transactional
    @Query("DELETE FROM Tarefa t WHERE t.nome = :nome")
    void deleteByNome(String nome);
    @Query("SELECT t FROM Tarefa t WHERE t.data = :data AND t.conta.id = :contaId")
    List<Tarefa> findByData(@Param("data") LocalDate data, @Param("contaId") Long contaId);
    @Query("SELECT t FROM Tarefa t WHERE t.data = :data")
    List<Tarefa> findByDataSemId(@Param("data") LocalDate data);
    @Query("SELECT t FROM Tarefa t WHERE t.conta.id = :contaId AND t.concluida = :concluida")
    List<Tarefa> findByIdEConcluida(Long contaId, boolean concluida);
    @Query("SELECT t FROM Tarefa t WHERE t.conta.id = :contaId AND t.prioridade = :prioridade")
    List<Tarefa> findByIdEPrioridade(Long contaId,String prioridade);
    @Query("SELECT t FROM Tarefa t WHERE t.conta.id = :contaId AND t.categoria = :categoria")
    List<Tarefa> findByIdECategoria(Long contaId,String categoria);
    @Query("SELECT t FROM Tarefa t WHERE t.submeta.id = :id")
    List<Tarefa> findBySubmetaId(Long id);

    List<Tarefa> findBySubmetaIdIsNull();
    Tarefa findByNome(String nome);
    @Query("SELECT t FROM Tarefa t WHERE t.data BETWEEN :dataInicio AND :dataFim")
    List<Tarefa> findByIntervaloDeData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    @Query("SELECT t FROM Tarefa t WHERE t.prioridade = :prioridade")
    List<Tarefa> findByPrioridade(@Param("prioridade") String prioridade);
    @Query("SELECT t FROM Tarefa t WHERE t.categoria = :categoria")
    List<Tarefa> findByCategoria(@Param("categoria") String categoria);
}
