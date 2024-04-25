package br.com.myegoo.app.myego.service.projeto;

import br.com.myegoo.app.myego.model.projetos.Projeto;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.ProjetoRepository;
import br.com.myegoo.app.myego.service.CategoriaService;
import br.com.myegoo.app.myego.utils.Mensagem;
import br.com.myegoo.app.myego.utils.ValidacaoDados;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

@Service
public class ProjetoService {
    public static void criarProjeto(TextField tfNomeCriarProjeto,
                                    DatePicker dpCriarProjeto,
                                    ChoiceBox<String> cbCategoriaProjeto,
                                    TextField tfCriarCategoriaProjeto,
                                    RadioButton rbNovaCategoria,
                                    ProjetoRepository projetoRepository,
                                    CategoriaRepository categoriaRepository,
                                    Label labelAvisosProjetos) {
        if (tfNomeCriarProjeto.getText().isEmpty()) {
            labelAvisosProjetos.setTextFill(Color.RED);
            labelAvisosProjetos.setText("Preencha o campo nome!");
            throw new TratadorDeErros("Preencha o campo nome!");
        }
        if (projetoRepository.findByNome(tfNomeCriarProjeto.getText()) != null ) {
            labelAvisosProjetos.setText("Já existe um projeto com esse nome!");
            throw new TratadorDeErros("Já existe um projeto com esse nome!");
        }
        if (rbNovaCategoria.isSelected()) {
            ValidacaoDados.validarDataInserida(dpCriarProjeto, labelAvisosProjetos);
            Projeto projeto = new Projeto(tfNomeCriarProjeto.getText(),
                    dpCriarProjeto.getValue(),
                    tfCriarCategoriaProjeto.getText());
            CategoriaService.salvarCategoria(categoriaRepository, tfCriarCategoriaProjeto, "Projetos");
            salvarProjeto(projeto, projetoRepository, labelAvisosProjetos);
            tfNomeCriarProjeto.setText("");
            dpCriarProjeto.setValue(null);
            tfCriarCategoriaProjeto.setText("");
            rbNovaCategoria.setSelected(false);
            cbCategoriaProjeto.setVisible(true);
        } else {
            ValidacaoDados.validarDataInserida(dpCriarProjeto, labelAvisosProjetos);
            Projeto projeto = new Projeto(tfNomeCriarProjeto.getText(),
                    dpCriarProjeto.getValue(),
                    cbCategoriaProjeto.getValue());
            salvarProjeto(projeto, projetoRepository, labelAvisosProjetos);
            tfNomeCriarProjeto.setText("");
            dpCriarProjeto.setValue(null);
            tfCriarCategoriaProjeto.setText("");
        }
    }
    private static void salvarProjeto(Projeto projeto, ProjetoRepository projetoRepository, Label labelAvisosProjetos) {
        try {
            projetoRepository.save(projeto);
            labelAvisosProjetos.setTextFill(Color.GREEN);
            labelAvisosProjetos.setText("Projeto criado com sucesso!");
        } catch (Exception e) {
            labelAvisosProjetos.setTextFill(Color.RED);
            labelAvisosProjetos.setText("Erro ao criar projeto!");
            throw new TratadorDeErros("Erro ao criar projeto!");
        }


    }
    public static void excluirProjeto(ProjetoRepository projetoRepository,
                                      Label labelGuardaNome,
                                      Label labelAvisosTarefas,
                                      GridPane meusProjetos,
                                      GridPane editaProjeto) {
        Mensagem mensagem = new Mensagem();
        int resposta = mensagem.retornoMessege("Excluir projeto", "Tem certeza que quer excluir o projeto?");
        if (resposta == 1) {
            try {
                Projeto projeto = projetoRepository.findByNome(labelGuardaNome.getText());
                projetoRepository.deleteByNome(projeto.getNome());
                labelAvisosTarefas.setTextFill(Color.GREEN);
                labelAvisosTarefas.setText("Projeto excluído com sucesso!");
                meusProjetos.setVisible(true);
                editaProjeto.setVisible(false);
            } catch (Exception e) {
                labelAvisosTarefas.setTextFill(Color.RED);
                labelAvisosTarefas.setText("Erro ao excluir projeto! Verifique se ele possui cards associados antes de excluir!");
                throw new TratadorDeErros("Erro ao excluir projeto!");
            }
        }
    }
    public static void carregaDadosParaEdicaoDoProjeto(TextField tfEditaProjeto,
                                                       DatePicker dpEditaProjeto,
                                                       Label labelGuardaNome,
                                                       ProjetoRepository projetoRepository) {
        Projeto projeto = projetoRepository.findByNome(labelGuardaNome.getText());
        tfEditaProjeto.setText(projeto.getNome());
        dpEditaProjeto.setValue(projeto.getData());
    }
    public static void editarProjeto(TextField tfEditaProjeto,
                                     DatePicker dpEditaProjeto,
                                     ChoiceBox<String> cbEditaProjeto,
                                     Label labelGuardaNome,
                                     Label labelAvisosTarefas,
                                     ProjetoRepository projetoRepository,
                                     GridPane meusProjetos,
                                     GridPane editaProjeto) {
        if (tfEditaProjeto.getText().isEmpty() || dpEditaProjeto.getValue() == null || cbEditaProjeto.getValue() == null) {
            labelAvisosTarefas.setTextFill(Color.RED);
            labelAvisosTarefas.setText("Preencha todos os campos!");
            throw new TratadorDeErros("Preencha todos os campos!");
        }
        try {
            Projeto projeto = projetoRepository.findByNome(labelGuardaNome.getText());
            projeto.setNome(tfEditaProjeto.getText());
            projeto.setData(dpEditaProjeto.getValue());
            projeto.setCategoria(cbEditaProjeto.getValue());
            projetoRepository.save(projeto);
            labelAvisosTarefas.setTextFill(Color.GREEN);
            labelAvisosTarefas.setText("Projeto editado com sucesso!");
            tfEditaProjeto.setText("");
            dpEditaProjeto.setValue(null);
            meusProjetos.setVisible(true);
            editaProjeto.setVisible(false);
        } catch (Exception e) {
            labelAvisosTarefas.setTextFill(Color.RED);
            labelAvisosTarefas.setText("Erro ao editar projeto!");
            throw new TratadorDeErros("Erro ao editar projeto!");
        }
    }
}
