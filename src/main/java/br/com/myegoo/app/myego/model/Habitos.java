package br.com.myegoo.app.myego.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table
public class Habitos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nome;
    private boolean concluido;
    private LocalDate data;

    public Habitos() {
    }

    public Habitos(String nome, boolean concluido, LocalDate data) {
        this.nome = nome;
        this.concluido = concluido;
        this.data = data;
    }

    public Long getId() {
        return id;
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

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
}
