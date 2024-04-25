package br.com.myegoo.app.myego.utils.financas;

import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;

import java.time.LocalDate;

public class FinancaUtil {
    public static DatePicker datePicker = new DatePicker();
    public static void calendarioFinancas(DatePicker calendario,
                                          Button passar,
                                          Button voltar,
                                          Button hoje){
        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.now());
        }

        passar.setOnAction(e -> {
            datePicker.setValue(datePicker.getValue().plusMonths(1));
            calendario.setValue(datePicker.getValue());

        });
        voltar.setOnAction(e -> {
            datePicker.setValue(datePicker.getValue().minusMonths(1));
            calendario.setValue(datePicker.getValue());
        });
        hoje.setOnAction(e -> {
            datePicker.setValue(LocalDate.now());
            calendario.setValue(datePicker.getValue());
        });

    }
}
