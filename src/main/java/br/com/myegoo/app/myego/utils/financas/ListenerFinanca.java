package br.com.myegoo.app.myego.utils.financas;

import br.com.myegoo.app.myego.model.financas.TabelaFinanca;
import br.com.myegoo.app.myego.repository.FinancaRepository;
import br.com.myegoo.app.myego.repository.RegistroFinancaRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

public class ListenerFinanca {
    public static void listenerAlterarRenda(RadioButton rbAlterarRenda,
                                            Button btSalvarNovaRenda,
                                            TextField tfNovaRenda,
                                            Label renda) {
        rbAlterarRenda.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (rbAlterarRenda.isSelected()) {
                btSalvarNovaRenda.setVisible(true);
                tfNovaRenda.setVisible(true);
                renda.setVisible(false);
            } else {
                btSalvarNovaRenda.setVisible(false);
                tfNovaRenda.setVisible(false);
                renda.setVisible(true);
            }
        });
    }

    public static void criarNovaCategoriaGastos(RadioButton radioButton,
                                                TextField textField,
                                                ChoiceBox<String> choiceBox) {
        radioButton.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) {
                textField.setVisible(true);
                choiceBox.setVisible(false);
            } else {
                textField.setVisible(false);
                choiceBox.setVisible(true);
            }
        });
    }
    public static void listenerMesSelecionad(DatePicker datePicker,
                                             TableView<TabelaFinanca> tabelaFinanca,
                                             ObservableList<TabelaFinanca> listaTabela,
                                             RegistroFinancaRepository registroFinancaRepository,
                                             FinancaRepository financaRepository,
                                             Label gastosLabel,
                                             Label saldo) {
        datePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            CarregaDadosService.carregaDadosDaTabelaFinancas(tabelaFinanca, listaTabela, registroFinancaRepository,
                    financaRepository, datePicker, gastosLabel, saldo);
        });
    }

}
