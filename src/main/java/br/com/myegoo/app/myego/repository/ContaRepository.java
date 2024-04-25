package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContaRepository extends JpaRepository<Conta,Long> {
    Conta findByNomeUsuario(String nomeUsuario);

}
