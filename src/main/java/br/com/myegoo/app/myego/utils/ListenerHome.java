package br.com.myegoo.app.myego.utils;

import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import br.com.myegoo.app.myego.repository.HabitosRepository;
import br.com.myegoo.app.myego.repository.TarefaRepository;
import br.com.myegoo.app.myego.repository.TreinoRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

public class ListenerHome {
    public static void listenerTarefas(ListView<String> tarefa,
                                       Label homeNomeTarefa,
                                       Label homeCategoriaTarefa,
                                       Label homePrioridadeTarefa,
                                       Label homeStatusTarefa,
                                       TarefaRepository tarefaRepository) {
        tarefa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Tarefa tarefaSelecionada = tarefaRepository.findByNome(newValue);
                homeNomeTarefa.setText(tarefaSelecionada.getNome());
                homeCategoriaTarefa.setText(tarefaSelecionada.getCategoria());
                homePrioridadeTarefa.setText(tarefaSelecionada.getPrioridade());
                homeStatusTarefa.setText(tarefaSelecionada.isConcluida() ? "Concluída" : "Pendente");

            }
        });
    }

    public static void listenerHabitos(ListView<String> homeHabitosLista,
                                       Label homeHabitosNome,
                                       Label homeStatusHabito,
                                       HabitosRepository habitosRepository,
                                       Label pegaDataHabito) {

        homeHabitosLista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate data = LocalDate.parse(pegaDataHabito.getText());
                homeHabitosNome.setText(newValue);
                homeStatusHabito.setText(habitosRepository.findByNome(newValue,LocalDate.now()).isConcluido() ? "Concluído" : "Pendente");
            }
        });
    }
    public static void listenerTreinos(ListView<String> homeHabitosLista,
                                       Label homeTipoTreino,
                                       Label homeStatusTreino,
                                       TreinoRepository treinoRepository) {
        homeHabitosLista.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                homeTipoTreino.setText(newValue);
                homeStatusTreino.setText(treinoRepository.findByNome(newValue).getConcluido() ? "Concluído" : "Pendente");
            }
        });
    }
    public static void listenerSelecionaData(DatePicker homeSelecionarDiaProgresso,
                                             ListView<String> homeTreinosLista,
                                             ListView<String> homeTarefasLista,
                                             ListView<String>homeHabitosLista,
                                             ProgressBar homeProgressBar,
                                             TreinoRepository treinoRepository ,
                                             TarefaRepository tarefaRepository,
                                             HabitosRepository habitosRepository,
                                             Label progressoHomeLabel) {
        homeSelecionarDiaProgresso.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                CarregaDadosService.carregarDadosDaTelaInicial(newValue,homeTreinosLista,homeTarefasLista,
                        homeHabitosLista,homeProgressBar,treinoRepository,tarefaRepository,habitosRepository,progressoHomeLabel);
            }
        });
    }
}

