package br.com.myegoo.app.myego.model;

import br.com.myegoo.app.myego.model.estudo.Estudos;
import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import jakarta.persistence.*;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "contas")
public class Conta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    @Column(name = "nome_usuario")
    private String nomeUsuario;
    private String senha;
    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;
    @OneToMany(mappedBy = "conta")
    private List<Tarefa> tarefas;
    @OneToMany(mappedBy = "conta")
    private List<Estudos> estudos;

    public Conta() {
    }

    public Conta(String nome, String nome_usuario, String senha, LocalDate dataNascimento) {
        this.nome = nome;
        this.nomeUsuario = nome_usuario;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.tarefas = new ArrayList<>();
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

    public String getNome_usuario() {
        return nomeUsuario;
    }

    public void setNome_usuario(String nome_usuario) {
        this.nomeUsuario = nome_usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}
