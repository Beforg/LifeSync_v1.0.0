package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.projetos.ProjetoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ProjetoCardRepository extends JpaRepository<ProjetoCard, Long> {
    @Query("SELECT p FROM ProjetoCard p WHERE p.projeto.nome = ?1")
    List<ProjetoCard> findByProjeto(String projeto);
    ProjetoCard findByNome(String nome);
    @Modifying
    @Transactional
    @Query("DELETE FROM ProjetoCard p WHERE p.nome = ?1")
    void deleteByNome(String nome);
}
