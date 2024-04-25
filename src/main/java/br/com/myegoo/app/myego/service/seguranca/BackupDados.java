package br.com.myegoo.app.myego.service.seguranca;

import br.com.myegoo.app.myego.service.CarregaDadosService;
import br.com.myegoo.app.myego.utils.Mensagem;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.List;

public class BackupDados {
    private static final String DB_URL = "jdbc:hsqldb:file:src/main/resources/data/mydb";
    private static final String DB_USER = "SA";
    private static final String DB_PASSWORD = "";

    public static void backup() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Salvar Backup");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de Backup", "*.tar.gz"));
        fileChooser.setInitialFileName("backup.tar.gz");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File selectedFile = fileChooser.showSaveDialog(new Stage());

        if (selectedFile != null) {
            try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
                 Statement stmt = conn.createStatement()) {

                stmt.executeUpdate("BACKUP DATABASE TO '" + selectedFile.getAbsolutePath() + "' SCRIPT");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void carregaBackup(Pane pane) {
        Mensagem mensagem = new Mensagem();
        int retorno = mensagem.retornoMessege("Aviso", "A base de dados será substituida,a aplicação vai ser finalizada quando o processo for concluído. deseja continuar?");
        if (retorno == 1) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Carregar Backup");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Arquivo de Backup", "*.script"));
            File selectedFile = fileChooser.showOpenDialog(new Stage());

            if (selectedFile != null) {
                try {
                    Path source = Paths.get(selectedFile.getAbsolutePath());
                    Path target = Paths.get("src/main/resources/data/mydb.script");

                    Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
                    Stage stage = (Stage) pane.getScene().getWindow();
                    stage.close();
                    CarregaDadosService.shutdown();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
