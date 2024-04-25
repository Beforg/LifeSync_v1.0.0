package br.com.myegoo.app.myego.model.projetos;

import jakarta.persistence.*;

@Entity
@Table(name = "item_projeto_card")
public class ItemProjetoCard {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean concluido;
    @ManyToOne
    private ProjetoCard projetoCard;

    public ItemProjetoCard() {
    }

    public ItemProjetoCard(String nome, boolean concluido) {
        this.nome = nome;
        this.concluido = concluido;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public boolean isConcluido() {
        return concluido;
    }

    public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }

    public ProjetoCard getProjetoCard() {
        return projetoCard;
    }

    public void setProjetoCard(ProjetoCard projetoCard) {
        this.projetoCard = projetoCard;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
