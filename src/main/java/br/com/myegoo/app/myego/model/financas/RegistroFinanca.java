package br.com.myegoo.app.myego.model.financas;

import br.com.myegoo.app.myego.model.financas.Financa;
import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "registro_financa")
public class RegistroFinanca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String descricao;
    private String categoria;
    private BigDecimal valor;
    private LocalDate data;
    @ManyToOne
    @JoinColumn(name = "financa_id")
    private Financa financa;

    public RegistroFinanca() {
    }

    public RegistroFinanca(String descricao, String categoria, BigDecimal valor, LocalDate data) {
        this.descricao = descricao;
        this.categoria = categoria;
        this.valor = valor;
        this.data = data;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public Financa getFinanca() {
        return financa;
    }

    public void setFinanca(Financa financa) {
        this.financa = financa;
    }
}
