package br.com.myegoo.app.myego.service.login;

import br.com.myegoo.app.myego.configuration.ApplicationConfiguration;
import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.repository.ApplicationConfigurationRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

public class CadastrarService {
    public static void cadastrar(TextField tfNomeCadastro,
                                 TextField tfNomeUsuarioCadastro,
                                 TextField tfSenhaCadastro,
                                 DatePicker dataPickerCadastro,
                                 SegurancaService segurancaService,
                                 Pane containerCadastro,
                                 Pane containerLogin,
                                 ApplicationConfigurationRepository applicationConfigurationRepository){
        Mensagem mensagem = new Mensagem();
        Conta conta = new Conta(tfNomeCadastro.getText(),tfNomeUsuarioCadastro.getText(),tfSenhaCadastro.getText(),dataPickerCadastro.getValue());
        String encryptedPassword = segurancaService.encryptPassword(conta.getSenha());
        conta.setSenha(encryptedPassword);
        try {
            segurancaService.salvarConta(conta);
            mensagem.showMessege("Sucesso", "Perfil criado com sucesso! acesse seu aplicativo", 2);
            ApplicationConfiguration applicationConfiguration = applicationConfigurationRepository.findById(1L).orElse(null);
            if (applicationConfiguration != null && applicationConfiguration.getPrimeiroAcesso()) {
                applicationConfiguration.setPrimeiroAcesso(false);
                applicationConfigurationRepository.save(applicationConfiguration);
            }
        } catch (Exception e) {
            mensagem.showMessege("Erro", "Erro ao salvar conta", 1);
            throw new RuntimeException("Erro ao salvar conta", e);
        }

        tfNomeCadastro.setText("");
        tfNomeUsuarioCadastro.setText("");
        tfSenhaCadastro.setText("");
        dataPickerCadastro.setValue(null);
        containerCadastro.setVisible(false);
        containerLogin.setVisible(true);

    }
}
