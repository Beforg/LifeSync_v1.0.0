package br.com.myegoo.app.myego.utils;


import br.com.myegoo.app.myego.repository.HabitosRepository;
import br.com.myegoo.app.myego.repository.RegistroEstudosRepository;
import br.com.myegoo.app.myego.repository.TarefaRepository;
import br.com.myegoo.app.myego.repository.TreinoRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class ListenerEstatisticasDados {
        public static void listenerAnoSelecionado(DatePicker dpEstatisticaDoAno,
                                                 Label anoTotalTarefa,
                                                 Label anoTotalTarefaConcluido,
                                                 Label anoDesempenhoTarefa,
                                                 Label anoEstudos,
                                                 Label anoHorasEstudos,
                                                 Label anoMediaEstudos,
                                                 Label anoTreinos,
                                                 Label anoTotalHorasTreino,
                                                 Label anoMediaTreinos,
                                                 Label anoHabitos,
                                                 Label anoHabitosConcluidos,
                                                 Label anoDesempenhoHabitos,
                                                 DatePicker datePicker,
                                                 TarefaRepository tarefaRepository,
                                                 RegistroEstudosRepository registroEstudosRepository,
                                                 TreinoRepository treinoRepository,
                                                 HabitosRepository habitosRepository) {
        dpEstatisticaDoAno.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                CarregaDadosService.carregaEstatisticasPorAno(anoTotalTarefa,anoTotalTarefaConcluido, anoDesempenhoTarefa,
                        anoEstudos, anoHorasEstudos, anoMediaEstudos, anoTreinos, anoTotalHorasTreino, anoMediaTreinos,
                        anoHabitos, anoHabitosConcluidos, anoDesempenhoHabitos, datePicker, tarefaRepository,
                        registroEstudosRepository, treinoRepository, habitosRepository);
            }
        });
        }
    public static void alteraDadosView(Label meusDadosNome,
                                       Label meusDadosDataNascimento,
                                       TextField tfAlteraNomeDados,
                                       DatePicker dpAlteraDataDados,
                                       Button botaoVoltar,
                                       Button botaoSalvar,
                                       boolean label,
                                       boolean componentes) {
            meusDadosNome.setVisible(label);
            meusDadosDataNascimento.setVisible(label);
            tfAlteraNomeDados.setVisible(componentes);
            dpAlteraDataDados.setVisible(componentes);
            botaoVoltar.setVisible(componentes);
            botaoSalvar.setVisible(componentes);
    }
    public static void alteraDadosContaView(Button btCancelarTrocaSenhaUser,
                                            Button btSalvarAlteracao,
                                            GridPane gridPaneAlteraNomeUsuario,
                                            GridPane gridPaneAlteraSenha,
                                            boolean usuario,
                                            boolean senha,
                                            boolean botoes) {
            btCancelarTrocaSenhaUser.setVisible(botoes);
            btSalvarAlteracao.setVisible(botoes);
            gridPaneAlteraNomeUsuario.setVisible(usuario);
            gridPaneAlteraSenha.setVisible(senha);
    }
}
