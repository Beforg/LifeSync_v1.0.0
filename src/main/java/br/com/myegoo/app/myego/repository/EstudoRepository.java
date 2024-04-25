package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.estudo.Estudos;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface EstudoRepository extends JpaRepository<Estudos, Long> {
    @Query("SELECT e FROM Estudos e WHERE e.conta.id = ?1")
    List<Estudos> findByConta(Long idConta);
    @Query("SELECT e FROM Estudos e WHERE e.nome = :nome")
    Estudos findByNomeAndConta(String nome);

}
