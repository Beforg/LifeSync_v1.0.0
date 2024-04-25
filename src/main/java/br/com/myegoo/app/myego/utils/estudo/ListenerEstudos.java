package br.com.myegoo.app.myego.utils.estudo;

import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.model.estudo.TabelaEstudos;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.EstudoRepository;
import br.com.myegoo.app.myego.repository.RegistroEstudosRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import br.com.myegoo.app.myego.service.estudo.EstudosService;
import br.com.myegoo.app.myego.utils.Filtros;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import org.springframework.stereotype.Component;

@Component
public class ListenerEstudos {
    public static void listenerIniciarEstudos(FlowPane flowPaneInfosEstudos,EstudoRepository estudoRepository, Conta conta, Label labelTipoMateria, ChoiceBox<String> choiceBoxSelecionarMateria, Button btPlay, Button btPause, Button btReset, Button btPassar, Button btSave) {
        choiceBoxSelecionarMateria.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btPlay.setDisable(false);
                btPause.setDisable(false);
                btReset.setDisable(false);
                btPassar.setDisable(false);
                btSave.setDisable(false);
                flowPaneInfosEstudos.setVisible(true);
                EstudosService.mostraLabelTipoEstudo(labelTipoMateria, estudoRepository, conta, choiceBoxSelecionarMateria);
            } else {
                btPlay.setDisable(true);
                btPause.setDisable(true);
                btReset.setDisable(true);
                btPassar.setDisable(true);
                btSave.setDisable(true);
                flowPaneInfosEstudos.setVisible(false);
            }
        });
    }
    public static void listenerFiltrosDaTabelaRegistros(Conta conta,
                                                        EstudoRepository estudosRepository,
                                                        RegistroEstudosRepository registroEstudosRepository,
                                                        ObservableList<TabelaEstudos> estudosLista,
                                                        TableView<TabelaEstudos> tabelaEstudos,
                                                        ChoiceBox<String> choiceBoxFiltroEstudos,
                                                        DatePicker datePickerFiltro,
                                                        TextField tfFiltroNome,
                                                        ChoiceBox<String> choiceBoxSubFiltro,
                                                        CategoriaRepository categoriaRepository) {
        choiceBoxFiltroEstudos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && newValue.equals("Data")) {
                FormatadorEstudos formatadorEstudos = new FormatadorEstudos();
                choiceBoxSubFiltro.getItems().clear();
                choiceBoxSubFiltro.setDisable(false);
                Filtros.setFiltroData(choiceBoxSubFiltro);
                formatadorEstudos.formatadorFiltroDaData(datePickerFiltro, choiceBoxSubFiltro);
                choiceBoxSubFiltro.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        datePickerFiltro.setDisable(false);
                        if (datePickerFiltro.getValue() != null) {
                            CarregaDadosService.carregaPorData(choiceBoxSubFiltro,registroEstudosRepository,tabelaEstudos,estudosLista,datePickerFiltro);
                        }
                    }

                });
                datePickerFiltro.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        CarregaDadosService.carregaPorData(choiceBoxSubFiltro,registroEstudosRepository,tabelaEstudos,estudosLista,datePickerFiltro);
                    }
                });


            } else if (newValue != null && newValue.equals("Conteúdo")) {
                choiceBoxSubFiltro.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        tabelaEstudos.getItems().clear();
                        estudosLista.clear();
                    }
                });
                CarregaDadosService.carregarMaterias(choiceBoxSubFiltro, estudosRepository, conta);
                tfFiltroNome.setDisable(false);
                datePickerFiltro.setDisable(true);
                choiceBoxSubFiltro.setDisable(false);
                CarregaDadosService.carregaPorMateriaEconteudo(choiceBoxSubFiltro,registroEstudosRepository, tabelaEstudos,estudosLista,tfFiltroNome);

            } else if (newValue != null && newValue.equals("Matéria")) {

                CarregaDadosService.carregarMaterias(choiceBoxSubFiltro, estudosRepository, conta);
                choiceBoxSubFiltro.setDisable(false);
                datePickerFiltro.setDisable(true);
                tfFiltroNome.setDisable(true);
                choiceBoxSubFiltro.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        CarregaDadosService.carregaPorMateria(choiceBoxSubFiltro, registroEstudosRepository, tabelaEstudos, estudosLista);
                    }
                });

            } else if (newValue != null && newValue.equals("Todos")) {
                CarregaDadosService.carregaRegistroDeEstudos(tabelaEstudos,estudosLista,registroEstudosRepository,conta.getId());
            } else if (newValue != null && newValue.equals("Tipo")) {
                CarregaDadosService.carregarTipoEstudos(choiceBoxSubFiltro, categoriaRepository);
                tfFiltroNome.setDisable(true);
                datePickerFiltro.setDisable(true);
                choiceBoxSubFiltro.setDisable(false);
                choiceBoxSubFiltro.getSelectionModel().selectedItemProperty().addListener((observable1, oldValue1, newValue1) -> {
                    if (newValue1 != null) {
                        CarregaDadosService.carregaPorTipo(choiceBoxSubFiltro, registroEstudosRepository, tabelaEstudos, estudosLista);
                    }
                });
            } else {
                datePickerFiltro.setDisable(true);
                tfFiltroNome.setDisable(true);
                choiceBoxSubFiltro.setDisable(true);
            }

        });
    }
    public static void listenerMinhasMaterias(ChoiceBox<String> choiceBoxMinhasMaterias, Label labelTipoMinhasMaterias, EstudoRepository estudoRepository, Conta conta) {
        choiceBoxMinhasMaterias.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                labelTipoMinhasMaterias.setVisible(true);
                EstudosService.carregaTipoMinhasMaterias(labelTipoMinhasMaterias, choiceBoxMinhasMaterias, estudoRepository, conta);
            } else {
                labelTipoMinhasMaterias.setVisible(false);
            }
        });
    }
}
