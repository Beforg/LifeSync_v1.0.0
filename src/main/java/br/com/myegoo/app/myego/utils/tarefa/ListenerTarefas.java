package br.com.myegoo.app.myego.utils.tarefa;

import br.com.myegoo.app.myego.controller.ApplicationController;
import br.com.myegoo.app.myego.model.tarefa.TabelaTarefas;
import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.TarefaRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import br.com.myegoo.app.myego.service.TarefaService;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ListenerTarefas {
    private static TarefasUtil tarefasUtil = new TarefasUtil();
    public static void listenerTabelaTarefas(TableView<TabelaTarefas> tabelaTarefas,
                                             Label muralNomeTarefa,
                                             Label muralDataTarefa,
                                             Label muralPrioridadeTarefa,
                                             Label muralCategoriaTarefa,
                                             Label muralDescricaoTarefa,
                                             TarefaRepository tarefaRepository,
                                             TextField nome,
                                             DatePicker data,
                                             ChoiceBox<String> prioridade,
                                             ChoiceBox<String> categoria,
                                             Button editar,
                                             Button excluir,
                                             Button salvar,
                                             TextArea textAreaEditarTarefa,
                                             CheckBox checkBoxConcluido) {
        tabelaTarefas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate setData = LocalDate.parse(newValue.getData(),formatter);
                TabelaTarefas tarefaSelecionada = newValue;
                muralNomeTarefa.setText(tarefaSelecionada.getNome());
                muralDataTarefa.setText(formatter.format(setData));
                muralPrioridadeTarefa.setText(tarefaSelecionada.getPrioridade());
                muralCategoriaTarefa.setText(tarefaSelecionada.getCategoria());
                textAreaEditarTarefa.setText(tarefaRepository.findByNomeAndId(tarefaSelecionada.getNome(), ApplicationController.conta.getId()).getDescricao());
                checkBoxConcluido.setSelected(tarefaRepository.findByNomeAndId(tarefaSelecionada.getNome(), ApplicationController.conta.getId()).isConcluida());
                checkBoxConcluido.setVisible(true);
                muralDataTarefa.setVisible(true);
                muralPrioridadeTarefa.setVisible(true);
                muralCategoriaTarefa.setVisible(true);
                data.setValue(setData);
                muralNomeTarefa.setVisible(true);


                nome.setText(tarefaSelecionada.getNome());

                prioridade.setValue(tarefaSelecionada.getPrioridade());
                categoria.setValue(tarefaSelecionada.getCategoria());


                TarefaService.descricaoTarefa(tarefaRepository, muralDescricaoTarefa, tarefaSelecionada);
                TarefasUtil.mostrarEditarTarefa(textAreaEditarTarefa,nome, data, prioridade, categoria, editar, excluir, salvar, false);
            }
        });
    }
    public static void listenerCheckBoxTarefas(CheckBox checkBoxConcluido, Label nomeTarefa, TarefaRepository tarefaRepository) {
        checkBoxConcluido.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                checkBoxConcluido.setText("Concluído");
                Tarefa tarefa = tarefaRepository.findByNomeAndId(nomeTarefa.getText(), ApplicationController.conta.getId());
                tarefa.setConcluido(true);
                tarefaRepository.save(tarefa);
            } else {
                checkBoxConcluido.setText("Não Concluído");
                Tarefa tarefa = tarefaRepository.findByNomeAndId(nomeTarefa.getText(), ApplicationController.conta.getId());
                tarefa.setConcluido(false);
                tarefaRepository.save(tarefa);
            }
        });
    }
    public static void listenerCheckBoxTarefasSemana(CheckBox selecionadoSemanaConculido, Label selecionadoSemanaNome, TarefaRepository tarefaRepository) {
        selecionadoSemanaConculido.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                selecionadoSemanaConculido.setText("Concluído");
                Tarefa tarefa = tarefaRepository.findByNomeAndId(selecionadoSemanaNome.getText(), ApplicationController.conta.getId());
                tarefa.setConcluido(true);
                tarefaRepository.save(tarefa);
            } else {
                selecionadoSemanaConculido.setText("Não Concluído");
                Tarefa tarefa = tarefaRepository.findByNomeAndId(selecionadoSemanaNome.getText(), ApplicationController.conta.getId());
                tarefa.setConcluido(false);
                tarefaRepository.save(tarefa);
            }
        });
    }
    public static void listenerListaDoCalendario(GridPane gridNovaTarefa,
                                                 GridPane gridCategoria,
                                                 GridPane gridTarefaSelecionadaCalendario,
                                                 ListView<String> lista,
                                                 Label selecionadaCalendarioNome,
                                                 Label selecionadaCalendarioData,
                                                 Label selecionadaCalendarioPrioridade,
                                                 Label selecionadaCalendarioCategoria,
                                                 CheckBox selecionadaCalendarioConcluido,
                                                 TarefaRepository tarefaRepository,
                                                 GridPane gridTarefaSelecionadaSemana) {
        lista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                gridNovaTarefa.setVisible(false);
                gridCategoria.setVisible(false);
                gridTarefaSelecionadaCalendario.setVisible(true);
                gridTarefaSelecionadaSemana.setVisible(false);
                mostraInformacoesDaLista(selecionadaCalendarioPrioridade, selecionadaCalendarioCategoria, selecionadaCalendarioData, selecionadaCalendarioNome, selecionadaCalendarioConcluido, tarefaRepository, newValue);
            } else if (lista.getItems().isEmpty()) {
                gridTarefaSelecionadaCalendario.setVisible(false);
            }
        });
    }
    public static void listenerVerificaPaneTarefas(GridPane gridTarefaSelecionadaCalendario,GridPane gridNovaTarefa) {
        gridNovaTarefa.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                gridTarefaSelecionadaCalendario.setVisible(false);
            }
        });

    }
    public static void listenerFiltro(Label selecionadaTarefaConcluida,Label selecionadaTarefaNumero,ChoiceBox<String> choiceBox, ChoiceBox<String> subFiltro, CategoriaRepository categoriaRepository, TableView<TabelaTarefas> tabelaTarefas, TarefaRepository tarefaRepository, ObservableList<TabelaTarefas> list) {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (ApplicationController.conta.getId() != null) {
              CarregaDadosService.carregaTarefas(selecionadaTarefaConcluida,selecionadaTarefaNumero,list, tarefaRepository, tabelaTarefas, ApplicationController.conta.getId(), choiceBox,subFiltro);
            }
            String filtrado = choiceBox.getValue();
            switch(filtrado) {
                case "Todas", "Concluídas","Não concluídas", "Atrasadas":
                    subFiltro.setDisable(true);
                    break;
                case "Prioridade", "Data":
                    TarefasUtil.setSubFiltroPrioridade(subFiltro,choiceBox);
                    subFiltro.setDisable(false);
                    break;
                case "Categoria":
                    CarregaDadosService.carregaCategorias(subFiltro, categoriaRepository);
                    subFiltro.setDisable(false);
                    break;
            }
        });

    }
    public static void listenerExibirChoiceBoxFiltroTarefa(HBox hboxFiltro, Pane muralCalendario) {
    muralCalendario.visibleProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue) {
            hboxFiltro.setVisible(false);

        } else {
            hboxFiltro.setVisible(true);
        }
    });
    }
    public static void mostraLabelDaSemana(Label muralCalendarioDia, GridPane gridSemana, Label muralCalendarioMesAtual) {
        gridSemana.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                muralCalendarioDia.setVisible(false);
                muralCalendarioMesAtual.setVisible(false);
            } else {
                muralCalendarioDia.setVisible(true);
                muralCalendarioMesAtual.setVisible(true);
            }
        });
    }
    public static void mostraMuralCalendarioSemana(GridPane gridCategoria,
                                                   GridPane gridTarefaSelecionadaSemana,
                                                   GridPane gridaTarefaSelecionadaCalendario,
                                                   GridPane gridSemana,
                                                   GridPane muralSemanaGridPane,
                                                   VBox muralSemanaVBoxLista,
                                                   GridPane muralCalendarioGridPane,
                                                   VBox muralCalendarioVBoxLista,
                                                   GridPane gridNovaTarefa) {
        gridSemana.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                if (gridCategoria.isVisible() || gridNovaTarefa.isVisible()) {

                } else {
                    muralSemanaGridPane.setVisible(true);
                    muralSemanaVBoxLista.setVisible(true);
                    muralCalendarioGridPane.setVisible(false);
                    muralCalendarioVBoxLista.setVisible(false);
                    gridTarefaSelecionadaSemana.setVisible(true);
                    gridaTarefaSelecionadaCalendario.setVisible(false);
                }


            } else {
                if (gridCategoria.isVisible() || gridNovaTarefa.isVisible()) {
                    gridTarefaSelecionadaSemana.setVisible(false);
                    gridaTarefaSelecionadaCalendario.setVisible(false);
                } else {
                    muralSemanaGridPane.setVisible(false);
                    muralSemanaVBoxLista.setVisible(false);
                    muralCalendarioGridPane.setVisible(true);
                    muralCalendarioVBoxLista.setVisible(true);
                    gridTarefaSelecionadaSemana.setVisible(false);
                    gridaTarefaSelecionadaCalendario.setVisible(true);
                }
            }
        });

    }
    public static void listenerListaDaSemana(GridPane gridCategoria,
                                             Label selecionadaSemanaPrioridade,
                                             Label selecionadoSemanaCategoria,
                                             Label selecionadoSemanaData,
                                             Label selecionadoSemanaNome,
                                             CheckBox selecionadoSemanaConcluido,
                                             ListView<String> muralSemanaLista,
                                             TarefaRepository tarefaRepository,
                                             GridPane gridNovaTarefa,
                                             GridPane gridTarefaSelecionadaSemana) {
        muralSemanaLista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            gridNovaTarefa.setVisible(false);
            gridCategoria.setVisible(false);
            gridTarefaSelecionadaSemana.setVisible(true);
            if (newValue != null) {
                mostraInformacoesDaLista(selecionadaSemanaPrioridade, selecionadoSemanaCategoria, selecionadoSemanaData, selecionadoSemanaNome, selecionadoSemanaConcluido, tarefaRepository, newValue);
            }
        });
        }

    private static void mostraInformacoesDaLista(Label selecionadaSemanaPrioridade, Label selecionadoSemanaCategoria, Label selecionadoSemanaData, Label selecionadoSemanaNome, CheckBox selecionadoSemanaConcluido, TarefaRepository tarefaRepository, String newValue) {
        Tarefa tarefa = tarefaRepository.findByNomeAndId(newValue, ApplicationController.conta.getId());
        selecionadoSemanaNome.setText(tarefa.getNome());
        selecionadoSemanaData.setText(tarefasUtil.formataData(tarefa.getData()));
        selecionadaSemanaPrioridade.setText(tarefa.getPrioridade());
        selecionadoSemanaCategoria.setText(tarefa.getCategoria());
        selecionadoSemanaConcluido.setSelected(tarefa.isConcluida());
    }
    public static void listenerNomeDaTarefaParaDescricao(Label selecionadoTipoTarefa,Label selecionadoTipoDescricao, TarefaRepository tarefaRepository) {
        selecionadoTipoTarefa.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                TarefasUtil.toolTipDescricaoTarefa(selecionadoTipoTarefa, selecionadoTipoDescricao, tarefaRepository);
            }
        });
    }
}

