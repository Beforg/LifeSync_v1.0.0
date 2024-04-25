package br.com.myegoo.app.myego.utils;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;

public class ValidacaoDados {
    public static void validarDataInserida(DatePicker data, Label labelAvisos) {
        if (data.getValue() == null) {
            labelAvisos.setTextFill(Color.RED);
            labelAvisos.setText("Data não pode ser vazia");
            throw new IllegalArgumentException("Data não pode ser vazia!");
        }
        if (data.getValue().isBefore(data.getChronology().dateNow())) {
            labelAvisos.setTextFill(Color.RED);
            labelAvisos.setText("Data não pode ser menor que a data atual!");
            throw new IllegalArgumentException("Data não pode ser menor que a data atual");
        }
    }
}
