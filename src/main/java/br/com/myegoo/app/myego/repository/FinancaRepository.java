package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.model.financas.Financa;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FinancaRepository extends JpaRepository<Financa, Long>{
}
