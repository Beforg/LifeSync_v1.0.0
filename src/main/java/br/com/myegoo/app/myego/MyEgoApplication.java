package br.com.myegoo.app.myego;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Objects;

@SpringBootApplication
public class MyEgoApplication extends Application {

    private ConfigurableApplicationContext context;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void init() throws Exception {
        String[] args = getParameters().getRaw().toArray(new String[0]);

        this.context = new SpringApplicationBuilder()
                .sources(MyEgoApplication.class).run(args);
    }

    @Override
    public void stop() throws Exception {
        this.context.close();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/main.fxml"));
        Image image = new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/ico/appicon.png")));
        fxmlLoader.setControllerFactory(this.context::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("LifeSync");
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(image);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(event -> System.exit(0));
        primaryStage.show();

    }
}
