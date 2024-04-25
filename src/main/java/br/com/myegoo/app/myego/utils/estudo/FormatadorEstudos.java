package br.com.myegoo.app.myego.utils.estudo;

import br.com.myegoo.app.myego.interfaces.IFormatacao;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FormatadorEstudos implements IFormatacao {
    @Override
    public void formatacaoDoTempo(TextField textField) {
        IFormatacao.super.formatacaoDoTempo(textField);
    }
}
