package br.com.myegoo.app.myego.model.meta;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "meta")
public class Meta {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    private String nome;
    private LocalDate data;
    private String tipo;
    private String status;
    @OneToMany(mappedBy = "meta", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Submeta> submetas;

    public Meta() {
        this.submetas = new ArrayList<>();
    }

    public Meta(String nome, LocalDate data, String tipo, String status) {
        this.nome = nome;
        this.data = data;
        this.tipo = tipo;
        this.status = status;
        this.submetas = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Submeta> getSubmetas() {
        return submetas;
    }

    public void setSubmetas(List<Submeta> submetas) {
        this.submetas = submetas;
    }

    public Long getId() {
        return id;
    }
}
