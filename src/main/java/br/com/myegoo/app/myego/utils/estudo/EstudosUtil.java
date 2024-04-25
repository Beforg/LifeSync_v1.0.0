package br.com.myegoo.app.myego.utils.estudo;

import br.com.myegoo.app.myego.interfaces.IPadraoDaData;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import org.w3c.dom.Text;

import java.io.File;
import java.util.Map;
import java.util.concurrent.Flow;

public class EstudosUtil implements IPadraoDaData {
    public static void editarPomodoro(Button btSalvarConfiguracaoPomodoro, Button btAumentar, Button btDiminuir, TextField campoTempoPomodoro, TextField campoPomodoroCurto, TextField campoPomodoroLongo, TextField quantidadePomodoro , boolean v) {
        btSalvarConfiguracaoPomodoro.setVisible(v);
        campoTempoPomodoro.setDisable(!v);
        campoPomodoroCurto.setDisable(!v);
        campoPomodoroLongo.setDisable(!v);
        quantidadePomodoro.setDisable(!v);
        btAumentar.setDisable(!v);
        btDiminuir.setDisable(!v);
    }
    public static void aumentarPomodoro(TextField campoQuantidadePomodoro, int quantidade) {
        int valor = Integer.parseInt(campoQuantidadePomodoro.getText());
        valor += quantidade;
        campoQuantidadePomodoro.setText(String.valueOf(valor));
    }
    public static void diminuirPomodoro(TextField campoQuantidadePomodoro, int quantidade) {
        int valor = Integer.parseInt(campoQuantidadePomodoro.getText());
        valor -= quantidade;
        campoQuantidadePomodoro.setText(String.valueOf(valor));
    }
    public static void tempoInicialPomodoro(TextField campoTempoPomodoro, Label labelPomodoro) {
        int tempo = Integer.parseInt(campoTempoPomodoro.getText())*60;
        int horas = tempo / 3600;
        int minutos = (tempo % 3600) / 60;
        int segundos = tempo % 60;
        labelPomodoro.setText(String.format("%02d:%02d:%02d", horas, minutos, segundos));
    }
    public static void botoesEstudos(Button btPlay, Button btPause, Button btReset, Button btPassar) {
        String PATH_PLAY ="src/main/resources/images/estudos/play.png";
        String PATH_PAUSE = "src/main/resources/images/estudos/pause.png";
        String PATH_RESET = "src/main/resources/images/estudos/reset.png";
        String PATH_PASSAR = "src/main/resources/images/estudos/proximo.png";
        String PATH_PLAY_PRESSED = "src/main/resources/images/estudos/play_pressed.png";
        String PATH_PAUSE_PRESSED = "src/main/resources/images/estudos/pause_pressed.png";
        String PATH_RESET_PRESSED = "src/main/resources/images/estudos/reset_pressed.png";
        String PATH_PASSAR_PRESSED = "src/main/resources/images/estudos/proximo_pressed.png";

        Image play = new Image(new File(PATH_PLAY).toURI().toString());
        Image pause = new Image(new File(PATH_PAUSE).toURI().toString());
        Image reset = new Image(new File(PATH_RESET).toURI().toString());
        Image passar = new Image(new File(PATH_PASSAR).toURI().toString());
        Image playPressed = new Image(new File(PATH_PLAY_PRESSED).toURI().toString());
        Image pausePressed = new Image(new File(PATH_PAUSE_PRESSED).toURI().toString());
        Image resetPressed = new Image(new File(PATH_RESET_PRESSED).toURI().toString());
        Image passarPressed = new Image(new File(PATH_PASSAR_PRESSED).toURI().toString());

        ImageView playView = new ImageView(play);
        playView.setFitHeight(20);
        playView.setFitWidth(20);

        ImageView pauseView = new ImageView(pause);
        pauseView.setFitHeight(20);
        pauseView.setFitWidth(20);

        ImageView resetView = new ImageView(reset);
        resetView.setFitHeight(20);
        resetView.setFitWidth(20);

        ImageView passarView = new ImageView(passar);
        passarView.setFitHeight(20);
        passarView.setFitWidth(20);

        btPlay.setGraphic(playView);
        btPause.setGraphic(pauseView);
        btReset.setGraphic(resetView);
        btPassar.setGraphic(passarView);

        ImageView playPressedView = new ImageView(playPressed);
        playPressedView.setFitHeight(20);
        playPressedView.setFitWidth(20);

        ImageView pausePressedView = new ImageView(pausePressed);
        pausePressedView.setFitHeight(20);
        pausePressedView.setFitWidth(20);

        ImageView resetPressedView = new ImageView(resetPressed);
        resetPressedView.setFitHeight(20);
        resetPressedView.setFitWidth(20);

        ImageView passarPressedView = new ImageView(passarPressed);
        passarPressedView.setFitHeight(20);
        passarPressedView.setFitWidth(20);


        btPlay.setOnMousePressed(e -> btPlay.setGraphic(playPressedView));
        btPlay.setOnMouseReleased(e -> btPlay.setGraphic(playView));
        btPause.setOnMousePressed(e -> btPause.setGraphic(pausePressedView));
        btPause.setOnMouseReleased(e -> btPause.setGraphic(pauseView));
        btReset.setOnMousePressed(e -> btReset.setGraphic(resetPressedView));
        btReset.setOnMouseReleased(e -> btReset.setGraphic(resetView));
        btPassar.setOnMousePressed(e -> btPassar.setGraphic(passarPressedView));
        btPassar.setOnMouseReleased(e -> btPassar.setGraphic(passarView));


    }
    public static void mostraPainelMateria(FlowPane flowPaneInfosEstudos, GridPane gridPaneRegistroManual, boolean f, boolean g) {
        flowPaneInfosEstudos.setVisible(f);
        gridPaneRegistroManual.setVisible(g);
    }
    public static int confirmarRegistroManual() {
        Mensagem mensagem = new Mensagem();
        return mensagem.retornoMessege("Confirmação", "Confirmar registro de estudo");
    }
    public static int confirmarRegistroCronometro() {
        Mensagem mensagem = new Mensagem();
        return mensagem.retornoMessege("Confirmação", "Finalizar tempo e registrar estudo?");

    }
    public static void erroRegistroCronometro() {
        Mensagem mensagem = new Mensagem();
        mensagem.showMessege("Erro", "Cronometro não iniciado ou campos não preenchidos!",1);
    }
    public static void escondeBotoesEditarMateria(Label labelTipoMinhasMaterias,
                                                  ChoiceBox<String> choiceBoxEditaTipoMateria,
                                                  TextField tfEditaMinhaMateria,
                                                  ChoiceBox<String> choiceBoxMinhasMaterias,
                                                  Button btEditar,
                                                  Button btSalvarEdit,
                                                  Button btVoltarEdit,
                                                  Button btExcluir,
                                                  Button btNovaMateria,
                                                  boolean b) {
        labelTipoMinhasMaterias.setVisible(b);
        btEditar.setVisible(b);
        btNovaMateria.setVisible(b);
        btExcluir.setVisible(b);
        btSalvarEdit.setVisible(!b);
        btVoltarEdit.setVisible(!b);
        choiceBoxEditaTipoMateria.setVisible(!b);
        tfEditaMinhaMateria.setVisible(!b);
        choiceBoxMinhasMaterias.setVisible(b);

    }
    public static boolean validarFormadoDoTempo(String tempo) {
        boolean valido;
        String minutos = tempo.substring(3,5);
        String segundos = tempo.substring(6,8);
        if (Integer.parseInt(minutos) > 59 || Integer.parseInt(segundos) > 59) {
            valido = false;
            erroFormatoTempo();
        } else {
            valido = true;
        }
        return valido;
    }
    public static void erroFormatoTempo() {
        Mensagem mensagem = new Mensagem();
        mensagem.showMessege("Erro", "Formato de tempo inválido!",1);
    }
}
