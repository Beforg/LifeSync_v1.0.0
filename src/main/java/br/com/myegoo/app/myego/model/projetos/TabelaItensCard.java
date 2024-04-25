package br.com.myegoo.app.myego.model.projetos;

import br.com.myegoo.app.myego.utils.Tabelas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.util.function.Consumer;

public class TabelaItensCard {
    private String nome;
    private Button concluido;
    private Button apagar;
    private boolean concluidoBoolean;
    final String PATH_CHECAR = "src/main/resources/images/ico/checked.png";
    final String PATH_CHECAR_PRESSED = "src/main/resources/images/ico/checked_pressed.png";
    final String PATH_DELETE = "src/main/resources/images/estudos/delete.png";
    final String PATH_DELETE_PRESSED = "src/main/resources/images/estudos/delete_pressed.png";

    public TabelaItensCard(String nome,boolean concluidoBoolean, Consumer<TabelaItensCard> concluir, Consumer<TabelaItensCard> apagar) {
        this.nome = nome;
        this.concluido = new Button();
        this.apagar = new Button();
        this.concluido.setOnAction(e -> concluir.accept(this));
        this.apagar.setOnAction(e -> apagar.accept(this));
        this.concluidoBoolean = concluidoBoolean;
        Tabelas.configuraImagemDoBotao(this.concluido,PATH_CHECAR,PATH_CHECAR_PRESSED, TabelaItensCard.class);
        Tabelas.configuraImagemDoBotao(this.apagar,PATH_DELETE,PATH_DELETE_PRESSED,TabelaItensCard.class);
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Button getConcluido() {
        return concluido;
    }

    public void setConcluido(Button concluido) {
        this.concluido = concluido;
    }

    public Button getApagar() {
        return apagar;
    }

    public void setApagar(Button apagar) {
        this.apagar = apagar;
    }

    public boolean isConcluidoBoolean() {
        return concluidoBoolean;
    }

    public void setConcluidoBoolean(boolean concluidoBoolean) {
        this.concluidoBoolean = concluidoBoolean;
    }

}
