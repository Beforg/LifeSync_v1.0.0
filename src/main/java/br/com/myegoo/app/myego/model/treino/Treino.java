package br.com.myegoo.app.myego.model.treino;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.time.LocalDate;

@Entity
@Table(name = "treino")
public class Treino {
    @Id
    @GeneratedValue
    private Long id;
    private String nome;
    private LocalDate data;
    private Boolean concluido;
    private String descricao;
    private String tempo;
    private String tipo;

    public Treino() {
    }

    public Treino(String nome, LocalDate data, Boolean concluido, String descricao, String tempo, String tipo) {
        this.nome = nome;
        this.data = data;
        this.concluido = concluido;
        this.descricao = descricao;
        this.tempo = tempo;
        this.tipo = tipo;
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

    public Boolean getConcluido() {
        return concluido;
    }

    public void setConcluido(Boolean concluido) {
        this.concluido = concluido;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

}
