package br.com.myegoo.app.myego.model.estudo;

import br.com.myegoo.app.myego.model.Conta;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "estudos")
public class Estudos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
    private String nome;
    private String descricao;
    @OneToMany(mappedBy = "estudo")
    private List<RegistroEstudo> registroEstudo;

    public Estudos(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public Estudos() {

    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
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

    public List<RegistroEstudo> getRegistroEstudo() {
        return registroEstudo;
    }

    public void setRegistroEstudo(List<RegistroEstudo> registroEstudo) {
        this.registroEstudo = registroEstudo;
    }
}
