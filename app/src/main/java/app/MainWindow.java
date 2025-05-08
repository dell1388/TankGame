package app;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import app.model.Country;
import app.model.VehicleStats;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;


public class MainWindow extends Application {

    @FXML
    public ImageView playButton;
    @FXML
    public ImageView quitButton;
    @FXML
    public ImageView creditsButton;
    @FXML
    public ImageView helpButton;    
    @FXML
    public ImageView newGameButton;
    @FXML  
    private ImageView menuBackgroundImage;
    @FXML
    private StackPane rootPane;
    @FXML
    private ImageView MenuKV1Turret;
    @FXML
    private ImageView MenuPanzer4Turret;
    @FXML
    private Text coordsText;
    // private Timeline timer;
    // private TanksGameWorld gameWorld = new TanksGameWorld().world();
    // private boolean animated;

    private Image creditsReleased = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/credits.png"));
    private Image creditsHovered = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/creditHover.png"));
    private Image creditsPressed = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/creditClicked.png"));

    private Image helpReleased = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/help.png"));
    private Image helpHovered = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/helpHovered.png"));
    private Image helpPressed = new Image(getClass().getResourceAsStream("/app/images/Buttons_and_Text/helpClicked.png"));

    final private Image playPressed = new Image(
            getClass().getResourceAsStream("images/Buttons_and_Text/PlayClicked.png"));
    final private Image playReleased = new Image(getClass().getResourceAsStream("images/Buttons_and_Text/Play.png"));
    final private Image playHovered = new Image(
            getClass().getResourceAsStream("images/Buttons_and_Text/PlayHovered.png"));

            final private Image newGamePressed = new Image(
                getClass().getResourceAsStream("images/Buttons_and_Text/NewGameClicked.png"));
        final private Image newGameReleased = new Image(getClass().getResourceAsStream("images/Buttons_and_Text/NewGame.png"));
        final private Image newGameHovered = new Image(
                getClass().getResourceAsStream("images/Buttons_and_Text/NewGameHovered.png"));
    

    final private Image quitPressed = new Image(
            getClass().getResourceAsStream("images/Buttons_and_Text/QuitClicked.png"));
    final private Image quitReleased = new Image(getClass().getResourceAsStream("images/Buttons_and_Text/Quit.png"));
    final private Image quitHovered = new Image(
            getClass().getResourceAsStream("images/Buttons_and_Text/QuitHovered.png"));

