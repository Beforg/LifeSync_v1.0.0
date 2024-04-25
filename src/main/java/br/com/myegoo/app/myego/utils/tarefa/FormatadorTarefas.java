package br.com.myegoo.app.myego.utils.tarefa;

import br.com.myegoo.app.myego.interfaces.IFormatacao;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class FormatadorTarefas implements IFormatacao {
    @Override
    public void formataData(DatePicker datePicker) {
        IFormatacao.super.formataData(datePicker);
    }

    @Override
    public void restricaoCampoDeTexto(TextField campoDeTexto, String restricao) {
        IFormatacao.super.restricaoCampoDeTexto(campoDeTexto, restricao);
    }

    @Override
    public void formatacaoDoTempo(TextField textField) {

    }
}
