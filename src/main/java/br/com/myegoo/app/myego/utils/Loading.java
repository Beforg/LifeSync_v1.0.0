package br.com.myegoo.app.myego.utils;

import javafx.concurrent.Task;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * Indica que a tela principal carrega
 * task que chama a tela de carregamento em outra thread, para o usúario não pensar que travou
 * em caso de demora para carregar.
 */

public class Loading {
    private static Stage loadingStage;
    public static void telaDeLoading() {
        loadingStage = new Stage();
        loadingStage.initStyle(StageStyle.TRANSPARENT);
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setStyle("-fx-progress-color: #9494de");
        progressIndicator.setVisible(true);
        StackPane stackPane = new StackPane(progressIndicator);
        stackPane.setPrefSize(100, 100);
        loadingStage.setAlwaysOnTop(true);
        loadingStage.setScene(new Scene(stackPane));
        loadingStage.show();
    }
    public static void carregamentoTela(Pane containerLogin, Pane containerAplicacao, Pane containerCadastro) {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                Thread.sleep(3000);
                return null;
            }
        };
        task.setOnRunning(e -> Loading.telaDeLoading());
        task.setOnSucceeded(e -> {
            loadingStage.close();
            containerLogin.setVisible(false);
            containerAplicacao.setVisible(true);
            containerCadastro.setVisible(false);
        });
        new Thread(task).start();
    }
}
