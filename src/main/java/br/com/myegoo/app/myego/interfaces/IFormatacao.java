package br.com.myegoo.app.myego.interfaces;

import javafx.application.Platform;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.util.StringConverter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public interface IFormatacao {
        default void formataData(DatePicker datePicker) {
        datePicker.setPromptText("dd/MM/yyyy");
        TextField editor = datePicker.getEditor();
        editor.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d{0,2}/\\d{0,2}/\\d{0,4}") && (oldValue.isEmpty() || !newValue.equals(oldValue.substring(0, oldValue.length() - 1)))) {
                Platform.runLater(() -> {
                    int length = newValue.length();
                    if (length == 2 || length == 5) {
                        int caretPosition = editor.getCaretPosition();
                        editor.setText(newValue + "/");
                        editor.positionCaret(caretPosition + 1);
                    } else if (length > 10) {
                        editor.setText(oldValue);
                    }
                });
                editor.focusedProperty().addListener(((observable1, oldValue1, newValue1) -> {
                    if (!newValue1) {
                        String text = editor.getText();
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        try {
                            LocalDate date = LocalDate.parse(text, formatter);
                            datePicker.setValue(date);
                        } catch (Exception e) {
                            editor.setText("");
                            datePicker.setValue(null);
                        }
                    }
                }));
            }
        });
    }
        default void formatadorFiltroDaData(DatePicker datePicker, ChoiceBox<String> choiceBox) {
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                DateTimeFormatter formatter = switch (newValue) {
                    case "Dia" -> DateTimeFormatter.ofPattern("EEEE, dd MMMM");
                    case "Semana" -> DateTimeFormatter.ofPattern("'semana' w 'de' MMMM");
                    case "Mês" -> DateTimeFormatter.ofPattern("MMMM yyyy");
                    case "Ano" -> DateTimeFormatter.ofPattern("yyyy");
                    default -> DateTimeFormatter.ofPattern("yyyy-MM-dd");
                };
                StringConverter<LocalDate> converter = new StringConverter<>() {
                    @Override
                    public String toString(LocalDate date) {
                        if (date != null) {
                            return formatter.format(date);
                        } else {
                            return "";
                        }
                    }

                    @Override
                    public LocalDate fromString(String string) {
                        if (string != null && !string.isEmpty()) {
                            return LocalDate.parse(string, formatter);
                        } else {
                            return null;
                        }
                    }
                };
                datePicker.setConverter(converter);
            }
        });
    }
    default void restricaoCampoDeTexto(TextField campoDeTexto, String restricao) {
        campoDeTexto.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!restricao.contains(event.getCharacter().toLowerCase())) {
                event.consume();
            }
        });
    }
    default void formatacaoDoTempo(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null && !newValue.matches("\\d{0,2}:\\d{0,2}:\\d{0,2}") && (oldValue.isEmpty() || !newValue.equals(oldValue.substring(0, oldValue.length() - 1)))) {
                if (newValue.length() == 2 || newValue.length() == 5) {
                    textField.setText(newValue + ":");
                } else if (newValue.length() > 8) {
                    textField.setText(oldValue);
                }
            }
        });
    }
    default void formataDatePickerAno(DatePicker datePicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy");
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
    }
    default void formataDatePickerMes(DatePicker datePicker) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMMM yyyy");
        StringConverter<LocalDate> converter = new StringConverter<>() {
            @Override
            public String toString(LocalDate date) {
                if (date != null) {
                    return formatter.format(date);
                } else {
                    return "";
                }
            }

            @Override
            public LocalDate fromString(String string) {
                if (string != null && !string.isEmpty()) {
                    return LocalDate.parse(string, formatter);
                } else {
                    return null;
                }
            }
        };
    }

}
