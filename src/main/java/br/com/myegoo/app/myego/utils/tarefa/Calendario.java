package br.com.myegoo.app.myego.utils.tarefa;

import br.com.myegoo.app.myego.controller.ApplicationController;
import br.com.myegoo.app.myego.model.Habitos;
import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import br.com.myegoo.app.myego.repository.HabitosRepository;
import br.com.myegoo.app.myego.repository.TarefaRepository;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.text.DecimalFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;

public class Calendario {

    private static final String style = "-fx-background-color: #BDBFF6;"+"-fx-border-color: #9EA1E3;" +
            "    -fx-border-width: 1px;" +
            "    -fx-border-radius: 5px;" +
            "    -fx-background-radius: 5px;" +
            "    -fx-padding: 2px;";
    private static final String styleDiaAtual = style + "-fx-text-fill: #510eb6;";
    private static final String styleIndicadora = "rgb(148, 148, 222, 0.25);"+"-fx-border-color: #9EA1E3;" +
            "    -fx-border-width: 1px;" +
            "    -fx-border-radius: 5px;" +
            "    -fx-background-radius: 5px;" +
            "    -fx-font-size: 15px;" +
            "    -fx-padding: 2px;" +
            "    -fx-text-fill: #5d5050;";
    private static final String styleHover = style +"-fx-background-color: rgba(118, 100, 238, 0.75);";
    private static final String styleConcluido = "-fx-background-color: #BDBFF6;"+"-fx-border-color: #9EA1E3;" +
            "    -fx-border-width: 1px;" +
            "    -fx-border-radius: 5px;" +
            "    -fx-background-radius: 5px;" +
            "    -fx-padding: 2px;" +
            "    -fx-text-fill: #10852c;" +
            "    -fx-strikethrough: true;";
    private static final String styleAtrasada = "-fx-background-color: #BDBFF6;"+"-fx-border-color: #9EA1E3;" +
            "    -fx-border-width: 1px;" +
            "    -fx-border-radius: 5px;" +
            "    -fx-background-radius: 5px;" +
            "    -fx-padding: 2px;" +
            "    -fx-text-fill: #ff0000;";
    public static final DatePicker datePicker = new DatePicker();
    public static final DatePicker datePickerSemana = new DatePicker();
    public static final DatePicker datePickerHabitos = new DatePicker();
    public static void trocarMes(Pane muralCalendario,
                                 Pane adicionarTarefa,
                                 Label muralSemanaTarefasSemana,
                                 Label muralSemanaConcluidas,
                                 Label muralSemanaAtrasadas,
                                 ProgressBar muralSemanaProgresso,
                                 Label muralCalendarioMesAtual,
                                 Label muralCalendarioAtrasadas,
                                 Label muralCalendarioMesEdia,
                                 ListView<String> muralCalendarioLista,
                                 ProgressBar muralCalendarioProgresso,
                                 Label muralCalendarioTarefasMes,
                                 Label muralCalendarioConcluidas,
                                 DatePicker datePickerTarefa,
                                 GridPane tarefas,
                                 GridPane categorias,
                                 TarefaRepository tarefaRepository,
                                 GridPane gridPane,
                                 Button proximo,
                                 Button anterior,
                                 Button hoje,
                                 GridPane muralSemana,
                                 Label segunda,
                                 VBox terca,
                                 VBox quarta,
                                 VBox quinta,
                                 VBox sexta,
                                 VBox sabado,
                                 VBox domingo,
                                 ListView<String> muralSemanaLista,
                                 GridPane calendarioFull) {

        proximo.setOnAction(event -> {
            if (calendarioFull.isVisible()) {
                datePicker.setValue(datePicker.getValue().plusMonths(1));
            } else {
                datePickerSemana.setValue(datePickerSemana.getValue().plusWeeks(1));
            }

            muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                    ,muralSemanaLista,muralSemana,segunda,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
            configuraTrocaDeMes(muralCalendario,adicionarTarefa,muralCalendarioMesAtual, muralCalendarioAtrasadas, muralCalendarioMesEdia, muralCalendarioLista, muralCalendarioProgresso, muralCalendarioTarefasMes, muralCalendarioConcluidas, datePickerTarefa, tarefas, categorias, tarefaRepository, gridPane);
        });
        anterior.setOnAction(event -> {
            if (calendarioFull.isVisible()) {
                datePicker.setValue(datePicker.getValue().minusMonths(1));
            } else {
                datePickerSemana.setValue(datePickerSemana.getValue().minusWeeks(1));
            }
            muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                    ,muralSemanaLista,muralSemana,segunda,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
            configuraTrocaDeMes(muralCalendario,adicionarTarefa,muralCalendarioMesAtual, muralCalendarioAtrasadas, muralCalendarioMesEdia, muralCalendarioLista, muralCalendarioProgresso, muralCalendarioTarefasMes, muralCalendarioConcluidas, datePickerTarefa, tarefas, categorias, tarefaRepository, gridPane);
        });
        hoje.setOnAction(event -> {
            if (calendarioFull.isVisible()) {
                datePicker.setValue(LocalDate.now());
            } else {
                datePickerSemana.setValue(LocalDate.now());
            }
            muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                    ,muralSemanaLista,muralSemana,segunda,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
            configuraTrocaDeMes(muralCalendario,adicionarTarefa,muralCalendarioMesAtual, muralCalendarioAtrasadas, muralCalendarioMesEdia, muralCalendarioLista, muralCalendarioProgresso, muralCalendarioTarefasMes, muralCalendarioConcluidas, datePickerTarefa, tarefas, categorias, tarefaRepository, gridPane);
        });
    }
    public static void trocaMesDosHabitos(Label habitosDoMes,
                                          Label habitosConcluidosDoMes,
                                          ProgressBar progressBarHabitos,
                                          Label labelDiaSelecionado,
                                          ListView<String> listaHabitosDoDia,
                                          Label numHabitosSelecionado,
                                          Label numHabitosSelecionadosConcluidos,
                                          Button proximo,
                                          Button anterior,
                                          Button atual,
                                          GridPane calendarioHabitos,
                                          HabitosRepository habitosRepository,
                                          Label pegaDataHabito,
                                          Label labelProgressoHabitos) {
        proximo.setOnAction(event ->{
            datePickerHabitos.setValue(datePickerHabitos.getValue().plusMonths(1));
            calendarioDeHabitos(habitosDoMes,habitosConcluidosDoMes,progressBarHabitos,
                    labelDiaSelecionado,listaHabitosDoDia,numHabitosSelecionado,
                    numHabitosSelecionadosConcluidos,calendarioHabitos,habitosRepository,pegaDataHabito,labelProgressoHabitos);
        });
        anterior.setOnAction(event -> {
            datePickerHabitos.setValue(datePickerHabitos.getValue().minusMonths(1));
            calendarioDeHabitos(habitosDoMes,habitosConcluidosDoMes,progressBarHabitos,
                    labelDiaSelecionado,listaHabitosDoDia,numHabitosSelecionado,
                    numHabitosSelecionadosConcluidos,calendarioHabitos,habitosRepository,pegaDataHabito,labelProgressoHabitos);
        });
        atual.setOnAction(event -> {
            datePickerHabitos.setValue(LocalDate.now());
            calendarioDeHabitos(habitosDoMes,habitosConcluidosDoMes,progressBarHabitos,
                    labelDiaSelecionado,listaHabitosDoDia,numHabitosSelecionado,
                    numHabitosSelecionadosConcluidos,calendarioHabitos,habitosRepository,pegaDataHabito,labelProgressoHabitos);

        });
    }

