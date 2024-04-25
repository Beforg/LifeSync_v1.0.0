package br.com.myegoo.app.myego.model.tarefa;

import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.model.meta.Submeta;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tarefas")
public class Tarefa {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "conta_id")
    private Conta conta;
    private String nome;
    private String descricao;
    private LocalDate data;
    private String prioridade;
    private String categoria;
    private boolean concluida;
   // @JoinColumn(name = "submeta_id")
    //(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @ManyToOne
    private Submeta submeta;

    public Tarefa() {
    }

    public Tarefa(String nome, String descricao, LocalDate data, boolean concluida, String prioridade, String categoria) {
        this.nome = nome;
        this.descricao = descricao;
        this.data = data;
        this.concluida = concluida;
        this.prioridade = prioridade;
        this.categoria = categoria;

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public boolean isConcluida() {
        return concluida;
    }

    public void setConcluida(boolean concluida) {
        this.concluida = concluida;
    }

    public String getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(String prioridade) {
        this.prioridade = prioridade;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Conta getConta() {
        return conta;
    }

    public void setConta(Conta conta) {
        this.conta = conta;
    }
    public boolean getConcluido() {
        return concluida;
    }
    public String getEstadoConcluido() {
        return concluida ? "Concluído" : "Não concluído";
    }
    public void setConcluido(boolean concluida) {
        this.concluida = concluida;
    }

    public Submeta getSubmeta() {
        return submeta;
    }

    public void setSubmeta(Submeta submeta) {
        this.submeta = submeta;
    }
}
