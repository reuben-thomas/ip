package kippchat;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import kipp.Kipp;

/**
 * Controller for the main GUI.
 */
public class MainWindow extends AnchorPane {
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private VBox dialogContainer;
    @FXML
    private TextField userInput;
    @FXML
    private Button sendButton;

    private Kipp kipp;

    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/dr_mann.png"));
    private Image kippImage = new Image(this.getClass().getResourceAsStream("/images/kipp.png"));

    /**
     * Initializes the main window.
     */
    @FXML
    public void initialize() {
        scrollPane.vvalueProperty().bind(dialogContainer.heightProperty());

        dialogContainer.getChildren().addAll(
                DialogBox.getKippDialog("Hi there!", kippImage)
        );
    }

    public void setKipp(Kipp k) {
        this.kipp = k;
    }

    @FXML
    private void handleUserInput() {
        String input = userInput.getText();
        String response = kipp.getResponse(input);
        dialogContainer.getChildren().addAll(
                DialogBox.getUserDialog(input, userImage),
                DialogBox.getKippDialog(response, kippImage)
        );
        userInput.clear();

        if (input.equals("bye")) {
            PauseTransition delay = new PauseTransition(javafx.util.Duration.seconds(3));
            delay.setOnFinished(event -> {
                Platform.exit();
            });
            delay.play();
        }
    }
}
