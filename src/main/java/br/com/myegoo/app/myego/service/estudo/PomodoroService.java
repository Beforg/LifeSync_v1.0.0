package br.com.myegoo.app.myego.service.estudo;

import br.com.myegoo.app.myego.model.estudo.Pomodoro;
import br.com.myegoo.app.myego.repository.PomodoroRepository;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class PomodoroService {
    public static void salvaConfiguracaoPomodoro(TextField campoTempoPomodoro,
                                                 TextField campoPomodoroCurto,
                                                 TextField campoPomodoroLongo,
                                                 TextField quantidadePomodoro,
                                                 PomodoroRepository pomodoroRepository) {
        Mensagem mensagem = new Mensagem();
        if (campoTempoPomodoro.getText().isEmpty() || campoPomodoroCurto.getText().isEmpty() || campoPomodoroLongo.getText().isEmpty() || quantidadePomodoro.getText().isEmpty()) {
            mensagem.showMessege("Erro", "Preencha todos os campos!", 1);
        } else {
            Pomodoro pomodoro = pomodoroRepository.findById(1L).get();
            pomodoro.setTempoPomodoro(Integer.parseInt(campoTempoPomodoro.getText()));
            pomodoro.setTempoBreakCurto(Integer.parseInt(campoPomodoroCurto.getText()));
            pomodoro.setTempoBreakLongo(Integer.parseInt(campoPomodoroLongo.getText()));
            pomodoro.setQuantidade(Integer.parseInt(quantidadePomodoro.getText()));
            pomodoroRepository.save(pomodoro);
            mensagem.showMessege("Sucesso", "Configuração salva com sucesso!", 2);
        }

    }
    public static void carregaConfiguracaoPomodoro(Label labelPomodoro,
                                                   TextField campoTempoPomodoro,
                                                   TextField campoPomodoroCurto,
                                                   TextField campoPomodoroLongo,
                                                   TextField quantidadePomodoro,
                                                   PomodoroRepository pomodoroRepository) {
        Pomodoro pomodoro = pomodoroRepository.findById(1L).get();
        campoTempoPomodoro.setText(String.valueOf(pomodoro.getTempoPomodoro()));
        campoPomodoroCurto.setText(String.valueOf(pomodoro.getTempoBreakCurto()));
        campoPomodoroLongo.setText(String.valueOf(pomodoro.getTempoBreakLongo()));
        quantidadePomodoro.setText(String.valueOf(pomodoro.getQuantidade()));

        int tempo = Integer.parseInt(campoTempoPomodoro.getText())*60;
        int horas = tempo / 3600;
        int minutos = (tempo % 3600) / 60;
        int segundos = tempo % 60;
        labelPomodoro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
    }
}
