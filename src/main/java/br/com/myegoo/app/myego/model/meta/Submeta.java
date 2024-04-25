package br.com.myegoo.app.myego.model.meta;

import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "submeta")
public class Submeta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String nome;
    private LocalDate data;
    private String status;
    @ManyToOne
    private Meta meta;
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "submeta", cascade = CascadeType.ALL)
    private List<Tarefa> tarefas;

    public Submeta() {
        this.tarefas = new ArrayList<>();
    }

    public Submeta(String nome, LocalDate data, String status) {
        this.nome = nome;
        this.data = data;
        this.status = status;
        this.tarefas = new ArrayList<>();
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    public Long getId() {
        return id;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }
}
