package br.com.myegoo.app.myego.model.projetos;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projeto_card")
public class ProjetoCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private String descricao;
    private LocalDate data;
    private String status;
    @ManyToOne
    private Projeto projeto;
    @OneToMany(mappedBy = "projetoCard", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<ItemProjetoCard> itens;

    public ProjetoCard() {
        this.itens = new ArrayList<>();
    }

    public ProjetoCard(String nome, String descricao, LocalDate data, String status) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.status = status;
        this.itens = new ArrayList<>();
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Projeto getProjeto() {
        return projeto;
    }

    public void setProjeto(Projeto projeto) {
        this.projeto = projeto;
    }

    public List<ItemProjetoCard> getItens() {
        return itens;
    }

    public void setItens(List<ItemProjetoCard> itens) {
        this.itens = itens;
    }
}
