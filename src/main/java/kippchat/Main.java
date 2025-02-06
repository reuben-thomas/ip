package kippchat;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import kipp.Kipp;

/**
 * A GUI application for Kipp using FXML.
 */
public class Main extends Application {

    private Kipp kipp = new Kipp();

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/view/MainWindow.fxml"));
            AnchorPane ap = fxmlLoader.load();
            Scene scene = new Scene(ap);
            stage.setScene(scene);
            fxmlLoader.<MainWindow>getController().setKipp(kipp);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
