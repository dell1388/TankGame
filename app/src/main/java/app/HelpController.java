package app;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.media.*;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import javafx.scene.control.Button;
import javafx.scene.control.Label;


public class HelpController {

    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView menuBackgroundImage;

    private Image selection = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/Selection.png"));
    private Image quitReleased = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/quit.png"));
    private Image quitHovered = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/quitHovered.png"));
    private Image quitPressed = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/quitClicked.png"));

    @FXML
    private void initialize() {
        menuBackgroundImage.fitWidthProperty().bind(rootPane.widthProperty());
        menuBackgroundImage.fitHeightProperty().bind(rootPane.heightProperty());

        Label helpdescr = new Label("After clicking play, you will spawn as a tank in the middle of the map. /n Use WASD to move around and space to shoot at your cursor. /n Shoot at other tanks to gain score, and don't get hit. You have three lives.");
        ImageView selectionView = new ImageView(selection);
        ImageView quitButton = new ImageView(quitReleased);

        helpdescr.setTranslateY(-45);
        quitButton.setTranslateY(45);
        quitButton.setOnMouseEntered(e -> quitButton.setImage(quitHovered));
        quitButton.setOnMouseExited(e -> quitButton.setImage(quitReleased));
        quitButton.setOnMousePressed(e -> quitButton.setImage(quitPressed));
        quitButton.setOnMouseReleased(e -> quitButton.setImage(quitHovered));
        quitButton.setOnMouseClicked(this::handleQuitClick);

        rootPane.getChildren().addAll(selectionView, quitButton, helpdescr);
    }

    private void handleQuitClick(MouseEvent event) {
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            Scene scene = new Scene(new FXMLLoader(getClass().getResource("/MainWindow.fxml")).load());
            stage.setScene(scene);
            stage.setTitle("Tank Game");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}