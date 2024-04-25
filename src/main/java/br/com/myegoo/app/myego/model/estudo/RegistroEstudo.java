package br.com.myegoo.app.myego.model.estudo;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "registro_estudo")
public class RegistroEstudo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "estudo_id")
    private Estudos estudo;
    private String materia;
    private String tipo;
    @Column(name = "nome_conteudo")
    private String nomeConteudo;
    private LocalDate data;
    private String tempo;
    private String descricao;

    public RegistroEstudo(String materia, LocalDate data, String tempo, String nomeConteudo, String descricao, String tipo) {
        this.materia = materia;
        this.data = data;
        this.tempo = tempo;
        this.nomeConteudo = nomeConteudo;
        this.descricao = descricao;
        this.tipo = tipo;
    }

    public RegistroEstudo() {

    }

    public Estudos getEstudo() {
        return estudo;
    }

    public void setEstudo(Estudos nome) {
        this.estudo = nome;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getTempo() {
        return tempo;
    }

    public void setTempo(String tempo) {
        this.tempo = tempo;
    }

    public String getNomeConteudo() {
        return nomeConteudo;
    }

    public void setNomeConteudo(String nomeConteudo) {
        this.nomeConteudo = nomeConteudo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getMateria() {
        return materia;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    public String getTipo() {
        return tipo;
    }
}
