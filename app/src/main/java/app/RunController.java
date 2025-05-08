package app;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import app.model.Bullet;
import app.model.Entity;
import app.model.Observer;
import app.model.TanksGameWorld;
import app.model.VehicleStats;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class RunController implements Observer {

    final private Image KV1_hull = new Image(
            getClass().getResourceAsStream("images/Tanks/KV-1/ww2_top_view_hull5.png"));
    private Image mapImage = new Image(getClass().getResource("/app/images/Buttons_and_Text/Map.png").toString());
    @FXML
    private StackPane gamePane;

    private ImageView gameBackgroundImage = new ImageView(mapImage);
    private ImageView playerTank = new ImageView(KV1_hull);
    private TanksGameWorld gameWorld = TanksGameWorld.instance();
    private double backgroundScrollSpeed = 0.5;
    private final double WINDOW_WIDTH = 1600;
    private final double WINDOW_HEIGHT = 900;
    private final double ZOOM_SCALE = 2.0;
    private double[] playerCoords = gameWorld.getPlayer().getCoords();
    private double[] viewShift, oldViewCoords;
    private Timeline timer;
    private Entity player = gameWorld.getPlayer();
    private Set<KeyCode> pressedKeys = new HashSet<>();
    private boolean animated;
    private Image bulletImage = new Image(getClass().getResource("/app/images/Enemies/bullet.png").toString());
    private long lastFire = 0;
    private long fireCD = 1000;
    private int score = 0;
    // @FXML
    private Label currentScore;
    private int startingLives = 3;
    private int remainingLives = startingLives;
    private ImageView turret = new ImageView(
            new Image(getClass().getResourceAsStream("images/Tanks/KV-1/ww2_top_view_turret5.png")));
    ImageView selection = new ImageView(
            getClass().getResource("/app/images/Buttons_and_Text/Selection.png").toString());
    ImageView options = new ImageView(
            getClass().getResource("/app/images/Buttons_and_Text/Options.png").toString());
    ImageView credits = new ImageView(
            getClass().getResource("/app/images/Buttons_and_Text/credits.png").toString());
    ImageView help = new ImageView(
            getClass().getResource("/app/images/Buttons_and_Text/help.png").toString());

    private Media tankShot = new Media(getClass().getResource("/app/music/shot.mp3").toString());
    private Media scream = new Media(getClass().getResource("/app/music/scream.mp3").toString());
    private Media scoreUp = new Media(getClass().getResource("/app/music/scoreUp.wav").toString());

    public boolean upPressed, downPressed, leftPressed, rightPressed, escPressed;

    private void readyClock() { // prepare clock for use in model
        var frame = new KeyFrame(Duration.millis(40), this::frameUpdate);
        timer = new Timeline(frame);
        timer.setCycleCount(Timeline.INDEFINITE);
    }

    private void frameUpdate(ActionEvent e) { // update the view to match the model
        TanksGameWorld.instance().tick();
        for (Entity entity : TanksGameWorld.instance().getEnemies()) {
            if (getEntityImageView(entity) != null) {

                placeEntity(getEntityImageView(entity), entity);

            } else {
                ImageView image = new ImageView(VehicleStats.vehicleStats.get(entity.getType()).complete());
                image.setUserData(entity);
                image.setTranslateX(entity.getCoords()[0]);
                image.setTranslateY(entity.getCoords()[1]);
                image.setRotate(entity.getDirection() + 90);
                gamePane.getChildren().add(image);
                // System.err.println(entity.getType() + " is " +
                // image.getBoundsInParent().getHeight() + " pixels tall");
            }
            if (!entity.isReloading())
                enemyShoot(entity);

        }
        try {
            for (Bullet bullet : gameWorld.getBullets()) {
                if (getBulletImageView(bullet) != null) {
                    placeBullet(getBulletImageView(bullet), bullet);
                } else {
                    ImageView image = new ImageView(bulletImage);
                    image.setUserData(bullet);
                    image.setTranslateX(bullet.getCoords()[0]);
                    image.setTranslateY(bullet.getCoords()[1]);
                    image.setRotate(bullet.getDirection() + 90);
                    gamePane.getChildren().add(image);
                }
                checkCollision(bullet);
                // System.err.println("checked bullet");
            }
        } catch (Exception er) {
        }

        double viewWidth = WINDOW_WIDTH / ZOOM_SCALE;
        double viewHeight = WINDOW_HEIGHT / ZOOM_SCALE;
        double viewX = playerCoords[0] - viewWidth / 2;
        double viewY = playerCoords[1] - viewHeight / 2;
        // player.adjust(1, 1);
        // gameWorld.getPlayer().setCoords(new double[] { 1000, 1000 });
        // playerCoords = new double[] { 900, 900 };
        try {
            viewShift = new double[] { -(viewX - oldViewCoords[0]), -(viewY - oldViewCoords[1]) };
        } catch (Exception er) {
        }
        viewX = Math.max(0, Math.min(viewX, mapImage.getWidth() - viewWidth));
        viewY = Math.max(0, Math.min(viewY, mapImage.getHeight() - viewHeight));
        // System.err.println(viewX + ", " + viewY);
        oldViewCoords = new double[] { viewX, viewY };
        gameBackgroundImage.setViewport(new Rectangle2D(viewX, viewY, viewWidth, viewHeight));
        gameBackgroundImage.setFitWidth(WINDOW_WIDTH);
        gameBackgroundImage.setFitHeight(WINDOW_HEIGHT);
        updatePosition();

    }

    private void enemyShoot(Entity entity) {
        entity.fire();
        Bullet b = new Bullet(12, entity.getDirection(),
                new double[] {
                        entity.getCoords()[0] + 0.7 * getEntityImageView(entity).getBoundsInParent().getHeight()
                                * Math.cos(Math.toRadians(entity.getDirection())),
                        entity.getCoords()[1] + 0.7 * getEntityImageView(entity).getBoundsInParent().getHeight()
                                * Math.sin(Math.toRadians(entity.getDirection())) });
        ImageView biv = new ImageView(bulletImage);
        biv.setUserData(b);
        gameWorld.getBullets().add(b);
        Thread shotSound = new Thread(() -> playShot());
        shotSound.start();
    }

    private void checkCollision(Bullet bullet) {
        try {
            for (Entity entity : TanksGameWorld.instance().getEnemies()) {
                if (getBulletImageView(bullet).getBoundsInParent()
                        .intersects(getEntityImageView(entity).getBoundsInParent())) {
                    gamePane.getChildren().remove(getBulletImageView(bullet));
                    gameWorld.getBullets().remove(bullet);
                    gameWorld.getEnemies().remove(entity);
                    gamePane.getChildren().remove(getEntityImageView(entity));
                    // System.err.println("collision found");
                    score += 1;
                    currentScore.setText("Score: " + score);
                    Thread scoreUpSound = new Thread(() -> scoreUp());
                    scoreUpSound.start();
                }
            }
        } catch (Exception e) {

        }
        if (getBulletImageView(bullet).getBoundsInParent()
                .intersects(playerTank.getBoundsInParent())) {
            playerHit();
            gamePane.getChildren().remove(getBulletImageView(bullet));
            gameWorld.getBullets().remove(bullet);

        }

    }

    private void playerHit() {
        remainingLives--;
        if (remainingLives == 0) {
            stopAnimate();
            Label end = new Label("Game Over");
            end.setStyle("-fx-font-size: 48px;" + // Large text size
                    "-fx-font-weight: bold;" + // Bold text
                    "-fx-text-fill: red;" + // Red color
                    "-fx-background-color: rgba(0, 0, 0, 0.7);" + // Semi-transparent black background
                    "-fx-padding: 10px;" + // Padding around text
                    "-fx-effect: dropshadow(gaussian, white, 10, 0.5, 0, 0);");
            gamePane.getChildren().add(end);
            Thread die = new Thread(() -> die());
            die.start();
        }
    }

    private ImageView getEntityImageView(Entity entity) { // get image for entity
        Optional<Node> optional = gamePane.getChildren().stream()
                .filter(c -> c.getUserData() == entity).findFirst();
        if (optional.isPresent())
            return (ImageView) optional.get();
        else {
            return null;
        }
    }

    private ImageView getBulletImageView(Bullet bullet) { // get image for bullet
        Optional<Node> optional = gamePane.getChildren().stream()
                .filter(c -> c.getUserData() == bullet).findFirst();
        if (optional.isPresent())
            return (ImageView) optional.get();
        else {
            return null;
        }
    }

    private void placeEntity(ImageView image, Entity entity) { // move entity
        gamePane.getChildren().remove(image);
        image.setTranslateX(entity.getCoords()[0]);
        image.setTranslateY(entity.getCoords()[1]);
        image.setRotate(entity.getDirection() + 90);
        image.setUserData(entity);
        gamePane.getChildren().add(image);
        if ((entity.getCoords()[0] > 1000 || entity.getCoords()[0] < -1000)
                || (entity.getCoords()[1] > 1000 || entity.getCoords()[1] < -1000)) {
            gamePane.getChildren().remove(image);
            gameWorld.getEnemies().remove(entity);
        }
    }

    public void animate() { // start clock
        animated = true;
        timer.play();
    }

    public void stopAnimate() { // stop clock
        animated = false;
        timer.pause();
    }

    public void setGameWorld(TanksGameWorld gameWorld) {
        this.gameWorld = gameWorld;
        // Add a picutre to the player
        // try {
        // gameWorld.readValues("game.bin");
        // System.err.println("found save");
        // } catch (Exception e) {
        gameWorld.playerInitial();
        // System.out.println("save not found");
        // }

        playerTank.setUserData(gameWorld.getPlayer());
        if (playerCoords == null) {
            playerCoords = new double[] { 1024, 1024 };
        }
    }

    @FXML
    private void initialize() {
        TanksGameWorld.instance().setObserver(this);
        setGameWorld(gameWorld);
        readyClock();
        animate();
        System.out.println("new run loaded");
        // check for font loading
        System.out.println("font path: " + getClass().getResource("/app/fonts/Minecraft.ttf")); // it loads but doesnt
                                                                                                // work
        initPane();
        keyListener();
        currentScore = new Label("Score: " + score);
        currentScore.setStyle("-fx-text-fill: white;");
        currentScore.setTranslateY(-200);
        gamePane.getChildren().add(currentScore);
        gamePane.getChildren().add(turret);

        // if (currentScore == null) {
        // System.err.println("currentScore is null!");
        // return;
        // }
        // currentScore.setText("Score: " + score);

    }

    private void mouseFollow(Scene newScene, MouseEvent event) {
        // works as long as not in full screen
        double viewWidth = Math.min(WINDOW_WIDTH, gamePane.getScene().getWidth());
        double viewHeight = Math.min(WINDOW_HEIGHT, gamePane.getScene().getHeight());

        double xMargin = gamePane.getScene().getWidth() - viewWidth / 2;
        double yMargin = gamePane.getScene().getHeight() - viewHeight / 2;
        double mouseMapX = ((event.getX() / gamePane.getScene().getWidth()) * viewWidth) - xMargin;
        double mouseMapY = ((event.getY() / gamePane.getScene().getHeight()) * viewHeight) - yMargin;

        double angle = Math.toDegrees(Math.atan2(mouseMapY, mouseMapX));
        player.setTurretDirection(angle);
        turret.setRotate(angle + 90);
        // System.err.println(gamePane.getScene().getWidth());
        // System.err.println(gamePane.getScene().getHeight());
        // System.err.println(xMargin + ", " + yMargin);
        // System.err.println(event.getX() + ", " + event.getY());
        // System.err.println(mouseMapX + ", " + mouseMapY);
        // System.err.println("Angle: " + angle);
        // works as long as not in full screen
    }

    private void keyListener() {
        if (gamePane.getScene() == null) {
            gamePane.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    newScene.setOnMouseMoved(event -> mouseFollow(newScene, event));
                    newScene.setOnKeyPressed(event -> {
                        pressedKeys.add(event.getCode());
                        if (event.getCode() == KeyCode.ESCAPE) {
                            escMenu();
                        }
                    });
                    newScene.setOnKeyReleased(event -> {
                        pressedKeys.remove(event.getCode());
                        if (event.getCode() == KeyCode.ESCAPE) {
                            escMenuRemove();
                        }
                    });
                }
            });
        } else {
            Scene scene = gamePane.getScene();
            scene.setOnKeyPressed(event -> pressedKeys.add(event.getCode()));
            scene.setOnKeyReleased(event -> pressedKeys.remove(event.getCode()));
        }
    }

    private void initPane() {
        gamePane.getChildren().addAll(gameBackgroundImage);
        gamePane.getChildren().add(playerTank);
    }

    private void updatePosition() {
        keyChecker();

        gameWorld.tickAllObjects(scaleViewShift(viewShift));
        playerTank.setRotate(player.getDirection());
        // System.err.println(viewShift[0] + ", " + viewShift[1]);
        // System.err.println(playerCoords[0] + ", " + playerCoords[1]);
        // double currentX = playerTank.getLayoutX();
        // double currentY = playerTank.getLayoutY();
    }

    private double[] scaleViewShift(double[] original) {
        double scale = ZOOM_SCALE;
        double[] mod = new double[] { original[0] * scale, original[1] * scale };
        return mod;
    }

    @Override
    public void updatePlayerPosition(double x, double y, double direction) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updatePlayerPosition'");
    }

    private void orientTurret() {

    }

    private void placeBullet(ImageView image, Bullet bullet) { // move bullet
        gamePane.getChildren().remove(image);
        image.setTranslateX(bullet.getCoords()[0]);
        image.setTranslateY(bullet.getCoords()[1]);
        image.setRotate(bullet.getDirection() + 90);
        image.setUserData(bullet);
        gamePane.getChildren().add(image);
        if ((bullet.getCoords()[0] > 1000 || bullet.getCoords()[0] < -1000)
                || (bullet.getCoords()[1] > 1000 || bullet.getCoords()[1] < -1000)) {
            gamePane.getChildren().remove(image);
            gameWorld.getBullets().remove(bullet);
        }
    }

    @Override
    public void fire() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFire >= fireCD) {
            lastFire = currentTime;
            // Bullet b = new Bullet(1, player.getTurretDirection(), playerCoords);
            Bullet b = new Bullet(12, player.getTurretDirection(),
                    new double[] { 0.7 * playerTank.getBoundsInParent().getHeight()
                            * Math.cos(Math.toRadians(player.getTurretDirection())),
                            0.7 * playerTank.getBoundsInParent().getHeight()
                                    * Math.sin(Math.toRadians(player.getTurretDirection())) });
            ImageView biv = new ImageView(bulletImage);
            biv.setUserData(b);
            gameWorld.getBullets().add(b);

            Thread shotSound = new Thread(() -> playShot());
            shotSound.start();
        }
    }

    @Override
    public void currentBlood() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'currentBlood'");
    }

    private void adjust(double x, double y) {
        playerCoords[0] += x;
        playerCoords[1] += y;
    }

    private void keyChecker() {
        if (pressedKeys.contains(KeyCode.W))

        {

            double rad = Math.toRadians(player.getDirection());
            adjust(player.getSpeed() * Math.sin(rad), -player.getSpeed() * Math.cos(rad));
            System.err.println(player.getSpeed());
        }
        if (pressedKeys.contains(KeyCode.S)) {

            double rad = Math.toRadians(player.getDirection());

            adjust(-player.getSpeed() * Math.sin(rad), player.getSpeed() * Math.cos(rad));
        }
        if (pressedKeys.contains(KeyCode.A)) {
            System.err.println(player.getRotationSpeed());
            player.setDirection(player.getDirection() - player.getRotationSpeed());
        }
        if (pressedKeys.contains(KeyCode.D)) {

            player.setDirection(player.getDirection() + player.getRotationSpeed());
        }
        if (pressedKeys.contains(KeyCode.SPACE)) {
            fire();
        }
    }

    private void escMenu() {
        stopAnimate();
        escPressed = true;
        options.setTranslateY(-45);
        options.setOnMouseClicked(event -> {
            try {
                gameWorld.saveToFile("game.bin");
            } catch (Exception e) {
            }
        });
        credits.setTranslateY(0);
        help.setTranslateY(45);
        gamePane.getChildren().add(selection);
        gamePane.getChildren().add(options);

        gamePane.getChildren().add(credits);
        gamePane.getChildren().add(help);
    }

    private void escMenuRemove() {
        System.err.println("go away");
        escPressed = false;
        gamePane.getChildren().remove(selection);
        gamePane.getChildren().remove(options);

        gamePane.getChildren().remove(credits);
        gamePane.getChildren().remove(help);
        animate();
        
    }    
    
    private void scoreUp(){
        MediaPlayer mediaPlayer = new MediaPlayer(scoreUp);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.6);
    } 

    private void playShot() {
        MediaPlayer mediaPlayer = new MediaPlayer(tankShot);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.6);
    }

    private void die() {
        MediaPlayer mediaPlayer = new MediaPlayer(scream);
        mediaPlayer.play();
        mediaPlayer.setVolume(0.6);
    }

}
