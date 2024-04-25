package br.com.myegoo.app.myego.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.InputStream;
import java.util.Objects;
import java.util.Optional;

public class Mensagem {
    String pathError = "/images/ico/erro.png";
    String pathDone = "/images/ico/done.png";
    String pathInfo = "/images/ico/info.png";

    public void showMessege(String title, String message, int type){

        String css = Mensagem.class.getResource("/styles/alert.css").toExternalForm();


        Alert alert = new Alert(javafx.scene.control.Alert.AlertType.NONE);
        alert.getDialogPane().getStylesheets().add(css);

        alert.setTitle(title);
        alert.setContentText(message);
        if(type == 1){
            alert.setAlertType(javafx.scene.control.Alert.AlertType.ERROR);
            setImage(alert, pathError);
            alert.setGraphic(setImageIcon(pathError));
        }else if(type == 2){
            alert.setAlertType(javafx.scene.control.Alert.AlertType.INFORMATION);
            setImage(alert, pathDone);
            alert.setGraphic(setImageIcon(pathDone));
        } else if(type == 3){
            alert.setAlertType(javafx.scene.control.Alert.AlertType.WARNING);
            setImage(alert, pathInfo);
            alert.setGraphic(setImageIcon(pathInfo));
        } else if(type == 4){
            alert.setAlertType(javafx.scene.control.Alert.AlertType.CONFIRMATION);
            setImage(alert, pathInfo);
            alert.setGraphic(setImageIcon(pathInfo));
        }
        alert.show();
    }
    public int retornoMessege(String title, String messege) {
        String css = Mensagem.class.getResource("/styles/alert.css").toExternalForm();
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.getDialogPane().getStylesheets().add(css);
        setImage(alert, pathInfo);
        alert.setGraphic(setImageIcon(pathInfo));
        alert.setTitle(title);
        alert.setHeaderText("Confirmação");
        alert.setContentText(messege);
        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            return 1;
        } else {
            return 0;
        }
    }
    public void setImage(Alert alert, String path){
        InputStream stream = Mensagem.class.getResourceAsStream(path);
        if (stream == null) {
            throw new IllegalArgumentException("File not found: " + path);
        }
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(115);
        alert.setGraphic(imageView);
    }
    public ImageView setImageIcon(String path){
        InputStream stream = Mensagem.class.getResourceAsStream(path);
        if (stream == null) {
            throw new IllegalArgumentException("File not found: " + path);
        }
        Image image = new Image(stream);
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(115);
        return imageView;
    }
}
