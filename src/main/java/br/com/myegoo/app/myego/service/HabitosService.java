package br.com.myegoo.app.myego.service;

import br.com.myegoo.app.myego.model.Habitos;
import br.com.myegoo.app.myego.repository.CategoriaRepository;
import br.com.myegoo.app.myego.repository.HabitosRepository;
import javafx.scene.control.*;
import javafx.scene.paint.Color;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class HabitosService {
    public static void criarHabito(Label labelAvisosHabitos,
                                   DatePicker datePicker,
                                   TextField textField,
                                   ChoiceBox<String> cbEscolha,
                                   CategoriaRepository categoriaRepository,
                                   HabitosRepository habitosRepository,
                                   RadioButton radioButton) {
        Habitos verificaSeExiste = habitosRepository.findByNome(cbEscolha.getValue(),datePicker.getValue());
        if (verificaSeExiste != null) {
            labelAvisosHabitos.setTextFill(Color.RED);
            labelAvisosHabitos.setText("Hábito já cadastrado para o dia " + datePicker.getValue());
            throw new RuntimeException("Hábito já cadastrado para o dia " + datePicker.getValue());
        }

        if (radioButton.isSelected()) {
            Habitos habitos = new Habitos(textField.getText(),false,datePicker.getValue());
          try {
              if (textField.getText().isEmpty()) {
                  labelAvisosHabitos.setTextFill(Color.RED);
                  labelAvisosHabitos.setText("O campo de texto não pode estar vazio");
                  throw new RuntimeException("O campo de texto não pode estar vazio");
              }
                CategoriaService.salvarCategoria(categoriaRepository,textField,"Hábitos");
                habitosRepository.save(habitos);
                labelAvisosHabitos.setTextFill(Color.GREEN);
                labelAvisosHabitos.setText("Hábito " + textField.getText() + "para o dia " + datePicker.getValue() + " adicionado com sucesso");
            } catch (RuntimeException ex) {
              labelAvisosHabitos.setTextFill(Color.RED);
                labelAvisosHabitos.setText("Erro ao criar o hábito");
                throw new RuntimeException(ex.getMessage());
            }
        } else {
            Habitos habitos = new Habitos(cbEscolha.getValue(),false,datePicker.getValue());
            try {
                if (cbEscolha.getValue() == null || datePicker.getValue() == null) {
                    labelAvisosHabitos.setText("Preencha os campos!");
                    throw new RuntimeException("O campo de texto não pode estar vazio");
                }
                habitosRepository.save(habitos);
                labelAvisosHabitos.setTextFill(Color.GREEN);
                labelAvisosHabitos.setText("Hábito " + cbEscolha.getValue() + " no dia " + datePicker.getValue()+ " adicionado com sucesso");
            } catch (RuntimeException ex) {
                labelAvisosHabitos.setTextFill(Color.RED);
                labelAvisosHabitos.setText("Erro ao criar o hábito, verifique se os campos estão preenchidos.");
                throw new RuntimeException(ex.getMessage());
            }
        }
    }
    public static void removerHabito(Label labelAvisosHabitos,
                                     HabitosRepository habitosRepository,
                                     ListView<String> listaHabitos,
                                     Label guardaDataHabito) {
        LocalDate data = LocalDate.parse(guardaDataHabito.getText());
        try {
            habitosRepository.deleteByNome(listaHabitos.getSelectionModel().getSelectedItem(),data);
            labelAvisosHabitos.setTextFill(Color.GREEN);
            labelAvisosHabitos.setText("Hábito removido com sucesso");
        } catch (RuntimeException ex) {
            labelAvisosHabitos.setTextFill(Color.RED);
            labelAvisosHabitos.setText("Erro ao remover o hábito");
            throw new RuntimeException(ex.getMessage());
        }
    }
}
