package br.com.myegoo.app.myego.service.projeto;

import br.com.myegoo.app.myego.model.projetos.ItemProjetoCard;
import br.com.myegoo.app.myego.model.projetos.ProjetoCard;
import br.com.myegoo.app.myego.model.projetos.TabelaItensCard;
import br.com.myegoo.app.myego.repository.ItemProjetoCardRepository;
import br.com.myegoo.app.myego.repository.ProjetoCardRepository;
import br.com.myegoo.app.myego.utils.projeto.ProjetoUtil;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

@Service
public class ItemProjetoCardService {
    public static void criarItem(TextField tfNomeDoItemCard,
                                 ItemProjetoCardRepository itemProjetoCardRepository,
                                 Label labeAvisosProjetos,
                                 Label nomeDoCard,
                                 ProjetoCardRepository projetoCardRepository) {
        ItemProjetoCard itemProjetoCard = new ItemProjetoCard(tfNomeDoItemCard.getText(),false);
        ProjetoCard projetoCard = projetoCardRepository.findByNome(nomeDoCard.getText());

        try {
            itemProjetoCard.setProjetoCard(projetoCard);
            itemProjetoCardRepository.save(itemProjetoCard);
            tfNomeDoItemCard.setText("");
            labeAvisosProjetos.setTextFill(Color.RED);
            labeAvisosProjetos.setText("Item criado com sucesso");
        } catch (RuntimeException ex) {
            labeAvisosProjetos.setTextFill(Color.RED);
            labeAvisosProjetos.setText("Erro ao criar o item!");
            throw new TratadorDeErros("Erro ao criar o item");
        }
    }
    public static void carregarItemNoCard(TableView<TabelaItensCard> tabelaItens,
                                          ItemProjetoCardRepository itemProjetoCardRepository,
                                          Label nomeDoCard) {
        try {
            tabelaItens.getItems().clear();
            ProjetoUtil.verificaItemConcluidoTabela(tabelaItens);
            itemProjetoCardRepository.findByCard(nomeDoCard.getText()).forEach(item -> {
                TabelaItensCard tabelaItensCard = new TabelaItensCard(item.getNome(), item.isConcluido(), concluir ->{
                    item.setConcluido(true);
                    itemProjetoCardRepository.save(item);
                    carregarItemNoCard(tabelaItens, itemProjetoCardRepository, nomeDoCard);
                }, apagar -> {
                    itemProjetoCardRepository.deleteByCard(item.getId());
                    carregarItemNoCard(tabelaItens, itemProjetoCardRepository, nomeDoCard);
                });
                tabelaItens.getItems().add(tabelaItensCard);
            });
        } catch (RuntimeException ex) {
            throw new TratadorDeErros("Erro ao carregar os itens");
        }
    }
}
