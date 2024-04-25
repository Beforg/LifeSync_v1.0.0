package br.com.myegoo.app.myego.utils.login;
import br.com.myegoo.app.myego.interfaces.IFormatacao;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;


public class FormatadorLogin implements IFormatacao {
    /**
     * Método para formatar a data no DatePicker
     * @param datePicker
     * verifica se o valor digitado é uma data válida
     * ele verifica a posição do cursor e adiciona a barra.
     */

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