    private static void configuraTrocaDeMes(Pane muralCalendario,
                                            Pane adicionarTarefa,Label muralCalendarioMesAtual,
                                            Label muralCalendarioAtrasadas,
                                            Label muralCalendarioMesEdia,
                                            ListView<String> muralCalendarioLista,
                                            ProgressBar muralCalendarioProgresso,
                                            Label muralCalendarioTarefasMes,
                                            Label muralCalendarioConcluidas,
                                            DatePicker datePickerTarefa,
                                            GridPane tarefas,
                                            GridPane categorias,
                                            TarefaRepository tarefaRepository,
                                            GridPane gridPane) {
        muralCalendarioMesEdia.setText(datePicker.getValue().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + " de " + datePicker.getValue().getYear());
        muralCalendarioMesAtual.setText(datePicker.getValue().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + " de " + datePicker.getValue().getYear());
        calendario(muralCalendario,adicionarTarefa,muralCalendarioAtrasadas,muralCalendarioMesEdia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,datePickerTarefa,tarefas,categorias,gridPane,tarefaRepository);
        listaDoMesEscolhido(muralCalendarioLista,datePicker.getValue(),tarefaRepository, muralCalendarioTarefasMes,muralCalendarioAtrasadas,muralCalendarioConcluidas,muralCalendarioProgresso);
    }

