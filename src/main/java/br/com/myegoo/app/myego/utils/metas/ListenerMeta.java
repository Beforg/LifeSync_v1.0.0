package br.com.myegoo.app.myego.utils.metas;

import br.com.myegoo.app.myego.repository.MetaRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.hibernate.annotations.View;

import java.util.List;

public class ListenerMeta {
    public static void listenerTelaInicial(Pane paneSubmetas, ListView<String> listaTarefasAssociadas,ListView<String> listaTarefaNaoAssociada, Pane paneNovaTarefaSubmeta){
        paneSubmetas.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                listaTarefasAssociadas.setVisible(false);
                listaTarefaNaoAssociada.setVisible(false);
                paneNovaTarefaSubmeta.setVisible(true);
            } else {
                listaTarefasAssociadas.setVisible(false);
                listaTarefaNaoAssociada.setVisible(false);
                paneNovaTarefaSubmeta.setVisible(false);
            }
        });
    }
    public static void listenerFiltrarMetaPorData(ChoiceBox<String> periodoSelecionado, DatePicker dataSelecionada, MetaRepository metaRepository, ListView<String> listaDasMetas) {
        periodoSelecionado.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                dataSelecionada.setDisable(false);
                dataSelecionada.valueProperty().addListener((observable1, oldValue1, newValue1) -> {
                    CarregaDadosService.carregaPorDataFiltroDaMeta(periodoSelecionado, listaDasMetas, metaRepository,dataSelecionada);
                });
            } else {
                dataSelecionada.setDisable(true);
            }
        });
    }
    public static void listenerMostraInformacaoDaMetaSelecionada(ListView<String> listaDasMinhasMetaView,
                                                                 Label sobreNomeMeta,
                                                                 Label sobreStatusMeta,
                                                                 Label sobreDiasRestantes,
                                                                 Label sobreAreaMeta,
                                                                 MetaRepository metaRepository) {
        listaDasMinhasMetaView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                CarregaDadosService.carregarInformacoesTelaDeMetas(listaDasMinhasMetaView,metaRepository,sobreNomeMeta,sobreStatusMeta,sobreDiasRestantes,sobreAreaMeta);
            }
        });
    }
}
