package br.com.myegoo.app.myego.interfaces;

import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.tarefa.TabelaTarefas;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public interface ITabela {
    default void statusTabelaTarefas(TableView<TabelaTarefas> tabelaTarefa) {
        tabelaTarefa.setRowFactory(tv -> new TableRow<TabelaTarefas>() {
            @Override
            protected void updateItem(TabelaTarefas item, boolean empty) {
                super.updateItem(item, empty);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                if (item == null || empty) {
                    setStyle("");
                } else if (item.getConcluido().equals("Concluído")) {
                    setStyle("-fx-background-color: lightgreen;");
                } else if (item.getConcluido().equals("Não concluído") && LocalDate.parse(item.getData(), formatter).isBefore(LocalDate.now())) {
                    setStyle("-fx-background-color: lightcoral;");
                }
                 else {
                    setStyle("");
                }
            }
        });
    }
    default void statusTabelaTreino(TableView<TabelaTreino> tabelaTreino) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        tabelaTreino.setRowFactory(tv -> new TableRow<TabelaTreino>() {
            @Override
            protected void updateItem(TabelaTreino item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else if (item.isConcluido()) {
                    setStyle("-fx-background-color: lightgreen;");
                } else if (LocalDate.parse(item.getData().toString(), formatter).equals(LocalDate.now())) {
                    setStyle("-fx-background-color: rgba(123,46,234,0.3);");
                } else {
                    setStyle("");
                }
            }
        });
    }
}
