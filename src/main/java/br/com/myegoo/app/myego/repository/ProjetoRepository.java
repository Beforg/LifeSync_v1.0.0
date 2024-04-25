package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.projetos.Projeto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ProjetoRepository extends JpaRepository<Projeto, Long> {
    Projeto findByNome(String nome);
    @Modifying
    @Transactional
    @Query("DELETE FROM Projeto p WHERE p.nome = :nome")
    void deleteByNome(String nome);
}
