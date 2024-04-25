package br.com.myegoo.app.myego.service.login;

import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.repository.ContaRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Service;

@Service
public class AlteraInfosService {

    public static void alteraSenha(PasswordField pfSenhaAtual,
                                   PasswordField pfNovaSenha,
                                   ContaRepository contaRepository,
                                   SegurancaService segurancaService) {
        Mensagem mensagem = new Mensagem();
        Conta conta = contaRepository.findById(10L).get();
        System.out.println(conta.getNome_usuario());
        if (segurancaService.checkPassword(pfSenhaAtual.getText(), conta.getSenha())) {
            int retorno = mensagem.retornoMessege("Trocar senha", "Deseja realmente trocar a senha?");
            if (retorno == 1) {
                String senhaCriptografada = segurancaService.encryptPassword(pfNovaSenha.getText());
                conta.setSenha(senhaCriptografada);
                contaRepository.save(conta);
                mensagem.showMessege("Sucesso", "Senha alterada com sucesso", 2);
                pfSenhaAtual.setText("");
                pfNovaSenha.setText("");
            } else {
                pfSenhaAtual.setText("");
                pfNovaSenha.setText("");
            }
        } else {
            mensagem.showMessege("Erro", "Senha atual incorreta", 1);
            throw new RuntimeException("Senha atual incorreta");
        }
    }
    public static void alteraNomeUsuario(TextField tfNomeUsuarioAtual,
                                         PasswordField senhaTrocaUser,
                                         ContaRepository contaRepository,
                                         SegurancaService segurancaService) {
        Mensagem mensagem = new Mensagem();
        Conta conta = contaRepository.findById(10L).get();
        if (segurancaService.checkPassword(senhaTrocaUser.getText(), conta.getSenha())) {
            int retorno = mensagem.retornoMessege("Trocar nome de usuário","Deseja realmente trocar o nome de usuário?");
            if (retorno == 1) {
                conta.setNome_usuario(tfNomeUsuarioAtual.getText());
                contaRepository.save(conta);
                mensagem.showMessege("Sucesso", "Nome de usuário alterado com sucesso", 2);
                tfNomeUsuarioAtual.setText("");
                senhaTrocaUser.setText("");
            } else {
                tfNomeUsuarioAtual.setText("");
                senhaTrocaUser.setText("");
            }
        } else {
            mensagem.showMessege("Erro", "Senha incorreta", 1);
            throw new RuntimeException("Senha incorreta");

        }

    }
    public static void ateraDadosDoUsuario(TextField tfAlteraNomeDados,
                                           DatePicker dpAlteraDataDados,
                                           ContaRepository contaRepository) {
        Mensagem mensagem = new Mensagem();
        Conta conta = contaRepository.findById(10L).get();
        conta.setNome(tfAlteraNomeDados.getText());
        conta.setDataNascimento(dpAlteraDataDados.getValue());
        contaRepository.save(conta);
        mensagem.showMessege("Sucesso", "Dados alterados com sucesso", 2);
        tfAlteraNomeDados.setText("");
        dpAlteraDataDados.setValue(null);
    }
}
