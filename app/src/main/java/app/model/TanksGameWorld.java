package app.model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Random;

public class TanksGameWorld {

    private double difficulty = 0.5;

    private Entity player = new Entity();
    private static ArrayList<Entity> enemies = new ArrayList<>();
    private static ArrayList<Bullet> bullets = new ArrayList<>();
    private Observer gameObserver;
    private double spawnBank;
    private int wait = 0;
    private double spawnRadius = 200;

    private static TanksGameWorld instance = new TanksGameWorld();

    public static TanksGameWorld instance() {
        return instance;
    }

    public static ArrayList<Bullet> getBullets() {
        return bullets;
    }

    public ArrayList<Entity> getEnemies() {
        return enemies;
    }

    public Entity getPlayer() {
        return this.player;
    }

    public double getSpawnBank() {
        return spawnBank;
    }

    public void setSpawnBank(double number) {
        this.spawnBank = number;
    }

    // Player initial
    public void playerInitial() {
        this.player.setHp(1000);
        this.player.setSpeed(4);
        this.player.setDamage(100);
        this.player.setRotationSpeed(3);
        this.player.setReload(1);
        this.player.setCoords(new double[] { 1024, 1024 });
        this.player.setTurretDirection(0);
        this.player.setType(Vehicle.RKV1);
        // player.setHullArmor(double[]);
        // player.setTurretArmor(double[]);
    }

    public void loadPlayer(double hp, double speed, double damage, double rotationSpeed, double reload, double x,
            double y) {
        this.player.setHp(hp);
        this.player.setSpeed(speed);
        this.player.setDamage(damage);
        this.player.setRotationSpeed(rotationSpeed);
        this.player.setReload(reload);
        this.player.setCoords(new double[] { x, y });
        this.player.setTurretDirection(0);
        this.player.setType(Vehicle.RKV1);
        // if (player.getSpeed() == 0) {
        player.setSpeed(4);
        // }
        if (player.getRotationSpeed() == 0) {
            player.setRotationSpeed(3);
        }
    }

    // public Tank getPlayer(){
    // return player;
    // }

    // public TankGame(int size){
    // this.size = size;

    // }

    public void createEnemies(int number, Vehicle v) {
        // create new enemies and add to arraylist
        for (int i = 0; i < number; i++) {
            enemies.add(new Entity(v, findSpawnCoords()));
        }

    }

    public double[] findSpawnCoords() {
        boolean parX = new Random().nextBoolean();
        boolean parY = new Random().nextBoolean();
        double x = new Random().nextInt(200);
        double y = (Math.sqrt(Math.pow(spawnRadius, 2) - Math.pow(x, 2)));
        if (!parX)
            x *= -1;
        if (!parY)
            y *= -1;
        double[] coords = { x, y };
        return coords;
    }

    // When player receive an attack
    public void getAttack(Entity tank, double damage) {
        double updateHp = tank.getHp() - damage;
        tank.setHp(updateHp);

    }

    public void setObserver(Observer observer) {
        this.gameObserver = observer;
    }

    // public void WMoving() {
    // double speed = player.getSpeed();
    // double radians = Math.toRadians(player.getDirection());
    // player.setX(player.getX() + speed * Math.cos(radians));
    // player.setY(player.getY() + speed * Math.sin(radians));
    // }

    // public void SMoving() {
    // double speed = player.getSpeed();
    // double radians = Math.toRadians(player.getDirection());
    // player.setX(player.getX() - speed * Math.cos(radians));
    // player.setY(player.getY() - speed * Math.sin(radians));
    // }

    // public void AMoving() {
    // player.setDirection(player.getDirection() - player.getRotationSpeed());
    // }

    // public void DMoving() {
    // player.setDirection(player.getDirection() + player.getRotationSpeed());
    // }

    public void tick() {
        spawnBank += difficulty;
        clockReducer();
        moveEntities();
        moveBullets();
    }

    public void moveEntities() {
        for (Entity e : enemies) {
            e.move();
        }
    }

    private void moveBullets() {
        for (Bullet b : bullets) {
            b.move();
        }
    }

    public void tickAllObjects(double[] delta) {
        for (Entity e : enemies) {
            e.adjust(delta);
            e.tickReload();
        }
        for (Bullet b : bullets) {
            b.adjust(delta);
        }
    }

    public void clockReducer() {
        wait++;
        if (wait > 24) {
            wait = 0;
            spawnSelector();
        }

    }

    public void spawnSelector() {

        Vehicle randomVehicle = Vehicle.values()[new Random().nextInt(Vehicle.values().length)];
        createEnemies((int) (spawnBank /
                VehicleStats.vehicleStats.get(randomVehicle).spawnCost()), randomVehicle);
        spawnBank -= (VehicleStats.vehicleStats.get(randomVehicle).spawnCost()
                * ((int) (spawnBank /
                        VehicleStats.vehicleStats.get(randomVehicle).spawnCost())));
    }

    public void saveToFile(String filename) throws IOException {
        try (DataOutputStream writer = new DataOutputStream(new FileOutputStream(filename))) {
            Tank player = (Tank) TanksGameWorld.instance().getPlayer();
            writer.writeDouble(player.getHp()); // double (8 bytes)
            writer.writeDouble(player.getSpeed()); // double (8 bytes)
            writer.writeDouble(player.getDamage()); // double (8 bytes)
            writer.writeDouble(player.getRotationSpeed()); // double (8 bytes)
            writer.writeDouble(player.getReload()); // double (8 bytes)
            writer.writeDouble(player.getX()); // double (8 bytes)
            writer.writeDouble(player.getY()); // double (8 bytes)
        }
    }

    public void readValues(String fileName) throws IOException {
        System.out.println("Reading values using readValues()");
        TanksGameWorld.instance().getEnemies().clear();
        if (Files.exists(Paths.get(fileName))) {
            try (var reader = new DataInputStream(new FileInputStream(fileName))) {
                while (reader.available() > 0) {
                    double hp = reader.readDouble();
                    double speed = reader.readDouble();
                    double damage = reader.readDouble();
                    double rotationSpeed = reader.readDouble();
                    double reload = reader.readDouble();
                    double positionX = reader.readDouble();
                    double positionY = reader.readDouble();
                    TanksGameWorld.instance().loadPlayer(hp, speed, damage, rotationSpeed, reload, positionX,
                            positionY);
                }
            }
        }
    }

}
