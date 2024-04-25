package br.com.myegoo.app.myego.utils.treino;

import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.treino.Treino;
import br.com.myegoo.app.myego.repository.TreinoRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import br.com.myegoo.app.myego.utils.TreinoUtil;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class ListenerTreino {
    private static final TreinoUtil treinoUtil = new TreinoUtil();
    public static void listenerTabelaTreino(TableView<TabelaTreino> tabelaTreinoTableView,
                                            Label detalheTipoTreino,
                                            Label detalheNomeTreino,
                                            Label detalheDataTreino,
                                            Label detalheConcluidoTreino,
                                            Label detalheTempoTreino,
                                            TreinoRepository treinoRepository) {
        tabelaTreinoTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Treino treino = treinoRepository.findByNome(newValue.getNome());
                detalheTipoTreino.setText(newValue.getTipo());
                detalheNomeTreino.setText(newValue.getNome());
                detalheDataTreino.setText(treinoUtil.formataData(treino.getData()));
                detalheConcluidoTreino.setText(newValue.isConcluido() ? "Concluído" : "Não concluído");
                detalheTempoTreino.setText(treino.getTempo());
            }
        });
    }
    public static void listenerFiltrosDaTabelaDeTreino(Label guardaNomeTreino,
                                                       TextField tfDuracaoTreino,
                                                       CheckBox checkBoxSemTempo,
                                                       Button btRegistar,
                                                       Button btVoltar,
                                                       Label labelInfoSelecaoTreino,
                                                       ChoiceBox<String> filtro,
                                                       DatePicker datePickerPeriodo,
                                                       TableView<TabelaTreino> tabelaTreino,
                                                       ObservableList<TabelaTreino> treinos,
                                                       TreinoRepository treinoRepository,
                                                       Label labelTreinosFiltrados) {
        filtro.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                datePickerPeriodo.setDisable(false);
                datePickerPeriodo.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        CarregaDadosService.carregaTabelaDeTreinoFiltrada(
                                guardaNomeTreino,
                                tfDuracaoTreino,
                                checkBoxSemTempo,
                                btRegistar,
                                btVoltar,
                                labelInfoSelecaoTreino,
                                datePickerPeriodo,
                                tabelaTreino,
                                treinos,
                                treinoRepository,
                                labelTreinosFiltrados,
                                filtro.getValue());
                    }
                });
            } else {
                datePickerPeriodo.setDisable(true);
            }
        });
    }
    public static void listenerRegistarTreino(TextField textField, ChoiceBox<String> choiceBox, DatePicker datePicker, Button btVoltarRegistroCompleto) {
        textField.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                choiceBox.setVisible(false);
                datePicker.setVisible(false);
                btVoltarRegistroCompleto.setVisible(true);
            } else {
                choiceBox.setVisible(true);
                datePicker.setVisible(true);
                btVoltarRegistroCompleto.setVisible(false);
            }
        });
    }
}
