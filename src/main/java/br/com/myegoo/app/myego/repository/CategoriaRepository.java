package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CategoriaRepository extends JpaRepository<Categoria, Long>{
    @Query("SELECT c FROM Categoria c WHERE c.nome = ?1")
    Categoria findByNome(String nome);
    @Query("SELECT c FROM Categoria c WHERE c.tipo = ?1")
    List<Categoria> findByTipo(String tipo);
    @Transactional
    @Modifying
    @Query("DELETE FROM Categoria c WHERE c.nome = :nome AND c.tipo = :tipo")
    void deleteByNomeAndTipo(String nome, String tipo);

}
