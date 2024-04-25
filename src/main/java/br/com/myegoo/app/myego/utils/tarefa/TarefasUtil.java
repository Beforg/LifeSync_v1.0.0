package br.com.myegoo.app.myego.utils.tarefa;

import br.com.myegoo.app.myego.controller.ApplicationController;
import br.com.myegoo.app.myego.interfaces.IPadraoDaData;
import br.com.myegoo.app.myego.interfaces.ITabela;
import br.com.myegoo.app.myego.model.tarefa.FiltroTarefa;
import br.com.myegoo.app.myego.model.tarefa.SubFiltroTarefa;
import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import br.com.myegoo.app.myego.repository.TarefaRepository;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.io.File;
import java.time.LocalDate;
@Component

public class TarefasUtil implements IPadraoDaData, ITabela {
    public static void botoresCalendario(Button anterior, Button proximo, Button hoje,boolean b) {
        anterior.setVisible(b);
        proximo.setVisible(b);
        hoje.setVisible(b);
    }
    public static void mostrarEditarTarefa(TextArea textAreaEditarTarefa, TextField nome, DatePicker data, ChoiceBox<String> prioridade, ChoiceBox<String> categoria,Button editar, Button excluir, Button salvar, boolean b) {
        nome.setVisible(b);
        data.setVisible(b);
        textAreaEditarTarefa.setVisible(b);
        prioridade.setVisible(b);
        categoria.setVisible(b);
        excluir.setVisible(!b);
        salvar.setVisible(b);
        editar.setVisible(!b);
    }
    public static void ocultaLabelTarefa(Label nome, Label data, Label prioridade, Label categoria) {
        nome.setVisible(false);
        data.setVisible(false);
        prioridade.setVisible(false);
        categoria.setVisible(false);
    }
    public static void muralAddTarefasCalendario(Pane muralCalendario, Pane adicionartarefa,GridPane tarefas, GridPane categorias, LocalDate data, DatePicker datePicker, boolean b) {
        tarefas.setVisible(b);
        categorias.setVisible(false);
        datePicker.setValue(data);
        adicionartarefa.setVisible(b);
        muralCalendario.setVisible(!b);

    }

    public static void setFiltroTarefa(ChoiceBox<String> choiceBox){
        for (FiltroTarefa filtro : FiltroTarefa.values()) {
            choiceBox.getItems().add(filtro.getDescricao());
        }
    }
    public static void setSubFiltroPrioridade(ChoiceBox<String> subFiltro, ChoiceBox<String> filtroEscolhido){
        String filtrado = filtroEscolhido.getValue();
        switch(filtrado) {
            case "Prioridade":
                subFiltro.getItems().clear();
                subFiltro.getItems().add(SubFiltroTarefa.BAIXA.getDescricao());
                subFiltro.getItems().add(SubFiltroTarefa.MEDIA.getDescricao());
                subFiltro.getItems().add(SubFiltroTarefa.ALTA.getDescricao());
                subFiltro.getItems().add(SubFiltroTarefa.URGENTE.getDescricao());
                break;
            case "Data":
                subFiltro.getItems().clear();
                subFiltro.getItems().add(SubFiltroTarefa.DIA.getDescricao());
                subFiltro.getItems().add(SubFiltroTarefa.SEMANA.getDescricao());
                subFiltro.getItems().add(SubFiltroTarefa.MES.getDescricao());
                break;
        }
    }
    public static void toolTipDescricaoTarefa(Label tarefaSelecionada,Label selecionadoTipoDescricao, TarefaRepository tarefaRepository) {
            if (ApplicationController.conta.getId() == null) {
                System.out.println("Aguardar conta");
            } else {
                Tooltip tooltip = new Tooltip();
                Tarefa tarefa = tarefaRepository.findByNomeAndId(tarefaSelecionada.getText(), ApplicationController.conta.getId());
                String textoDaDescricao = tarefa.getDescricao();
                tooltip.setText(textoDaDescricao);
                Tooltip.install(selecionadoTipoDescricao, tooltip);
            }


    }
    public static void gifProgresso(Label muralTipoProgresso, ImageView imagemProgresso) {
        final String PATH = "src/main/resources/images/tarefas/progress_bar.png";
        final String PATH_GIF = "src/main/resources/images/tarefas/progress_bar_gif.gif";
        Image imageGif = new Image(new File(PATH_GIF).toURI().toString());
        Image image = new Image(new File(PATH).toURI().toString());
        muralTipoProgresso.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            imagemProgresso.setImage(imageGif);
        });
        muralTipoProgresso.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            imagemProgresso.setImage(image);
        });
    }
    public static void gifAtrasado(Label muralTipoAtrasdo, ImageView imagemProgresso) {
        final String PATH = "src/main/resources/images/tarefas/atrasada.png";
        final String PATH_GIF = "src/main/resources/images/tarefas/atrasada_gif.gif";
        Image imageGif = new Image(new File(PATH_GIF).toURI().toString());
        Image image = new Image(new File(PATH).toURI().toString());
        muralTipoAtrasdo.addEventHandler(MouseEvent.MOUSE_ENTERED, e -> {
            imagemProgresso.setImage(imageGif);
        });
        muralTipoAtrasdo.addEventHandler(MouseEvent.MOUSE_EXITED, e -> {
            imagemProgresso.setImage(image);
        });
    }
}
