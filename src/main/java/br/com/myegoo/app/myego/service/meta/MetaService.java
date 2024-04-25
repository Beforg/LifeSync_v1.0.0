package br.com.myegoo.app.myego.service.meta;

import br.com.myegoo.app.myego.model.meta.Meta;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.MetaRepository;
import br.com.myegoo.app.myego.service.CategoriaService;
import br.com.myegoo.app.myego.utils.metas.MetaUtil;
import br.com.myegoo.app.myego.utils.ValidacaoDados;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

@Service
public class MetaService {
    public static void criarMeta(MetaRepository metaRepository,
                                 TextField tfNomeCriarMeta,
                                 ChoiceBox<String> choiceBoxCriarMeta,
                                 DatePicker datePickerCriarMeta,
                                 Label labelAvisosNovaMeta) {
        if (tfNomeCriarMeta.getText().isEmpty() || choiceBoxCriarMeta.getValue().isEmpty() || datePickerCriarMeta.getValue() == null) {
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Preencha todos os campos");
            throw new TratadorDeErros("Preencha todos os campos");
        }
        Meta meta = new Meta(tfNomeCriarMeta.getText(), datePickerCriarMeta.getValue(), choiceBoxCriarMeta.getValue(), "Não iniciada");
        try {
            ValidacaoDados.validarDataInserida(datePickerCriarMeta, labelAvisosNovaMeta);
            metaRepository.save(meta);
            labelAvisosNovaMeta.setTextFill(Color.GREEN);
            labelAvisosNovaMeta.setText("Meta criada com sucesso");
            tfNomeCriarMeta.clear();
            choiceBoxCriarMeta.setValue(null);
            datePickerCriarMeta.setValue(null);
        } catch (Exception e) {
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Erro ao criar meta");
            throw new TratadorDeErros("Erro ao criar meta");
        }

    }
    public static void criarArea(ChoiceBox<String> choiceBox,TextField tfCriarArea, Label labelAvisosNovaMeta, CategoriaRepository categoriaRepository) {
        if (tfCriarArea.getText().isEmpty()) {
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Preencha o campo");
            throw new TratadorDeErros("Preencha o campo");
        }
        CategoriaService.salvarCategoria(categoriaRepository, tfCriarArea, "Área");
        labelAvisosNovaMeta.setTextFill(Color.GREEN);
        labelAvisosNovaMeta.setText("nova Área criada com sucesso!");
        tfCriarArea.setText("");
        choiceBox.setValue("");

    }
    public static void carregaDadosParaEdicao(ChoiceBox<String> choiceBoxStatusMeta,Label labelAvisosNovaMeta,TextField textField, DatePicker datePicker, ChoiceBox<String> choiceBox, MetaRepository metaRepository, ListView<String> lista) {
        if (lista.getSelectionModel().getSelectedItem() == null) {
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Selecione uma meta para editar!");
            throw new TratadorDeErros("Selecione uma meta");
        } else {
            choiceBoxStatusMeta.getItems().add("Não iniciada");
            choiceBoxStatusMeta.getItems().add("Em andamento");
            choiceBoxStatusMeta.getItems().add("Concluída");
            Meta meta = metaRepository.findByNome(lista.getSelectionModel().getSelectedItem());
            textField.setText(meta.getNome());
            datePicker.setValue(meta.getData());
            choiceBox.setValue(meta.getTipo());
        }
    }
    public static void salvarEdicaoDeMeta(ChoiceBox<String> choiceBoxStatusMeta,Label labelAvisosNovaMeta,TextField textField, DatePicker datePicker, ChoiceBox<String> choiceBox, MetaRepository metaRepository) {
        boolean submetasConcluidas = metaRepository.findByNome(textField.getText()).getSubmetas().stream().allMatch(submeta -> submeta.getStatus().equals("Concluída"));
        if (textField.getText().isEmpty() || choiceBox.getValue().isEmpty() || datePicker.getValue() == null || choiceBoxStatusMeta.getValue() == null){
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Preencha todos os campos");
            throw new TratadorDeErros("Preencha todos os campos");
        }
        Meta meta = metaRepository.findByNome(textField.getText());
        meta.setNome(textField.getText());
        meta.setData(datePicker.getValue());
        meta.setTipo(choiceBox.getValue());
        meta.setStatus(choiceBoxStatusMeta.getValue());

        if(choiceBoxStatusMeta.getValue().equals("Concluída") && !submetasConcluidas) {
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Não é possível concluir a meta, pois existem submetas não concluídas");
            throw new TratadorDeErros("Não é possível concluir a meta, pois existem submetas não concluídas");
        }
        
        try {
            metaRepository.save(meta);
            labelAvisosNovaMeta.setTextFill(Color.GREEN);
            labelAvisosNovaMeta.setText("Meta editada com sucesso");
            textField.clear();
            choiceBox.setValue(null);
            datePicker.setValue(null);
        } catch (Exception e) {
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Erro ao editar meta");
            throw new TratadorDeErros("Erro ao editar meta");
        }
    }
    public static void apagarMeta(MetaRepository metaRepository, ListView<String> lista, Label labelAvisosNovaMeta) {
        if (lista.getSelectionModel().getSelectedItem() == null) {
            labelAvisosNovaMeta.setTextFill(Color.RED);
            labelAvisosNovaMeta.setText("Selecione uma meta para apagar!");
            throw new TratadorDeErros("Selecione uma meta");
        } else {
            if(MetaUtil.verificaSeMetaTemSubmetas(labelAvisosNovaMeta,metaRepository, lista)) {
                Meta meta = metaRepository.findByNome(lista.getSelectionModel().getSelectedItem());
                metaRepository.delete(meta);
                labelAvisosNovaMeta.setTextFill(Color.GREEN);
                labelAvisosNovaMeta.setText("Meta apagada com sucesso!");
            }
        }
    }

}
