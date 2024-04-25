package br.com.myegoo.app.myego.utils;

import br.com.myegoo.app.myego.interfaces.IPadraoDaData;
import br.com.myegoo.app.myego.interfaces.ITabela;
import br.com.myegoo.app.myego.model.Categoria;
import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.treino.Treino;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.service.CategoriaService;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

import java.time.LocalDate;

public class TreinoUtil implements IPadraoDaData, ITabela {
    public static void trocaTelasControle(GridPane gridPaneInicialTreinos,
                                          GridPane gridPaneAtividadesNovoTreino,
                                          GridPane gridPaneAtividadesMeusTreinos,
                                          boolean nt,
                                          boolean mt,
                                          boolean it) {
        gridPaneAtividadesNovoTreino.setVisible(nt);
        gridPaneAtividadesMeusTreinos.setVisible(mt);
        gridPaneInicialTreinos.setVisible(it);
    }
    public static void exibirEditarTreino(Button novoTreino,
                                          Button botaoVoltarEditarTreino,
                                          GridPane gridPaneNovoTreino,
                                          GridPane gridPaneAtividadesMeusTreinos,
                                          boolean visivel) {
        botaoVoltarEditarTreino.setVisible(visivel);
        gridPaneNovoTreino.setVisible(visivel);
        gridPaneAtividadesMeusTreinos.setVisible(!visivel);
        novoTreino.setDisable(visivel);


    }
    public static void exibirCriarNovoTipo(TextField textField, ChoiceBox<String> choiceBox, RadioButton radioButton) {
        if (radioButton.isSelected()) {
            textField.setVisible(true);
            choiceBox.setVisible(false);
        } else {
            textField.setVisible(false);
            choiceBox.setVisible(true);
        }

    }
    public static Treino setarTreino(String nome, LocalDate data, String tipo, String descricao, String tempo, boolean concluido) {
        return new Treino(nome, data, concluido, descricao, tempo, tipo);
    }
    public static void limparDadosSalvosDoTreino(TextField tfNomeTreino,
                                                 DatePicker dpDataTreino,
                                                 ChoiceBox<String> cbTipoTreino,
                                                 TextArea taDescricaoTreino,
                                                 TextField tfNovoTipoTreino,
                                                 RadioButton rbNovoTipoTreino) {
        tfNomeTreino.clear();
        dpDataTreino.setValue(null);
        cbTipoTreino.setValue(null);
        taDescricaoTreino.clear();
        tfNovoTipoTreino.clear();
        rbNovoTipoTreino.setSelected(false);
    }
    public static void salvaTipoDeTreinoNovo(ChoiceBox<String> cbTipoTreino, TextField tfNovoTipoTreino,
                                             CategoriaRepository categoriaRepository, Label labelAvisosTreino) {
        if (!cbTipoTreino.isVisible()) {
            Categoria categoria = categoriaRepository.findByNome(tfNovoTipoTreino.getText());
            if(categoria != null) {
                labelAvisosTreino.setText("Tipo de treino já cadastrado!");
                labelAvisosTreino.setTextFill(Color.RED);
                tfNovoTipoTreino.setVisible(false);
                throw new TratadorDeErros("Tipo de treino já cadastrado!");
            }
            CategoriaService.salvarCategoria(categoriaRepository, tfNovoTipoTreino, "Treino");
        }
    }
    public static void filtraTreinos(int qtdDeTreinoFiltrado, Label labelTreinosFiltrados, ObservableList<TabelaTreino> treinos) {
        for (TabelaTreino treino : treinos) {
            qtdDeTreinoFiltrado++;
        }
        labelTreinosFiltrados.setText("Treinos filtrados: " + qtdDeTreinoFiltrado);
    }
    public static void exibeRegistroCompletoDoTreino(TextField textField, CheckBox checkBox, Button button,Button button2, Label label, boolean estado) {
        checkBox.setVisible(estado);
        textField.setVisible(estado);
        button.setVisible(estado);
        if (estado) {
            label.setText("Informe o tempo para registrar o treino:");
        } else {
            label.setText("Filtrar por período");
        }
    }
    public static void guardaNomeDoTreinoParaEditar(Label guardaNomeTreino, ListView<String> listaTreinos) {
        guardaNomeTreino.setText(listaTreinos.getSelectionModel().getSelectedItem());
    }
}
