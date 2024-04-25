package br.com.myegoo.app.myego.utils.projeto;

import br.com.myegoo.app.myego.model.projetos.Projeto;
import br.com.myegoo.app.myego.model.projetos.ProjetoCard;
import br.com.myegoo.app.myego.model.projetos.TabelaItensCard;
import br.com.myegoo.app.myego.repository.ItemProjetoCardRepository;
import br.com.myegoo.app.myego.repository.ProjetoCardRepository;
import br.com.myegoo.app.myego.repository.ProjetoRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import br.com.myegoo.app.myego.service.projeto.ProjetoCardService;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.List;

public class ListenerProjetos {

    private static final ProjetoUtil projetoUtil = new ProjetoUtil();
    public static void listenerProjetoSelecionado(ChoiceBox<String> cbSelecionaProjetoMural,
                                                  VBox naoIniciadas,
                                                  VBox emAndamento,
                                                  VBox concluidas,
                                                  ProjetoCardRepository projetoCardRepository,
                                                  Label nomeDataCard,
                                                  Label descricaoCard,
                                                  ChoiceBox<String> cbStatusCard,
                                                  TableView<TabelaItensCard> tabelaitens,
                                                  ItemProjetoCardRepository itemProjetoCardRepository,
                                                  Label naoIniciadasCount,Label emAndamentoCount,Label completoCount,
                                                  Label diasRestantesProjetos,ProjetoRepository projetoRepository){
        cbSelecionaProjetoMural.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ProjetoCardService.carregaCardsNoMural(naoIniciadas, emAndamento, concluidas, projetoCardRepository,
                        newValue,nomeDataCard,descricaoCard,cbStatusCard,tabelaitens,itemProjetoCardRepository,
                        naoIniciadasCount, emAndamentoCount,completoCount,diasRestantesProjetos,projetoRepository);
            }
        });
    }
    public static void listenerProjetoSelecionadoMostraCardsEinfo(Label dataLimiteMeusProjetos,
                                                                  Label statusMeuProjeto,
                                                                  ChoiceBox<String> cbMeusProjetos,
                                                                  ChoiceBox<String> cbCardsRelacionadosMeusProjetos,
                                                                  ProjetoCardRepository projetoCardRepository,
                                                                  ProjetoRepository projetoRepository) {
        cbMeusProjetos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                int totalDeCards = 0;
                int totalDeCardsConcluidos = 0;
                int totalDeCardsEmAndamento = 0;
                List<ProjetoCard> cardsCarregados = CarregaDadosService.carregaMeusCards(cbCardsRelacionadosMeusProjetos,projetoCardRepository, newValue);
                Projeto projetoSelecionado = projetoRepository.findByNome(newValue);
                dataLimiteMeusProjetos.setText("Data limite: " + projetoUtil.formataData(projetoSelecionado.getData()));
                if (!cardsCarregados.isEmpty()) {
                    for (ProjetoCard card : cardsCarregados) {
                        totalDeCards++;
                        if (card.getStatus().equals("Concluído")) {
                            totalDeCardsConcluidos++;
                        } else if (card.getStatus().equals("Em andamento")) {
                            totalDeCardsEmAndamento++;
                        }
                    }
                    if (totalDeCards == totalDeCardsConcluidos) {
                        statusMeuProjeto.setText("Concluído!");
                    } else if (totalDeCardsConcluidos < totalDeCards && totalDeCardsEmAndamento > 0) {
                        statusMeuProjeto.setText("Em andamento!");
                    } else {
                        statusMeuProjeto.setText("Não iniciado!");
                    }
                }
            }
        });
    }
    public static void resetaChoiceBoxMural(GridPane gridPaneCriaItem,
                                            GridPane gridPaneMeusProjetos,
                                            Label dataLimiteMeusProjetos,
                                            Label statusMeuProjeto,
                                            ChoiceBox<String> cbMeusProjetos,
                                            ChoiceBox<String> cbCardsRelacionadosMeusProjetos,
                                            Label guardaNomeCard,
                                            Label guardaNomeProjeto,
                                            Button editarCard,
                                            Button btEditaProjeto) {
        gridPaneMeusProjetos.visibleProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue) {
                if (!gridPaneCriaItem.isVisible()) {
                    dataLimiteMeusProjetos.setText("");
                    statusMeuProjeto.setText("");
                    cbMeusProjetos.setValue(null);
                    cbCardsRelacionadosMeusProjetos.getItems().clear();
                }
            }
        });
        cbCardsRelacionadosMeusProjetos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                guardaNomeCard.setText(newValue);
                editarCard.setDisable(false);
            } else {
                editarCard.setDisable(true);
            }
        });
        cbMeusProjetos.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                guardaNomeProjeto.setText(newValue);
                btEditaProjeto.setDisable(false);
            } else {
                btEditaProjeto.setDisable(true);
            }
        });
    }
    public static void listenerCardStatus(ChoiceBox<String> cbStatusCard,
                                          ProjetoCardRepository projetoCardRepository,
                                          ChoiceBox<String> projetoSelecionado,
                                          VBox naoIniciadas,
                                          VBox emAndamento,
                                          VBox concluidas,
                                          Label nomeDoCard,
                                          Label descricaoCard,
                                          TableView<TabelaItensCard> tabelaitens,
                                          ItemProjetoCardRepository itemProjetoCardRepository,
                                          Label naoIniciadasCount,Label emAndamentoCount,Label completoCount,
                                          Label diasRestantesProjetos, ProjetoRepository projetoRepository) {



        cbStatusCard.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                ProjetoCard projetoCard = projetoCardRepository.findByNome(nomeDoCard.getText());
                System.out.println(nomeDoCard.getText());

                if (newValue.equals("Não iniciado")) {
                    projetoCard.setStatus(newValue);
                    projetoCardRepository.save(projetoCard);
                    ProjetoCardService.carregaCardsNoMural(naoIniciadas,emAndamento,concluidas,
                            projetoCardRepository,projetoSelecionado.getValue(),nomeDoCard,descricaoCard,
                            cbStatusCard,tabelaitens,itemProjetoCardRepository,naoIniciadasCount, emAndamentoCount,completoCount,
                            diasRestantesProjetos,projetoRepository);
                }
                else if(newValue.equals("Em andamento")) {
                    projetoCard.setStatus(newValue);
                    projetoCardRepository.save(projetoCard);
                    ProjetoCardService.carregaCardsNoMural(naoIniciadas,emAndamento,concluidas,
                            projetoCardRepository,projetoSelecionado.getValue(),nomeDoCard,descricaoCard,
                            cbStatusCard,tabelaitens,itemProjetoCardRepository,naoIniciadasCount, emAndamentoCount,completoCount,
                            diasRestantesProjetos,projetoRepository);
                }
                else {
                    projetoCard.setStatus(newValue);
                    projetoCardRepository.save(projetoCard);
                    ProjetoCardService.carregaCardsNoMural(naoIniciadas,emAndamento,concluidas,
                            projetoCardRepository,projetoSelecionado.getValue(),nomeDoCard,descricaoCard,
                            cbStatusCard,tabelaitens,itemProjetoCardRepository,naoIniciadasCount, emAndamentoCount,completoCount,
                            diasRestantesProjetos,projetoRepository);
                }

        }
        });
    }
}
