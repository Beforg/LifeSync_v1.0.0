package br.com.myegoo.app.myego.service.estudo;

import br.com.myegoo.app.myego.model.Conta;
import br.com.myegoo.app.myego.model.estudo.Estudos;
import br.com.myegoo.app.myego.model.estudo.RegistroEstudo;
import br.com.myegoo.app.myego.repository.EstudoRepository;
import br.com.myegoo.app.myego.repository.RegistroEstudosRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class RegistroEstudoService {

    public static void registrarEstudo(TextField nome,
                                TextArea descricao,
                                String tempoCronometro,
                                LocalDate date,
                                String tipoEstudo,
                                ChoiceBox<String> materia,
                                Conta conta,
                                RegistroEstudosRepository registroEstudosRepository,
                                EstudoRepository estudoRepository,
                                Label labelAvisosEstudos) {
        Estudos estudo = estudoRepository.findByNomeAndConta(materia.getValue());
        if (estudo != null) {
            RegistroEstudo registroEstudo = new RegistroEstudo();
            registroEstudo.setMateria(estudo.getNome());
            registroEstudo.setDescricao(descricao.getText());
            registroEstudo.setTempo(tempoCronometro);
            registroEstudo.setNomeConteudo(nome.getText());
            registroEstudo.setData(date);
            registroEstudo.setTipo(tipoEstudo);
            registroEstudo.setEstudo(estudo);


            registroEstudosRepository.save(registroEstudo);
            labelAvisosEstudos.setTextFill(Color.GREEN);
            labelAvisosEstudos.setText("Estudo registrado com sucesso!");
        }
    }
}
