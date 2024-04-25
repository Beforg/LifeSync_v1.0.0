package br.com.myegoo.app.myego.model.treino;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.time.LocalDate;
import java.util.function.Consumer;

public class TabelaTreino {
    private String nome;
    private String tipo;
    private Button registro;
    private String descricao;
    private boolean concluido;
    private LocalDate data;

    public TabelaTreino(String nome, String tipo, String descricao, boolean concluido,LocalDate data, Consumer<TabelaTreino> registrar) {
        this.nome = nome;
        this.tipo = tipo;
        this.registro = new Button();
        this.descricao = descricao;
        this.concluido = concluido;
        this.registro.setOnAction(e -> registrar.accept(this));
        this.data = data;
        configuraImagemDoBotao(this.registro);
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isConcluido() {
        return concluido;
    }

public void setConcluido(boolean concluido) {
        this.concluido = concluido;
    }
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Button getRegistro() {
        return registro;
    }

    public void setRegistro(Button registro) {
        this.registro = registro;
    }
    public static void configuraImagemDoBotao(Button button) {
        InputStream stream = TabelaTreino.class.getResourceAsStream("/images/ico/registrar_treino.png");
        InputStream stream2 =TabelaTreino.class.getResourceAsStream("/images/ico/registar_treino_pressed.png");

        if (stream == null || stream2 == null) {
            throw new IllegalArgumentException("A imagem não pode ser carregada!");
        }
        Image image = new Image(stream);
        Image imagePressed = new Image(stream2);

        ImageView imageView = new ImageView(image);
        button.setGraphic(imageView);

        ImageView imageViewPressed = new ImageView(imagePressed);

        button.setStyle("-fx-background-color: transparent;");
        button.setOnMousePressed(e -> button.setGraphic(imageViewPressed));
        button.setOnMouseReleased(e -> button.setGraphic(imageView));
    }
}
