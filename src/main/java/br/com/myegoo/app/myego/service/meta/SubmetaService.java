package br.com.myegoo.app.myego.service.meta;

import br.com.myegoo.app.myego.model.meta.Meta;
import br.com.myegoo.app.myego.model.meta.Submeta;
import br.com.myegoo.app.myego.repository.MetaRepository;
import br.com.myegoo.app.myego.repository.SubmetaRepository;
import br.com.myegoo.app.myego.utils.ValidacaoDados;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

@Service
public class SubmetaService {
    public static void criarSubmeta(SubmetaRepository submetaRepository,
                                    ChoiceBox<String> choiceBoxCriaSubmeta,
                                    TextField tfCriaSubmeta,
                                    DatePicker datePickerSubmeta,
                                    MetaRepository metaRepository,
                                    Label labelAvisosSubmeta) {
        Submeta submeta = new Submeta(tfCriaSubmeta.getText(), datePickerSubmeta.getValue(),"Não iniciada");
        ValidacaoDados.validarDataInserida(datePickerSubmeta, labelAvisosSubmeta);
        Meta metaSelecionada = metaRepository.findByNome(choiceBoxCriaSubmeta.getValue());
        submeta.setMeta(metaSelecionada);
        if (tfCriaSubmeta.getText().isEmpty()) {
            labelAvisosSubmeta.setTextFill(Color.RED);
            labelAvisosSubmeta.setText("Preencha o campo nome");
            throw new RuntimeException("Preencha o campo nome");
        }
        try {
            submetaRepository.save(submeta);
            tfCriaSubmeta.setText("");
            labelAvisosSubmeta.setTextFill(Color.GREEN);
            labelAvisosSubmeta.setText("Submeta criada com sucesso");
            datePickerSubmeta.setValue(null);
            choiceBoxCriaSubmeta.setValue(null);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao salvar submeta");
        }
    }
    public static void salvarEdicaoSubmeta(Label labelDeAvisos,
                                           Label submetaSelecionada,
                                           TextField tfEditaSubmeta,
                                           DatePicker datePickerEditaSubmeta,
                                           ChoiceBox<String> cbRelacionadoEditaSubmeta,
                                           ChoiceBox<String> cbStatusEditaSubmeta,
                                           SubmetaRepository submetaRepository,
                                           MetaRepository metaRepository,
                                           ListView<String> listaDasMinhasSubmetasView,
                                           Label labelAvisosSubmeta) {

        Submeta submeta = submetaRepository.findByNome(submetaSelecionada.getText());
        submeta.setNome(tfEditaSubmeta.getText());
        submeta.setData(datePickerEditaSubmeta.getValue());
        submeta.setStatus(cbStatusEditaSubmeta.getValue());
        Meta meta = metaRepository.findByNome(cbRelacionadoEditaSubmeta.getValue());
        submeta.setMeta(meta);
        try {
            ValidacaoDados.validarDataInserida(datePickerEditaSubmeta, labelDeAvisos);
            submetaRepository.save(submeta);
            labelDeAvisos.setTextFill(Color.GREEN);
            listaDasMinhasSubmetasView.getItems().clear();
            labelDeAvisos.setText("Edição salva com sucesso");
            tfEditaSubmeta.setText("");
            datePickerEditaSubmeta.setValue(null);
            cbRelacionadoEditaSubmeta.setValue(null);
        } catch (Exception e) {
            labelDeAvisos.setTextFill(Color.RED);
            labelDeAvisos.setText("Erro ao salvar edição");
            throw new RuntimeException("Erro ao salvar edição");
        }

    }
    public static void apagarSubmeta(Label labelDeAvisos,
                                     SubmetaRepository submetaRepository,
                                     ListView<String> listaDasMinhasSubmetasView,
                                     MetaRepository metaRepository) {
        Submeta submeta = submetaRepository.findByNome(listaDasMinhasSubmetasView.getSelectionModel().getSelectedItem());
        System.out.println(submeta.getNome());
        try {
            submetaRepository.deleteByNome(submeta.getNome());
            labelDeAvisos.setTextFill(Color.GREEN);
            labelDeAvisos.setText("Submeta apagada com sucesso");
            listaDasMinhasSubmetasView.getItems().clear();
        } catch (Exception e) {
            labelDeAvisos.setTextFill(Color.RED);
            labelDeAvisos.setText("Erro ao apagar submeta, verifique se a submeta não possui tarefas associadas");
            throw new RuntimeException("Erro ao apagar submeta");
        }
    }
}
