package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.projetos.ItemProjetoCard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface ItemProjetoCardRepository extends JpaRepository<ItemProjetoCard, Long> {
    ItemProjetoCard findByNome(String nome);
    @Query("SELECT i FROM ItemProjetoCard i WHERE i.projetoCard.nome = ?1")
    List<ItemProjetoCard> findByCard(String nome);
    @Modifying
    @Transactional
    @Query("DELETE FROM ItemProjetoCard i WHERE i.id = ?1")
    void deleteByCard(Long id);
}
