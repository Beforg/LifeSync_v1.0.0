package br.com.myegoo.app.myego.controller;

import br.com.myegoo.app.myego.configuration.Configuration;
import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.model.financas.TabelaFinanca;
import br.com.myegoo.app.myego.model.projetos.TabelaItensCard;
import br.com.myegoo.app.myego.model.treino.TabelaTreino;
import br.com.myegoo.app.myego.model.estudo.TabelaEstudos;
import br.com.myegoo.app.myego.model.tarefa.TabelaTarefas;
import br.com.myegoo.app.myego.repository.*;
import br.com.myegoo.app.myego.service.*;
import br.com.myegoo.app.myego.service.estudo.EstudosService;
import br.com.myegoo.app.myego.service.estudo.PomodoroService;
import br.com.myegoo.app.myego.service.estudo.RegistroEstudoService;
import br.com.myegoo.app.myego.service.login.AlteraInfosService;
import br.com.myegoo.app.myego.service.login.CadastrarService;
import br.com.myegoo.app.myego.service.login.CheckLoginService;
import br.com.myegoo.app.myego.service.login.SegurancaService;
import br.com.myegoo.app.myego.service.meta.MetaService;
import br.com.myegoo.app.myego.service.meta.SubmetaService;
import br.com.myegoo.app.myego.service.projeto.ItemProjetoCardService;
import br.com.myegoo.app.myego.service.projeto.ProjetoCardService;
import br.com.myegoo.app.myego.service.projeto.ProjetoService;
import br.com.myegoo.app.myego.service.TarefaService;
import br.com.myegoo.app.myego.service.seguranca.BackupDados;
import br.com.myegoo.app.myego.utils.*;
import br.com.myegoo.app.myego.utils.estudo.*;
import br.com.myegoo.app.myego.utils.financas.FinancaUtil;
import br.com.myegoo.app.myego.utils.financas.FormatadorFinancas;
import br.com.myegoo.app.myego.utils.financas.ListenerFinanca;
import br.com.myegoo.app.myego.utils.habitos.ListenerHabitos;
import br.com.myegoo.app.myego.utils.login.FormatadorLogin;
import br.com.myegoo.app.myego.utils.login.ListenerLogin;
import br.com.myegoo.app.myego.utils.metas.FormatadorMetas;
import br.com.myegoo.app.myego.utils.metas.ListenerMeta;
import br.com.myegoo.app.myego.utils.metas.MetaUtil;
import br.com.myegoo.app.myego.utils.projeto.ListenerProjetos;
import br.com.myegoo.app.myego.utils.projeto.ProjetoUtil;
import br.com.myegoo.app.myego.utils.tarefa.Calendario;
import br.com.myegoo.app.myego.utils.tarefa.FormatadorTarefas;
import br.com.myegoo.app.myego.utils.tarefa.ListenerTarefas;
import br.com.myegoo.app.myego.utils.tarefa.TarefasUtil;
import br.com.myegoo.app.myego.utils.treino.FormatadorTreino;
import br.com.myegoo.app.myego.utils.treino.ListenerTreino;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.ResourceBundle;

@Component
public class ApplicationController implements Initializable {
    /**
     * SEGURANÇA E CONFIGURAÇÕES
     */
    @Autowired
    ApplicationConfigurationRepository applicationConfigurationRepository;
    @FXML
    private ChoiceBox<String> nomePreferencia,tipoPreferencia;
    @FXML
    private Pane panePreferências,paneBoasVindas;

    public void backup() {
        BackupDados.backup();
    }
    public void carregarBackup() {
        BackupDados.carregaBackup(paneHome);
    }
    public void removerCategoria() {
        Configuration.removerCategoria(tipoPreferencia,nomePreferencia,categoriaRepository);
    }
    public void escolheTema() {

    }
    public void comecarAplicacaoCadastro() {
        Configuration.irParaTelaDeCadastro(paneBoasVindas,containerCadastro);
    }
    /**
     * CONTROLADOR HOME:
     */
    @FXML
    private ListView<String> homeTreinosLista,homeTarefasLista,homeHabitosLista;
    @FXML
    private Label homeNomeTarefa,homeCategoriaTarefa,homePrioridadeTarefa,homeTipoTreino,homeStatusTreino
            ,homeHabitosNome,homeStatusHabito,homeStatusTarefa,progressoHomeLabel,tituloSemana,guardaIdConta;
    @FXML
    private ProgressBar homeProgressBar;
    @FXML
    private DatePicker homeSelecionarDiaProgresso;

    private void atualizaDadosHome() {
        CarregaDadosService.carregarDadosDaTelaInicial(LocalDate.now(),homeTreinosLista,homeTarefasLista,homeHabitosLista,homeProgressBar,treinoRepository,
                tarefaRepository,habitosRepository,progressoHomeLabel);
    }


    /**
     * CONTROLADOR TAREFAS:
     */

    @FXML
    private Pane paneHome, panePerfil, paneTarefas, paneEstudos, paneAtividades,cadastro, login, containerAplicacao,
            containerLogin,containerCadastro, muralCalendario,paneMuralTarefas,paneMetas,adicionarTarefas,paneHabitos,paneFinancas,
            paneEstatisticasEdados;
    @FXML
    private Label muralSemanaDia,muralCalendarioMesAtual,muralCalendarioAtrasadas,muralCalendarioDia, labelDateTime,
            labelNome,textoSenha,muralNomeTarefa, muralDataTarefa, muralPrioridadeTarefa, muralCategoriaTarefa,
            muralDescricaoTarefa, muralCalendarioTarefasMes,muralCalendarioConcluidas,labelAvisosTarefas,labelAvisosTarefasMural;
    @FXML
    private Label selecionadaCalendarioNome, selecionadaCalendarioData,selecionadaCalendarioCategoria,
            selecionadaCalendarioPrioridade,selecionadaCalendarioDescricao;
    @FXML
    private Label muralSemanaTarefasSemana, muralSemanaConcluidas,muralSemanaAtrasadas,selecionadaTarefaConcluida,
            selecionadaTarefaNumero,labelCalendarioAtrasadas;
    @FXML
    private Label selecionadaSemanaPrioridade,selecionadaSemanaCategoria,selecionadaSemanaData,selecionadaSemanaNome,
            selecionadaSemanaDescricao,labelCalendarioProgresso,labelSemanaProgresso;
    @FXML
    private TextField tfNomeCadastro, tfNomeUsuarioCadastro, tfUserLogin,tfNomeTarefa,tfNomeCategoria, editarNomeTarefa ;
    @FXML
    private HBox hboxFiltroTarefas;
    @FXML
    private PasswordField tfPasswordLogin, tfSenhaCadastro;
    @FXML
    private DatePicker dataPickerCadastro, dataTarefa, editarDataTarefa,dataFiltroDaTarefa;
    @FXML
    private Button botaoCadastrar, botaoLogin, botaoNovaTarefa, botaoNovaCategoria, atual, anterior,proximo,
            botaoEditarTarefa, editarTarefaBtSalvar,editarTarefaBtExcluir;
    @FXML
    private ChoiceBox<String> cbCategoriaTarefa, cbPrioridadeTarefa, editarPrioridadeTarefa, editarCategoriaTarefa,
            filtroTarefa,filtroTarefaSub,choiceBoxEscolhaSubmeta;
    @FXML
    private TableView<TabelaTarefas> tabelaTarefas;
    @FXML
    private TableColumn<TabelaTarefas,String> colNome, colData, colPrioridade, colCategoria;
    @FXML
    private TableColumn<TabelaTarefas,Boolean> colConcluido;
    @FXML
    private GridPane gridDetalhesDaTabelaTarefa,gridTarefaSelecionadaSemana,gridCategoria, gridNovaTarefa,calendarioFull
            ,muralSemanaGridPane,muralCalendarioGridPane,gridSemana,gridTarefaSelecionadaCalendario;
    @FXML
    private VBox vBox_descricao,muralTarefasDescricao,muralSemanaVBoxLista,muralCalendarioVBoxLista;
    @FXML
    private VBox terca,quarta,quinta,sexta,sabado,domingo;
    @FXML
    private TextArea textDescricao, textAreaEditarTarefa;
    @FXML
    private CheckBox checkBoxConcluido,selecionadaCalendarioConcluido,selecionadaSemanaConcluido;
    @FXML
    private ListView<String> muralCalendarioLista,muralSemanaLista;
    @FXML
    private ProgressBar muralCalendarioProgresso,muralSemanaProgresso;
    @FXML
    private ImageView imageViewProgresso,imageViewAtrasadas;
    @Autowired
    ContaRepository contaRepository;
    @Autowired
    TarefaRepository tarefaRepository;
    @Autowired
    CategoriaRepository categoriaRepository;

    @Autowired
    SegurancaService segurancaService;


    ObservableList<TabelaTarefas> tarefasObservableList = FXCollections.observableArrayList();