    final private Image background = new Image(getClass().getResourceAsStream("images/Buttons_and_Text/home.png"));
    final private Image KV1_Turret = new Image(
            getClass().getResourceAsStream("images/Tanks/KV-1/ww2_top_view_turret5.png"));
    final private Image Panzer4_Turret = new Image(
            getClass().getResourceAsStream("images/Tanks/Panzer 4/ww2_top_view_turret2.png"));
    private Media song = new Media(getClass().getResource("/app/music/ussr/ckatyusha.mp3").toString());


    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/app/MainWindow.fxml"));
        Scene scene = new Scene(loader.load(), 800, 600);
        primaryStage.setTitle("Tank Menu");
        primaryStage.setScene(scene);
        double aspectRatio = background.getWidth() / background.getHeight();
        primaryStage.setWidth(800);
        primaryStage.setHeight(800 / aspectRatio);
        primaryStage.widthProperty()
                .addListener((obs, oldWidth, newWidth) -> primaryStage.setHeight(newWidth.doubleValue() / aspectRatio));
        primaryStage.heightProperty().addListener(
                (obs, oldHeight, newHeight) -> primaryStage.setWidth(newHeight.doubleValue() * aspectRatio));
        primaryStage.show();
    }

    @FXML
    private void initialize() {
        loadMainMenu();
        playMusic(Country.BRITAIN);
        // readyClock();
        VehicleStats.initStats();
        new F3(rootPane, coordsText).setupF3Toggle();
        System.out.println("menuBackgroundImage: " + menuBackgroundImage);
        System.out.println("MenuKV1Turret: " + MenuKV1Turret);
        System.out.println("MenuPanzer4Turret: " + MenuPanzer4Turret);
        System.out.println("coordsText: " + coordsText);
        System.out.println("rootPane: " + rootPane);
        System.out.println("playButton: " + playButton);
        System.out.println("quitButton: " + quitButton);
        rootPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                List<double[]> pivots = Arrays.asList(
                        new double[] { MenuKV1Turret.getFitWidth() / 2, MenuKV1Turret.getFitHeight() * 0.7 },
                        new double[] { MenuPanzer4Turret.getFitWidth() / 2, MenuPanzer4Turret.getFitHeight() * 0.7 });
                setupMouseFollowing(newScene, coordsText, Arrays.asList(MenuKV1Turret, MenuPanzer4Turret), pivots);
            }
        });
    }

    private void setupMouseFollowing(Scene scene, Text coordsText, List<ImageView> turrets, List<double[]> pivots) {
        if (scene != null) {
            scene.setOnMouseMoved(event -> {
                if (coordsText != null) {
                    coordsText.setText(String.format("Mouse: X=%.1f, Y=%.1f", event.getX(), event.getY()));
                }
                for (int i = 0; i < turrets.size() && i < pivots.size(); i++) {
                    ImageView turret = turrets.get(i);
                    double[] pivot = pivots.get(i);
                    if (turret != null && pivot != null && pivot.length == 2) {
                        double pivotX = pivot[0];
                        double pivotY = pivot[1];
                        // Compute pivot in scene coordinates
                        double scenePivotX = turret.getBoundsInParent().getMinX() + pivotX;
                        double scenePivotY = turret.getBoundsInParent().getMinY() + pivotY;
                        // Calculate angle from pivot to mouse
                        double deltaX = (event.getX() - 150 - scenePivotX);
                        double deltaY = (event.getY() - 150 - scenePivotY);
                        double angle = Math.toDegrees(Math.atan2(deltaY, deltaX));
                        turret.setRotate(angle + 90);
                        // works basically only in full screen
                    }
                }
            });
        }
    }

    private void loadMainMenu() {
        // background image is loaing
        if (menuBackgroundImage.getImage() == null) {
            System.out.println("background image is null");
        }
        menuBackgroundImage.setSmooth(false);
        menuBackgroundImage.setImage(background);
        setupButton(playButton, playReleased, playHovered, playPressed, this::handlePlayClick);
        setupButton(newGameButton, newGameReleased, newGameHovered, newGamePressed, this::handleNewGameClick);
        setupButton(quitButton, quitReleased, quitHovered, quitPressed, this::handleQuitClick);
        setupButton(creditsButton, creditsReleased, creditsHovered, creditsPressed, this::handleCreditsClick);
        setupButton(helpButton, helpReleased, helpHovered, helpPressed, this::handleHelpClick);
    }

    private void setupButton(ImageView button, Image normal, Image hover, Image pressed,
            javafx.event.EventHandler<MouseEvent> clickHandler) {
        button.setImage(normal);
        button.setOnMouseEntered(event -> button.setImage(hover));
        button.setOnMouseExited(event -> button.setImage(normal));
        button.setOnMousePressed(event -> button.setImage(pressed));
        button.setOnMouseReleased(event -> button.setImage(hover));
        button.setOnMouseClicked(clickHandler);
    }

    private void handleHelpClick(MouseEvent event) {
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/Help.fxml")).load()));
            stage.setTitle("Help");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleCreditsClick(MouseEvent event) {
        try {
            Stage stage = (Stage) rootPane.getScene().getWindow();
            stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/Credits.fxml")).load()));
            stage.setTitle("Credits");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handlePlayClick(MouseEvent event) {
        try {
            Node source = (Node) event.getSource();
            System.out.println("Event source: " + source);
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Run.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Run.fxml"));
            System.out.println("Run.fxml path: " + getClass().getResource("/Run.fxml"));
            Parent runParent = loader.load();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene runScene = new Scene(runParent);
            stage.setScene(runScene);
            stage.setTitle("Tank Game");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load Run.fxml: " + e.getMessage());
        }
    }

    private void handleNewGameClick(MouseEvent event) {
        try {
            Node source = (Node) event.getSource();
            System.out.println("Event source: " + source);
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Run.fxml"));
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Run.fxml"));
            System.out.println("Run.fxml path: " + getClass().getResource("/Run.fxml"));
            Parent runParent = loader.load();
            Stage stage = (Stage) source.getScene().getWindow();
            Scene runScene = new Scene(runParent);
            stage.setScene(runScene);
            stage.setTitle("Tank Game");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Failed to load Run.fxml: " + e.getMessage());
        }
    }


    private void handleQuitClick(MouseEvent event) {
        System.out.println("quit game");
        javafx.application.Platform.exit();
    }

    // plays music based on a path selected by music picker method

    private void playMusic(Country c) {
        System.err.println("music picked");

        Media media = new Media(
                getClass().getResource("music/uk/tipperary.mp3").toString());
        System.err.println("1");
        MediaPlayer mediaPlayer = new MediaPlayer(song);
        System.err.println("2");
        mediaPlayer.play();
        System.err.println("3");
        mediaPlayer.setVolume(0.6);
        mediaPlayer.setOnEndOfMedia(() -> {
        // Loop when audio ends
        mediaPlayer.seek(mediaPlayer.getStartTime());
        mediaPlayer.play();
        });

    }

    // selects a song based on country or lack thereof and returns a string path
    //
    // private String musicPicker(Country c) {
    // int random = (int) (Math.random() * 4);
    // String path = "0";
    // Country country = c;
    // while (path.equals("0")) {
    // switch (country) {
    // case BRITAIN:
    // System.err.println("brits");
    // switch (random) {
    // case 0:
    // path = "music/uk/tipperary.mp3";
    // break;
    // case 1:
    // path = "music/uk/HeartOfOak.mp3";
    // break;
    // case 2:
    // path = "music/uk/ruleBritannia.mp3";
    // break;
    // case 3:
    // path = "music/uk/BlackAndTans.mp3";
    // break;
    // }
    // break;
    // case USA:
    // switch (random) {
    // case 0:
    // path = "music/usa/battleHymn.mp3";
    // break;
    // case 1:
    // path = "music/usa/yankeeDoodle.mp3";
    // break;
    // case 2:
    // path = "music/usa/GodBlessAmerica.mp3";
    // break;
    // case 3:
    // path = "music/usa/johnny.mp3";
    // break;
    // }
    // break;
    // case USSR:
    // switch (random) {
    // case 0:
    // path = "music/ussr/katyusha.mp3";
    // break;
    // case 1:
    // path = "music/ussr/smuglianka.mp3";
    // break;
    // case 2:
    // path = "music/ussr/whiteArmyBlackBaron.mp3";
    // break;
    // case 3:
    // path = "music/ussr/sacredWar.mp3";
    // break;
    // }
    // break;
    // case GERMANY:
    // switch (random) {
    // case 0:
    // path = "music/germany/Erika.mp3";
    // break;
    // case 1:
    // path = "music/germany/GermanyNationalAnthem.mp3";
    // break;
    // case 2:
    // path = "music/germany/LiliMarlene.mp3";
    // break;
    // case 3:
    // path = "music/germany/PrussianGlory.mp3";
    // break;
    // }
    // break;
    // default:
    // Country randCountry = Country.values()[(int) (Math.random() *
    // Country.values().length)];
    // c = randCountry;
    // break;
    // }
    // }
    // return path;
    // }

    /*
     * 
     * Moving section for the player (UI & Dynamic control)
     * 
     */

    // @Override

    public void updatePlayerPosition(double x, double y, double direction) {
        // Update UI elements (e.g., tank ImageView)
        // tankImageView.setLayoutX(x);
        // tankImageView.setLayoutY(y);
        // tankImageView.setRotate(direction);
    }

    public void keyTyped(KeyEvent e) {
        // switch (e.getKeyCode()) {
        // case W:
        // gameWorld.WMoving();
        // break;
        // case S:
        // gameWorld.SMoving();
        // break;
        // case A:
        // gameWorld.AMoving();
        // break;
        // case D:
        // gameWorld.DMoving();
        // break;
        // }
    }

    public void updatePlayerPosition() {
        // gameWorld.getPlayer().getX();
        // gameWorld.getPlayer().getY();
        // gameWorld.getPlayer().getDirection();

    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }
}
