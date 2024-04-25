package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.Habitos;
import br.com.myegoo.app.myego.model.estudo.RegistroEstudo;
import org.springframework.cglib.core.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface HabitosRepository extends JpaRepository<Habitos, Long> {
    List<Habitos> findByData(LocalDate data);
    @Query("SELECT h FROM Habitos h WHERE h.nome = :nome AND h.data = :data")
    Habitos findByNome(String nome, LocalDate data);
    @Modifying
    @Transactional
    @Query("DELETE FROM Habitos h WHERE h.nome = :nome AND h.data = :data")
    void deleteByNome(String nome, LocalDate data);

    @Query("SELECT h FROM Habitos h WHERE YEAR(h.data) = :ano")
    List<Habitos> findByAno(@Param("ano") int ano);
}
