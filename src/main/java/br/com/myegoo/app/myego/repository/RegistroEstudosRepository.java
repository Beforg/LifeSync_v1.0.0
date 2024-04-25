package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.estudo.RegistroEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface RegistroEstudosRepository extends JpaRepository<RegistroEstudo, Long> {
    @Query("SELECT r FROM RegistroEstudo r WHERE r.nomeConteudo ilike %:trecho% AND r.materia = :materia ORDER BY r.nomeConteudo ASC")
    List<RegistroEstudo> buscaPorNomeConteudo(String trecho, String materia);
    @Query("SELECT r FROM RegistroEstudo r WHERE r.tipo = :tipo")
    List<RegistroEstudo> buscaPorTipo(String tipo);
    @Query("SELECT r FROM RegistroEstudo r WHERE r.data = :data")
    List<RegistroEstudo> buscaPorData(String data);
    @Query("SELECT r FROM RegistroEstudo r WHERE r.materia = :materia")
    List<RegistroEstudo> buscaPorMateria(String materia);

    @Query("SELECT r FROM RegistroEstudo r WHERE r.data BETWEEN :dataInicio AND :dataFim")
    List<RegistroEstudo> buscaPorIntervaloData(String dataInicio, String dataFim);
    List<RegistroEstudo> findByMateria(String nome);
    @Query("SELECT r FROM RegistroEstudo r WHERE r.data BETWEEN :dataInicio AND :dataFim")
    List<RegistroEstudo> findByIntervaloDeData(@Param("dataInicio") LocalDate dataInicio, @Param("dataFim") LocalDate dataFim);
    @Query("SELECT r FROM RegistroEstudo r WHERE r.estudo.nome = :nome")
    List<RegistroEstudo> findByEstudo(String nome);
}