    public static Conta conta;
    public void salvarTarefa(){
        TarefaService.salvarTarefa(tarefaRepository,tfNomeTarefa,textDescricao,dataTarefa,cbPrioridadeTarefa,
                cbCategoriaTarefa,conta,choiceBoxEscolhaSubmeta,submetaRepository,labelAvisosTarefas);
        atualizaTarefa();
        atualizaDadosHome();
    }
    public void atualizaTarefa() {
        CarregaDadosService.carregaTarefas(selecionadaTarefaConcluida,selecionadaTarefaNumero,tarefasObservableList,
                tarefaRepository, tabelaTarefas, conta.getId(), filtroTarefaSub,filtroTarefaSub);
        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,
                muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,
                dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
        Calendario.muralCalendario(muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,
                muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,tarefaRepository);
        Calendario.muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                ,muralSemanaLista,gridSemana, muralSemanaDia,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
        Calendario.carregaDadosDaSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,
                muralSemanaProgresso,muralSemanaDia,muralSemanaLista,tarefaRepository);
        atualizaDadosHome();
    }
    public void checkConcluir() {

        CarregaDadosService.carregaTarefas(selecionadaTarefaConcluida,selecionadaTarefaNumero,tarefasObservableList, tarefaRepository, tabelaTarefas, conta.getId(), filtroTarefa,filtroTarefaSub);
        Calendario.muralCalendario(muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,tarefaRepository);
        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
        if (!gridTarefaSelecionadaSemana.isVisible()) {
            gridTarefaSelecionadaCalendario.setVisible(selecionadaCalendarioConcluido.isSelected() || !selecionadaCalendarioConcluido.isSelected());
        }
        ListenerTarefas.listenerListaDaSemana(gridCategoria,selecionadaSemanaPrioridade,selecionadaSemanaCategoria,selecionadaSemanaData,selecionadaSemanaNome,selecionadaSemanaConcluido,muralSemanaLista,tarefaRepository,gridNovaTarefa,gridTarefaSelecionadaSemana);
        Calendario.muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                ,muralSemanaLista,gridSemana, muralSemanaDia,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
        Calendario.carregaDadosDaSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso,muralSemanaDia,muralSemanaLista,tarefaRepository);
        if (gridDetalhesDaTabelaTarefa.isVisible()) {
            gridTarefaSelecionadaCalendario.setVisible(false);
        }
        atualizaDadosHome();
    }

    public void excluirTarefa() {
        TarefaService.excluirTarefa(muralNomeTarefa.getText(), tarefaRepository,labelAvisosTarefas);
        CarregaDadosService.carregaTarefas(selecionadaTarefaConcluida,selecionadaTarefaNumero,tarefasObservableList, tarefaRepository, tabelaTarefas, conta.getId(), filtroTarefa,filtroTarefaSub);
        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
        Calendario.muralCalendario(muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,tarefaRepository);
        Calendario.muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                ,muralSemanaLista,gridSemana, muralSemanaDia,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
        Calendario.carregaDadosDaSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso,muralSemanaDia,muralSemanaLista,tarefaRepository);
        atualizaDadosHome();
    }

    public void voltarEscreverDescricao(){
        vBox_descricao.setVisible(false);
        gridNovaTarefa.setVisible(true);
        botaoNovaCategoria.setVisible(true);
        botaoNovaTarefa.setVisible(true);
    }
    public void categoriaView(){
        gridTarefaSelecionadaSemana.setVisible(false);
        gridNovaTarefa.setVisible(false);
        gridCategoria.setVisible(true);
        gridTarefaSelecionadaCalendario.setVisible(false);
        gridDetalhesDaTabelaTarefa.setVisible(false);
    }
    public void tarefaView(){
        gridTarefaSelecionadaSemana.setVisible(false);
        gridNovaTarefa.setVisible(true);
        gridCategoria.setVisible(true);
        gridTarefaSelecionadaCalendario.setVisible(false);
        gridDetalhesDaTabelaTarefa.setVisible(false);
        muralCalendario.setVisible(false);
        paneMuralTarefas.setVisible(false);
        adicionarTarefas.setVisible(true);


    }
    public void editarTarefaSelecionada() {
        if (muralNomeTarefa.getText().isEmpty()) {
            System.out.println("Sem tarefa//");
        } else {
            TarefasUtil.mostrarEditarTarefa(textAreaEditarTarefa,editarNomeTarefa,editarDataTarefa,editarPrioridadeTarefa,
                    editarCategoriaTarefa,botaoEditarTarefa,editarTarefaBtExcluir,editarTarefaBtSalvar,true);
            TarefasUtil.ocultaLabelTarefa(muralNomeTarefa,muralDataTarefa,muralPrioridadeTarefa,muralCategoriaTarefa);
        }
    }
    public void passarMes(){
        gridCategoria.setVisible(false);
        gridNovaTarefa.setVisible(false);
        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,
                muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,
                dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
        Calendario.trocarMes(muralCalendario,adicionarTarefas,muralSemanaTarefasSemana,
                muralSemanaConcluidas,
                muralSemanaAtrasadas,
                muralSemanaProgresso,
                muralCalendarioMesAtual,
                muralCalendarioAtrasadas,
                muralCalendarioDia,
                muralCalendarioLista,
                muralCalendarioProgresso,
                muralCalendarioTarefasMes,
                muralCalendarioConcluidas,
                dataTarefa,
                gridNovaTarefa,
                gridCategoria,
                tarefaRepository,
                calendarioFull,
                proximo,
                anterior,
                atual,
                gridSemana,
                muralSemanaDia,
                terca,
                quarta,
                quinta,
                sexta,
                sabado,
                domingo,
                muralSemanaLista,
                calendarioFull);
    }
    public void voltarMesAtual() {
        gridCategoria.setVisible(false);
        gridNovaTarefa.setVisible(false);
        Calendario.trocarMes(muralCalendario,adicionarTarefas,muralSemanaTarefasSemana,
                muralSemanaConcluidas,
                muralSemanaAtrasadas,
                muralSemanaProgresso,
                muralCalendarioMesAtual,
                muralCalendarioAtrasadas,
                muralCalendarioDia,
                muralCalendarioLista,
                muralCalendarioProgresso,
                muralCalendarioTarefasMes,
                muralCalendarioConcluidas,
                dataTarefa,
                gridNovaTarefa,
                gridCategoria,
                tarefaRepository,
                calendarioFull,
                proximo,
                anterior,
                atual,
                gridSemana,
                muralSemanaDia,
                terca,
                quarta,
                quinta,
                sexta,
                sabado,
                domingo,
                muralSemanaLista,
                calendarioFull);
        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
    }
    public void voltarMes() {
        gridNovaTarefa.setVisible(false);
        gridCategoria.setVisible(false);
        Calendario.trocarMes(muralCalendario,adicionarTarefas,muralSemanaTarefasSemana,
                muralSemanaConcluidas,
                muralSemanaAtrasadas,
                muralSemanaProgresso,
                muralCalendarioMesAtual,
                muralCalendarioAtrasadas,
                muralCalendarioDia,
                muralCalendarioLista,
                muralCalendarioProgresso,
                muralCalendarioTarefasMes,
                muralCalendarioConcluidas,
                dataTarefa,
                gridNovaTarefa,
                gridCategoria,
                tarefaRepository,
                calendarioFull,
                proximo,
                anterior,
                atual,
                gridSemana,
                muralSemanaDia,
                terca,
                quarta,
                quinta,
                sexta,
                sabado,
                domingo,
                muralSemanaLista,
                calendarioFull);

        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,
                muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,
                dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
    }
    public void salvarEdicaoTarefa() {
        TarefaService.editarTarefa(botaoEditarTarefa,editarTarefaBtSalvar,editarTarefaBtExcluir, tarefaRepository,
                muralNomeTarefa,editarNomeTarefa,textAreaEditarTarefa,editarDataTarefa,editarPrioridadeTarefa,
                editarCategoriaTarefa,labelAvisosTarefasMural);
        CarregaDadosService.carregaTarefas(selecionadaTarefaConcluida,selecionadaTarefaNumero,tarefasObservableList,
                tarefaRepository, tabelaTarefas, conta.getId(), filtroTarefa,filtroTarefaSub);
        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,
                muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,
                dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
        Calendario.muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                ,muralSemanaLista,gridSemana, muralSemanaDia,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
        Calendario.carregaDadosDaSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,
                muralSemanaProgresso,muralSemanaDia,muralSemanaLista,tarefaRepository);

    }
    public void mostrarCalendario(){
        calendarioFull.setVisible(true);
        tabelaTarefas.setVisible(false);
        gridSemana.setVisible(false);
        muralCalendario.setVisible(true);
        paneMuralTarefas.setVisible(false);
        TarefasUtil.botoresCalendario(anterior,proximo,atual,true);
        gridTarefaSelecionadaSemana.setVisible(false);
        gridDetalhesDaTabelaTarefa.setVisible(false);
        adicionarTarefas.setVisible(false);
        muralSemanaGridPane.setVisible(false);
        muralCalendarioGridPane.setVisible(true);
        muralSemanaVBoxLista.setVisible(false);
        muralCalendarioVBoxLista.setVisible(true);
        gridTarefaSelecionadaCalendario.setVisible(true);
        gridTarefaSelecionadaSemana.setVisible(false);
    }
    public void mostrarTarefas(){
        muralCalendarioMesAtual.setVisible(false);
        calendarioFull.setVisible(false);
        tabelaTarefas.setVisible(true);
        gridSemana.setVisible(false);
        muralCalendario.setVisible(false);
        paneMuralTarefas.setVisible(true);
        TarefasUtil.botoresCalendario(anterior,proximo,atual,false);
        gridTarefaSelecionadaCalendario.setVisible(false);
        gridNovaTarefa.setVisible(false);
        gridDetalhesDaTabelaTarefa.setVisible(true);
        adicionarTarefas.setVisible(false);
    }
    public void salvarCategoria(){
         CategoriaService.salvarCategoria(categoriaRepository,tfNomeCategoria,"Tarefas");
         CarregaDadosService.carregaCategorias(cbCategoriaTarefa,categoriaRepository);
    }
    public void home() {
        paneHome.setVisible(true);
        paneTarefas.setVisible(false);
        paneEstudos.setVisible(false);
        paneAtividades.setVisible(false);
        paneMetas.setVisible(false);
        paneEstatisticasEdados.setVisible(false);
        paneHabitos.setVisible(false);
        panePreferências.setVisible(false);
        paneFinancas.setVisible(false);
    }
    public void tarefas() {
        paneTarefas.setVisible(true);
        paneEstatisticasEdados.setVisible(false);
        paneHome.setVisible(false);
        paneEstudos.setVisible(false);
        paneMetas.setVisible(false);
        paneAtividades.setVisible(false);
        paneHabitos.setVisible(false);
        paneFinancas.setVisible(false);
        panePreferências.setVisible(false);
    }
    public void mostrarSemana() {
        gridDetalhesDaTabelaTarefa.setVisible(false);
        gridTarefaSelecionadaCalendario.setVisible(false);
        gridTarefaSelecionadaSemana.setVisible(true);
        tabelaTarefas.setVisible(false);
        gridSemana.setVisible(true);
        calendarioFull.setVisible(false);
        muralCalendario.setVisible(true);
        muralSemanaGridPane.setVisible(true);
        muralCalendarioGridPane.setVisible(false);
        paneMuralTarefas.setVisible(false);
        adicionarTarefas.setVisible(false);
        muralSemanaVBoxLista.setVisible(true);
        muralCalendarioVBoxLista.setVisible(false);
        TarefasUtil.botoresCalendario(anterior,proximo,atual,true);
        Calendario.carregaDadosDaSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso,muralSemanaDia,muralSemanaLista,tarefaRepository);
    }
    public void estudos(){
        paneEstudos.setVisible(true);
        paneHome.setVisible(false);
        paneEstatisticasEdados.setVisible(false);
        paneTarefas.setVisible(false);
        paneAtividades.setVisible(false);
        paneMetas.setVisible(false);
        paneHabitos.setVisible(false);
        paneFinancas.setVisible(false);
        panePreferências.setVisible(false);
    }
    public void dadosEestatisicas() {
        paneEstudos.setVisible(false);
        paneHome.setVisible(false);
        paneTarefas.setVisible(false);
        paneAtividades.setVisible(false);
        paneMetas.setVisible(false);
        paneHabitos.setVisible(false);
        paneFinancas.setVisible(false);
        paneEstatisticasEdados.setVisible(true);
        panePreferências.setVisible(false);
    }
    public void atividades(){
        paneEstudos.setVisible(false);
        paneEstatisticasEdados.setVisible(false);
        paneTarefas.setVisible(false);
        paneAtividades.setVisible(true);
        paneHome.setVisible(false);
        paneMetas.setVisible(false);
        paneHabitos.setVisible(false);
        paneFinancas.setVisible(false);
        panePreferências.setVisible(false);
    }
    public void habitos() {
        paneEstudos.setVisible(false);
        paneTarefas.setVisible(false);
        paneAtividades.setVisible(false);
        paneEstatisticasEdados.setVisible(false);
        paneHome.setVisible(false);
        paneMetas.setVisible(false);
        paneFinancas.setVisible(false);
        paneHabitos.setVisible(true);
        panePreferências.setVisible(false);
    }
    public void metas() {
        paneEstudos.setVisible(false);
        paneTarefas.setVisible(false);
        paneAtividades.setVisible(false);
        paneEstatisticasEdados.setVisible(false);
        paneHome.setVisible(false);
        paneMetas.setVisible(true);
        paneHabitos.setVisible(false);
        paneFinancas.setVisible(false);
        panePreferências.setVisible(false);
    }
    public void financas() {
        paneEstudos.setVisible(false);
        paneTarefas.setVisible(false);
        paneAtividades.setVisible(false);
        paneEstatisticasEdados.setVisible(false);
        paneHome.setVisible(false);
        paneMetas.setVisible(false);
        paneHabitos.setVisible(false);
        paneMetas.setVisible(false);
        paneFinancas.setVisible(true);
        panePreferências.setVisible(false);
    }
    public void opcoes() {
        paneEstudos.setVisible(false);
        paneTarefas.setVisible(false);
        paneAtividades.setVisible(false);
        paneEstatisticasEdados.setVisible(false);
        paneHome.setVisible(false);
        paneMetas.setVisible(false);
        paneHabitos.setVisible(false);
        paneMetas.setVisible(false);
        paneFinancas.setVisible(false);
        panePreferências.setVisible(true);
    }
    public void sair(){
          Stage stage = (Stage) paneHome.getScene().getWindow();
          stage.close();
          CarregaDadosService.shutdown();
    }
    public void submetaViewVoltar() {

    }
    public void cadastrarTela(){
        containerCadastro.setVisible(true);
        containerLogin.setVisible(false);
    }

    public void voltarLogin() {
        containerCadastro.setVisible(false);
        containerLogin.setVisible(true);
    }

    public void cadastrar(){
        CadastrarService.cadastrar(tfNomeCadastro,tfNomeUsuarioCadastro,tfSenhaCadastro,dataPickerCadastro,
                segurancaService,containerCadastro,containerLogin,applicationConfigurationRepository);
    }

    public void entrarLogin(){
        CheckLoginService.checkLogin(contaRepository,tfUserLogin,tfPasswordLogin,segurancaService,containerAplicacao,containerLogin, containerCadastro);
        if (conta != null) {
            botaoLogin.setDisable(true);
            CarregaDadosService.carregaTarefas(selecionadaTarefaConcluida,selecionadaTarefaNumero,tarefasObservableList, tarefaRepository, tabelaTarefas, conta.getId(), filtroTarefa,filtroTarefaSub);
            CarregaDadosService.carregarMaterias(choiceBoxSelecionarMateria,estudoRepository,conta);
            CarregaDadosService.carregarMaterias(chpiceBoxMinhasMaterias,estudoRepository,conta);
            CarregaDadosService.carregaRegistroDeEstudos(tabelaEstudos,registroEstudosObservableList,registroEstudosRepository,conta.getId());
            labelNome.setText("Olá, " + conta.getNome());
            dadosIniciaisCalendario();
            ListenerEstudos.listenerIniciarEstudos(flowPaneInfosEstudos,estudoRepository,conta,labelTipoMateria,choiceBoxSelecionarMateria,btPlay,btPause,btReset,btPassar,btSave);
            ListenerEstudos.listenerFiltrosDaTabelaRegistros(
                    conta,
                    estudoRepository,
                    registroEstudosRepository,
                    registroEstudosObservableList,
                    tabelaEstudos,
                    choiceBoxFiltroEstudos,
                    datePickerFiltroData,
                    tfFiltroTexto,
                    choiceBoxFiltroMateria,
                    categoriaRepository);
            CarregaDadosService.carregaAreaDaMeta(categoriaRepository,choiceBoxCriarMeta);
            CarregaDadosService.carregaDadosDoUsuario(meusDadosNome,meusDadosDataNascimento,diasAniversario,contaRepository);
            CarregaDadosService.carregaMinhasMetas(mmNomeMeta, nmDataMeta,nmAreaMeta,nmStatus,nmAssociadasMeta, nmConcluidasMeta, nmProgressoMeta,metaRepository,listaDeMeta,choiceBoxNovaSubmeta);

        }
        ListenerEstudos.listenerMinhasMaterias(chpiceBoxMinhasMaterias, labelTipoMinhasMaterias, estudoRepository, conta);
    }
    private void dadosIniciaisCalendario() {
        Calendario.calendario(muralCalendario,adicionarTarefas,muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,dataTarefa,gridNovaTarefa,gridCategoria,calendarioFull,tarefaRepository);
        Calendario.muralSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso
                ,muralSemanaLista,gridSemana, muralSemanaDia,terca,quarta,quinta,sexta,sabado,domingo,tarefaRepository);
        Calendario.muralCalendario(muralCalendarioAtrasadas,muralCalendarioDia,muralCalendarioLista,muralCalendarioProgresso,muralCalendarioTarefasMes,muralCalendarioConcluidas,tarefaRepository);
        Calendario.carregaDadosDaSemana(muralSemanaTarefasSemana,muralSemanaConcluidas,muralSemanaAtrasadas,muralSemanaProgresso,muralSemanaDia,muralSemanaLista,tarefaRepository);
    }

    /**
     * Controlador da tela de Estudos: -------------------------------------
     */

    @FXML
    private GridPane gridPaneRegistroManual,gridPaneEstudar, gridPaneMateria, gridPanePomodoro,gridPaneNovaMateria,
            gridPaneNovoTipo;
    @FXML
    private TextField tfEditaMinhaMateria,campoNomeTipo, campoNomeMateria,campoTempoPomodoro, campoPomodoroCurto,
            campoPomodoroLongo, quantidadePomodoro,tfTempoManual,tfNomeManual,tfNomeCronometrado,tfFiltroTexto;
    @FXML
    private DatePicker datePickerManual, datePickerFiltroData;
    @FXML
    private ChoiceBox<String> choiceBoxTipoMateria, choiceBoxSelecionarMateria,choiceBoxFiltroEstudos,
            choiceBoxFiltroMateria,chpiceBoxMinhasMaterias,choiceBoxEditaTipo;
    @FXML
    private Label labelCronometro, labelPomodoro,labelIndicadoraPomodoro,labelTipoMateria,labelTipoMinhasMaterias,
            labelAvisosEstudos;
    @FXML
    private Button btSalvarConfiguracaoPomodoro, btAumentar, btDiminuir, btPlay, btPause, btReset,btPassar,btSave,
            btConfigPomodoro,btSalvarEdit, btVoltarEdit,btEditarMateria,btNovaMateria,btExcluirMateria;
    @FXML
    private TableColumn<TabelaEstudos, String> tcMateria, tcTipo, tcConteudo, tcData, tcTempoEstudado,tcDescricao,tcApagar;
    @FXML
    private TableView<TabelaEstudos> tabelaEstudos;
    @FXML
    private FlowPane flowPaneInfosEstudos;
    @FXML
    private TextArea textAreaDescricaoManual, textAreaDescricaoCronometrado;
    ObservableList<TabelaEstudos> registroEstudosObservableList = FXCollections.observableArrayList();

    @Autowired
    EstudoRepository estudoRepository;
    @Autowired
    PomodoroRepository pomodoroRepository;
    @Autowired
    RegistroEstudosRepository registroEstudosRepository;
    public void estudarView() {
        gridPaneEstudar.setVisible(true);
        gridPaneMateria.setVisible(false);
        gridPanePomodoro.setVisible(false);
        gridPaneRegistroManual.setVisible(false);
        flowPaneInfosEstudos.setVisible(false);
        gridPaneNovaMateria.setVisible(false);
    }
    public void materiaView() {
        gridPaneEstudar.setVisible(false);
        gridPaneMateria.setVisible(true);
        gridPanePomodoro.setVisible(false);
        gridPaneRegistroManual.setVisible(false);
        gridPaneNovoTipo.setVisible(false);
        flowPaneInfosEstudos.setVisible(false);
        choiceBoxSelecionarMateria.setValue(null);
    }
    public void pomodoroView() {
        gridPaneEstudar.setVisible(false);
        gridPaneMateria.setVisible(false);
        gridPanePomodoro.setVisible(true);
        gridPaneRegistroManual.setVisible(false);
        gridPaneNovoTipo.setVisible(false);
        flowPaneInfosEstudos.setVisible(false);
        choiceBoxSelecionarMateria.setValue(null);
        gridPaneNovaMateria.setVisible(false);
    }
    public void tipoEstudosView() {
        gridPaneNovoTipo.setVisible(true);
        gridPaneNovaMateria.setVisible(false);
    }
    public void voltarEstudoView() {
        gridPaneRegistroManual.setVisible(false);
        flowPaneInfosEstudos.setVisible(true);
    }
    public void voltarDoNovoTipo() {
        gridPaneNovoTipo.setVisible(false);
        gridPaneNovaMateria.setVisible(true);
    }
    public void criarNovaCategoria() {
        gridPaneNovoTipo.setVisible(false);
        gridPaneNovaMateria.setVisible(true);
        CategoriaService.salvarCategoria(categoriaRepository,campoNomeTipo,"Estudos");
        atualizaDadosEstudos();
        atualizarTabelaEstudos();
    }
    public void criaMateria() {
        EstudosService.criarNovoEstudo(gridPaneNovaMateria,campoNomeMateria,choiceBoxTipoMateria,estudoRepository,conta,
                labelAvisosEstudos);
        atualizaDadosEstudos();
        atualizarTabelaEstudos();

    }
    public void editarMateria() {
        EstudosService.editarEstudoSelecionar(labelTipoMinhasMaterias,choiceBoxEditaTipo,tfEditaMinhaMateria,
                chpiceBoxMinhasMaterias,estudoRepository,conta,categoriaRepository,btEditarMateria,btSalvarEdit,
                btVoltarEdit,btExcluirMateria,btNovaMateria,false,labelAvisosEstudos);
    }
    public void excluirMateria() {
        EstudosService.excluirMateria(chpiceBoxMinhasMaterias,estudoRepository,conta);
        atualizaDadosEstudos();
        atualizarTabelaEstudos();
    }
    public void salvarEditMateria() {
        EstudosService.editarEstudoSalvar(labelTipoMinhasMaterias,choiceBoxEditaTipo,tfEditaMinhaMateria,
                chpiceBoxMinhasMaterias,estudoRepository,registroEstudosRepository,conta,btEditarMateria,
                btSalvarEdit,btVoltarEdit,btExcluirMateria,btNovaMateria);
        atualizaDadosEstudos();
        atualizarTabelaEstudos();
    }
    public void voltarEditTarefa() {
        EstudosService.editarEstudoSelecionar(labelTipoMinhasMaterias,choiceBoxEditaTipo,tfEditaMinhaMateria,
                chpiceBoxMinhasMaterias,estudoRepository,conta,categoriaRepository,btEditarMateria,btSalvarEdit,
                btVoltarEdit,btExcluirMateria,btNovaMateria,true,labelAvisosEstudos);
    }
    public void novaMateriaView() {
        gridPaneNovaMateria.setVisible(true);
    }

    public void atualizaDadosEstudos() {
        CarregaDadosService.carregarTipoEstudos(choiceBoxTipoMateria, categoriaRepository);
        CarregaDadosService.carregarMaterias(choiceBoxSelecionarMateria, estudoRepository,conta);
        CarregaDadosService.carregarMaterias(chpiceBoxMinhasMaterias,estudoRepository,conta);
        CarregaDadosService.carregaRegistroDeEstudos(tabelaEstudos,registroEstudosObservableList,registroEstudosRepository,conta.getId());
    }
    public void atualizarTabelaEstudos() {
        choiceBoxFiltroMateria.getItems().clear();
        CarregaDadosService.carregaRegistroDeEstudos(tabelaEstudos,registroEstudosObservableList,registroEstudosRepository,conta.getId());
    }
    public void iniciarCronometro() throws InterruptedException {
        Cronometro.iniciarCronometro(labelCronometro);
        Cronometro.iniciarCronometroPomodoro(campoTempoPomodoro,labelIndicadoraPomodoro,labelPomodoro, Integer.parseInt(campoTempoPomodoro.getText()), Integer.parseInt(campoPomodoroCurto.getText()), Integer.parseInt(campoPomodoroLongo.getText()), Integer.parseInt(quantidadePomodoro.getText()));
        Cronometro.setEmAndamento(true);
        btConfigPomodoro.setDisable(true);
        btPlay.setDisable(true);
    }
    public void pausarCronometro() {
        Cronometro.pausarCronometro();
        Cronometro.pausarCronometroPomodoro();
        btPlay.setDisable(false);
    }
    public void resetarCronometro() {
        Cronometro.resetarCronometroPomodoro(labelIndicadoraPomodoro,labelCronometro,labelPomodoro,Integer.parseInt(campoTempoPomodoro.getText()));
        btConfigPomodoro.setDisable(false);
    }
    public void aumentarCiclo() {
        EstudosUtil.aumentarPomodoro(quantidadePomodoro,1);
    }
    public void diminuirCiclo() {
        EstudosUtil.diminuirPomodoro(quantidadePomodoro,1);

    }
    public void editarPomodoro() {
        EstudosUtil.editarPomodoro(btSalvarConfiguracaoPomodoro,btAumentar,btDiminuir,campoTempoPomodoro,campoPomodoroCurto,campoPomodoroLongo,quantidadePomodoro,true);
    }
    public void salvarConfiguracaoPomodoro() {
        EstudosUtil.editarPomodoro(btSalvarConfiguracaoPomodoro,btAumentar,btDiminuir,campoTempoPomodoro,campoPomodoroCurto,campoPomodoroLongo,quantidadePomodoro,false);
        PomodoroService.salvaConfiguracaoPomodoro(campoTempoPomodoro,campoPomodoroCurto,campoPomodoroLongo,quantidadePomodoro,pomodoroRepository);
        atualizaPomodoro();
    }
    public void atualizaPomodoro() {
        PomodoroService.carregaConfiguracaoPomodoro(labelPomodoro,campoTempoPomodoro,campoPomodoroCurto,campoPomodoroLongo,quantidadePomodoro,pomodoroRepository);
    }
    public void passarPomodoroAtual(){
        Cronometro.passarPomodoroAtual();
        btPlay.setDisable(false);
    }
    public void registroManualView() {
        EstudosUtil.mostraPainelMateria(flowPaneInfosEstudos,gridPaneRegistroManual,false,true);
    }
    public void registroInfoView() {
        EstudosUtil.mostraPainelMateria(flowPaneInfosEstudos,gridPaneRegistroManual,true,false);
    }
    public void registrarManual() {
        if (EstudosUtil.validarFormadoDoTempo(tfTempoManual.getText())) {
            int retorno = EstudosUtil.confirmarRegistroManual();
            if (retorno == 1) {
                RegistroEstudoService.registrarEstudo(
                        tfNomeManual,
                        textAreaDescricaoManual,
                        tfTempoManual.getText(),
                        datePickerManual.getValue(),
                        labelTipoMateria.getText(),
                        choiceBoxSelecionarMateria,
                        conta,
                        registroEstudosRepository,
                        estudoRepository,
                        labelAvisosEstudos
                );
                atualizaDadosEstudos();
                EstudosUtil.mostraPainelMateria(flowPaneInfosEstudos,gridPaneRegistroManual,false,false);
            }
        }
    }
    public void registrarCronometrado() {
        if (labelCronometro.getText().equals("00:00:00") || tfNomeCronometrado.getText().isEmpty()) {
            EstudosUtil.erroRegistroCronometro();
        } else {
            int resposta = EstudosUtil.confirmarRegistroCronometro();
            if (resposta == 1) {

                RegistroEstudoService.registrarEstudo(
                        tfNomeCronometrado,
                        textAreaDescricaoCronometrado,
                        labelCronometro.getText(),
                        LocalDate.now(),
                        labelTipoMateria.getText(),
                        choiceBoxSelecionarMateria,
                        conta,
                        registroEstudosRepository,
                        estudoRepository,
                        labelAvisosEstudos
                );
                atualizaDadosEstudos();
                EstudosUtil.mostraPainelMateria(flowPaneInfosEstudos,gridPaneRegistroManual,false,false);
                Cronometro.registraEfinalizaEstudo(labelCronometro,labelPomodoro,Integer.parseInt(campoTempoPomodoro.getText()));
            }
        }
    }

    /**
     *
     *
     *
     * Controlador da tela de Metas: -------------------------------------
     *
     *
     */

    @FXML
    private Pane paneMinhasMetas, paneInferiorSubmetas,paneMinhasNovaMeta, paneSubmetas, paneInferiorNovaMeta,
            paneTelaInicial,paneNovaTarefaSubmeta;
    @FXML
    private Label labelAvisosNovaMeta,labelCriarArea,mmNomeMeta, nmDataMeta,nmAreaMeta,nmStatus,nmAssociadasMeta,
            nmConcluidasMeta,nmNomeSubmeta,nmDataSubmeta,nmStatusSubmeta,nmAssociadasSubmeta,nmConcluidasSubmeta
            ,nmNomeTarefa,nmDataTarefa,nmDescricaoTarefa,nmCategoriaTarefa,nmPrioridadeTarefa,nmStatusTarefa, labelNomeArea,
            labelAreaMetaView,labelDataLimiteMetaView,labelStatusArea,sobreNomeMeta,sobreStatusMeta,sobreDiasRestantes,
            sobreAreaMeta, sobreNomeSubmeta,sobreDataSubmeta,sobreRelacionadoSubmeta,sobreStatusSubmeta,sobreNumTarefasSubmetas,
            sobreTarefasFeitasSubmetas,labelAvisosSubmeta;
    @FXML
    private TextField tfNomeCriarMeta,tfCriarArea,tfNomeNovaSubmeta,tfAddTarefaSubmeta,tfEditaSubmeta;
    @FXML
    private DatePicker datePickerCriarMeta,datePickerNovaSubmeta,datePickerPeriodoSelecionado,dpAddTarefaSubmeta,datePickerEditaSubmeta;
    @FXML
    private ChoiceBox<String> cbRelacionadoEditaSubmeta,cbStatusEditaSubmeta,choiceBoxCriarMeta,choiceBoxNovaSubmeta,choiceBoxStatusMeta,choiceBoxFiltroPeriodoMeta,cbCatAddTarefaSubmeta,cbPriorAddTarefasubmeta,cbSubmetaAddTarefaSubmeta;
    @FXML
    private Button btCriarArea,btCriarMeta,btApagarMeta,btSalvarMeta,btSalvarEdicaoSubmeta;
    @FXML
    private ListView<String> listaDeMeta, listaDeSubmeta, listaDeTarefaSubmeta,listaTodasSubmetas,listaTarefasAssociadas, listaTarefaNaoAssociada,listaDasMinhasMetaView;
    @FXML
    private GridPane gridAdicionarSubmeta;
    @FXML
    private ProgressBar nmProgressoSubmeta,nmProgressoMeta,sobreProgressSubmeta;
    @FXML
    private TextArea taAddTarefaSubmeta;
    @FXML
    private VBox vBoxEditaSubmeta;
    @Autowired
    MetaRepository metaRepository;
    @Autowired
    SubmetaRepository submetaRepository;



    public void minhasMetasView() {
        paneMinhasMetas.setVisible(true);
        paneTelaInicial.setVisible(true);
        paneInferiorSubmetas.setVisible(false);
        paneMinhasNovaMeta.setVisible(false);
        paneSubmetas.setVisible(false);
        paneInferiorNovaMeta.setVisible(false);


    }
    public void submetaView() {
        paneMinhasMetas.setVisible(false);
        paneTelaInicial.setVisible(false);
        paneInferiorSubmetas.setVisible(true);
        paneMinhasNovaMeta.setVisible(false);
        paneSubmetas.setVisible(true);
        paneInferiorNovaMeta.setVisible(false);

    }
    public void novaMetaView() {
        paneMinhasMetas.setVisible(false);
        paneTelaInicial.setVisible(false);
        paneInferiorSubmetas.setVisible(false);
        paneMinhasNovaMeta.setVisible(true);
        labelStatusArea.setVisible(false);
        paneSubmetas.setVisible(false);
        choiceBoxStatusMeta.setVisible(false);
        paneInferiorNovaMeta.setVisible(true);
        labelCriarArea.setVisible(false);

    }
    public void criarAreaView() {
        labelNomeArea.setVisible(false);
        labelDataLimiteMetaView.setVisible(false);
        btCriarMeta.setVisible(false);
        btApagarMeta.setVisible(false);
        tfNomeCriarMeta.setVisible(false);
        choiceBoxCriarMeta.setVisible(false);
        labelStatusArea.setVisible(false);
        datePickerCriarMeta.setVisible(false);
        labelAreaMetaView.setVisible(true);
        choiceBoxStatusMeta.setVisible(false);
        labelCriarArea.setVisible(true);
        tfCriarArea.setVisible(true);
        btCriarArea.setVisible(true);
        btSalvarMeta.setVisible(false);
        labelAreaMetaView.setVisible(false);
    }
    public void criarMeta() {
        MetaService.criarMeta(metaRepository,tfNomeCriarMeta,choiceBoxCriarMeta,datePickerCriarMeta,labelAvisosNovaMeta);
        atualizaMetas();
    }
    public void editarMeta() {
        MetaService.salvarEdicaoDeMeta(choiceBoxStatusMeta,labelAvisosNovaMeta,tfNomeCriarMeta,datePickerCriarMeta,choiceBoxCriarMeta,metaRepository);
        atualizaMetas();
        CarregaDadosService.carregaPrioridadeCategoriaEsubmeta(cbCatAddTarefaSubmeta,cbPriorAddTarefasubmeta,cbSubmetaAddTarefaSubmeta,categoriaRepository,submetaRepository);
        vBoxEditaSubmeta.setVisible(false);
        sobreNomeSubmeta.setVisible(true);
        sobreDataSubmeta.setVisible(true);
        sobreRelacionadoSubmeta.setVisible(true);
        sobreStatusSubmeta.setVisible(true);
        btSalvarEdicaoSubmeta.setVisible(true);

    }
    public void salvarEdicaoSubmeta() {
        MetaUtil.editSubmetaView(vBoxEditaSubmeta,sobreNomeSubmeta,sobreDataSubmeta,sobreRelacionadoSubmeta,
                sobreStatusSubmeta,btSalvarEdicaoSubmeta,false);
        SubmetaService.salvarEdicaoSubmeta(labelAvisosNovaMeta,sobreNomeSubmeta,tfEditaSubmeta,datePickerEditaSubmeta,
                cbRelacionadoEditaSubmeta,cbStatusEditaSubmeta,submetaRepository, metaRepository,listaTodasSubmetas,labelAvisosSubmeta);
        atualizaSubmetas();
    }
    public void apagarSubmeta() {
        SubmetaService.apagarSubmeta(labelAvisosNovaMeta,submetaRepository,listaTodasSubmetas,metaRepository);
        atualizaSubmetas();
    }
    public void criarMetaView() {
        labelNomeArea.setVisible(true);
        labelDataLimiteMetaView.setVisible(true);
        btCriarMeta.setVisible(true);
        btApagarMeta.setVisible(false);
        tfNomeCriarMeta.setVisible(true);
        choiceBoxCriarMeta.setVisible(true);
        datePickerCriarMeta.setVisible(true);
        labelAreaMetaView.setVisible(true);
        choiceBoxStatusMeta.setVisible(false);
        labelStatusArea.setVisible(false);
        labelCriarArea.setVisible(false);
        tfCriarArea.setVisible(false);
        btCriarArea.setVisible(false);
        btSalvarMeta.setVisible(false);

    }
    public void editarMetaView() {
        labelNomeArea.setVisible(true);
        labelDataLimiteMetaView.setVisible(true);
        btCriarMeta.setVisible(false);
        btApagarMeta.setVisible(true);
        tfNomeCriarMeta.setVisible(true);
        choiceBoxCriarMeta.setVisible(true);
        datePickerCriarMeta.setVisible(true);
        labelAreaMetaView.setVisible(true);
        choiceBoxStatusMeta.setVisible(true);
        labelStatusArea.setVisible(true);
        labelCriarArea.setVisible(false);
        tfCriarArea.setVisible(false);
        btCriarArea.setVisible(false);
        btSalvarMeta.setVisible(true);
        MetaService.carregaDadosParaEdicao(choiceBoxStatusMeta,labelAvisosNovaMeta,tfNomeCriarMeta,datePickerCriarMeta,
                choiceBoxCriarMeta,metaRepository,listaDasMinhasMetaView);
    }
    public void editarSubmetaView() {
        MetaUtil.editSubmetaView(vBoxEditaSubmeta,sobreNomeSubmeta,sobreDataSubmeta,sobreRelacionadoSubmeta,
                sobreStatusSubmeta,btSalvarEdicaoSubmeta,true);
        CarregaDadosService.carregaDadosParaEdicaoDaSubmeta(submetaRepository,metaRepository,tfEditaSubmeta,
                datePickerEditaSubmeta,cbStatusEditaSubmeta,cbRelacionadoEditaSubmeta,sobreNomeSubmeta);

    }
    public void apagarMeta() {
        MetaService.apagarMeta(metaRepository,listaDasMinhasMetaView,labelAvisosNovaMeta);
        atualizaMetas();
    }
    public void criarArea(){
        MetaService.criarArea(choiceBoxCriarMeta,tfCriarArea,labelAvisosNovaMeta,categoriaRepository);
        atualizaArea();
    }
    public void atualizaMetas() {
        CarregaDadosService.carregaMeta(listaDasMinhasMetaView,metaRepository);
        CarregaDadosService.carregaMinhasMetas(mmNomeMeta, nmDataMeta,nmAreaMeta,nmStatus,nmAssociadasMeta,
                nmConcluidasMeta, nmProgressoMeta,metaRepository,listaDeMeta,choiceBoxNovaSubmeta);
    }
    public void criarTarefaEmMeta() {

        MetaUtil.criarTarefaEmMeta(labelAvisosSubmeta,tarefaRepository,tfAddTarefaSubmeta,taAddTarefaSubmeta,
                dpAddTarefaSubmeta,cbPriorAddTarefasubmeta,cbCatAddTarefaSubmeta,cbSubmetaAddTarefaSubmeta,conta,
                submetaRepository);
        atualizaSubmetas();
        atualizaTarefa();
        CarregaDadosService.carregaDadosEtarefasAssociadasDaSubmeta(sobreNomeSubmeta,sobreDataSubmeta,
                sobreRelacionadoSubmeta,sobreStatusSubmeta,sobreNumTarefasSubmetas,sobreTarefasFeitasSubmetas,
                sobreProgressSubmeta,listaTodasSubmetas,listaTarefasAssociadas,listaTarefaNaoAssociada,submetaRepository,
                tarefaRepository);
    }
    public void atualizaSubmetas() {
        CarregaDadosService.carregaPrioridadeCategoriaEsubmeta(cbCatAddTarefaSubmeta,cbPriorAddTarefasubmeta,
                cbSubmetaAddTarefaSubmeta,categoriaRepository,submetaRepository);
        CarregaDadosService.carregaSubmeta(listaTodasSubmetas,submetaRepository);
        CarregaDadosService.carregaDadosEtarefasAssociadasDaSubmeta(sobreNomeSubmeta,sobreDataSubmeta,
                sobreRelacionadoSubmeta,sobreStatusSubmeta,sobreNumTarefasSubmetas,sobreTarefasFeitasSubmetas,
                sobreProgressSubmeta,listaTodasSubmetas,listaTarefasAssociadas,listaTarefaNaoAssociada,submetaRepository,
                tarefaRepository);
    }
    public void atualizaArea() {
        CarregaDadosService.carregaAreaDaMeta(categoriaRepository,choiceBoxCriarMeta);
    }


    public void novaSubmetaView() {
        gridAdicionarSubmeta.setVisible(true);
        listaTodasSubmetas.setVisible(false);
    }
    public void voltarDaNovaSubmetaView() {
        gridAdicionarSubmeta.setVisible(false);
        listaTodasSubmetas.setVisible(true);
    }
    public void criarSubmeta() {
        SubmetaService.criarSubmeta(submetaRepository,choiceBoxNovaSubmeta,tfNomeNovaSubmeta,datePickerNovaSubmeta,
                metaRepository,labelAvisosSubmeta);
        atualizaSubmetas();
    }

    /**
     * **
     * **
     * Tela de Projetos e Treino:
     * **
     * **
     * Começo 180424
     */

    @FXML
    private GridPane gridPaneInicialTreinos,gridPaneAtividadesNovoTreino,gridPaneAtividadesMeusTreinos,
            gridPaneNovoProjeto,gridPaneMeusProjetos,gridPaneNovoCard,gridPaneCriaItem,gridPaneMuralProjetos,
            gridPaneEditaCard,gridPaneEditaProjeto;
    @FXML
    private Pane paneOpcaoProjetos,paneInferiorProjetos,paneOpcaoTreino,paneInferiorTreino;
    @FXML
    private TableView<TabelaTreino> tabelaTreinos;
    @FXML
    private TableView<TabelaItensCard> tabelaItensCard;
    @FXML
    private TableColumn<TabelaItensCard, String> tcNomeItemCard,tcConcluido,tcApagarItem;
    @FXML
    private TableColumn<TabelaTreino, String> tcTipoTreino,tcNomeTreino,tcDescricaoTreino,tcRegistroTreino;
    @FXML
    private ChoiceBox<String> filtroPeriodoTipo,cbTipoTreino,cbEscolheProjetoCard,cbCategoriaProjeto,cbSelecionaProjetoMural,
            cbMeusProjetos,cbCardsRelacionadosMeusProjetos,cbStatusCard,cbEditaCard,cbEditaProjeto;
    @FXML
    private DatePicker dpSelecionadoPeriodoTreino,dpDataTreino,dpCriarCard,dpCriarProjeto,dpEditaCard,dpEditaProjeto;
    @FXML
    private ListView<String> listaMeusTreinos;
    @FXML
    private Button btVoltarEditarTreino,btNovoTreino,btVoltarRegistroCompleto,btRegistrarTreino,btEditaCard;
    @FXML
    private TextArea taDescricaoTreino,taCriarCard,taEditCard;
    @FXML
    private CheckBox checkBoxSemTempo;
    @FXML
    private TextField tfNomeTreino,tfNovoTipoTreino,tfDuracaoTreino,tfCriarCategoriaProjeto,tfNomeCriarProjeto,tfNomeCard,
            tfNomeDoItemCard,tfEditaCard,tfEditaProjeto;
    @FXML
    private Label nomeDataTreinoUm,nomeDataTreinoDois,nomeDataTreinoTres,nomeDataTreinoQuatro,treinoUm,treinoDois
            ,treinoTres,treinoQuatro, labelAvisosTreino,detalheTipoTreino,qtdTreinosMeusTreinos,detalheNomeTreino,detalheDataTreino,
            detalheConcluidoTreino,detalheTempoTreino,treinosFiltrados,labelInfoSelecaoTreino,guardaNomeTreino,
            labelAvisosProjetos,guardaNomeProjeto,diasRestantesProjetos,
            dataLimiteMeusProjetos,statusMeuProjeto,descricaoCard, nomeDoCard,nomeDoCardItem,guardaNomeCard,naoIniciadasCount,emAndamentoCount,completoCount;
    @FXML
    private RadioButton rbNovoTipo,rbNovaCategoria;
    @FXML
    private VBox vBoxNaoIniciada,vBoxEmAndamento,vBoxConcluida;
    @Autowired
    TreinoRepository treinoRepository;
    @Autowired
    ProjetoRepository projetoRepository;
    @Autowired
    ProjetoCardRepository projetoCardRepository;
    @Autowired
    ItemProjetoCardRepository itemProjetoCardRepository;
    ObservableList<TabelaTreino> treinosObservableList = FXCollections.observableArrayList();

    public void novoTreinoView() {
        TreinoUtil.trocaTelasControle(gridPaneInicialTreinos,gridPaneAtividadesNovoTreino,gridPaneAtividadesMeusTreinos,true,false,false);
        tfNomeTreino.setText("");
        taDescricaoTreino.setText("");
        dpDataTreino.setValue(LocalDate.now());
    }
    public void meusTreinosView() {
        TreinoUtil.trocaTelasControle(gridPaneInicialTreinos,gridPaneAtividadesNovoTreino,gridPaneAtividadesMeusTreinos,false,true,false);
        btNovoTreino.setDisable(false);
        btVoltarEditarTreino.setVisible(false);
    }
    public void treinoView() {
        ProjetoUtil.controleDeAtividades(paneOpcaoProjetos,paneInferiorProjetos,paneOpcaoTreino,paneInferiorTreino,tabelaTreinos,false,true);
        atividades();
    }
    public void criarNovoTipo() {
        TreinoUtil.exibirCriarNovoTipo(tfNovoTipoTreino,cbTipoTreino,rbNovoTipo);
    }
    public void criarTreino() {
        TreinoUtil.salvaTipoDeTreinoNovo(cbTipoTreino,tfNovoTipoTreino,categoriaRepository,labelAvisosTreino);
        TreinoService.criarTreino(labelAvisosTreino,tfNomeTreino,dpDataTreino,listaMeusTreinos,cbTipoTreino,
                taDescricaoTreino,tfNovoTipoTreino,treinoRepository, rbNovoTipo,btVoltarEditarTreino,guardaNomeTreino);
        TreinoUtil.limparDadosSalvosDoTreino(tfNomeTreino,dpDataTreino,cbTipoTreino,taDescricaoTreino,tfNovoTipoTreino,rbNovoTipo);
        atualizarTabelaDeTreino();
        atualizaDadosHome();
        CarregaDadosService.atualizaListaDeTreinos(listaMeusTreinos,treinoRepository);

    }
    public void editarTreinoView() {
        TreinoUtil.guardaNomeDoTreinoParaEditar(guardaNomeTreino,listaMeusTreinos);
        TreinoUtil.exibirEditarTreino(btNovoTreino,btVoltarEditarTreino,gridPaneAtividadesNovoTreino,
                gridPaneAtividadesMeusTreinos,true);
        CarregaDadosService.carregaDadosParaEdicaoDoTreino(listaMeusTreinos,dpDataTreino,cbTipoTreino,
                taDescricaoTreino,tfNomeTreino,treinoRepository,false);
        atualizaDadosHome();
        atualizarTabelaDeTreino();
    }
    public void apagarTreino() {
        TreinoService.apagarTreino(listaMeusTreinos,treinoRepository);
        atualizarTabelaDeTreino();
        atualizaDadosHome();
        CarregaDadosService.atualizaListaDeTreinos(listaMeusTreinos,treinoRepository);
    }
    public void voltarEditarTreino() {
        TreinoUtil.exibirEditarTreino(btNovoTreino,btVoltarEditarTreino,gridPaneAtividadesNovoTreino,
                gridPaneAtividadesMeusTreinos,false);
        CarregaDadosService.carregaDadosParaEdicaoDoTreino(listaMeusTreinos,dpDataTreino,cbTipoTreino,
                taDescricaoTreino,tfNomeTreino,treinoRepository,true);
    }
    public void atualizarTabelaDeTreino() {
        CarregaDadosService.carregarDadosParaTabelaDeTreino(guardaNomeTreino,tfDuracaoTreino,checkBoxSemTempo,
                btRegistrarTreino,btVoltarRegistroCompleto,labelInfoSelecaoTreino,qtdTreinosMeusTreinos,
                tabelaTreinos,treinosObservableList,listaMeusTreinos,treinoRepository);
        CarregaDadosService.carregaTiposDeTreino(cbTipoTreino,categoriaRepository);
        TreinoService.carregaProximosTreinos(treinoUm,treinoDois,treinoTres,treinoQuatro,nomeDataTreinoUm,
                nomeDataTreinoDois,nomeDataTreinoTres,nomeDataTreinoQuatro,treinoRepository);
    }
    public void desabilitaTfDuracaoTreino() {
        tfDuracaoTreino.setDisable(true);
    }
    public void voltarFiltroView() {
        TreinoUtil.exibeRegistroCompletoDoTreino(tfDuracaoTreino,checkBoxSemTempo,btRegistrarTreino,btVoltarRegistroCompleto,labelInfoSelecaoTreino,false);
    }
    public void registrarTreinoCompleto() {
        if (EstudosUtil.validarFormadoDoTempo(tfDuracaoTreino.getText())) {
            TreinoService.registrarTreino(labelAvisosTreino,guardaNomeTreino,tfDuracaoTreino,checkBoxSemTempo,treinoRepository);
            atualizarTabelaDeTreino();
            voltarFiltroView();
            atualizaDadosHome();
        }
    }
    public void inicioTreinosView() {
        TreinoUtil.trocaTelasControle(gridPaneInicialTreinos,gridPaneAtividadesNovoTreino,gridPaneAtividadesMeusTreinos,false,false,true);
    }
    public void projetosView() {
        ProjetoUtil.controleDeAtividades(paneOpcaoProjetos,paneInferiorProjetos,paneOpcaoTreino,paneInferiorTreino,tabelaTreinos,true,false);
        atividades();
    }
    public void meusProjetosView() {
        ProjetoUtil.trocaTelaDeProjetos(gridPaneCriaItem,gridPaneNovoProjeto, gridPaneNovoCard, gridPaneMeusProjetos, gridPaneMuralProjetos, gridPaneEditaCard,gridPaneEditaProjeto,true, false);

    }
    public void editarProjetoView() {
        ProjetoService.carregaDadosParaEdicaoDoProjeto(tfEditaProjeto,dpEditaProjeto,guardaNomeProjeto,projetoRepository);
        ProjetoUtil.telaEditarCard(gridPaneEditaCard,gridPaneMeusProjetos,gridPaneEditaProjeto,true,false);
        CarregaDadosService.carregaCategoriasProjeto(cbEditaProjeto,categoriaRepository);
    }
    public void novoProjetoView() {
        ProjetoUtil.novoProjetoEcardView(gridPaneCriaItem,gridPaneNovoProjeto,gridPaneNovoCard,gridPaneMuralProjetos,gridPaneMeusProjetos,gridPaneEditaCard,gridPaneEditaProjeto,true,false,false);
    }
    public void novoCardView() {
        ProjetoUtil.novoProjetoEcardView(gridPaneCriaItem,gridPaneNovoProjeto,gridPaneNovoCard,gridPaneMuralProjetos,gridPaneMeusProjetos,gridPaneEditaCard,gridPaneEditaProjeto,false,true,false);
    }
    public void criarCategoriaView() {
        ProjetoUtil.criarCategoriaView(cbCategoriaProjeto,tfCriarCategoriaProjeto,rbNovaCategoria);
    }
    public void criarProjeto() {
        ProjetoService.criarProjeto(tfNomeCriarProjeto,dpCriarProjeto,cbCategoriaProjeto,tfCriarCategoriaProjeto,rbNovaCategoria,projetoRepository,categoriaRepository,labelAvisosProjetos);
        atualizaProjetos();
    }
    public void editarCard() {
        ProjetoUtil.telaEditarCard(gridPaneEditaCard,gridPaneMeusProjetos,gridPaneEditaProjeto,false,true);
        ProjetoCardService.carregaDadosParaEdicaoDoCard(guardaNomeCard,tfEditaCard,dpEditaCard,taEditCard,cbEditaCard,
                projetoCardRepository);
        atualizaProjetos();
    }
    public void removerCard() {
        ProjetoCardService.removerCard(guardaNomeCard,projetoCardRepository,labelAvisosProjetos);
    }
    public void salvarEdicaoProjeto(){
        ProjetoService.editarProjeto(tfEditaProjeto,dpEditaProjeto,cbEditaProjeto,guardaNomeProjeto,labelAvisosProjetos,
                projetoRepository, gridPaneMeusProjetos,gridPaneEditaProjeto);
    }
    public void removerProjeto() {
        ProjetoService.excluirProjeto(projetoRepository,guardaNomeProjeto,labelAvisosProjetos,gridPaneMeusProjetos,
                gridPaneEditaProjeto);
        atualizaProjetos();
    }
    public void salvarEdicaoCard() {
        ProjetoCardService.editarCard(tfEditaCard,dpEditaCard,taEditCard,cbEditaCard,guardaNomeCard,projetoCardRepository,
                projetoRepository,labelAvisosProjetos,gridPaneEditaCard,gridPaneMeusProjetos);
        atualizaProjetos();
    }
    public void criarCard() {
        ProjetoCardService.criarCardEAdicionarAoVBox(tfNomeCard,taCriarCard,cbEscolheProjetoCard,dpCriarCard,labelAvisosProjetos,projetoCardRepository,projetoRepository,vBoxNaoIniciada, nomeDoCard,descricaoCard,cbStatusCard,tabelaItensCard,itemProjetoCardRepository);
        atualizaProjetos();
    }
    private void atualizaProjetos() {
        CarregaDadosService.carregarProjetosParaNovoCard(cbEscolheProjetoCard,projetoRepository);
        CarregaDadosService.carregarProjetosParaNovoCard(cbSelecionaProjetoMural,projetoRepository);
        CarregaDadosService.carregarProjetosParaNovoCard(cbMeusProjetos,projetoRepository);
        CarregaDadosService.carregaCategoriasProjeto(cbCategoriaProjeto,categoriaRepository);
    }
    public void muralView() {
        ProjetoUtil.trocaTelaDeProjetos(gridPaneCriaItem,gridPaneNovoProjeto,gridPaneNovoCard,gridPaneMeusProjetos,gridPaneMuralProjetos,gridPaneEditaCard,gridPaneEditaProjeto,false,true);
    }
    public void addItemView() {
        ProjetoUtil.novoProjetoEcardView(gridPaneCriaItem,gridPaneNovoProjeto,gridPaneNovoCard,gridPaneMuralProjetos,gridPaneMeusProjetos,gridPaneEditaCard,gridPaneEditaProjeto,false,false,true);
        ProjetoUtil.carregaNomeDoCard(cbCardsRelacionadosMeusProjetos,nomeDoCardItem);
    }
    public void criarItem() {
        ItemProjetoCardService.criarItem(tfNomeDoItemCard,itemProjetoCardRepository,labelAvisosProjetos,nomeDoCardItem,projetoCardRepository);
    }

    /**
     * CONTROLES TELA DE HABITOS:
     */
    @FXML
    private GridPane calendarioHabitos;
    @FXML
    private TextField tfCriarHabito;
    @FXML
    private RadioButton rbCriarNovoHabito;
    @FXML
    private DatePicker dpDiaHabito;
    @FXML
    private ChoiceBox<String> cbSelecionarHabito;
    @FXML
    private Label labelAvisosHabitos,labelDiaSelecionado,numHabitosSelecionado,numHabitosSelecionadosConcluidos,pegaDataHabito,
            habitosDoMes,habitosConcluidosDoMes,labelProgressoHabitos,guardaNomeHabito;
    @FXML
    private Button btPassarMesHabito,btAtualHabitos,btVoltarHabitos,btRemoverHabito,btApagarHabito;
    @FXML
    private ListView<String> listaHabitosDoDia;
    @FXML
    private ProgressBar progressBarHabitos;
    @FXML
    private CheckBox checkBoxHabitos;
    @Autowired
    private HabitosRepository habitosRepository;
    public void criarHabito() {
        HabitosService.criarHabito(labelAvisosHabitos,
                dpDiaHabito,tfCriarHabito,cbSelecionarHabito,categoriaRepository,habitosRepository,rbCriarNovoHabito);
        atualizaCalendarioHabitos();
        atualizaDadosHome();
        atualizarHabitosListados();
    }

    public void trocaMesHabito() {
        Calendario.trocaMesDosHabitos(habitosDoMes,habitosConcluidosDoMes,progressBarHabitos,
                labelDiaSelecionado,listaHabitosDoDia,numHabitosSelecionado,
                numHabitosSelecionadosConcluidos,btPassarMesHabito,btVoltarHabitos, btAtualHabitos,
                calendarioHabitos,habitosRepository,pegaDataHabito,labelProgressoHabitos);
    }
    public void checkConcluirHabito() {
        atualizaCalendarioHabitos();
        atualizaDadosHome();
    }
    private void atualizaCalendarioHabitos() {
        Calendario.calendarioDeHabitos(habitosDoMes,habitosConcluidosDoMes,progressBarHabitos,
                labelDiaSelecionado,listaHabitosDoDia,numHabitosSelecionado,
                numHabitosSelecionadosConcluidos,calendarioHabitos,habitosRepository,pegaDataHabito,labelProgressoHabitos);
    }
    private void atualizarHabitosListados() {
        CarregaDadosService.carregarHabitos(categoriaRepository,cbSelecionarHabito);
    }
    public void removerHabitoDoDia() {
        HabitosService.removerHabito(labelAvisosHabitos,habitosRepository,listaHabitosDoDia,pegaDataHabito);
        atualizarHabitosListados();
        atualizaCalendarioHabitos();
        atualizaDadosHome();
        CarregaDadosService.carregaListaDeHabitos(listaHabitosDoDia,habitosRepository,pegaDataHabito);
    }
    public void apagarHabito() {
        CategoriaService.removerCategoria(categoriaRepository,cbSelecionarHabito.getValue(),"Hábitos");
        atualizarHabitosListados();
    }

    /**
     * CONTROLES DE FINANÇAS
     */
    @FXML
    private TextField  tfInserirRenda,tfDescricaoFinanca,tfValorGasto,tfInserirReserva,tfNovaCategoriaFinanca;
    @FXML
    private Button btSalvarNovaRenda,btSalvarGasto,btAlterarReserva,btVoltarMesFinanca,btAtualFinanca,btPassarMesFinanca;
    @FXML
    private RadioButton rbAlterarRenda,rbNovaCategoriaFinanca;
    @FXML
    private ChoiceBox<String> cbCategoriaFinanca;
    @FXML
    private Label labelSaldo,labelRenda,labelGastos,labelAvisosFinancas;
    @FXML
    private TableView<TabelaFinanca> tabelaFinancas;
    @FXML
    private TableColumn<TabelaFinanca,String> tcCategoriaFinanca, tcDescFinanca,tcDataFinanca,tcValorFinanca,tcRemoverFinanca;
    @FXML
    private DatePicker dpSelecionaMesAtual,dpDataDoRegistro;

    @Autowired
    private FinancaRepository financaRepository;
    @Autowired
    private RegistroFinancaRepository registroFinancaRepository;
    ObservableList<TabelaFinanca> observableListFinancas = FXCollections.observableArrayList();
    public void registraGasto() {
        FinancaService.registrarGastoNaTabela(registroFinancaRepository,cbCategoriaFinanca,tfNovaCategoriaFinanca,
                tfValorGasto,tfDescricaoFinanca,dpDataDoRegistro,categoriaRepository,financaRepository,labelAvisosFinancas,rbNovaCategoriaFinanca);
        carregaTabelaFinancas();
        atualizaInfosFinanca();
        CarregaDadosService.carregarCategoriaGastos(cbCategoriaFinanca,categoriaRepository);
    }
    public void salvarRenda() {
        FinancaService.registrarRenda(tfInserirRenda,financaRepository,labelAvisosFinancas);
        atualizaInfosFinanca();
        carregaTabelaFinancas();
    }
    private void atualizaInfosFinanca() {
        CarregaDadosService.carregaDadoDasFinancas(labelRenda,labelGastos,labelSaldo,financaRepository);

    }
    public void carregaTabelaFinancas() {
        CarregaDadosService.carregaDadosDaTabelaFinancas(tabelaFinancas,observableListFinancas,registroFinancaRepository,financaRepository,dpSelecionaMesAtual,labelGastos,labelSaldo);
    }
    public void trocaMesFinanca() {
        FinancaUtil.calendarioFinancas(dpSelecionaMesAtual,btPassarMesFinanca,btVoltarMesFinanca,btAtualFinanca);
        carregaTabelaFinancas();
        atualizaInfosFinanca();
    }

    /**
     * CONTROLE ESTATÍSTICAS E DADOS DO PERFIL:
     */
    @FXML
    private Label geralTotalTarefas,geralTarefasFiltrada,geralTarefasFiltradaConcluida,porcentagemGeralTarefas,
            geralTotalEstudos,geralTempoEstudos,geralFiltradoTempoEstudos,geralFiltradoQtdEstudos,geralTreinosRegistrados,
            geralHorasDeTreino,geralFiltradoQtdTreinos,geralFiltradoTotalHorasTreino,geralTotalHabitos,
            geralHabitosConcluidos,geralFiltradoQtdHabitos,geralFiltradoHabitosConcluidos,anoTotalTarefa,
            anoTotalTarefaConcluido,anoDesempenhoTarefa,anoEstudos,anoHorasEstudos,anoMediaEstudos,anoTreinos,
            anoTotalHorasTreino,anoMediaTreinos,anoHabitos,anoHabitosConcluidos,anoDesempenhoHabitos,geralTarefasConcluidas,
            porcentagemFiltradaTarefas,porcentagemFiltradoHabitos,porcentagemGeralHabitos,meusDadosNome,meusDadosDataNascimento,diasAniversario;
    @FXML
    private DatePicker dpEstatisticaDoAno,dpAlteraDataDados;
    @FXML
    private GridPane gridPaneAlteraSenha,gridPaneAlteraUsuario;
    @FXML
    private PasswordField pfSenhaAtual,pfNovaSenha,senhaTrocaUser;
    @FXML
    private TextField tfAlteraNomeDados,tfNovoNomeUsuario;
    @FXML
    private Button btSalvarAlteracao,btVoltarEditDados,btSalvarAlterarDados, btCancelarTrocaSenhaUser,btEditaProjeto;
    public void alteraDadosView() {
        ListenerEstatisticasDados.alteraDadosView(meusDadosNome,meusDadosDataNascimento,tfAlteraNomeDados,dpAlteraDataDados,
                btVoltarEditDados, btSalvarAlterarDados,false,true);
    }
    public void alterarSenhaView() {
        ListenerEstatisticasDados.alteraDadosContaView(btCancelarTrocaSenhaUser,btSalvarAlteracao,
                gridPaneAlteraUsuario,gridPaneAlteraSenha,false,true,true);
    }
    public void alterarNomeDeUsuario() {
        ListenerEstatisticasDados.alteraDadosContaView(btCancelarTrocaSenhaUser,btSalvarAlteracao,
                gridPaneAlteraUsuario,gridPaneAlteraSenha,true,false,true);
    }
    public void alterarSenhaOuNomeDeUsuario() {
        if (gridPaneAlteraUsuario.isVisible()) {
            AlteraInfosService.alteraNomeUsuario(tfNovoNomeUsuario,senhaTrocaUser,contaRepository,segurancaService);
        } else {
            AlteraInfosService.alteraSenha(pfSenhaAtual,pfNovaSenha,contaRepository,segurancaService);
        }
    }
    public void salvarAlteracaoDeDados() {
        AlteraInfosService.ateraDadosDoUsuario(tfAlteraNomeDados,dpAlteraDataDados,contaRepository);
    }
    public void voltarAlterarDadosView() {
        ListenerEstatisticasDados.alteraDadosView(meusDadosNome,meusDadosDataNascimento,tfAlteraNomeDados,dpAlteraDataDados,
                btVoltarEditDados, btSalvarAlterarDados,true,false);
    }
    public void voltarViewAlterarSenhaOuUser() {
        ListenerEstatisticasDados.alteraDadosContaView(btCancelarTrocaSenhaUser,btSalvarAlteracao,
                gridPaneAlteraUsuario,gridPaneAlteraSenha,false,false,false);
    }

    FormatadorLogin formatadorLogin= new FormatadorLogin();
    FormatadorEstudos formatadorEstudos = new FormatadorEstudos();
    FormatadorTarefas formatadorTarefas = new FormatadorTarefas();
    FormatadorMetas formatadorMetas = new FormatadorMetas();
    FormatadorTreino formatadorTreino = new FormatadorTreino();
    FormatadorFinancas formatadorFinancas = new FormatadorFinancas();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Configuration.inicializaFinancaEpomodoro(financaRepository,pomodoroRepository);
        muralCalendarioMesAtual.setText(LocalDate.now().getMonth().getDisplayName(TextStyle.FULL, new Locale("pt", "BR")) + " de " + LocalDate.now().getYear());
        Tabelas.setTarefas(colNome,colData,colPrioridade,colCategoria,colConcluido);
        Tabelas.setEstudos(tcMateria,tcConteudo,tcData,tcTempoEstudado,tcTipo,tcDescricao,tcApagar);
        Tabelas.setTreinos(tcNomeTreino,tcTipoTreino,tcDescricaoTreino,tcRegistroTreino);
        Tabelas.setTabelaItensCard(tcNomeItemCard,tcConcluido,tcApagarItem);
        Tabelas.setTabelaFinancas(tcDescFinanca,tcDataFinanca,tcValorFinanca,tcCategoriaFinanca,tcRemoverFinanca);
        CarregaDadosService.carregaPrioridadeCategoriaEsubmeta(cbCatAddTarefaSubmeta,cbPriorAddTarefasubmeta,
                cbSubmetaAddTarefaSubmeta,categoriaRepository,submetaRepository);
        CarregaDadosService.carregaEstatisticas(geralTotalTarefas,geralTarefasConcluidas,geralTotalEstudos,geralTempoEstudos,
                geralTreinosRegistrados,geralHorasDeTreino,geralTotalHabitos,geralHabitosConcluidos,porcentagemGeralHabitos,
                porcentagemGeralTarefas,tarefaRepository,registroEstudosRepository,treinoRepository,habitosRepository);
        atualizarTabelaDeTreino();
        atualizaProjetos();
        atualizaInfosFinanca();
        carregaTabelaFinancas();
        Calendario.calendarioDeHabitos(habitosDoMes,habitosConcluidosDoMes,progressBarHabitos,
                labelDiaSelecionado,listaHabitosDoDia,numHabitosSelecionado,
                numHabitosSelecionadosConcluidos,calendarioHabitos,habitosRepository,pegaDataHabito,labelProgressoHabitos);
        CarregaDadosService.carregaEstatisticasPorAno(anoTotalTarefa,anoTotalTarefaConcluido,anoDesempenhoTarefa,anoEstudos, anoHorasEstudos,
                anoMediaEstudos,anoTreinos,anoTotalHorasTreino,anoMediaTreinos,anoHabitos,anoHabitosConcluidos,anoDesempenhoHabitos,dpEstatisticaDoAno,tarefaRepository,registroEstudosRepository,treinoRepository,habitosRepository);
        CarregaDadosService.carregarDados(labelDateTime);
        CarregaDadosService.carregaMeta(listaDasMinhasMetaView,metaRepository);
        CarregaDadosService.carregarTipoEstudos(choiceBoxTipoMateria,categoriaRepository);
        CarregaDadosService.carregaCategorias(cbCategoriaTarefa,categoriaRepository);
        CarregaDadosService.carregaSubmetasTarefas(choiceBoxEscolhaSubmeta,submetaRepository);
        CarregaDadosService.carregarCategoriaGastos(cbCategoriaFinanca,categoriaRepository);
        CarregaDadosService.carregaDadosEtarefasAssociadasDaSubmeta(sobreNomeSubmeta,sobreDataSubmeta,
                sobreRelacionadoSubmeta,sobreStatusSubmeta,sobreNumTarefasSubmetas,sobreTarefasFeitasSubmetas,
                sobreProgressSubmeta,listaTodasSubmetas,listaTarefasAssociadas,listaTarefaNaoAssociada,
                submetaRepository,tarefaRepository);
        CarregaDadosService.carregaTarefaPorNomeDaSubmeta(nmNomeTarefa, nmDataTarefa, nmDescricaoTarefa,
                nmCategoriaTarefa,nmPrioridadeTarefa,nmStatusTarefa,listaDeSubmeta,listaDeTarefaSubmeta,
                tarefaRepository,submetaRepository);
        CarregaDadosService.carregaSubmetaPorNomeDaMeta(nmNomeSubmeta,nmDataSubmeta,nmStatusSubmeta,nmAssociadasSubmeta,
                nmConcluidasSubmeta,nmProgressoSubmeta,listaDeMeta,listaDeSubmeta,submetaRepository,metaRepository);
        CarregaDadosService.atualizaListaDeTreinos(listaMeusTreinos,treinoRepository);
        atualizaDadosHome();
        atualizaMetas();
        atualizaSubmetas();
        atualizarHabitosListados();
        tabelaTarefas.getItems().addAll(tarefasObservableList);
        tabelaEstudos.getItems().addAll(registroEstudosObservableList);
        ListenerLogin.botaoCadastro(tfNomeCadastro,tfNomeUsuarioCadastro,tfSenhaCadastro,dataPickerCadastro,botaoCadastrar );
        ListenerLogin.botaoLogin(tfUserLogin,tfPasswordLogin,botaoLogin);
        ListenerLogin.passwordField(tfSenhaCadastro,textoSenha,botaoCadastrar);
        ListenerLogin.passwordFieldKey(tfPasswordLogin,botaoLogin);
        formatadorLogin.formataData(dataPickerCadastro);
        formatadorLogin.restricaoCampoDeTexto(tfNomeCadastro,"abcçdefghijklmnopqrstuvwxyzáéíóúàèìòùâêîôûãõ ");
        formatadorEstudos.formatacaoDoTempo(tfTempoManual);
        formatadorTreino.formatacaoDoTempo(tfDuracaoTreino);
        formatadorEstudos.restricaoCampoDeTexto(campoTempoPomodoro,"1234567890");
        formatadorEstudos.restricaoCampoDeTexto(campoPomodoroCurto,"1234567890");
        formatadorEstudos.restricaoCampoDeTexto(campoPomodoroLongo,"1234567890");
        formatadorEstudos.restricaoCampoDeTexto(quantidadePomodoro,"1234567890");
        formatadorEstudos.formataData(datePickerManual);
        formatadorEstudos.formataDatePickerAno(dpEstatisticaDoAno);
        formatadorEstudos.formataDatePickerMes(dpSelecionaMesAtual);
        formatadorTarefas.formataData(dataTarefa);
        formatadorMetas.formatadorFiltroDaData(datePickerPeriodoSelecionado,choiceBoxFiltroPeriodoMeta);
        formatadorTreino.formatadorFiltroDaData(dpSelecionadoPeriodoTreino,filtroPeriodoTipo);
        formatadorFinancas.restricaoCampoDeTexto(tfValorGasto,"1234567890,.");
        formatadorFinancas.restricaoCampoDeTexto(tfInserirRenda,"1234567890,.");
        Filtros.setPrioridade(cbPrioridadeTarefa);
        Filtros.setFiltroEstudos(choiceBoxFiltroEstudos);
        Filtros.setFiltroData(choiceBoxFiltroPeriodoMeta);
        Filtros.setFiltroData(filtroPeriodoTipo);
        Filtros.setFiltroStatus(cbStatusCard);
        ListenerTarefas.listenerTabelaTarefas(
                tabelaTarefas,
                muralNomeTarefa,
                muralDataTarefa,
                muralPrioridadeTarefa,
                muralCategoriaTarefa,
                muralDescricaoTarefa,
                tarefaRepository,
                editarNomeTarefa,
                editarDataTarefa,
                editarPrioridadeTarefa,
                editarCategoriaTarefa,
                botaoEditarTarefa,
                editarTarefaBtExcluir,
                editarTarefaBtSalvar,
                textAreaEditarTarefa,
                checkBoxConcluido);
        ListenerTarefas.listenerCheckBoxTarefas(checkBoxConcluido,muralNomeTarefa,tarefaRepository);
        ListenerTarefas.listenerVerificaPaneTarefas(gridTarefaSelecionadaCalendario,gridNovaTarefa);
        ListenerTarefas.listenerCheckBoxTarefas(selecionadaCalendarioConcluido,selecionadaCalendarioNome,tarefaRepository);
        ListenerTarefas.listenerCheckBoxTarefasSemana(selecionadaSemanaConcluido,selecionadaSemanaNome,tarefaRepository);
        ListenerTarefas.listenerListaDoCalendario(gridNovaTarefa,gridCategoria,gridTarefaSelecionadaCalendario,
                muralCalendarioLista,selecionadaCalendarioNome,selecionadaCalendarioData,selecionadaCalendarioCategoria,
                selecionadaCalendarioPrioridade,selecionadaCalendarioConcluido,tarefaRepository,gridTarefaSelecionadaSemana);
        ListenerTarefas.listenerExibirChoiceBoxFiltroTarefa(hboxFiltroTarefas,muralCalendario);
        ListenerTarefas.listenerFiltro(selecionadaTarefaConcluida,selecionadaTarefaNumero,filtroTarefa,filtroTarefaSub,
                categoriaRepository,tabelaTarefas,tarefaRepository,tarefasObservableList);
        ListenerTarefas.mostraMuralCalendarioSemana(gridCategoria,gridTarefaSelecionadaSemana,
                gridTarefaSelecionadaCalendario,gridSemana,muralSemanaGridPane,muralSemanaVBoxLista,
                muralCalendarioGridPane,muralCalendarioVBoxLista,gridNovaTarefa);
        ListenerTarefas.mostraLabelDaSemana(muralCalendarioDia, gridSemana,muralCalendarioMesAtual);
        ListenerTarefas.listenerNomeDaTarefaParaDescricao(selecionadaSemanaNome,selecionadaSemanaDescricao,tarefaRepository);
        ListenerTarefas.listenerListaDaSemana(gridCategoria,selecionadaSemanaPrioridade,selecionadaSemanaCategoria,
                selecionadaSemanaData,selecionadaSemanaNome,selecionadaSemanaConcluido,muralSemanaLista,tarefaRepository,
                gridNovaTarefa,gridTarefaSelecionadaSemana);
        ListenerMeta.listenerTelaInicial(paneSubmetas, listaTarefasAssociadas, listaTarefaNaoAssociada, paneNovaTarefaSubmeta);
        ListenerMeta.listenerFiltrarMetaPorData(choiceBoxFiltroPeriodoMeta,datePickerPeriodoSelecionado,metaRepository,
                listaDasMinhasMetaView);
        ListenerMeta.listenerMostraInformacaoDaMetaSelecionada(listaDasMinhasMetaView,sobreNomeMeta, sobreStatusMeta,
                sobreDiasRestantes, sobreAreaMeta, metaRepository);
        ListenerTreino.listenerFiltrosDaTabelaDeTreino(guardaNomeTreino,tfDuracaoTreino,checkBoxSemTempo,
                btRegistrarTreino,btVoltarRegistroCompleto,labelInfoSelecaoTreino,filtroPeriodoTipo,dpSelecionadoPeriodoTreino,
                tabelaTreinos,treinosObservableList,treinoRepository,treinosFiltrados);
        ListenerTreino.listenerRegistarTreino(tfDuracaoTreino,filtroPeriodoTipo,dpSelecionadoPeriodoTreino,btVoltarRegistroCompleto);
        ListenerTreino.listenerTabelaTreino(tabelaTreinos,detalheTipoTreino,detalheNomeTreino,detalheDataTreino,
                detalheConcluidoTreino,detalheTempoTreino,treinoRepository);
        ListenerProjetos.listenerProjetoSelecionadoMostraCardsEinfo(dataLimiteMeusProjetos,statusMeuProjeto,cbMeusProjetos,
                cbCardsRelacionadosMeusProjetos,projetoCardRepository,projetoRepository);
        ListenerProjetos.resetaChoiceBoxMural(gridPaneCriaItem,gridPaneMeusProjetos,dataLimiteMeusProjetos,statusMeuProjeto,
                cbMeusProjetos,cbCardsRelacionadosMeusProjetos,guardaNomeCard,guardaNomeProjeto,btEditaCard,btEditaProjeto);
        ListenerProjetos.listenerCardStatus(cbStatusCard,projetoCardRepository,cbSelecionaProjetoMural,vBoxNaoIniciada,
                vBoxEmAndamento,vBoxConcluida,nomeDoCard,descricaoCard,tabelaItensCard,itemProjetoCardRepository,
                naoIniciadasCount, emAndamentoCount,completoCount,diasRestantesProjetos,projetoRepository);
        ListenerProjetos.listenerProjetoSelecionado(cbSelecionaProjetoMural,vBoxNaoIniciada,vBoxEmAndamento,
                vBoxConcluida,projetoCardRepository, nomeDoCard,descricaoCard,cbStatusCard,tabelaItensCard,
                itemProjetoCardRepository,naoIniciadasCount, emAndamentoCount,completoCount,
                diasRestantesProjetos,projetoRepository);
        ListenerHabitos.listenerRadioButton(rbCriarNovoHabito,tfCriarHabito,cbSelecionarHabito);
        ListenerHabitos.listenerRemoverHabito(cbSelecionarHabito,btApagarHabito);
        ListenerHabitos.listenerInfosListaHabito(checkBoxHabitos,habitosRepository,listaHabitosDoDia,btRemoverHabito,
                pegaDataHabito,guardaNomeHabito);
        ListenerHabitos.listenerCheckHabito(checkBoxHabitos,guardaNomeHabito,pegaDataHabito,habitosRepository);
        ListenerFinanca.listenerAlterarRenda(rbAlterarRenda,btSalvarNovaRenda,tfInserirRenda,labelRenda);
        ListenerFinanca.criarNovaCategoriaGastos(rbNovaCategoriaFinanca,tfNovaCategoriaFinanca,cbCategoriaFinanca);
        ListenerFinanca.listenerMesSelecionad(dpSelecionaMesAtual,tabelaFinancas,observableListFinancas,
                registroFinancaRepository,financaRepository,labelGastos,labelSaldo);
        ListenerEstatisticasDados.listenerAnoSelecionado(dpEstatisticaDoAno,anoTotalTarefa,anoTotalTarefaConcluido, anoDesempenhoTarefa,
                anoEstudos, anoHorasEstudos, anoMediaEstudos, anoTreinos, anoTotalHorasTreino, anoMediaTreinos,
                anoHabitos, anoHabitosConcluidos, anoDesempenhoHabitos, dpEstatisticaDoAno, tarefaRepository,
                registroEstudosRepository, treinoRepository, habitosRepository);
        ListenerHome.listenerHabitos(homeHabitosLista,homeHabitosNome,homeStatusHabito,habitosRepository,pegaDataHabito);
        ListenerHome.listenerTarefas(homeTarefasLista,homeNomeTarefa,homeCategoriaTarefa,homePrioridadeTarefa,homeStatusTarefa,tarefaRepository);
        ListenerHome.listenerTreinos(homeTreinosLista,homeTipoTreino,homeStatusTreino,treinoRepository);
        ListenerHome.listenerSelecionaData(homeSelecionarDiaProgresso,homeTreinosLista,homeTarefasLista,
                homeHabitosLista,homeProgressBar,treinoRepository,tarefaRepository,habitosRepository,progressoHomeLabel);
        TarefasUtil.setFiltroTarefa(filtroTarefa);
        TarefasUtil.gifProgresso(labelCalendarioProgresso,imageViewProgresso);
        TarefasUtil.gifAtrasado(labelCalendarioAtrasadas,imageViewAtrasadas);
        EstudosUtil.botoesEstudos(btPlay,btPause,btReset,btPassar);
        Configuration.carregarCategorias(tipoPreferencia,nomePreferencia,categoriaRepository);
        Configuration.setNumeroDaSemanaDoAno(tituloSemana);
        Configuration.verificaPrimeiroAcesso(applicationConfigurationRepository,paneBoasVindas,containerLogin);
        ListenerLogin.passwordField(pfNovaSenha,new Label(),btCancelarTrocaSenhaUser);
        atualizaPomodoro();
    }

}
