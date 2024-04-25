package br.com.myegoo.app.myego.utils.metas;


import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.repository.MetaRepository;
import br.com.myegoo.app.myego.repository.SubmetaRepository;
import br.com.myegoo.app.myego.repository.TarefaRepository;
import br.com.myegoo.app.myego.service.TarefaService;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Component;

@Component
public class MetaUtil {


    public static boolean verificaSeMetaTemSubmetas(Label labelAvisos ,MetaRepository metaRepository, ListView<String> lista) {
        if (lista.getSelectionModel().getSelectedItem() == null) {
            throw new RuntimeException("Selecione uma meta");
        } else {
            if (!metaRepository.findByNome(lista.getSelectionModel().getSelectedItem()).getSubmetas().isEmpty()) {
                labelAvisos.setTextFill(Color.RED);
                labelAvisos.setText("A meta selecionada possui submetas e não pode ser apagada.");
                throw new RuntimeException("A meta selecionada possui submetas");
            }
        }
        return true;
    }

    public static void criarTarefaEmMeta(Label labelInformativa,
                                         TarefaRepository tarefaRepository,
                                         TextField tfNomeTarefa,
                                         TextArea textArea,
                                         DatePicker dataTarefa,
                                         ChoiceBox<String> prioridadeTarefa,
                                         ChoiceBox<String> categoriaTarefa,
                                         ChoiceBox<String> submeta,
                                         Conta conta,
                                         SubmetaRepository subetaRepository) {
        if (tfNomeTarefa.getText().isEmpty() || dataTarefa.getValue() == null
                || prioridadeTarefa.getValue().isEmpty() || categoriaTarefa.getValue().isEmpty()) {
            labelInformativa.setTextFill(Color.RED);
            labelInformativa.setText("Preencha todos os campos");
            throw new RuntimeException("Preencha todos os campos");
        }
        TarefaService.salvarTarefa(tarefaRepository,tfNomeTarefa,textArea,dataTarefa,prioridadeTarefa,categoriaTarefa,conta,submeta,subetaRepository, new Label());
        labelInformativa.setTextFill(Color.GREEN);
        labelInformativa.setText("Tarefa criada com sucesso para a submeta " + submeta.getValue());
    }
    public static void editSubmetaView(VBox vBoxEditaSubmeta,
                                       Label sobreNomeSubmeta,
                                       Label sobreDataSubmeta,
                                       Label sobreRelacionadoSubmeta,
                                       Label sobreStatusSubmeta,
                                       Button btSalvarEdicaoSubmeta,
                                       boolean b) {
        vBoxEditaSubmeta.setVisible(b);
        sobreNomeSubmeta.setVisible(!b);
        sobreDataSubmeta.setVisible(!b);
        sobreRelacionadoSubmeta.setVisible(!b);
        sobreStatusSubmeta.setVisible(!b);
        btSalvarEdicaoSubmeta.setVisible(b);
    }
}
