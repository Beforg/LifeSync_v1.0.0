package br.com.myegoo.app.myego.repository;

import br.com.myegoo.app.myego.configuration.ApplicationConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApplicationConfigurationRepository extends JpaRepository<ApplicationConfiguration, Long> {
}
