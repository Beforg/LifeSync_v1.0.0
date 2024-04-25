package br.com.myegoo.app.myego.service;

import br.com.myegoo.app.myego.controller.ApplicationController;
import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.model.meta.Submeta;
import br.com.myegoo.app.myego.model.tarefa.TabelaTarefas;
import br.com.myegoo.app.myego.model.tarefa.Tarefa;
import br.com.myegoo.app.myego.repository.SubmetaRepository;
import br.com.myegoo.app.myego.repository.TarefaRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import br.com.myegoo.app.myego.utils.ValidacaoDados;
import br.com.myegoo.app.myego.utils.exception.TratadorDeErros;
import br.com.myegoo.app.myego.utils.tarefa.TarefasUtil;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

@Service
public class TarefaService {



    public static void salvarTarefa(TarefaRepository tarefaRepository,
                                    TextField nomeTarefa,
                                    TextArea descricaoTarefa,
                                    DatePicker dataTarefa,
                                    ChoiceBox<String> prioridadeTarefa,
                                    ChoiceBox<String> categoriaTarefa,
                                    Conta conta,
                                    ChoiceBox<String> subMeta,
                                    SubmetaRepository submetaRepository,
                                    Label labelAvisosTarefas) {
        if (nomeTarefa.getText().isEmpty()) {
            labelAvisosTarefas.setTextFill(Color.RED);
            labelAvisosTarefas.setText("Preencha o campo nome!");
        }
        Mensagem mensagem = new Mensagem();
        Tarefa tarefa = new Tarefa(nomeTarefa.getText(),
                descricaoTarefa.getText(),
                dataTarefa.getValue(),
                false,
                prioridadeTarefa.getValue(),
                categoriaTarefa.getValue());
        tarefa.setConta(conta);
        Submeta submeta = submetaRepository.findByNome(subMeta.getValue());
        tarefa.setSubmeta(submeta);
        ValidacaoDados.validarDataInserida(dataTarefa,labelAvisosTarefas);
        if (tarefaRepository.findByNomeAndId(tarefa.getNome(), ApplicationController.conta.getId()) != null){
            labelAvisosTarefas.setTextFill(Color.RED);
            labelAvisosTarefas.setText("Nome da tarefa já cadastrada!");
            throw new TratadorDeErros("Tarefa já registrada");
        }else if (tarefa.getNome().isEmpty()  || tarefa.getPrioridade().isEmpty() || tarefa.getCategoria().isEmpty()) {
            labelAvisosTarefas.setTextFill(Color.RED);
            labelAvisosTarefas.setText("Preencha todos os campos!");
            throw new TratadorDeErros("Preencha todos os campos!");
        } else {
            try {
                tarefaRepository.save(tarefa);
                labelAvisosTarefas.setTextFill(Color.GREEN);
                labelAvisosTarefas.setText("Tarefa salva com sucesso!");

                nomeTarefa.clear();
                descricaoTarefa.clear();
                dataTarefa.getEditor().clear();
                prioridadeTarefa.setValue(null);
                categoriaTarefa.setValue(null);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    public static void descricaoTarefa(TarefaRepository tarefaRepository,
                                       Label descricaoTarefa,
                                       TabelaTarefas tarefaSelecionada) {
        Tarefa tarefa = tarefaRepository.findByNomeAndId(tarefaSelecionada.getNome(), ApplicationController.conta.getId());
        descricaoTarefa.setText(tarefa.getDescricao());

    }

    public static void excluirTarefa(String nome, TarefaRepository tarefaRepository,Label labelAvisosTarefas) {
        Mensagem mensagem = new Mensagem();
        if (tarefaRepository.findByNomeAndId(nome,ApplicationController.conta.getId()) == null) {
            labelAvisosTarefas.setTextFill(Color.RED);
            labelAvisosTarefas.setText("Tarefa não encontrada!");
            throw new TratadorDeErros("Tarefa não encontrada!");
        } else {
            int resposta = mensagem.retornoMessege("Atenção", "Deseja realmente excluir a tarefa?");
            if (resposta == 1) {
                tarefaRepository.deleteByNome(nome);
                labelAvisosTarefas.setTextFill(Color.GREEN);
                labelAvisosTarefas.setText("Tarefa apagada com sucesso!");
            }
        }
    }
    public static void editarTarefa(Button botaoEditar,
                                    Button botaoSalvar,
                                    Button botaoExcluir,
                                    TarefaRepository tarefaRepository,
                                    Label nomeTarefa,
                                    TextField nome,
                                    TextArea descricao,
                                    DatePicker data,
                                    ChoiceBox<String> prioridade,
                                    ChoiceBox<String> categoria,
                                    Label labelAvisosTarefas) {
                Mensagem mensagem = new Mensagem();
                Tarefa tarefa = tarefaRepository.findByNomeAndId(nomeTarefa.getText(),ApplicationController.conta.getId());
                tarefa.setNome(nome.getText());
                tarefa.setDescricao(descricao.getText());
                tarefa.setData(data.getValue());
                tarefa.setPrioridade(prioridade.getValue());
                tarefa.setCategoria(categoria.getValue());
                ValidacaoDados.validarDataInserida(data,labelAvisosTarefas);
                if (tarefa.getNome().isEmpty()  || tarefa.getPrioridade().isEmpty() || tarefa.getCategoria().isEmpty()) {
                    labelAvisosTarefas.setTextFill(Color.RED);
                    labelAvisosTarefas.setText("Preencha todos os campos!");
                    throw new TratadorDeErros("Preencha todos os campos!");
                } else {
                    try {
                        tarefaRepository.save(tarefa);
                        labelAvisosTarefas.setTextFill(Color.GREEN);
                        labelAvisosTarefas.setText("Tarefa editada com sucesso!");
                        TarefasUtil.mostrarEditarTarefa(descricao,nome, data, prioridade, categoria, botaoEditar, botaoExcluir, botaoSalvar, false);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }



    }


}
