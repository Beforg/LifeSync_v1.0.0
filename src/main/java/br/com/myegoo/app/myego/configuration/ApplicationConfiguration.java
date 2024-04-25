package br.com.myegoo.app.myego.configuration;

import jakarta.persistence.*;

@Entity
@Table(name = "configuracao")
public class ApplicationConfiguration {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private boolean primeiroAcesso;

    public ApplicationConfiguration() {
    }

    public ApplicationConfiguration(boolean primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }

    public boolean getPrimeiroAcesso() {
        return primeiroAcesso;
    }

    public void setPrimeiroAcesso(boolean primeiroAcesso) {
        this.primeiroAcesso = primeiroAcesso;
    }
}
