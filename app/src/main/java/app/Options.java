package app;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Options {

    private StackPane rootPane;
    private ImageView overlay;
    private final boolean[] showOptions = { false };
    
    final private Image playPressed = new Image(getClass().getResourceAsStream("images/Buttons_and_Text/NewGameClicked.png"));
    final private Image playReleased = new Image(getClass().getResourceAsStream("images/Buttons_and_Text/NewGame.png"));
    final private Image playHovered = new Image(getClass().getResourceAsStream("images/Buttons_and_Text/NewGameHovered.png"));

    public void Options(StackPane rootPane, ImageView overlay) {
        this.rootPane = rootPane;
        this.overlay = overlay;
    }
    public void setupOptionsToggle() {
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.ESCAPE) {
                        showOptions[0] = !showOptions[0];
                        if (overlay != null) {
                            overlay.setVisible(showOptions[0]);
                        }
                    }
                });
            }
        });
    }
}