    public static void listaDoMesEscolhido(ListView<String> lista, LocalDate data, TarefaRepository tarefaRepository, Label muralCalendarioTarefasMes, Label muralCalendarioAtrasadas, Label muralCalendarioConcluidas, ProgressBar muralCalendarioProgresso) {
        lista.getItems().clear();
        int tarefasMes = 0;
        int tarefasConcluidas = 0;
        int tarefasAtrasadas = 0;
        for (int i = 1; i <= data.lengthOfMonth(); i++) {
            List<Tarefa> tarefas = tarefaRepository.findByData(data.withDayOfMonth(i), ApplicationController.conta.getId());
            if (tarefas != null) {
                for (Tarefa t : tarefas) {
                    tarefasMes++;
                    if (t.isConcluida()) {
                        tarefasConcluidas++;
                    }
                    if (!t.isConcluida() && data.withDayOfMonth(i).isBefore(LocalDate.now())) {
                        tarefasAtrasadas++;
                    }
                    lista.getItems().add(t.getNome());

                }
            }
        }
        muralCalendarioTarefasMes.setText(String.valueOf(tarefasMes));
        muralCalendarioAtrasadas.setText(String.valueOf(tarefasAtrasadas));
        muralCalendarioConcluidas.setText(String.valueOf(tarefasConcluidas));
        muralCalendarioProgresso.setProgress((double) tarefasConcluidas / tarefasMes);
    }

