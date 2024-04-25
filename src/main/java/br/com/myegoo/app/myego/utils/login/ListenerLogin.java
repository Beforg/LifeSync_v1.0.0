package br.com.myegoo.app.myego.utils.login;

import javafx.beans.value.ChangeListener;
import javafx.scene.control.*;

/**
 * Classe para ouvir os campos de cadastro e login, para habilitar o botão de cadastro e login
 */
public class ListenerLogin {
    public static void botaoCadastro(TextField tfNomeCadastro,
                                     TextField tfNomeUsuarioCadastro,
                                     TextField tfSenhaCadastro,
                                     DatePicker datePickerCadastro,
                                     Button botaoCadastrar) {
        ChangeListener<String> listner = (observable, oldValue, newValue) -> {
            boolean campos = !tfNomeCadastro.getText().isEmpty()
                    && !tfNomeUsuarioCadastro.getText().isEmpty()
                    && !tfSenhaCadastro.getText().isEmpty()
                    && datePickerCadastro.getValue() != null;
            botaoCadastrar.setDisable(!campos);
        };
        tfNomeCadastro.textProperty().addListener(listner);
        tfNomeUsuarioCadastro.textProperty().addListener(listner);
        tfSenhaCadastro.textProperty().addListener(listner);
        datePickerCadastro.valueProperty().addListener((observable, oldValue, newValue) -> {
            boolean campos = !tfNomeCadastro.getText().isEmpty()
                    && !tfNomeUsuarioCadastro.getText().isEmpty()
                    && !tfSenhaCadastro.getText().isEmpty()
                    && newValue != null;
            botaoCadastrar.setDisable(!campos);
        });
        botaoCadastrar.setDisable(true);
    }
    public static void botaoLogin(TextField tfUserLogin, TextField tfPasswordLogin, Button botaoLogin) {
        ChangeListener<String> listener = ((observable, oldValue, newValue) -> {
            boolean campos = !tfUserLogin.getText().isEmpty() && !tfPasswordLogin.getText().isEmpty();
            botaoLogin.setDisable(!campos);
        });
        tfPasswordLogin.textProperty().addListener(listener);
        tfUserLogin.textProperty().addListener(listener);
        botaoLogin.setDisable(true);
    }

    public static void passwordField(PasswordField passwordField, Label textoSenha, Button botaoCadastrar) {
        passwordField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() < 6) {
                botaoCadastrar.setDisable(true);
                textoSenha.setVisible(true);
                textoSenha.setText("*A senha deve ter no mínimo 6 caracteres.");
                textoSenha.setTextFill(javafx.scene.paint.Color.RED);
            } else {
                botaoCadastrar.setDisable(false);
                textoSenha.setText("Senha válida!");
                textoSenha.setTextFill(javafx.scene.paint.Color.GREEN);
            }
        });
    }
    public static void passwordFieldKey(PasswordField passwordField, Button entrar) {
        passwordField.addEventHandler(javafx.scene.input.KeyEvent.KEY_PRESSED, event -> {
            if (event.getCode().toString().equals("ENTER")) {
                entrar.fire();
            }
        });
    }
}
