package br.com.myegoo.app.myego.service;

import br.com.myegoo.app.myego.model.Categoria;
import br.com.myegoo.app.myego.model.financas.Financa;
import br.com.myegoo.app.myego.model.financas.RegistroFinanca;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.FinancaRepository;
import br.com.myegoo.app.myego.repository.RegistroFinancaRepository;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class FinancaService {
    public static void registrarRenda(TextField tfInserirRenda,
                                      FinancaRepository financaRepository,
                                      Label labelAvisosFinancas) {

        Financa financa = financaRepository.findById(1L).get();
        financa.setRenda(new BigDecimal(tfInserirRenda.getText().replace(",",".")));
        financa.setSaldo(financa.getRenda().subtract(financa.getGastos()).subtract(financa.getReserva()));
        try {
            financaRepository.save(financa);
            labelAvisosFinancas.setTextFill(Color.GREEN);
            labelAvisosFinancas.setText("Renda registrada com sucesso!");
        } catch (Exception e) {
            labelAvisosFinancas.setTextFill(Color.RED);
            labelAvisosFinancas.setText("Erro ao registrar renda!");
            throw new RuntimeException("Erro ao registrar renda!");
        }
    }
    public static void registrarGastoNaTabela(RegistroFinancaRepository registroFinancaRepository,
                                              ChoiceBox<String> cbCategoriaFinanca,
                                              TextField tfNovaCategoriaFinanca,
                                              TextField tfValorGasto,
                                              TextField tfDescricaoFinanca,
                                              DatePicker dpDataDoRegistro,
                                              CategoriaRepository categoriaRepository,
                                              FinancaRepository financaRepository,
                                              Label labelAvisosFinancas,
                                              RadioButton rbNovaCategoriaFinanca) {
        Financa financa = financaRepository.findById(1L).get();
        if (tfValorGasto.getText().isEmpty() || tfDescricaoFinanca.getText().isEmpty() || dpDataDoRegistro.getValue() == null) {
            labelAvisosFinancas.setTextFill(Color.RED);
            labelAvisosFinancas.setText("Preencha todos os campos!");
            throw new RuntimeException("Preencha todos os campos!");
        }
        if (rbNovaCategoriaFinanca.isSelected()) {
            if (tfNovaCategoriaFinanca.getText().isEmpty()) {
                labelAvisosFinancas.setTextFill(Color.RED);
                labelAvisosFinancas.setText("Escolha uma categoria!");
                throw new RuntimeException("Escolha uma categoria!");
            }
            RegistroFinanca registroFinanca = new RegistroFinanca(tfDescricaoFinanca.getText(),tfNovaCategoriaFinanca.getText(),
                    new BigDecimal(tfValorGasto.getText().replace(",",".")), dpDataDoRegistro.getValue());
            registroFinanca.setFinanca(financa);
            Categoria categoria = new Categoria(tfNovaCategoriaFinanca.getText(),"Finanças");
            categoriaRepository.save(categoria);
            registroFinancaRepository.save(registroFinanca);
            labelAvisosFinancas.setTextFill(Color.GREEN);
            tfNovaCategoriaFinanca.setText("");
            tfNovaCategoriaFinanca.setVisible(false);
            rbNovaCategoriaFinanca.setSelected(false);
            cbCategoriaFinanca.setVisible(true);
            labelAvisosFinancas.setText("Gasto registrado com sucesso!");
        } else {
            if (cbCategoriaFinanca.getValue() == null) {
                labelAvisosFinancas.setTextFill(Color.RED);
                labelAvisosFinancas.setText("Escolha uma categoria!");
                throw new RuntimeException("Escolha uma categoria!");
            }
            RegistroFinanca registroFinanca = new RegistroFinanca(tfDescricaoFinanca.getText(),cbCategoriaFinanca.getValue(),
                    new BigDecimal(tfValorGasto.getText().replace(",",".")), dpDataDoRegistro.getValue());
            registroFinanca.setFinanca(financa);
            registroFinancaRepository.save(registroFinanca);
            labelAvisosFinancas.setTextFill(Color.GREEN);
            labelAvisosFinancas.setText("Gasto registrado com sucesso!");
        }
    }
}
