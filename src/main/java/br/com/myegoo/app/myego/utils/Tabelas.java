package br.com.myegoo.app.myego.utils;

import br.com.myegoo.app.myego.model.financas.TabelaFinanca;
import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.estudo.TabelaEstudos;
import br.com.myegoo.app.myego.model.projetos.TabelaItensCard;
import br.com.myegoo.app.myego.model.tarefa.TabelaTarefas;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;

public class Tabelas {
    public static void setTarefas(TableColumn<TabelaTarefas,String> nome,
                                  TableColumn<TabelaTarefas,String> data,
                                  TableColumn<TabelaTarefas,String> prioridade,
                                  TableColumn<TabelaTarefas,String> categoria,
                                  TableColumn<TabelaTarefas,Boolean> concluido) {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        prioridade.setCellValueFactory(new PropertyValueFactory<>("prioridade"));
        categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        concluido.setCellValueFactory(new PropertyValueFactory<>("concluido"));
    }
    public static void setEstudos(TableColumn<TabelaEstudos, String> nome,
                                  TableColumn<TabelaEstudos, String> nomeConteudo,
                                  TableColumn<TabelaEstudos, String> data,
                                  TableColumn<TabelaEstudos, String> tempo,
                                  TableColumn<TabelaEstudos, String> tipo,
                                  TableColumn<TabelaEstudos, String> descricao,
                                  TableColumn<TabelaEstudos, String> apagar) {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        nomeConteudo.setCellValueFactory(new PropertyValueFactory<>("nomeConteudo"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        tempo.setCellValueFactory(new PropertyValueFactory<>("tempo"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        apagar.setCellValueFactory(new PropertyValueFactory<>("apagar"));
    }
    public static void setTreinos(TableColumn<TabelaTreino, String> nome,
                                  TableColumn<TabelaTreino, String> tipo,
                                  TableColumn<TabelaTreino, String> descricao,
                                  TableColumn<TabelaTreino, String> registro) {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        tipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        registro.setCellValueFactory(new PropertyValueFactory<>("registro"));

    }
    public static void setTabelaItensCard(TableColumn<TabelaItensCard, String> nome,
                                          TableColumn<TabelaItensCard, String> concluido,
                                          TableColumn<TabelaItensCard, String> tcApagarItem) {
        nome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        concluido.setCellValueFactory(new PropertyValueFactory<>("concluido"));
        tcApagarItem.setCellValueFactory(new PropertyValueFactory<>("apagar"));
    }
    public static void setTabelaFinancas(TableColumn<TabelaFinanca, String> descricao,
                                         TableColumn<TabelaFinanca, String> data,
                                         TableColumn<TabelaFinanca, String> valor,
                                         TableColumn<TabelaFinanca, String> categoria,
                                         TableColumn<TabelaFinanca, String> remover) {
        descricao.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        data.setCellValueFactory(new PropertyValueFactory<>("data"));
        valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        remover.setCellValueFactory(new PropertyValueFactory<>("remover"));
    }
    public static void configuraImagemDoBotao(Button button, String path, String pathPressed, Class<?> classe) {
        InputStream stream = classe.getResourceAsStream(path);
        InputStream stream2 = classe.getResourceAsStream(pathPressed);

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
