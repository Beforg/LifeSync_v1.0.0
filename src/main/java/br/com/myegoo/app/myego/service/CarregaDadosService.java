package br.com.myegoo.app.myego.service;
import br.com.myegoo.app.myego.controller.ApplicationController;
import br.com.myegoo.app.myego.model.*;
import br.com.myegoo.app.myego.model.estudo.RegistroEstudo;
import br.com.myegoo.app.myego.model.estudo.TabelaEstudos;
import br.com.myegoo.app.myego.model.financas.Financa;
import br.com.myegoo.app.myego.model.financas.TabelaFinanca;
import br.com.myegoo.app.myego.model.meta.FiltroMetas;
import br.com.myegoo.app.myego.model.meta.Meta;
import br.com.myegoo.app.myego.model.meta.Submeta;
import br.com.myegoo.app.myego.model.projetos.ProjetoCard;
import br.com.myegoo.app.myego.model.tarefa.Prioridade;
import br.com.myegoo.app.myego.model.tarefa.TabelaTarefas;
import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.treino.Treino;
import br.com.myegoo.app.myego.repository.*;
import br.com.myegoo.app.myego.utils.Mensagem;
import br.com.myegoo.app.myego.utils.TreinoUtil;
import br.com.myegoo.app.myego.utils.estudo.EstudosUtil;
import br.com.myegoo.app.myego.utils.tarefa.TarefasUtil;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.scene.control.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class CarregaDadosService {
    private static final TarefasUtil tarefasUtil = new TarefasUtil();
    private static final EstudosUtil estudosUtil = new EstudosUtil();
    private static final ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
    private static final TreinoUtil treinoUtil = new TreinoUtil();

    public static void carregarDados(Label labelDateTime) {
        Runnable updateClock = new Runnable() {
            public void run() {
                Platform.runLater(() -> {
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).withLocale(new Locale("pt", "BR"));
                    DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
                    LocalDateTime now = LocalDateTime.now();
                    String formattedDate = now.format(dateFormatter);
                    String formattedTime = now.format(timeFormatter);
                    labelDateTime.setText(formattedDate + ", " + formattedTime);
                });
            }
        };
        executor.scheduleAtFixedRate(updateClock, 0, 1, TimeUnit.SECONDS);
    }

    public static void shutdown() {
        executor.shutdown();
    }

    public static void carregaTarefas(Label selecionadaTarefaConcluida,
                                      Label selecionadaTarefaNumero,
                                      ObservableList<TabelaTarefas> tarefas,
                                      TarefaRepository tarefaRepository,
                                      TableView<TabelaTarefas> tabelaTarefas,
                                      Long conta,
                                      ChoiceBox<String> filtro,
                                      ChoiceBox<String> subFiltro) {
        if (ApplicationController.conta.getId() == null) {

        } else {
            tarefasUtil.statusTabelaTarefas(tabelaTarefas);
            int contadorTarefas = 0;
            int contadorTarefasConcluidas = 0;
            tarefas.clear();
            if (filtro.getValue() == null || filtro.getValue().equals("Todas")) {
                System.out.println("Todas");
                tabelaTarefas.getItems().clear();
                tarefas.clear();
                tarefaRepository.findByContaId(conta).forEach(tarefa -> {

                    tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefasUtil.formataData(tarefa.getData()), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                });
                for (TabelaTarefas tarefa : tarefas) {
                    contadorTarefas++;
                    if (tarefa.getConcluido().equals("Concluído")) {
                        contadorTarefasConcluidas++;
                    }
                }
                selecionadaTarefaConcluida.setText(String.valueOf(contadorTarefasConcluidas));
                selecionadaTarefaNumero.setText(String.valueOf(contadorTarefas));
                tabelaTarefas.getItems().addAll(tarefas);
            } else if (filtro.getValue().equals("Não Concluídas")) {
                tabelaTarefas.getItems().clear();
                tarefas.clear();
                tarefaRepository.findByIdEConcluida(conta, false).forEach(tarefa -> {
                    tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefa.getData().toString(), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                });
                tabelaTarefas.getItems().addAll(tarefas);
            } else if (filtro.getValue().equals("Concluídas")) {
                tabelaTarefas.getItems().clear();
                tarefas.clear();
                tarefaRepository.findByIdEConcluida(conta, true).forEach(tarefa -> {
                    tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefa.getData().toString(), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                });
                tabelaTarefas.getItems().addAll(tarefas);

            } else if (filtro.getValue().equals("Atrasadas")) {
                LocalDate data = LocalDate.now();
                tabelaTarefas.getItems().clear();
                tarefas.clear();
                tarefaRepository.findByIdEConcluida(conta, false).forEach(t -> {
                    System.out.println(t.getNome());
                    LocalDate dataDaTarefa = LocalDate.parse(t.getData().toString());
                    if (dataDaTarefa.isBefore(data))
                        tarefas.add(new TabelaTarefas(t.getNome(), t.getData().toString(), t.getPrioridade(), t.getCategoria(), t.getSubmeta().getNome()));
                });
                tabelaTarefas.getItems().addAll(tarefas);
            } else if (filtro.getValue().equals("Prioridade")) {
                subFiltro.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        String valorSubFiltro = subFiltro.getValue();
                        switch (valorSubFiltro) {
                            case "Baixa":
                                tabelaTarefas.getItems().clear();
                                tarefas.clear();
                                tarefaRepository.findByIdEPrioridade(conta, "Baixa").forEach(tarefa -> {
                                    tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefa.getData().toString(), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                                });
                                tabelaTarefas.getItems().addAll(tarefas);
                                break;
                            case "Média":
                                tabelaTarefas.getItems().clear();
                                tarefas.clear();
                                tarefaRepository.findByIdEPrioridade(conta, "Média").forEach(tarefa -> {
                                    tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefa.getData().toString(), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                                });
                                tabelaTarefas.getItems().addAll(tarefas);
                                break;
                            case "Alta":
                                tabelaTarefas.getItems().clear();
                                tarefas.clear();
                                tarefaRepository.findByIdEPrioridade(conta, "Alta").forEach(tarefa -> {
                                    tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefa.getData().toString(), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                                });
                                tabelaTarefas.getItems().addAll(tarefas);
                                break;
                            case "Urgente":
                                tabelaTarefas.getItems().clear();
                                tarefas.clear();
                                tarefaRepository.findByIdEPrioridade(conta, "Urgente").forEach(tarefa -> {
                                    tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefa.getData().toString(), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                                });
                                tabelaTarefas.getItems().addAll(tarefas);
                                break;
                        }
                    }
                });
            } else if (filtro.getValue().equals("Categoria")) {
                subFiltro.getSelectionModel().selectedItemProperty().addListener(((observableValue, oldValuie, newValue) -> {
                    if (newValue != null) {
                        String valorSubFiltro = subFiltro.getValue();
                        tabelaTarefas.getItems().clear();
                        tarefas.clear();
                        tarefaRepository.findByIdECategoria(conta, valorSubFiltro).forEach(tarefa -> {
                            tarefas.add(new TabelaTarefas(tarefa.getNome(), tarefa.getData().toString(), tarefa.getPrioridade(), tarefa.getCategoria(), tarefa.getSubmeta().getNome()));
                        });
                        tabelaTarefas.getItems().addAll(tarefas);
                    }
                }));
            }
        }
    }

    public static void carregaCategorias(ChoiceBox<String> cbCategoria, CategoriaRepository categoriaRepository) {
        cbCategoria.getItems().clear();
        categoriaRepository.findByTipo("Tarefas").forEach(categoria -> {
            cbCategoria.getItems().add(categoria.getNome());
        });
    }

    public static void carregarTipoEstudos(ChoiceBox<String> tipoEstudos, CategoriaRepository categoriaRepository) {
        tipoEstudos.getItems().clear();
        categoriaRepository.findByTipo("Estudos").forEach(categoria -> {
            tipoEstudos.getItems().add(categoria.getNome());
        });
    }

    public static void carregarMaterias(ChoiceBox<String> materias, EstudoRepository estudoRepository, Conta conta) {
        materias.getItems().clear();
        estudoRepository.findByConta(conta.getId()).forEach(estudo -> {
            System.out.println(estudo.getNome());
            materias.getItems().add(estudo.getNome());
        });

    }

    public static void carregaRegistroDeEstudos(TableView<TabelaEstudos> tabelaEstudos,
                                                ObservableList<TabelaEstudos> registroEstudosObservableList,
                                                RegistroEstudosRepository registroEstudosRepository,
                                                Long conta) {
        limparRegistro(tabelaEstudos, registroEstudosObservableList);
        registroEstudosRepository.findAll().forEach(registro -> {
            busca(registroEstudosObservableList, registro, registroEstudosRepository, tabelaEstudos);
        });
        tabelaEstudos.getItems().addAll(registroEstudosObservableList);
    }

    public static void carregaPorMateria(ChoiceBox<String> materia,
                                         RegistroEstudosRepository registroEstudosRepository,
                                         TableView<TabelaEstudos> tabelaEstudos,
                                         ObservableList<TabelaEstudos> estudos) {
        limparRegistro(tabelaEstudos, estudos);
        registroEstudosRepository.buscaPorMateria(materia.getValue()).forEach(registroEstudo -> {
            busca(estudos, registroEstudo, registroEstudosRepository, tabelaEstudos);
        });
        tabelaEstudos.getItems().addAll(estudos);
    }

    public static void carregaPorMateriaEconteudo(ChoiceBox<String> materia,
                                                  RegistroEstudosRepository registroEstudosRepository,
                                                  TableView<TabelaEstudos> tabelaEstudos,
                                                  ObservableList<TabelaEstudos> estudos,
                                                  TextField tfCampoNome) {
        limparRegistro(tabelaEstudos, estudos);
        tfCampoNome.textProperty().addListener((observable, oldValue, newValue) -> {
            tabelaEstudos.getItems().clear();
            estudos.clear();
            registroEstudosRepository.buscaPorNomeConteudo(tfCampoNome.getText(), materia.getValue()).forEach(registroEstudo -> {
                busca(estudos, registroEstudo, registroEstudosRepository, tabelaEstudos);
            });
            tabelaEstudos.getItems().addAll(estudos);
        });


    }

    public static void carregaPorTipo(ChoiceBox<String> materia,
                                      RegistroEstudosRepository registroEstudosRepository,
                                      TableView<TabelaEstudos> tabelaEstudos,
                                      ObservableList<TabelaEstudos> estudos) {
        limparRegistro(tabelaEstudos, estudos);
        registroEstudosRepository.buscaPorTipo(materia.getValue()).forEach(registroEstudo -> {
            busca(estudos, registroEstudo, registroEstudosRepository, tabelaEstudos);
        });
        tabelaEstudos.getItems().addAll(estudos);

    }

    public static void carregaPorData(ChoiceBox<String> filtro,
                                      RegistroEstudosRepository registroEstudosRepository,
                                      TableView<TabelaEstudos> tabelaEstudos,
                                      ObservableList<TabelaEstudos> estudos,
                                      DatePicker dataIndicada) {
        limparRegistro(tabelaEstudos, estudos);
        LocalDate data = dataIndicada.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = data.format(formatter);

        switch (filtro.getValue()) {
            case "Dia":
                limparRegistro(tabelaEstudos, estudos);
                registroEstudosRepository.buscaPorData(dataFormatada).forEach(registroEstudo -> {
                    busca(estudos, registroEstudo, registroEstudosRepository, tabelaEstudos);
                });
                tabelaEstudos.getItems().addAll(estudos);
                break;
            case "Semana":
                limparRegistro(tabelaEstudos, estudos);
                LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);
                LocalDate fimSemana = data.with(DayOfWeek.SUNDAY);
                registroEstudosRepository.buscaPorIntervaloData(inicioSemana.format(formatter), fimSemana.format(formatter)).forEach(registroEstudo -> {
                    busca(estudos, registroEstudo, registroEstudosRepository, tabelaEstudos);
                });
                tabelaEstudos.getItems().addAll(estudos);
                break;
            case "Mês":
                limparRegistro(tabelaEstudos, estudos);
                LocalDate inicioMes = data.withDayOfMonth(1);
                LocalDate fimMes = data.withDayOfMonth(data.lengthOfMonth());
                registroEstudosRepository.buscaPorIntervaloData(inicioMes.format(formatter), fimMes.format(formatter)).forEach(registroEstudo -> {
                    busca(estudos, registroEstudo, registroEstudosRepository, tabelaEstudos);
                });
                tabelaEstudos.getItems().addAll(estudos);
                break;
            case "Ano": {
                limparRegistro(tabelaEstudos, estudos);
                LocalDate inicioAno = data.withDayOfYear(1);
                LocalDate fimAno = data.withDayOfYear(data.lengthOfYear());
                registroEstudosRepository.buscaPorIntervaloData(inicioAno.format(formatter), fimAno.format(formatter)).forEach(registroEstudo -> {
                    busca(estudos, registroEstudo, registroEstudosRepository, tabelaEstudos);
                });
                tabelaEstudos.getItems().addAll(estudos);
                break;
            }
        }

    }

    public static void carregaPorDataFiltroDaMeta(ChoiceBox<String> filtro,
                                                  ListView<String> listaDasMetas,
                                                  MetaRepository metaRepository,
                                                  DatePicker dataIndicada) {
        listaDasMetas.getItems().clear();
        LocalDate data = dataIndicada.getValue();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String dataFormatada = data.format(formatter);

        switch (filtro.getValue()) {
            case "Dia":
                listaDasMetas.getItems().clear();
                metaRepository.findByData(LocalDate.parse(dataFormatada)).forEach(meta -> {
                    listaDasMetas.getItems().add(meta.getNome());
                });
                break;
            case "Semana":
                listaDasMetas.getItems().clear();
                LocalDate inicioSemana = data.with(DayOfWeek.MONDAY);

                LocalDate fimSemana = data.with(DayOfWeek.SUNDAY);
                metaRepository.findByIntervaloDeData(inicioSemana, fimSemana).forEach(meta -> {
                    listaDasMetas.getItems().add(meta.getNome());
                });
                break;
            case "Mês":
                listaDasMetas.getItems().clear();
                LocalDate inicioMes = data.withDayOfMonth(1);
                LocalDate fimMes = data.withDayOfMonth(data.lengthOfMonth());
                metaRepository.findByIntervaloDeData(inicioMes, fimMes).forEach(meta -> {
                    listaDasMetas.getItems().add(meta.getNome());
                });
                break;
            case "Ano": {
                listaDasMetas.getItems().clear();
                LocalDate inicioAno = data.withDayOfYear(1);
                LocalDate fimAno = data.withDayOfYear(data.lengthOfYear());
                metaRepository.findByIntervaloDeData(inicioAno, fimAno).forEach(meta -> {
                    listaDasMetas.getItems().add(meta.getNome());
                });
                break;
            }
        }

    }

    private static void busca(ObservableList<TabelaEstudos> estudos, RegistroEstudo registroEstudo, RegistroEstudosRepository registroEstudosRepository, TableView<TabelaEstudos> tabelaEstudos) {
        estudos.add(new TabelaEstudos(registroEstudo.getMateria(), registroEstudo.getNomeConteudo(), estudosUtil.formataData(registroEstudo.getData()), registroEstudo.getTempo(), registroEstudo.getTipo(), registroEstudo.getDescricao(), estudo -> {
            Mensagem mensagem = new Mensagem();
            int resposta = mensagem.retornoMessege("Aviso", "Deseja realmente apagar o registro de estudo?");
            if (resposta == 1) {
                registroEstudosRepository.delete(registroEstudo);
                estudos.remove(estudo);
                tabelaEstudos.getItems().remove(estudo);
                mensagem.showMessege("Sucesso", "Registro de estudo apagado com sucesso!", 2);
            }
        }));
    }

    private static void limparRegistro(TableView<TabelaEstudos> tabelaEstudos, ObservableList<TabelaEstudos> estudos) {
        tabelaEstudos.getItems().clear();
        estudos.clear();
    }

    public static void carregaAreaDaMeta(CategoriaRepository categoriaRepository, ChoiceBox<String> choiceBoxFiltroAreasMeta) {
        choiceBoxFiltroAreasMeta.getItems().clear();
        for (FiltroMetas filtroData : FiltroMetas.values()) {
            choiceBoxFiltroAreasMeta.getItems().add(filtroData.getDescricao());
        }
        categoriaRepository.findByTipo("Área").forEach(categoria -> {
            choiceBoxFiltroAreasMeta.getItems().add(categoria.getNome());
        });
    }

    public static void carregaSubmetasTarefas(ChoiceBox<String> choiceBoxEscolhaSubmeta, SubmetaRepository submetaRepository) {
        choiceBoxEscolhaSubmeta.getItems().clear();
        submetaRepository.findAll().forEach(submeta -> {
            choiceBoxEscolhaSubmeta.getItems().add(submeta.getNome());

        });
    }

    public static void carregaSubmeta(ListView<String> listaTodasSubmetas, SubmetaRepository submetaRepository) {
        listaTodasSubmetas.getItems().clear();
        submetaRepository.findAll().forEach(submeta -> {
            listaTodasSubmetas.getItems().add(submeta.getNome());
        });
    }

    public static void carregaMeta(ListView<String> listaDasMinhasMetaView, MetaRepository metaRepository) {
        listaDasMinhasMetaView.getItems().clear();
        metaRepository.findAll().forEach(meta -> {
            listaDasMinhasMetaView.getItems().add(meta.getNome());
        });
    }

    public static void carregaMinhasMetas(Label mmNomeMeta,
                                          Label nmDataMeta,
                                          Label nmAreaMeta,
                                          Label nmStatus,
                                          Label nmAssociadasMeta,
                                          Label nmConcluidasMeta,
                                          ProgressBar nmProgressoMeta,
                                          MetaRepository metaRepository,
                                          ListView<String> listaDeMeta,
                                          ChoiceBox<String> choiceBoxNovaSubmeta) {
        listaDeMeta.getItems().clear();
        choiceBoxNovaSubmeta.getItems().clear();
        metaRepository.findAll().forEach(meta -> {
            listaDeMeta.getItems().add(meta.getNome());
            choiceBoxNovaSubmeta.getItems().add(meta.getNome());
        });
        listaDeMeta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int totalDeSubmetas = metaRepository.findByNome(newValue).getSubmetas().size();
                int submetasConcluidas = 0;
                Meta metaSelecionada = metaRepository.findByNome(newValue);
                mmNomeMeta.setText(metaSelecionada.getNome());
                nmDataMeta.setText(metaSelecionada.getData().toString());
                nmAreaMeta.setText(metaSelecionada.getTipo());
                nmStatus.setText(metaSelecionada.getStatus());
                nmAssociadasMeta.setText(String.valueOf(totalDeSubmetas));

                for (Submeta submeta : metaSelecionada.getSubmetas()) {
                    if (submeta.getStatus().equals("Concluída")) {
                        submetasConcluidas++;
                    }
                }

                nmConcluidasMeta.setText(String.valueOf(submetasConcluidas));
                double porcentagem = (double) submetasConcluidas / totalDeSubmetas;
                nmProgressoMeta.setProgress(porcentagem);


            }
        });
    }

    public static void carregaSubmetaPorNomeDaMeta(Label nmNomeSubmeta,
                                                   Label nmDataSubmeta,
                                                   Label nmStatusSubmeta,
                                                   Label nmAssociadasSubmeta,
                                                   Label nmConcluidasSubmeta,
                                                   ProgressBar nmProgressoSubmeta,
                                                   ListView<String> listaDeMeta,
                                                   ListView<String> listaDeSubmeta,
                                                   SubmetaRepository submetaRepository,
                                                   MetaRepository metaRepository) {
        listaDeMeta.getItems().clear();
        listaDeMeta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Meta meta = metaRepository.findByNome(newValue);
                System.out.println(meta.getNome());
                listaDeSubmeta.getItems().clear();
                submetaRepository.findByMeta(meta.getId()).forEach(submeta -> {
                    System.out.println(submeta.getNome());
                    listaDeSubmeta.getItems().add(submeta.getNome());
                });
            }
        });
        listaDeSubmeta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int tarefasAssociadas = submetaRepository.findByNome(newValue).getTarefas().size();
                int tarefasConcluidas = 0;
                Submeta submetaSelecionada = submetaRepository.findByNome(newValue);
                nmNomeSubmeta.setText(submetaSelecionada.getNome());
                nmDataSubmeta.setText(submetaSelecionada.getData().toString());
                nmStatusSubmeta.setText(submetaSelecionada.getStatus());
                nmAssociadasSubmeta.setText("Tarefas vinculadas: " + tarefasAssociadas);

                for (Tarefa tarefas : submetaSelecionada.getTarefas()) {
                    if (tarefas.isConcluida()) {
                        tarefasConcluidas++;
                    }
                }

                nmConcluidasSubmeta.setText("Tarefas concluídas: " + tarefasConcluidas);
                double porcentagem = (double) tarefasConcluidas / tarefasAssociadas;
                nmProgressoSubmeta.setProgress(porcentagem);

            }
        });
    }

    public static void carregaTarefaPorNomeDaSubmeta(Label nmNomeTarefa,
                                                     Label nmDataTarefa,
                                                     Label nmDescricaoTarefa,
                                                     Label nmCategoriaTarefa,
                                                     Label nmPrioridadeTarefa,
                                                     Label nmStatusTarefa,
                                                     ListView<String> listaDeSubmeta,
                                                     ListView<String> listaDeTarefa,
                                                     TarefaRepository tarefaRepository,
                                                     SubmetaRepository submetaRepository) {
        listaDeSubmeta.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Submeta submeta = submetaRepository.findByNome(newValue);

                listaDeTarefa.getItems().clear();
                tarefaRepository.findBySubmetaId(submeta.getId()).forEach(tarefa -> {
                    listaDeTarefa.getItems().add(tarefa.getNome());
                });
            }
        });
        listaDeTarefa.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                Tarefa tarefa = tarefaRepository.findByNomeAndId(newValue, ApplicationController.conta.getId());
                nmNomeTarefa.setText(tarefa.getNome());
                nmDataTarefa.setText(tarefa.getData().toString());
                if (tarefa.getDescricao().isBlank()) {
                    nmDescricaoTarefa.setText("Sem descrição");
                } else {
                    nmDescricaoTarefa.setText(tarefa.getDescricao());
                }
                nmCategoriaTarefa.setText(tarefa.getCategoria());
                nmPrioridadeTarefa.setText(tarefa.getPrioridade());
                if (tarefa.isConcluida()) {
                    nmStatusTarefa.setText("Concluída!");
                } else {
                    nmStatusTarefa.setText("Não concluída.");
                }


            }
        });
    }

    public static void carregarInformacoesTelaDeMetas(ListView<String> lista,
                                                      MetaRepository metaRepository,
                                                      Label sobreNomeMeta,
                                                      Label sobreStatusMeta,
                                                      Label sobreDiasRestantes,
                                                      Label sobreAreaMeta) {
        Meta meta = metaRepository.findByNome(lista.getSelectionModel().getSelectedItem());
        sobreNomeMeta.setText(meta.getNome());
        sobreStatusMeta.setText(meta.getStatus());
        int diasRestantes = Integer.parseInt(String.valueOf(meta.getData().getDayOfYear() - LocalDate.now().getDayOfYear()));
        if (diasRestantes < 0) {
            sobreDiasRestantes.setText("Meta atrasada!");
        } else if (diasRestantes == 0) {
            sobreDiasRestantes.setText("Meta para hoje!");
        } else {
            sobreDiasRestantes.setText(String.valueOf(diasRestantes+ " dias restantes"));
        }

        sobreAreaMeta.setText(meta.getTipo());
    }
    public static void carregaDadosEtarefasAssociadasDaSubmeta(Label sobreNomeSubmeta,
                                                               Label sobreDataSubmeta,
                                                               Label sobreRelacionadoSubmeta,
                                                               Label sobreStatusSubmeta,
                                                               Label sobreNumTarefasSubmetas,
                                                               Label sobreTarefasFeitasSubmetas,
                                                               ProgressBar sobreProgressSubmeta,
                                                               ListView<String> submetas,
                                                               ListView<String> listaDeTarefas,
                                                               ListView<String> listaDeNaoAssociadas,
                                                               SubmetaRepository submetaRepository,
                                                               TarefaRepository tarefaRepository) {

        submetas.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int tarefasAssociadas = 0;
                int tarefasConcluidas = 0;
                listaDeTarefas.getItems().clear();
                listaDeNaoAssociadas.getItems().clear();
                Submeta submeta = submetaRepository.findByNome(newValue);
                tarefaRepository.findBySubmetaId(submeta.getId()).forEach(tarefa -> {
                    listaDeTarefas.getItems().add(tarefa.getNome());
                });
                tarefaRepository.findBySubmetaIdIsNull().forEach(tarefa -> {
                    listaDeNaoAssociadas.getItems().add(tarefa.getNome());

                });
                sobreNomeSubmeta.setText(submeta.getNome());
                sobreDataSubmeta.setText(submeta.getData().toString());
                sobreRelacionadoSubmeta.setText(submeta.getMeta().getNome());
                sobreStatusSubmeta.setText(submeta.getStatus());
                for (Tarefa tarefa : submeta.getTarefas()) {
                    tarefasAssociadas++;
                    if (tarefa.isConcluida()) {
                        tarefasConcluidas++;
                    }
                }
                sobreNumTarefasSubmetas.setText("Tarefas associadas: " + tarefasAssociadas);
                sobreTarefasFeitasSubmetas.setText("Tarefas concluídas: " + tarefasConcluidas);
                double porcentagem = (double) tarefasConcluidas / tarefasAssociadas;
                sobreProgressSubmeta.setProgress(porcentagem);

            }
        });
    }
    public static void carregaDadosParaEdicaoDaSubmeta(SubmetaRepository submetaRepository,
                                                       MetaRepository metaRepository,
                                                       TextField textfield,
                                                       DatePicker datePicker,
                                                       ChoiceBox<String> choiceBox,
                                                       ChoiceBox<String> choiceBoxRelacionado,
                                                       Label nomeDaSubmeta) {
        choiceBoxRelacionado.getItems().clear();
        Submeta submeta = submetaRepository.findByNome(nomeDaSubmeta.getText());
        textfield.setText(submeta.getNome());
        datePicker.setValue(submeta.getData());
        choiceBox.setValue(submeta.getStatus());
        choiceBoxRelacionado.setValue(submeta.getMeta().getNome());
        metaRepository.findAll().forEach(meta -> {
            choiceBoxRelacionado.getItems().add(meta.getNome());
        });

    }
    public static void carregaPrioridadeCategoriaEsubmeta(ChoiceBox<String> cbCatAddTarefaSubmeta,
                                                          ChoiceBox<String> cbPriorAddTarefasubmeta,
                                                          ChoiceBox<String> cbSubmetaAddTarefaSubmeta,
                                                          CategoriaRepository categoriaRepository,
                                                          SubmetaRepository submetaRepository) {
        cbCatAddTarefaSubmeta.getItems().clear();
        cbPriorAddTarefasubmeta.getItems().clear();
        cbSubmetaAddTarefaSubmeta.getItems().clear();
        categoriaRepository.findByTipo("Tarefas").forEach(categoria -> {
            cbCatAddTarefaSubmeta.getItems().add(categoria.getNome());
        });
        for (Prioridade prioridade : Prioridade.values()) {
            cbPriorAddTarefasubmeta.getItems().add(prioridade.getDescricao());
        }
        submetaRepository.findAll().forEach(submeta -> {
            cbSubmetaAddTarefaSubmeta.getItems().add(submeta.getNome());
        });

    }
    public static void carregaDadosParaEdicaoDoTreino(ListView<String> listaDosTreinos,
                                                      DatePicker dpDataTreino,
                                                      ChoiceBox<String> cbTipoTreino,
                                                      TextArea taDescricaoTreino,
                                                      TextField tfNovoTipoTreino,
                                                      TreinoRepository treinoRepository,
                                                      boolean clear) {
            if (clear) {
                tfNovoTipoTreino.setText("");
                dpDataTreino.setValue(null);
                cbTipoTreino.setValue(null);
                taDescricaoTreino.setText("");
            } else {
                Treino treino = treinoRepository.findByNome(listaDosTreinos.getSelectionModel().getSelectedItem());
                tfNovoTipoTreino.setText(treino.getNome());
                dpDataTreino.setValue(treino.getData());
                cbTipoTreino.setValue(treino.getTipo());
                taDescricaoTreino.setText(treino.getDescricao());
            }

    }
    public static void carregarDadosParaTabelaDeTreino(Label guardaNomeTreino,
                                                       TextField tfDuracaoTreino,
                                                       CheckBox checkBoxSemTempo,
                                                       Button btRegistar,
                                                       Button btVoltar,
                                                       Label labelInforma,
                                                       Label qtdTreinosMeusTreinos,
                                                       TableView<TabelaTreino> tabelaTreino,
                                                       ObservableList<TabelaTreino> treinos,
                                                       ListView<String> listaMeusTreinos,
                                                       TreinoRepository treinoRepository) {
        int quantidadeDeTreinos = 0;
        treinos.clear();
        tabelaTreino.getItems().clear();
        listaMeusTreinos.getItems().clear();
        treinoUtil.statusTabelaTreino(tabelaTreino);
        treinoRepository.findAllOrderByDataDesc().forEach(treino -> {
            if (!treino.getData().isAfter(LocalDate.now())) {
                treinos.add(new TabelaTreino(treino.getNome(), treino.getTipo(), treino.getDescricao(), treino.getConcluido(),treino.getData(), registrar -> {
                    TreinoUtil.exibeRegistroCompletoDoTreino(tfDuracaoTreino,checkBoxSemTempo, btRegistar, btVoltar, labelInforma, true);
                    guardaNomeTreino.setText(treino.getNome());

                }));
                listaMeusTreinos.getItems().add(treino.getNome());
            }
        });

        for (TabelaTreino treino : treinos) {
            quantidadeDeTreinos++;
        }
        qtdTreinosMeusTreinos.setText("Treinos: " + quantidadeDeTreinos);
        tabelaTreino.getItems().addAll(treinos);
    }
    public static void atualizaListaDeTreinos(ListView<String> listaDeTreinos, TreinoRepository treinoRepository) {
        listaDeTreinos.getItems().clear();
        treinoRepository.findAllOrderByDataDesc().forEach(treino -> {
            listaDeTreinos.getItems().add(treino.getNome());
        });
    }
    public static void carregaTiposDeTreino(ChoiceBox<String> cbTipoTreino, CategoriaRepository categoriaRepository) {
        cbTipoTreino.getItems().clear();
        categoriaRepository.findByTipo("Treino").forEach(categoria -> {
            cbTipoTreino.getItems().add(categoria.getNome());
        });
    }
    public static void carregaTabelaDeTreinoFiltrada(Label guardaNomeTreino,
                                                     TextField tfDuracaoTreino,
                                                     CheckBox checkBoxSemTempo,
                                                     Button btRegistar,
                                                     Button btVoltar,
                                                     Label labelInforma,
                                                     DatePicker datePicker,
                                                     TableView<TabelaTreino> tabelaTreino,
                                                     ObservableList<TabelaTreino> treinos,
                                                     TreinoRepository treinoRepository,
                                                     Label labelTreinosFiltrados,
                                                     String caseName) {
        int qtdDeTreinoFiltrado = 0;
         treinos.clear();
         tabelaTreino.getItems().clear();
        treinoUtil.statusTabelaTreino(tabelaTreino);
         switch (caseName) {
             case "Dia":
                 treinoRepository.findByData(datePicker.getValue()).forEach(treino -> {
                     treinos.add(new TabelaTreino(treino.getNome(), treino.getTipo(), treino.getDescricao(), treino.getConcluido(),treino.getData(), registro -> {
                         TreinoUtil.exibeRegistroCompletoDoTreino(tfDuracaoTreino,checkBoxSemTempo, btRegistar, btVoltar, labelInforma, true);
                         guardaNomeTreino.setText(treino.getNome());
                     }));
                 });
                 TreinoUtil.filtraTreinos(qtdDeTreinoFiltrado,labelTreinosFiltrados,treinos);
                 tabelaTreino.getItems().addAll(treinos);
                 break;
             case "Semana":
                 LocalDate inicioSemana = datePicker.getValue().with(DayOfWeek.MONDAY);
                 LocalDate fimSemana = datePicker.getValue().with(DayOfWeek.SUNDAY);
                 inserirTreino(guardaNomeTreino, tfDuracaoTreino, checkBoxSemTempo, btRegistar, btVoltar, labelInforma, treinos, treinoRepository, inicioSemana, fimSemana);
                 TreinoUtil.filtraTreinos(qtdDeTreinoFiltrado,labelTreinosFiltrados,treinos);
                 tabelaTreino.getItems().addAll(treinos);
                 break;
             case "Mês":
                 LocalDate inicioMes = datePicker.getValue().withDayOfMonth(1);
                 LocalDate fimMes = datePicker.getValue().withDayOfMonth(datePicker.getValue().lengthOfMonth());
                 inserirTreino(guardaNomeTreino, tfDuracaoTreino, checkBoxSemTempo, btRegistar, btVoltar, labelInforma, treinos, treinoRepository, inicioMes, fimMes);
                 TreinoUtil.filtraTreinos(qtdDeTreinoFiltrado,labelTreinosFiltrados,treinos);
                 tabelaTreino.getItems().addAll(treinos);
                 break;
             case "Ano":
                    LocalDate inicioAno = datePicker.getValue().withDayOfYear(1);
                    LocalDate fimAno = datePicker.getValue().withDayOfYear(datePicker.getValue().lengthOfYear());
                 inserirTreino(guardaNomeTreino, tfDuracaoTreino, checkBoxSemTempo, btRegistar, btVoltar, labelInforma, treinos, treinoRepository, inicioAno, fimAno);
                 TreinoUtil.filtraTreinos(qtdDeTreinoFiltrado,labelTreinosFiltrados,treinos);
                 tabelaTreino.getItems().addAll(treinos);
                    break;
         }
    }

    private static void inserirTreino(Label guardaNomeTreino, TextField tfDuracaoTreino, CheckBox checkBoxSemTempo, Button btRegistar, Button btVoltar, Label labelInforma, ObservableList<TabelaTreino> treinos, TreinoRepository treinoRepository, LocalDate inicioSemana, LocalDate fimSemana) {
        treinoRepository.findByIntervaloDeData(inicioSemana, fimSemana).forEach(treino1 -> {
            treinos.add(new TabelaTreino(treino1.getNome(), treino1.getTipo(), treino1.getDescricao(), treino1.getConcluido(),treino1.getData(), registro -> {
                TreinoUtil.exibeRegistroCompletoDoTreino(tfDuracaoTreino,checkBoxSemTempo, btRegistar, btVoltar, labelInforma, true);
                guardaNomeTreino.setText(treino1.getNome());
            }));
        });
    }
    public static void carregarProjetosParaNovoCard(ChoiceBox<String> cbEscolheProjetoCard, ProjetoRepository projetoRepository) {
        cbEscolheProjetoCard.getItems().clear();
        projetoRepository.findAll().forEach(projeto -> {
            cbEscolheProjetoCard.getItems().add(projeto.getNome());
        });
    }
    public static List<ProjetoCard> carregaMeusCards(ChoiceBox<String> cbCardsRelacionadosMeusProjetos, ProjetoCardRepository projetoCardRepository, String projeto) {
        List<ProjetoCard> listaDeCards = new ArrayList<>();
        cbCardsRelacionadosMeusProjetos.getItems().clear();
        projetoCardRepository.findByProjeto(projeto).forEach(projetoCard -> {
            cbCardsRelacionadosMeusProjetos.getItems().add(projetoCard.getNome());
            listaDeCards.add(projetoCard);
        });
        return listaDeCards;
    }
    public static void carregaCategoriasProjeto(ChoiceBox<String> cbCategoriaProjeto, CategoriaRepository categoriaRepository) {
        cbCategoriaProjeto.getItems().clear();
        categoriaRepository.findByTipo("Projetos").forEach(categoria -> {
            cbCategoriaProjeto.getItems().add(categoria.getNome());
        });
    }
    public static void carregarHabitos(CategoriaRepository categoriaRepository,
                                       ChoiceBox<String> cbSelecionadoHabito) {
        categoriaRepository.findByTipo("Hábitos").forEach(habitos -> {
            cbSelecionadoHabito.getItems().add(habitos.getNome());
        });
    }
    public static void carregaListaDeHabitos(ListView<String> listaDeHabitos,
                                             HabitosRepository habitosRepository,
                                             Label pegaData) {
        LocalDate data = LocalDate.parse(pegaData.getText());
        listaDeHabitos.getItems().clear();
        habitosRepository.findByData(data).forEach(habitos -> {
            listaDeHabitos.getItems().add(habitos.getNome());
        });
    }
    public static void carregaDadoDasFinancas(Label labelRenda,
                                              Label labelGastos,
                                              Label labelSaldo,
                                              FinancaRepository financaRepository) {
        Financa financa = financaRepository.findById(1L).get();
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        double valorDaRenda = Double.parseDouble(financa.getRenda().toPlainString());
        double valorGaGastos = Double.parseDouble(financa.getGastos().toPlainString());
        double valorDoSaldo = Double.parseDouble(financa.getSaldo().toPlainString());
        labelRenda.setText(nf.format(valorDaRenda));
        labelGastos.setText(nf.format(valorGaGastos));
        labelSaldo.setText(nf.format(valorDoSaldo));
    }
    public static void carregaDadosDaTabelaFinancas(TableView<TabelaFinanca> tabelaFinanca,
                                                    ObservableList<TabelaFinanca> listaTabela,
                                                    RegistroFinancaRepository registroFinancaRepository,
                                                    FinancaRepository financaRepository,
                                                    DatePicker datePicker,
                                                    Label gastosLabel,
                                                    Label saldo) {
        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.now());
        }
        listaTabela.clear();
        tabelaFinanca.getItems().clear();
        LocalDate inicioDoMes = datePicker.getValue().withDayOfMonth(1);
        LocalDate fimDoMes = datePicker.getValue().withDayOfMonth(datePicker.getValue().lengthOfMonth());
        registroFinancaRepository.findByIntervaloDeData(inicioDoMes,fimDoMes).forEach(registro -> {
            listaTabela.add(new TabelaFinanca(registro.getDescricao(),tarefasUtil.formataData(registro.getData()),registro.getValor().toString(),registro.getCategoria(), apagar -> {
                Mensagem mensagem = new Mensagem();
                int resposta = mensagem.retornoMessege("Aviso", "Deseja realmente apagar o registro de finança?");
                if (resposta == 1) {
                    registroFinancaRepository.deleteByFinanca(registro.getId());
                    listaTabela.remove(apagar);
                    tabelaFinanca.getItems().remove(apagar);
                    mensagem.showMessege("Sucesso", "Registro de finança apagado com sucesso!", 2);
                }
            }));
        });
        NumberFormat nf = NumberFormat.getCurrencyInstance();
        tabelaFinanca.getItems().addAll(listaTabela);
        Financa financa = financaRepository.findById(1L).get();
        BigDecimal gastos = registroFinancaRepository.sumByFinancaAsBigDecimal(inicioDoMes,fimDoMes);
        financa.setGastos(gastos);
        financa.setSaldo(financa.getRenda().subtract(gastos));
        double valorDosGastos = Double.parseDouble(financa.getGastos().toPlainString());
        double valorDoSaldo = Double.parseDouble(financa.getSaldo().toPlainString());
        gastosLabel.setText(nf.format(valorDosGastos));
        saldo.setText(nf.format(valorDoSaldo));
        financaRepository.save(financa);

    }
    public static void carregarCategoriaGastos(ChoiceBox<String> categoria,
                                               CategoriaRepository categoriaRepository) {
        categoria.getItems().clear();
        categoriaRepository.findByTipo("Finanças").forEach(categorias -> {
            categoria.getItems().add(categorias.getNome());
        });
    }
    public static void carregaEstatisticas(Label geralTotalTarefas,
                                           Label geralTarefasConcluidas,
                                           Label geralTotalEstudos,
                                           Label geralTempoEstudos,
                                           Label geralTreinosRegistrados,
                                           Label geralHorasDeTreino,
                                           Label geralTotalHabitos,
                                           Label geralHabitosConcluidos,
                                           Label porcentagemGeralHabitos,
                                           Label porcentagemGeralTarefas,
                                           TarefaRepository tarefaRepository,
                                           RegistroEstudosRepository registroEstudosRepository,
                                           TreinoRepository treinoRepository,
                                           HabitosRepository habitosRepository) {
        DecimalFormat df = new DecimalFormat("0.00%");
        int totalTarefas = 0;
        int tarefasConcluidas = 0;
        double porcentagemTodasTarefas = 0;
        List<Tarefa> todasAsTarefas = tarefaRepository.findAll();
        for (Tarefa tarefas : todasAsTarefas) {
            totalTarefas++;
            if (tarefas.isConcluida()) {
                tarefasConcluidas++;
            }
        }
        geralTotalTarefas.setText(String.valueOf(totalTarefas));
        geralTarefasConcluidas.setText(String.valueOf(tarefasConcluidas));
        if (totalTarefas != 0) {
            porcentagemTodasTarefas = ((double) tarefasConcluidas / totalTarefas);
            porcentagemGeralTarefas.setText(df.format(porcentagemTodasTarefas));
            System.out.println(porcentagemTodasTarefas);
        } else {
            porcentagemGeralTarefas.setText("0%");

        }
        List<String> tempoTotalDosEstudos = new ArrayList<>();
        int estudos = 0;
        List<RegistroEstudo> todosOsEstudos = registroEstudosRepository.findAll();
        for (RegistroEstudo estudo : todosOsEstudos) {
            estudos++;
            tempoTotalDosEstudos.add(estudo.getTempo());
        }
        geralTotalEstudos.setText(String.valueOf(estudos));
        geralTempoEstudos.setText(somadorDoTempo(tempoTotalDosEstudos));

        List<Treino> todosOsTreinos = treinoRepository.findAll();
        List<String> tempoDosTreinos = new ArrayList<>();
        int treinos = 0;

        for (Treino treino : todosOsTreinos) {
            treinos++;
            tempoDosTreinos.add(treino.getTempo());
        }
        geralTreinosRegistrados.setText(String.valueOf(treinos));
        geralHorasDeTreino.setText(somadorDoTempo(tempoDosTreinos));

        List<Habitos> todosOsHabitos = habitosRepository.findAll();
        int totalHabitos = 0;
        int habitosConcluidos = 0;
        double porcentagemTodosHabitos = 0;
        for (Habitos habitos : todosOsHabitos) {
            totalHabitos++;
            if (habitos.isConcluido()) {
                habitosConcluidos++;
            }
        }
        if (totalHabitos != 0) {
            porcentagemTodosHabitos = (double) habitosConcluidos / totalHabitos;
            porcentagemGeralHabitos.setText(df.format(porcentagemTodosHabitos));
        } else {
            porcentagemGeralHabitos.setText("0%");

        }
        geralTotalHabitos.setText(String.valueOf(totalHabitos));
        geralHabitosConcluidos.setText(String.valueOf(habitosConcluidos));

    }
    private static String somadorDoTempo(List<String> tempoTotalDosEstudos) {
        int horas = 0;
        int minutos = 0;
        int segundos = 0;
        for (String tempo : tempoTotalDosEstudos) {
            String[] tempoSeparado = tempo.split(":");
            if (tempoSeparado.length < 3) {
                continue;
            }
            horas += Integer.parseInt(tempoSeparado[0]);
            minutos += Integer.parseInt(tempoSeparado[1]);
            segundos += Integer.parseInt(tempoSeparado[2]);
        }
        if (segundos >= 60) {
            minutos += segundos / 60;
            segundos = segundos % 60;
        }
        if (minutos >= 60) {
            horas += minutos / 60;
            minutos = minutos % 60;
        }
        return String.format("%02d:%02d:%02d", horas, minutos, segundos);
    }
    public static void carregaEstatisticasPorAno(Label anoTotalTarefa,
                                                 Label anoTotalTarefaConcluido,
                                                 Label anoDesempenhoTarefa,
                                                 Label anoEstudos,
                                                 Label anoHorasEstudos,
                                                 Label anoMediaEstudos,
                                                 Label anoTreinos,
                                                 Label anoTotalHorasTreino,
                                                 Label anoMediaTreinos,
                                                 Label anoHabitos,
                                                 Label anoHabitosConcluidos,
                                                 Label anoDesempenhoHabitos,
                                                 DatePicker datePicker,
                                                 TarefaRepository tarefaRepository,
                                                 RegistroEstudosRepository registroEstudosRepository,
                                                 TreinoRepository treinoRepository,
                                                 HabitosRepository habitosRepository) {
        if (datePicker.getValue() == null) {
            datePicker.setValue(LocalDate.now());
        }
        DecimalFormat df = new DecimalFormat("0.00%");
        int totalTarefas = 0;
        int tarefasConcluidas = 0;
        double porcentagemTodasTarefas = 0;
        LocalDate inicioDoAno = datePicker.getValue().withDayOfYear(1);
        LocalDate fimDoAno = datePicker.getValue().withDayOfYear(datePicker.getValue().lengthOfYear());
        List<Tarefa> todasAsTarefas = tarefaRepository.findByIntervaloDeData(inicioDoAno,fimDoAno);
        for (Tarefa tarefas : todasAsTarefas) {
            totalTarefas++;
            if (tarefas.isConcluida()) {
                tarefasConcluidas++;
            }
        }
        anoTotalTarefa.setText(String.valueOf(totalTarefas));
        anoTotalTarefaConcluido.setText(String.valueOf(tarefasConcluidas));
        if (totalTarefas != 0) {
            porcentagemTodasTarefas = (double) tarefasConcluidas / totalTarefas;
            anoDesempenhoTarefa.setText(df.format(porcentagemTodasTarefas));
        } else {
            anoDesempenhoTarefa.setText("0%");

        }

        List<String> tempoTotalDosEstudos = new ArrayList<>();
        int estudos = 0;
        inicioDoAno = datePicker.getValue().withDayOfYear(1);
        fimDoAno = datePicker.getValue().withDayOfYear(datePicker.getValue().lengthOfYear());
        List<RegistroEstudo> todosOsEstudos = registroEstudosRepository.findByIntervaloDeData(inicioDoAno,fimDoAno);
        for (RegistroEstudo estudo : todosOsEstudos) {
            estudos++;
            tempoTotalDosEstudos.add(estudo.getTempo());
        }
        anoEstudos.setText(String.valueOf(estudos));
        anoHorasEstudos.setText(somadorDoTempo(tempoTotalDosEstudos));
        calculaMedia(datePicker, anoHorasEstudos.getText(), anoMediaEstudos);

        List<Treino> todosOsTreinos = treinoRepository.findByAno(datePicker.getValue().getYear());
        List<String> tempoDosTreinos = new ArrayList<>();
        int treinos = 0;

        for (Treino treino : todosOsTreinos) {
            treinos++;
            tempoDosTreinos.add(treino.getTempo());
        }
        anoTreinos.setText(String.valueOf(treinos));
        anoTotalHorasTreino.setText(somadorDoTempo(tempoDosTreinos));
        calculaMedia(datePicker, anoTotalHorasTreino.getText(), anoMediaTreinos);

        List<Habitos> todosOsHabitos = habitosRepository.findByAno(datePicker.getValue().getYear());
        int totalHabitos = 0;
        int habitosConcluidos = 0;
        double porcentagemTodosHabitos = 0;
        for (Habitos habitos : todosOsHabitos) {
            totalHabitos++;
            if (habitos.isConcluido()) {
                habitosConcluidos++;
            }
        }
        if (totalHabitos != 0) {
            porcentagemTodosHabitos = (double) habitosConcluidos / totalHabitos;
            anoDesempenhoHabitos.setText(df.format(porcentagemTodosHabitos));
        } else {
            anoDesempenhoHabitos.setText("0%");

        }
        anoHabitos.setText(String.valueOf(totalHabitos));
        anoHabitosConcluidos.setText(String.valueOf(habitosConcluidos));


    }
    private static void calculaMedia(DatePicker datePicker, String horasDaMedia, Label labelMedia) {
        int anoAtual = LocalDate.now().getYear();
        int anoSelecionado = datePicker.getValue().getYear();
        String[] valoresTempo = horasDaMedia.split(":");
        if (anoAtual < anoSelecionado) {
            logicaTempo(12, valoresTempo, labelMedia);
        } else {
            int mesAtual = LocalDate.now().getMonthValue();

            logicaTempo(mesAtual, valoresTempo, labelMedia);

        }
    }
    private static void logicaTempo(int valorDoMes,
                             String[] valoresTempo,
                             Label labelMedia) {
        int horas = Integer.parseInt(valoresTempo[0]) * 3600;
        int minutos = Integer.parseInt(valoresTempo[1]) * 60;
        int segundos = Integer.parseInt(valoresTempo[2]);

        int tempoTotal = horas + minutos + segundos;
        int media = tempoTotal / valorDoMes;
        int horasMedia = media / 3600;
        int minutosMedia = (media % 3600) / 60;
        int segundosMedia = media % 60;
        labelMedia.setText(String.format("%02d:%02d:%02d", horasMedia, minutosMedia, segundosMedia));

    }
    public static void carregaDadosDoUsuario(Label meusDadosNome,
                                             Label meusDadosDataNascimento,
                                             Label diasAniversario,
                                             ContaRepository contaRepository) {
        Conta conta = contaRepository.findById(ApplicationController.conta.getId()).get();
        meusDadosNome.setText(conta.getNome());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        meusDadosDataNascimento.setText(formatter.format(conta.getDataNascimento()));
        int diaDoAniversario = conta.getDataNascimento().getDayOfMonth();
        int mesDoAniversario = conta.getDataNascimento().getMonthValue();
        LocalDate dataDoAniversario = LocalDate.of(LocalDate.now().getYear(), mesDoAniversario, diaDoAniversario);
        if (dataDoAniversario.isBefore(LocalDate.now())) {
            dataDoAniversario = dataDoAniversario.plusYears(1);
        }
        long diasRestantes = ChronoUnit.DAYS.between(LocalDate.now(), dataDoAniversario);

        diasAniversario.setText(String.valueOf(diasRestantes));
    }
    public static void carregarDadosDaTelaInicial(LocalDate data,
                                                  ListView<String> homeTreinosLista,
                                                  ListView<String> homeTarefasLista,
                                                  ListView<String>homeHabitosLista,
                                                  ProgressBar homeProgressBar,
                                                  TreinoRepository treinoRepository ,
                                                  TarefaRepository tarefaRepository,
                                                  HabitosRepository habitosRepository,
                                                  Label progressoHomeLabel) {
        homeTreinosLista.getItems().clear();
        homeTarefasLista.getItems().clear();
        homeHabitosLista.getItems().clear();
        int habitosCont =0;
        int habitosConcluidos = 0;
        int tarefasCont = 0;
        int tarefasConcluidas =0;
        int treinosCont = 0;
        int treinosConcluidos = 0;
        List<Treino> treinos = treinoRepository.findByData(data);
        List<Tarefa> tarefas = tarefaRepository.findByDataSemId(data);
        List<Habitos> habitos = habitosRepository.findByData(data);

        for (Treino treino : treinos) {
            treinosCont++;
            if (treino.getConcluido()) {
                treinosConcluidos++;
            }
            homeTreinosLista.getItems().add(treino.getNome());
        }

        for (Tarefa tarefa : tarefas) {
            tarefasCont++;
            if (tarefa.isConcluida()) {
                tarefasConcluidas++;
            }
            homeTarefasLista.getItems().add(tarefa.getNome());
        }

        for (Habitos habito : habitos) {
            habitosCont++;
            if (habito.isConcluido()) {
                habitosConcluidos++;
            }
            homeHabitosLista.getItems().add(habito.getNome());
        }
        int total = treinosCont+tarefasCont+habitosCont;
        int concluidos = treinosConcluidos+tarefasConcluidas+habitosConcluidos;
        double progresso = (double) concluidos / total;
        DecimalFormat df = new DecimalFormat("0.00%");
        progressoHomeLabel.setText(df.format(progresso));

        homeProgressBar.setProgress(progresso);

    }
}
