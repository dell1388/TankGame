package app;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class F3 {
    private final StackPane rootPane;
    private final Text coordsText;
    private final boolean[] showCoords = { false };

    public F3(StackPane rootPane, Text coordsText) {
        this.rootPane = rootPane;
        this.coordsText = coordsText;
    }

    public void setupF3Toggle() {
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    if (event.getCode() == KeyCode.F3) {
                        showCoords[0] = !showCoords[0];
                        if (coordsText != null) {
                            coordsText.setVisible(showCoords[0]);
                        }
                    }
                });
            }
        });
    }
}