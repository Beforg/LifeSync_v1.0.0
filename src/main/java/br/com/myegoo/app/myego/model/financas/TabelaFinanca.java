package br.com.myegoo.app.myego.model.financas;

import br.com.myegoo.app.myego.utils.Tabelas;
import javafx.scene.control.Button;

import java.util.function.Consumer;

public class TabelaFinanca {
    private String descricao;
    private String data;
    private String valor;
    private String categoria;
    private Button remover;
    final String PATH = "/images/estudos/delete.png";
    final String PATH_PRESSED = "/images/estudos/delete_pressed.png";
    public TabelaFinanca(String descricao, String data, String valor, String categoria, Consumer<TabelaFinanca> remover) {
        this.descricao = descricao;
        this.data = data;
        this.valor = valor;
        this.categoria = categoria;
        this.remover = new Button();
        this.remover.setOnAction(event -> remover.accept(this));
        Tabelas.configuraImagemDoBotao(this.remover,PATH,PATH_PRESSED, TabelaFinanca.class);
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public void setData(String data) {
        this.data = data;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public String getData() {
        return data;
    }

    public String getValor() {
        return valor;
    }

    public String getCategoria() {
        return categoria;
    }

    public Button getRemover() {
        return remover;
    }

    public void setRemover(Button remover) {
        this.remover = remover;
    }


}
