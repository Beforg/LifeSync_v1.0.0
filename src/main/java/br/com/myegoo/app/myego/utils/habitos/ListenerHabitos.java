package br.com.myegoo.app.myego.utils.habitos;

import br.com.myegoo.app.myego.model.Habitos;
import br.com.myegoo.app.myego.repository.HabitosRepository;
import javafx.scene.control.*;

import java.time.LocalDate;

public class ListenerHabitos {
    public static void listenerRadioButton(RadioButton rb,
                                           TextField textField,
                                           ChoiceBox<String> cbEscolheHabito) {
        rb.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.setVisible(true);
                cbEscolheHabito.setVisible(false);
            } else {
                textField.setVisible(false);
                cbEscolheHabito.setVisible(true);
            }
        });
    }
    public static void listenerInfosListaHabito(CheckBox checkBoxHabitos,
                                                HabitosRepository habitosRepository,
                                                ListView<String> listView,
                                                Button btRemoverHabito,
                                                Label pegaDataHabito,
                                                Label guardaNomeHabito) {

        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                LocalDate data = LocalDate.parse(pegaDataHabito.getText());
                guardaNomeHabito.setText(newValue);
                Habitos habitoSelecionado = habitosRepository.findByNome(newValue,data);
                checkBoxHabitos.setSelected(habitoSelecionado.isConcluido());

                checkBoxHabitos.setDisable(false);
                btRemoverHabito.setDisable(false);

            } else {
                checkBoxHabitos.setDisable(true);
                btRemoverHabito.setDisable(true);
            }
        });
    }
    public static void listenerRemoverHabito(ChoiceBox<String>  cbSelecionarHabito,
                                             Button btRemoverHabito) {
        cbSelecionarHabito.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                btRemoverHabito.setDisable(false);
            } else {
                btRemoverHabito.setDisable(true);
            }
        });
    }
    public static void listenerCheckHabito(CheckBox checkBoxHabitos,
                                           Label guardaNomeHabito,
                                           Label dataHabito,
                                           HabitosRepository habitosRepository) {
        checkBoxHabitos.selectedProperty().addListener((observable1, oldValue1, newValueHabito) -> {
            Habitos habitoSelecionado = habitosRepository.findByNome(guardaNomeHabito.getText(), LocalDate.parse(dataHabito.getText()));
            if (newValueHabito) {
                habitoSelecionado.setConcluido(true);
                habitosRepository.save(habitoSelecionado);
            } else {
                habitoSelecionado.setConcluido(false);
                habitosRepository.save(habitoSelecionado);
            }
        });
    }
}
