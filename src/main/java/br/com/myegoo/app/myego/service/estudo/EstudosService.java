package br.com.myegoo.app.myego.service.estudo;

import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.model.estudo.Estudos;
import br.com.myegoo.app.myego.model.estudo.RegistroEstudo;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.EstudoRepository;
import br.com.myegoo.app.myego.repository.RegistroEstudosRepository;
import br.com.myegoo.app.myego.service.CarregaDadosService;
import br.com.myegoo.app.myego.utils.estudo.EstudosUtil;
import br.com.myegoo.app.myego.utils.Mensagem;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstudosService {

    public static void criarNovoEstudo(GridPane gridPaneNovaMateria,
                                       TextField nome,
                                       ChoiceBox<String> tipo,
                                       EstudoRepository estudoRepository,
                                       Conta conta,
                                       Label labelAvisosEstudos) {

        if (nome.getText().isEmpty() || tipo.getValue() == null) {
           labelAvisosEstudos.setTextFill(Color.RED);
           labelAvisosEstudos.setText("Preencha todos os campos!");
           throw new TratadorDeErros("Preencha todos os campos!");
        } else {
            Estudos estudos = new Estudos(nome.getText(), tipo.getValue());
            estudos.setConta(conta);
            estudoRepository.save(estudos);
            nome.clear();
            tipo.setValue(null);
            gridPaneNovaMateria.setVisible(false);
            labelAvisosEstudos.setTextFill(Color.GREEN);
            labelAvisosEstudos.setText("Matéria criada com sucesso!");

        }
    }
    public static void editarEstudoSelecionar(Label labelTipoMinhasMaterias,
                                              ChoiceBox<String> choiceBoxEditaTipoMateria,
                                              TextField tfEditaMinhaMateria, ChoiceBox<String> choiceBoxMinhasMaterias,
                                              EstudoRepository estudoRepository,
                                              Conta conta,
                                              CategoriaRepository categoriaRepository,
                                              Button btEditar,
                                              Button btSalvarEdit,
                                              Button btVoltarEdit,
                                              Button btExcluir,
                                              Button btNovaMateria,
                                              boolean b,
                                              Label labelAvisosEstudos) {
        if (choiceBoxMinhasMaterias.getValue() != null) {
            Estudos estudo = estudoRepository.findByNomeAndConta(choiceBoxMinhasMaterias.getValue());
            tfEditaMinhaMateria.setText(estudo.getNome());

            EstudosUtil.escondeBotoesEditarMateria(labelTipoMinhasMaterias,choiceBoxEditaTipoMateria,tfEditaMinhaMateria,
                    choiceBoxMinhasMaterias,btEditar, btSalvarEdit, btVoltarEdit, btExcluir, btNovaMateria, b);

            CarregaDadosService.carregarTipoEstudos(choiceBoxEditaTipoMateria, categoriaRepository);
            choiceBoxEditaTipoMateria.setValue(estudo.getDescricao());
            labelAvisosEstudos.setText("");


        } else {
            labelAvisosEstudos.setTextFill(Color.RED);
            labelAvisosEstudos.setText("Selecione um estudo para editar!");
            throw new TratadorDeErros("Selecione um estudo para editar!");
        }
    }
    public static void editarEstudoSalvar(Label labelTipoMinhasMaterias,
                                          ChoiceBox<String> choiceBoxEditaTipoMateria,
                                          TextField tfEditaMinhaMateria,
                                          ChoiceBox<String> choiceBoxMinhasMaterias,
                                          EstudoRepository estudoRepository,
                                          RegistroEstudosRepository registroEstudosRepository,
                                          Conta conta,
                                          Button btEditar,
                                          Button btSalvarEdit,
                                          Button btVoltarEdit,
                                          Button btExcluir,
                                          Button btNovaMateria) {
        if (!tfEditaMinhaMateria.getText().isEmpty() && choiceBoxEditaTipoMateria.getValue() != null) {
            Estudos estudo = estudoRepository.findByNomeAndConta(choiceBoxMinhasMaterias.getValue());

            String nomeAntigo = estudo.getNome();
            String novoNome = tfEditaMinhaMateria.getText();
            List<RegistroEstudo> atualizarRegistros = registroEstudosRepository.findByMateria(nomeAntigo);
            for (RegistroEstudo registro : atualizarRegistros) {
                registro.setMateria(novoNome);
                registroEstudosRepository.save(registro);
            }
            estudo.setNome(tfEditaMinhaMateria.getText());
            estudo.setDescricao(choiceBoxEditaTipoMateria.getValue());
            estudoRepository.save(estudo);
            Mensagem mensagem = new Mensagem();
            mensagem.showMessege("Sucesso", "Estudo editado com sucesso!", 2);
            tfEditaMinhaMateria.setText("");
            choiceBoxEditaTipoMateria.setValue(null);
            choiceBoxMinhasMaterias.setValue(null);
            tfEditaMinhaMateria.clear();
            EstudosUtil.escondeBotoesEditarMateria(labelTipoMinhasMaterias,choiceBoxEditaTipoMateria,tfEditaMinhaMateria,choiceBoxMinhasMaterias,btEditar, btSalvarEdit, btVoltarEdit, btExcluir, btNovaMateria, true);
            CarregaDadosService.carregarMaterias(choiceBoxMinhasMaterias, estudoRepository, conta);
        } else {
            Mensagem mensagem = new Mensagem();
            mensagem.showMessege("Erro", "Preencha todos os campos!", 1);
        }
    }
    public static void excluirMateria(ChoiceBox<String> materiaSelecionada, EstudoRepository estudoRepository, Conta conta) {
        Mensagem mensagem = new Mensagem();
        if (materiaSelecionada.getValue() != null) {
            int retorno = mensagem.retornoMessege("ATENÇÃO", "Tem certeza que deseja excluir a matéria selecionada? Você precisa antes excluir todos os registros de estudo associados a ela!");
            if (retorno == 1) {
                try {
                    Estudos estudo = estudoRepository.findByNomeAndConta(materiaSelecionada.getValue());
                    estudoRepository.delete(estudo);
                    mensagem.showMessege("Sucesso", "Estudo excluído com sucesso!", 2);
                } catch (Exception e) {
                    mensagem.showMessege("Erro", "Existem registros associados a matéria!!", 1);
                }
            }

        } else {
            mensagem.showMessege("Erro", "Selecione um estudo para excluir!", 1);
        }
    }
    public static void mostraLabelTipoEstudo(Label label, EstudoRepository estudoRepository, Conta conta, ChoiceBox<String> choiceBoxSelecionarMateria) {
        label.setVisible(true);
        Estudos estudoSelecionado = estudoRepository.findByNomeAndConta(choiceBoxSelecionarMateria.getValue());
        label.setText(estudoSelecionado.getDescricao());
    }
    public static void carregaTipoMinhasMaterias(Label labelTipoMinhasMaterias, ChoiceBox<String> choiceBoxMinhasMaterias, EstudoRepository estudoRepository, Conta conta) {
        if (choiceBoxMinhasMaterias.getValue() != null) {
            labelTipoMinhasMaterias.setVisible(true);
            Estudos estudo = estudoRepository.findByNomeAndConta(choiceBoxMinhasMaterias.getValue());
            labelTipoMinhasMaterias.setText("Tipo: "+ estudo.getDescricao());
        }
    }
}