    public static void calendario(Pane muralCalendario,
                                  Pane adicionarTarefa,
                                  Label muralCalendarioAtrasadas,
                                  Label muralCalendarioMesEdia,
                                  ListView<String> muralCalendarioLista,
                                  ProgressBar muralCalendarioProgresso,
                                  Label muralCalendarioTarefasMes,
                                  Label muralCalendarioConcluidas,
                                  DatePicker datePickerTarefa,
                                  GridPane tarefas,
                                  GridPane categorias,
                                  GridPane gridPane,
                                  TarefaRepository tarefaRepository) {
        gridPane.getChildren().clear();
        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.now());
        }

        int inicioDaSemana = datePicker.getValue().getDayOfWeek().getValue() % 7;

        for (int dia = 1; dia <= datePicker.getValue().lengthOfMonth(); dia++) {
            int gridPaneDay = dia + inicioDaSemana - 1;
            int row = gridPaneDay / 7;
            int col = gridPaneDay % 7;
            int contador = 0;
            int contadorConcluidas = 0;
            List<Tarefa> tarefa = tarefaRepository.findByData(datePicker.getValue().withDayOfMonth(dia), ApplicationController.conta.getId());
            if (tarefa != null) {
                for (Tarefa t : tarefa) {
                    contador++;
                    if (t.isConcluida()) {
                        contadorConcluidas++;
                    }
                }
            }
            gridPane.add(setLabelCalendario(muralCalendario, adicionarTarefa,muralCalendarioAtrasadas,muralCalendarioMesEdia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,datePickerTarefa,tarefas,categorias,datePicker,contador,contadorConcluidas,tarefaRepository,dia), col, row);
        }
    }
    public static void calendarioDeHabitos(Label habitosDoMes,
                                           Label habitosConcluidosDoMes,
                                           ProgressBar progressBarHabitos,
                                           Label labelDiaSelecionado,
                                           ListView<String> listaHabitosDoDia,
                                           Label numHabitosSelecionado,
                                           Label numHabitosSelecionadosConcluidos,
                                           GridPane calendarioHabitos,
                                           HabitosRepository habitosRepository,
                                           Label pegaDataHabito,
                                           Label labelProgressoHabitos) {
        calendarioHabitos.getChildren().clear();
        if (datePickerHabitos.getValue() == null) {
            datePickerHabitos.setValue(LocalDate.now());
        }
        int inicioDaSemana = datePickerHabitos.getValue().getDayOfWeek().getValue() % 7;
        int numeroDeHabitos = 0;
        int numeroDeHabitosConcluidos = 0;
        for (int dia = 1; dia <= datePickerHabitos.getValue().lengthOfMonth(); dia++) {
            int gridPaneDay = dia + inicioDaSemana - 1;
            int row = gridPaneDay / 7;
            int col = gridPaneDay % 7;
            int contador = 0;
            int contadorConcluidas = 0;
            List<Habitos> habitos = habitosRepository.findByData(datePickerHabitos.getValue().withDayOfMonth(dia));
            if (habitos != null) {
                for (Habitos hab : habitos) {
                    contador++;
                    numeroDeHabitos++;
                    if(hab.isConcluido()) {
                        contadorConcluidas++;
                        numeroDeHabitosConcluidos++;
                    }
                }
            }
            calendarioHabitos.add(setLabelHabitos(contador,contadorConcluidas,dia,
                    datePickerHabitos,habitosRepository,labelDiaSelecionado,listaHabitosDoDia,
                    numHabitosSelecionado,numHabitosSelecionadosConcluidos,pegaDataHabito),col,row);
        }

        habitosDoMes.setText(String.valueOf(numeroDeHabitos));
        habitosConcluidosDoMes.setText(String.valueOf(numeroDeHabitosConcluidos));
        progressBarHabitos.setProgress((double) numeroDeHabitosConcluidos / numeroDeHabitos);
        DecimalFormat df = new DecimalFormat("0.00");
        labelProgressoHabitos.setText(df.format((double) numeroDeHabitosConcluidos / numeroDeHabitos * 100) + "%");
    }
    public static void muralCalendario(Label muralCalendarioAtrasadas,
                                       Label muralCalendarioMes,
                                       ListView<String> muralCalendarioLista,
                                       ProgressBar muralCalendarioProgresso,
                                       Label muralCalendarioTarefasMes,
                                       Label muralCalendarioConcluidas,
                                       TarefaRepository tarefaRepository) {
        muralCalendarioLista.getItems().clear();
        muralCalendarioMes.setText(LocalDate.now().getMonth().getDisplayName(TextStyle.FULL,
                new Locale("pt", "BR")) + " de " + LocalDate.now().getYear());
        for (int i = 1; i <= LocalDate.now().lengthOfMonth(); i++) {
            List<Tarefa> tarefas = tarefaRepository.findByData(LocalDate.now().withDayOfMonth(i), ApplicationController.conta.getId());
            if (tarefas != null) {
                for (Tarefa t : tarefas) {
                        muralCalendarioLista.getItems().add(t.getNome());
                }
            }
        }
        muralCalendarioProgresso.setProgress(0);
        muralCalendarioTarefasMes.setText("0");
        muralCalendarioConcluidas.setText("0");
        LocalDate data = LocalDate.now();
        int tarefasMes = 0;
        int tarefasConcluidas = 0;
        int tarefasAtrasadas = 0;
        for (int i = 1; i <= data.lengthOfMonth(); i++) {
            List<Tarefa> tarefas = tarefaRepository.findByData(data.withDayOfMonth(i), ApplicationController.conta.getId());
            if (tarefas != null) {
                for (Tarefa t : tarefas) {
                    tarefasMes++;
                    if (t.isConcluida()) {
                        tarefasConcluidas++;
                    }
                    if (!t.isConcluida() && data.withDayOfMonth(i).isBefore(LocalDate.now())) {
                        tarefasAtrasadas++;
                    }
                }
            }
        }
        muralCalendarioTarefasMes.setText(String.valueOf(tarefasMes));
        muralCalendarioAtrasadas.setText(String.valueOf(tarefasAtrasadas));
        muralCalendarioConcluidas.setText(String.valueOf(tarefasConcluidas));
        muralCalendarioProgresso.setProgress((double) tarefasConcluidas / tarefasMes);

    }
    public static void muralCalendarioDiaSelecionado(Label muralCalendarioAtrasadas,
                                                     Label muralCalendarioMesEdia,
                                                     ListView<String> muralCalendarioLista,
                                                     ProgressBar muralCalendarioProgresso,
                                                     LocalDate data,
                                                     Label muralCalendarioTarefasMes,
                                                     Label muralCalendarioConcluidas,
                                                     TarefaRepository tarefaRepository) {
        int contadorConcluidas = 0;
        int contador = 0;
        int tarefasAtrasadas = 0;
        muralCalendarioLista.getItems().clear();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("pt", "BR"));
        muralCalendarioMesEdia.setText(data.format(dateFormatter));
        List<Tarefa> tarefas = tarefaRepository.findByData(data, ApplicationController.conta.getId());
        for (Tarefa t : tarefas) {
            LocalDate dataTarefa = LocalDate.parse(t.getData().toString());
            contador++;
            if (t.isConcluida()) {
                muralCalendarioLista.getItems().add(t.getNome());
                contadorConcluidas++;
            } else {
                muralCalendarioLista.getItems().add(t.getNome());
            } if (!t.isConcluida() && dataTarefa.isBefore(LocalDate.now())) {
                tarefasAtrasadas++;
            }

        }
        muralCalendarioTarefasMes.setText(String.valueOf(contador));
        muralCalendarioConcluidas.setText(String.valueOf(contadorConcluidas));
        muralCalendarioAtrasadas.setText(String.valueOf(tarefasAtrasadas));
        muralCalendarioProgresso.setProgress((double) contadorConcluidas / contador);



    }
    public static void muralDiaSelecionadoHabitos(Label labelDiaSelecionado,
                                                  ListView<String> listaHabitosDoDia,
                                                  Label numHabitosSelecionado,
                                                  Label numHabitosSelecionadosConcluidos,
                                                  LocalDate data,
                                                  HabitosRepository habitosRepository) {
        int contador = 0;
        int contadorConcluidas = 0;
        listaHabitosDoDia.getItems().clear();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("pt", "BR"));
        labelDiaSelecionado.setText(data.format(dateFormatter));

        List<Habitos> habitos = habitosRepository.findByData(data);
        for (Habitos hab : habitos) {
            contador++;
            if (hab.isConcluido()) {
                contadorConcluidas++;
                listaHabitosDoDia.getItems().add(hab.getNome());
            } else {
                listaHabitosDoDia.getItems().add(hab.getNome());
            }
        }
        numHabitosSelecionado.setText("Hábitos do dia: "+ contador);
        numHabitosSelecionadosConcluidos.setText("Hábitos feitos: " + contadorConcluidas);
    }
    public static Label setLabelCalendario(Pane muralCalendario,
                                           Pane adicionarTarefa,
                                           Label muralCalendarioAtrasadas,
                                           Label muralCalendarioMesEdia,
                                           ListView<String> muralCalendarioLista,
                                           ProgressBar muralCalendarioProgresso,
                                           Label muralCalendarioTarefasMes,
                                           Label muralCalendarioConcluidas,
                                           DatePicker datePickerTarefa,
                                           GridPane tarefasPane,
                                           GridPane categorias,
                                           DatePicker datePicker,
                                           int contador,
                                           int contadorConcluidas,
                                           TarefaRepository tarefaRepository,
                                           int dia) {
        String tarefas;
        if (contador == 0) {
            tarefas = " | Nenhuma tarefa";
            tarefas += " | Adicione uma tarefa";
        } else if (datePicker.getValue().withDayOfMonth(dia).isBefore(LocalDate.now()) && contador > contadorConcluidas) {
            int atrasadas = contador - contadorConcluidas;
            tarefas = " | " + contador + " tarefa(s)";
            tarefas += " | " + contadorConcluidas + " concluída(s)";
            tarefas += " | " + atrasadas + " atrasada(s)";
        } else if (contador == contadorConcluidas & contador > 0) {
            tarefas = " | " + contador + " tarefa(s)";
            tarefas += " | todas concluídas       ";

        } else {
            tarefas =" | " + contador+ " tarefa(s)";
            tarefas += " | " + contadorConcluidas + " concluída(s)";
        }

        String dayOfWeek = datePicker.getValue().withDayOfMonth(dia).getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
        String month = datePicker.getValue().getMonth().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
        String year = String.valueOf(datePicker.getValue().getYear());

        Label label = new Label(dayOfWeek + ", "+ dia  + " de " + month+" de " + year+tarefas);
        verificaStatus(datePicker, contador, contadorConcluidas, dia, label);
        label.wrapTextProperty().setValue(true);
        int finalDia = dia;
        label.setOnMouseEntered(event -> {
            label.setStyle(styleHover);
        });
        label.setOnMouseExited(event -> {
            verificaStatus(datePicker, contador, contadorConcluidas, finalDia, label);
        });
        label.setOnMouseClicked(event -> {

            List<Tarefa> tarefasDoDia = tarefaRepository.findByData(datePicker.getValue().withDayOfMonth(finalDia), ApplicationController.conta.getId());
            if (tarefasDoDia.isEmpty()) {
                muralCalendarioMesEdia.setText(datePicker.getValue().withDayOfMonth(finalDia).format(DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("pt", "BR"))));
                TarefasUtil.muralAddTarefasCalendario(muralCalendario,adicionarTarefa,tarefasPane,categorias,datePicker.getValue().withDayOfMonth(finalDia),datePickerTarefa,true);
                muralCalendarioAtrasadas.setText("0");
                muralCalendarioConcluidas.setText("0");
                muralCalendarioProgresso.setProgress(0);
                muralCalendarioTarefasMes.setText("0");
                muralCalendarioLista.getItems().clear();
            } else {
                muralCalendarioDiaSelecionado(muralCalendarioAtrasadas,muralCalendarioMesEdia,muralCalendarioLista,muralCalendarioProgresso,datePicker.getValue().withDayOfMonth(finalDia),muralCalendarioTarefasMes,muralCalendarioConcluidas,tarefaRepository);
                TarefasUtil.muralAddTarefasCalendario(muralCalendario,adicionarTarefa,tarefasPane,categorias,datePicker.getValue().withDayOfMonth(finalDia),datePickerTarefa,false);

            }
        });
        return label;
    }

    private static void verificaStatus(DatePicker datePicker, int contador, int contadorConcluidas, int dia, Label label) {
        if (contadorConcluidas == contador && contador > 0) {
            label.setStyle(styleConcluido);
        } else if (datePicker.getValue().withDayOfMonth(dia).isBefore(LocalDate.now()) && contador > contadorConcluidas) {
            label.setStyle(styleAtrasada);
        } else if (datePicker.getValue().withDayOfMonth(dia).equals(LocalDate.now())) {
            label.setStyle(styleDiaAtual);
        } else {
            label.setStyle(style);
        }
    }

    public static Label setLabelHabitos(int contador,
                                        int contadorConcluidas,
                                        int dia,
                                        DatePicker diaHabito,
                                        HabitosRepository habitosRepository,
                                        Label labelDiaSelecionado,
                                        ListView<String> listaHabitosDoDia,
                                        Label numHabitosSelecionado,
                                        Label numHabitosSelecionadosConcluidos,
                                        Label pegaDataHabito) {
        String habitosDoDia;
        if (contador == 0) {
            habitosDoDia = " | Sem registro";
        } else if(contador == contadorConcluidas && contador > 0) {
            habitosDoDia = " | " + contador + " hábito(s)";
            habitosDoDia += " | Todos concluídos";
        } else {
            habitosDoDia = " | " + contador + " hábito(s)";
            habitosDoDia += " | " + contadorConcluidas + " concluídos";
        }

        String dayOfWeek = diaHabito.getValue().withDayOfMonth(dia).getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
        String month = diaHabito.getValue().getMonth().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
        String year = String.valueOf(diaHabito.getValue().getYear());
        Label label = new Label(dayOfWeek + ", "+ dia  + " de " + month+" de " + year+habitosDoDia);
        label.setStyle(style);
        label.setOnMouseEntered(event -> {
            label.setStyle(styleHover);
        });
        label.setOnMouseExited(event -> {
            verificaStatus(diaHabito, contador, contadorConcluidas, dia, label);
        });
        verificaStatus(datePickerHabitos, contador, contadorConcluidas, dia, label);
        label.wrapTextProperty().setValue(true);
        label.setOnMouseClicked(e -> {
            habitosRepository.findByData(diaHabito.getValue().withDayOfMonth(dia));
            muralDiaSelecionadoHabitos(labelDiaSelecionado,listaHabitosDoDia,numHabitosSelecionado,numHabitosSelecionadosConcluidos,diaHabito.getValue().withDayOfMonth(dia),habitosRepository);
            pegaDataHabito.setText(String.valueOf(diaHabito.getValue().withDayOfMonth(dia)));
        });
        return label;
    }
    public static void muralSemana(Label muralSemanaTarefasSemana,
                                   Label muralSemanaConcluidas,
                                   Label muralSemanaAtrasadas,
                                   ProgressBar muralSemanaProgresso,
                                   ListView<String> muralSemanaLista,
                                   GridPane muralSemana,
                                   Label muralSemanaDia,
                                   VBox terca,
                                   VBox quarta,
                                   VBox quinta,
                                   VBox sexta,
                                   VBox sabado,
                                   VBox domingo,
                                   TarefaRepository tarefaRepository) {
        if (datePickerSemana.getValue() == null) {
            datePickerSemana.setValue(LocalDate.now());
        }
        muralSemana.getChildren().clear();
        terca.getChildren().clear();
        quarta.getChildren().clear();
        quinta.getChildren().clear();
        sexta.getChildren().clear();
        sabado.getChildren().clear();
        domingo.getChildren().clear();


        LocalDate data = datePickerSemana.getValue();
        LocalDate segundaFeira = data.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd/MM");
        segundaFeira.format(df);
        LocalDate dataFimSemana = segundaFeira.plusDays(6);
        muralSemanaDia.setText("Semana de " + segundaFeira.format(df) + " a " + dataFimSemana.format(df));
        String tituloSemana = muralSemanaDia.getText();

        for (int i = 0; i < 7; i++) {
            int linha = 1;
            String numeroDiaMes = segundaFeira.format(df);
            String nomeDoDiaPorData = segundaFeira.getDayOfWeek().getDisplayName(TextStyle.SHORT, new Locale("pt", "BR"));
            switch (nomeDoDiaPorData) {
                case ("seg."):
                    setDiasSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                            ,tituloSemana,muralSemanaDia,"Segunda",0,numeroDiaMes,segundaFeira,muralSemana,tarefaRepository,muralSemanaLista,linha);
                    break;
                case ("ter."):
                    setDiasSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                            ,tituloSemana,muralSemanaDia,"Terça",1,numeroDiaMes,segundaFeira,muralSemana,tarefaRepository,muralSemanaLista,linha);
                    break;
                case ("qua."):
                    setDiasSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                            ,tituloSemana,muralSemanaDia,"Quarta",2,numeroDiaMes,segundaFeira,muralSemana,tarefaRepository,muralSemanaLista,linha);
                    break;
                case ("qui."):
                    setDiasSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                            ,tituloSemana,muralSemanaDia,"Quinta",3,numeroDiaMes,segundaFeira,muralSemana,tarefaRepository,muralSemanaLista,linha);
                    break;
                case ("sex."):
                    setDiasSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                            ,tituloSemana,muralSemanaDia,"Sexta",4,numeroDiaMes,segundaFeira,muralSemana,tarefaRepository,muralSemanaLista,linha);
                    break;
                case ("sáb."):
                    setDiasSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                            ,tituloSemana,muralSemanaDia,"Sábado",5,numeroDiaMes,segundaFeira,muralSemana,tarefaRepository,muralSemanaLista,linha);
                    break;
                case ("dom."):
                    setDiasSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                            ,tituloSemana,muralSemanaDia,"Domingo",6,numeroDiaMes,segundaFeira,muralSemana,
                            tarefaRepository,muralSemanaLista,linha);
                    break;
            }
            segundaFeira = segundaFeira.plusDays(1);
        }
    }
    public static void setDiasSemana(Label muralSemanaTarefasSemana,
                                     Label muralSemanaConcluidas,
                                     Label muralSemanaAtrasadas,
                                     ProgressBar muralSemanaProgresso,
                                     String tituloSemana,
                                     Label muralSemanaDia,
                                     String nomeDoDia,
                                     int col,
                                     String numeroDiaMes,
                                     LocalDate segundaFeira,
                                     GridPane muralSemana,
                                     TarefaRepository tarefaRepository,
                                     ListView<String> muralSemanaLista,
                                     int linha) {

        Label label2 = new Label(nomeDoDia+", " + numeroDiaMes);
        label2.setStyle(style);
        label2.setOnMouseEntered(event -> label2.setStyle(styleHover));
        label2.setOnMouseExited(event -> label2.setStyle(style));
        muralSemana.add(label2, col,0);
        List<Tarefa> tarefasDia = tarefaRepository.findByData(segundaFeira, ApplicationController.conta.getId());
        for (Tarefa t : tarefasDia) {
            Label label = new Label(t.getNome());
            label.setStyle(styleIndicadora);
            muralSemana.add(label,col,linha);
            System.out.println("LInha:" + linha + " e tarefa: " + t.getNome());
            linha++;
        }
        label2.setOnMouseClicked(event -> {
            muralSemanaLista.getItems().clear();
            for (Tarefa t : tarefasDia) {
                muralSemanaLista.getItems().add(t.getNome());
            }
            muralSemanaDia.setText(tituloSemana+","+nomeDoDia);
            carregaDadosDaSemanaDia(segundaFeira,muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso,muralSemanaDia,muralSemanaLista,tarefaRepository);

        });
    }

    public static void carregaDadosDaSemana(Label muralSemanaTarefasSemana, Label muralSemanaConcluidas, Label muralSemanaAtrasadas,ProgressBar progressBar, Label muralSemanaDia,ListView<String> muralSemanaLista, TarefaRepository tarefaRepository) {
        int contador = 0;
        int contadorConcluidas = 0;
        int contadorAtrasadas = 0;

        muralSemanaLista.getItems().clear();

        LocalDate data = LocalDate.now();
        LocalDate segundaFeira = data.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));

        for (int i = 0; i < 7; i++) {
            List<Tarefa> tarefas = tarefaRepository.findByData(segundaFeira.plusDays(i), ApplicationController.conta.getId());
            if (tarefas != null) {
                for (Tarefa t : tarefas) {
                    muralSemanaLista.getItems().add(t.getNome());
                    contador++;
                    if (t.isConcluida()) {
                        contadorConcluidas++;
                    }
                    if (!t.isConcluida() && segundaFeira.plusDays(i).isBefore(LocalDate.now())) {
                        contadorAtrasadas++;
                    }
                }
            }
        }
        muralSemanaTarefasSemana.setText(String.valueOf(contador));
        muralSemanaConcluidas.setText(String.valueOf(contadorConcluidas));
        muralSemanaAtrasadas.setText(String.valueOf(contadorAtrasadas));
        progressBar.setProgress((double) contadorConcluidas / contador);
        }

    private static void carregaDadosDaSemanaDia(LocalDate segundaFeira,Label muralSemanaTarefasSemana, Label muralSemanaConcluidas, Label muralSemanaAtrasadas,ProgressBar progressBar, Label muralSemanaDia,ListView<String> muralSemanaLista, TarefaRepository tarefaRepository) {
        int contador = 0;
        int contadorConcluidas = 0;
        int contadorAtrasadas = 0;

        muralSemanaLista.getItems().clear();
        List<Tarefa> tarefas = tarefaRepository.findByData(segundaFeira, ApplicationController.conta.getId());

            if (tarefas != null) {
                for (Tarefa t : tarefas) {
                    muralSemanaLista.getItems().add(t.getNome());
                    contador++;
                    if (t.isConcluida()) {
                        contadorConcluidas++;
                    }
                    if (!t.isConcluida() && segundaFeira.isBefore(LocalDate.now())) {
                        contadorAtrasadas++;
                    }
                }
            }

        muralSemanaTarefasSemana.setText(String.valueOf(contador));
        muralSemanaConcluidas.setText(String.valueOf(contadorConcluidas));
        muralSemanaAtrasadas.setText(String.valueOf(contadorAtrasadas));
        progressBar.setProgress((double) contadorConcluidas / contador);

    }
}
