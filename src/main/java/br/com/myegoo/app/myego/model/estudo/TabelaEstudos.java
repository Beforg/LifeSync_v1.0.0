package br.com.myegoo.app.myego.model.estudo;

import br.com.myegoo.app.myego.utils.Tabelas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.function.Consumer;

public class TabelaEstudos {
    private String nome;
    private String nomeConteudo;
    private String data;
    private String tempo;
    private String tipo;
    private String descricao;
    private Button apagar;
    final String PATH_DELETE = "src/main/resources/images/estudos/delete.png";
    final String PATH_DELETE_PRESSED = "src/main/resources/images/estudos/delete_pressed.png";

    public TabelaEstudos(String nome, String nomeConteudo, String data, String tempo, String tipo, String descricao, Consumer<TabelaEstudos> deletarRegistro) {
        this.nome = nome;
        this.nomeConteudo = nomeConteudo;
        this.data = data;
        this.tempo = tempo;
        this.tipo = tipo;
        this.descricao = descricao;
        this.apagar = new Button();
        this.apagar.setOnAction(e -> deletarRegistro.accept(this));
        Tabelas.configuraImagemDoBotao(this.apagar,PATH_DELETE,PATH_DELETE_PRESSED, TabelaEstudos.class);
    }


    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNomeConteudo() {
        return nomeConteudo;
    }

    public void setNomeConteudo(String nomeConteudo) {
        this.nomeConteudo = nomeConteudo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
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

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Button getApagar() {
        return apagar;
    }

    public void setApagar(Button apagar) {
        this.apagar = apagar;
    }
}
