package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.meta.Submeta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface SubmetaRepository extends JpaRepository<Submeta, Long> {
    @Query("SELECT s FROM Submeta s WHERE s.meta.id = :id")
    List<Submeta> findByMeta(Long id);
    Submeta findByNome(String tipo);
    @Transactional
    @Modifying
    @Query("DELETE FROM Submeta s WHERE s.nome = :nome")
    void deleteByNome(String nome);
    @Transactional
    @Modifying
    @Query(value = "DELETE FROM tarefa_submeta ts WHERE ts.tarefa_id = (SELECT t.id FROM Tarefa t WHERE t.nome = :nomeTarefa) AND ts.submeta_id = (SELECT s.id FROM Submeta s WHERE s.nome = :nomeSubmeta)", nativeQuery = true)
    void removerSubmetaDaTarefa(String nomeTarefa, String nomeSubmeta);
}
