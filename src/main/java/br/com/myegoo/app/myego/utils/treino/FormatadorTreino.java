package br.com.myegoo.app.myego.utils.treino;

import br.com.myegoo.app.myego.interfaces.IFormatacao;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class FormatadorTreino implements IFormatacao {
    @Override
    public void formataData(DatePicker datePicker) {
        IFormatacao.super.formataData(datePicker);
    }

    @Override
    public void formatadorFiltroDaData(DatePicker datePicker, ChoiceBox<String> choiceBox) {
        IFormatacao.super.formatadorFiltroDaData(datePicker, choiceBox);
    }

    @Override
    public void restricaoCampoDeTexto(TextField campoDeTexto, String restricao) {
        IFormatacao.super.restricaoCampoDeTexto(campoDeTexto, restricao);
    }

    @Override
    public void formatacaoDoTempo(TextField textField) {
        IFormatacao.super.formatacaoDoTempo(textField);
    }
}
