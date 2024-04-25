package br.com.myegoo.app.myego.service.login;

import br.com.myegoo.app.myego.controller.ApplicationController;
import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.repository.ContaRepository;
import br.com.myegoo.app.myego.utils.Loading;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CheckLoginService {

    public static void checkLogin(ContaRepository contaRepository, TextField tfUserLogin, TextField tfPasswordLogin, SegurancaService segurancaService, Pane containerAplicacao, Pane containerLogin, Pane containerCadastro) {
        Mensagem mensagem = new Mensagem();
        Conta conta = contaRepository.findByNomeUsuario(tfUserLogin.getText());

        if(conta != null){
            if(segurancaService.checkPassword(tfPasswordLogin.getText(),conta.getSenha())){
                Loading.carregamentoTela(containerLogin, containerAplicacao, containerCadastro);
                ApplicationController.conta = conta;


            }else{
                mensagem.showMessege("Erro", "A senha inserida está incorreta", 1);
            }
        }else{
            mensagem.showMessege("Erro", "Usuário não encontrado.",1);
        }

    }


}
