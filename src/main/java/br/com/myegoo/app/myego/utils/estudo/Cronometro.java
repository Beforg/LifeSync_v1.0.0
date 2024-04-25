package br.com.myegoo.app.myego.utils.estudo;

import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class Cronometro {
    private static Integer tempo = 0;
    private static Integer tempoPomodoro = 0;
    private static Timeline timeline;
    private static Timeline timeLinePomodoro;
    private static Integer cicloAtual = 1;
    private static Integer playPause = 0;
    private static boolean emAndamento = false;
    private static boolean emAndamentoPomodoro = false;
    private static Integer limite = 1;

    private static final String STYLE_INTERVALO = "-fx-text-fill: rgba(125,125,243,0.54)";
    private static final String STYLE_INTERVALO_LONGO = "-fx-text-fill: #243581";
    private static final String STYLE_POMODORO = "-fx-text-fill: #ffffff";

    public static void iniciarCronometro(Label labelCronometro) {
        if (emAndamentoPomodoro) {
            timeline.stop();
        } else {

            timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> incrementarTempo(labelCronometro)));
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        }
    }
    public static void iniciarCronometroPomodoro(TextField campoTempoPomodoro,Label labelIndicadoraPomodoro,Label labelPomodoro, Integer tempoInicial,Integer tempoBreakCurto,Integer tempoBreakLongo, Integer quantidadeCiclos) throws InterruptedException {
        if (emAndamento) {
            Thread.sleep(700);
            timeLinePomodoro.play();
            } else {
                labelIndicadoraPomodoro.setText("Pomodoro #"+cicloAtual);
                tempoPomodoro = tempoInicial * 60;
                timeLinePomodoro = new Timeline(new KeyFrame(Duration.seconds(1), event -> decrementarTempoPomodoro(campoTempoPomodoro,labelIndicadoraPomodoro,labelPomodoro,tempoBreakCurto,tempoBreakLongo,quantidadeCiclos)));
                timeLinePomodoro.setCycleCount(Timeline.INDEFINITE);
                timeLinePomodoro.play();
            }
    }

    public static void pausarCronometro() {
        if (timeline != null) {
            timeline.pause();
        }
    }
    public static void pausarCronometroPomodoro() {
        if (timeLinePomodoro != null) {
            timeLinePomodoro.pause();
        }
    }
    public static void resetarCronometroPomodoro(Label labelIndicadorPomodoro,Label labelCronometro,Label labelPomodoro, Integer tempoConfigurado) {
        Mensagem mensagem = new Mensagem();
        int resposta = mensagem.retornoMessege("Atenção", "Deseja realmente reiniciar o estudo?");
        if (resposta == 1) {
            if (timeLinePomodoro != null) {
                timeLinePomodoro.stop();
                timeline.stop();
            }
            tempoPomodoro = tempoConfigurado * 60;
            tempo = 0;
            limite = 0;
            playPause =0;
            cicloAtual =1;
            atualizarCronometro(labelCronometro);
            atualizarCronometroPomodoro(labelPomodoro);
            labelIndicadorPomodoro.setText("Pomodoro #"+cicloAtual);
        }
    }
    public static void registraEfinalizaEstudo(Label labelCronometro, Label labelPomodoro, Integer tempoConfigurado) {
        if (timeLinePomodoro != null) {
            timeLinePomodoro.stop();
            timeline.stop();
            playPause =0;
            cicloAtual = 1;
            tempo = 0;
            tempoPomodoro = tempoConfigurado * 60;
            atualizarCronometro(labelCronometro);
            atualizarCronometroPomodoro(labelPomodoro);
        }
    }

    private static void incrementarTempo(Label labelCronometro) {
        tempo++;
        atualizarCronometro(labelCronometro);
    }

    private static void atualizarCronometro(Label labelCronometro) {
        int horas = tempo / 3600;
        int minutos = (tempo % 3600) / 60;
        int segundos = tempo % 60;
        labelCronometro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
    }
    private static void atualizarCronometroPomodoro(Label labelPomodoro) {
        int horas = tempoPomodoro / 3600;
        int minutos = tempoPomodoro / 60;
        int segundos = tempoPomodoro % 60;
        labelPomodoro.setText(String.format("%02d:%02d:%02d", horas,minutos, segundos));
    }
    
    private static void decrementarTempoPomodoro(TextField campoTempoPomodoro,Label labelIndicadoraPomodoro, Label labelPomodoro, Integer tempoBreakCurto, Integer tempoBreakLongo, Integer quantidadeCiclos) {
        if (playPause != 2) {
            if (tempoPomodoro == 0) {
                emAndamento = false;
                playPause++;
                timeline.stop();

                if (limite.equals(quantidadeCiclos)) {
                    labelPomodoro.setStyle(STYLE_INTERVALO_LONGO);
                    emAndamentoPomodoro = true;
                    tempoPomodoro = tempoBreakLongo * 60;
                    timeLinePomodoro.play();
                    emAndamento = true;
                    labelIndicadoraPomodoro.setText("Pomodoro #"+cicloAtual+" Concluído!, Break Longo!");
                    limite = 0;
                } else {
                    if (playPause != 2) {
                        labelPomodoro.setStyle(STYLE_INTERVALO);
                        tempoPomodoro = tempoBreakCurto * 60;
                        timeLinePomodoro.play();
                        emAndamentoPomodoro = true;
                        emAndamento = true;
                        labelIndicadoraPomodoro.setText("Pomodoro #"+cicloAtual+" Concluído!, Break Curto!");
                    }
                }

            } else {
                tempoPomodoro--;
                atualizarCronometroPomodoro(labelPomodoro);
            }
        } else {
            labelPomodoro.setStyle(STYLE_POMODORO);
            labelIndicadoraPomodoro.setText("Intervalo finalizado! próximo Pomodoro!");
            emAndamento = false;
            emAndamentoPomodoro = false;
            EstudosUtil.tempoInicialPomodoro(campoTempoPomodoro,labelPomodoro);
            timeLinePomodoro.stop();
            cicloAtual++;
            limite++;
            playPause = 0;
        }
    }
    public static void passarPomodoroAtual() {
        tempoPomodoro = 0;
    }

    public static void setEmAndamento(boolean emAndamento) {
        Cronometro.emAndamento = emAndamento;
    }

}
