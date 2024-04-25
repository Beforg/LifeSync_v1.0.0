package br.com.myegoo.app.myego.configuration;

import br.com.myegoo.app.myego.model.Categoria;
import br.com.myegoo.app.myego.model.financas.Financa;
import br.com.myegoo.app.myego.model.estudo.Pomodoro;
import br.com.myegoo.app.myego.repository.ApplicationConfigurationRepository;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.FinancaRepository;
import br.com.myegoo.app.myego.repository.PomodoroRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class Configuration {
    public static void removerCategoria(ChoiceBox<String> tipo,ChoiceBox<String> nome, CategoriaRepository categoriaRepository) {
        Mensagem mensagem = new Mensagem();
        if (nome.getValue() == null) {
            mensagem.showMessege("Categoria não selecionad","Selecione uma categoria para remover",1);
            throw new IllegalArgumentException("Selecione uma categoria para remover");
        }
        categoriaRepository.deleteByNomeAndTipo(nome.getValue(),tipo.getValue());
        tipo.setValue(null);
        nome.setValue(null);
        mensagem.showMessege("Categoria removida","Categoria removida com sucesso, reinicie o aplicativo para atualizar.",2);
    }
    public static void carregarCategorias(ChoiceBox<String> tipo, ChoiceBox<String> nome,CategoriaRepository categoriaRepository) {
        String[] categoriasString = {"Projetos","Tarefas","Estudos","Área","Finanças","Treino"};
        List<String> categorias = new ArrayList<>(List.of(categoriasString));
        tipo.getItems().addAll(categorias);
        tipo.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

            if (newValue.equals("Projetos")) {
                nome.getItems().clear();
                findCategoria(categoriaRepository, "Projetos", nome);
                nome.getItems().addAll();
            } else if (newValue.equals("Tarefas")) {
                nome.getItems().clear();
                findCategoria(categoriaRepository, "Tarefas", nome);
            } else if (newValue.equals("Estudos")) {
                nome.getItems().clear();
                findCategoria(categoriaRepository, "Estudos", nome);
            } else if (newValue.equals("Área")) {
                nome.getItems().clear();
                findCategoria(categoriaRepository, "Área", nome);
            } else if (newValue.equals("Finanças")) {
                nome.getItems().clear();
                findCategoria(categoriaRepository, "Finanças", nome);
            } else  {
                nome.getItems().clear();
                findCategoria(categoriaRepository, "Treino", nome);
            }
        });

    }

    private static void findCategoria(CategoriaRepository categoriaRepository, String tipo, ChoiceBox<String> nome) {
        List<Categoria> categoria = categoriaRepository.findByTipo(tipo);
        for (Categoria c : categoria) {
            nome.getItems().add(c.getNome());
        }
    }
    public static void setNumeroDaSemanaDoAno(Label tituloSemana) {
        LocalDate data = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("w");
        int semanaAtual = Integer.parseInt(formatter.format(data));
        int totalSemanas = (int) WeekFields.of(Locale.getDefault()).weekOfWeekBasedYear().range().getMaximum();
        tituloSemana.setText("Semana " + semanaAtual + "/" + totalSemanas + " de " + data.getYear());
    }
    public static void verificaPrimeiroAcesso(ApplicationConfigurationRepository applicationConfigurationRepository,
                                              Pane panePrimeiroAcesso, Pane paneLogin) {
        ApplicationConfiguration applicationConfiguration = applicationConfigurationRepository.findById(1L).orElse(null);
        if (applicationConfiguration == null) {
            applicationConfigurationRepository.save(new ApplicationConfiguration(true));
        }
        if (applicationConfiguration != null && !applicationConfiguration.getPrimeiroAcesso()) {
            panePrimeiroAcesso.setVisible(false);
            paneLogin.setVisible(true);
        }
    }
    public static void irParaTelaDeCadastro(Pane panePrimeiroAcesso, Pane paneCadastro) {
            panePrimeiroAcesso.setVisible(false);
            paneCadastro.setVisible(true);

    }
    public static void inicializaFinancaEpomodoro(FinancaRepository financaRepository,
                                           PomodoroRepository pomodoroRepository) {
        if (financaRepository.findAll().isEmpty()) {
            Financa financa = new Financa(BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO,BigDecimal.ZERO);
            financa.setId(1L);
            financaRepository.save(financa);
        }
        if (pomodoroRepository.findAll().isEmpty()) {
            Pomodoro pomodoro = new Pomodoro(25,5,15,4);
            pomodoro.setId(1L);
            pomodoroRepository.save(pomodoro);

        }
    }
}
