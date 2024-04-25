package br.com.myegoo.app.myego.model.projetos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projeto")
public class Projeto {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private LocalDate data;
    private String categoria;
    @OneToMany(mappedBy = "projeto", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ProjetoCard> projetoCard;

    public Projeto() {
       this.projetoCard = new ArrayList<>();
    }

    public Projeto(String nome, LocalDate data, String categoria) {
        this.nome = nome;
        this.data = data;
        this.categoria = categoria;
        this.projetoCard = new ArrayList<>();
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public List<ProjetoCard> getProjetoCard() {
        return projetoCard;
    }

    public void setProjetoCard(List<ProjetoCard> projetoCard) {
        this.projetoCard = projetoCard;
    }
}
