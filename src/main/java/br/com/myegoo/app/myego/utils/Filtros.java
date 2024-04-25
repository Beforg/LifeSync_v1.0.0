package br.com.myegoo.app.myego.utils;

import br.com.myegoo.app.myego.model.meta.FiltroData;
import br.com.myegoo.app.myego.model.projetos.FiltroStatusCard;
import br.com.myegoo.app.myego.model.estudo.FiltroEstudos;
import br.com.myegoo.app.myego.model.tarefa.FiltroTarefa;
import br.com.myegoo.app.myego.model.tarefa.Prioridade;
import javafx.scene.control.ChoiceBox;

public class Filtros {
    public static void setPrioridade(ChoiceBox<String> choiceBox){
        for (Prioridade prioridade : Prioridade.values()) {
            choiceBox.getItems().add(prioridade.getDescricao());
        }
    }
    public static void setFiltroEstudos(ChoiceBox<String> choiceBoxFiltroEstudos) {
        for (FiltroEstudos filtroEstudos : FiltroEstudos.values()) {
            choiceBoxFiltroEstudos.getItems().add(filtroEstudos.getDescricao());
        }
    }
    public static void setFiltroData(ChoiceBox<String> choiceBoxFiltroData) {
        for (FiltroData filtroData : FiltroData.values()) {
            choiceBoxFiltroData.getItems().add(filtroData.getDescricao());
        }
    }
    public static void setFiltroStatus(ChoiceBox<String> cbStatusCard) {
        for (FiltroStatusCard filtro : FiltroStatusCard.values()) {
            cbStatusCard.getItems().add(filtro.getDescricao());
        }
    }


}
