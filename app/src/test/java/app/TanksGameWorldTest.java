package app;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import app.model.Bullet;
import app.model.Entity;
import app.model.TanksGameWorld;
import app.model.Vehicle;
import app.model.VehicleStats;

public class TanksGameWorldTest {

    @Test
    public void test_the_constructor_for_TanksGameWorld() {
        TanksGameWorld game = TanksGameWorld.instance();
        game.playerInitial();
        // assertEquals(game.getPlayer().getClass(), );
        assertEquals(game.getPlayer().getHp(), 1000);
        assertEquals(game.getPlayer().getDamage(), 100);
        assertEquals(game.getPlayer().getSpeed(), 4);
        assertEquals(game.getPlayer().getRotationSpeed(), 3);
    }

    @Test
    public void testSingletonInstance() {
        TanksGameWorld instance1 = TanksGameWorld.instance();
        TanksGameWorld instance2 = TanksGameWorld.instance();
        assertSame(instance1, instance2);
    }

    @Test
    public void testPlayerInitialization() {
        TanksGameWorld game = TanksGameWorld.instance();
        game.playerInitial();

        assertEquals(1000, game.getPlayer().getHp());
        assertEquals(100, game.getPlayer().getDamage());
        assertEquals(4, game.getPlayer().getSpeed());
        assertEquals(3, game.getPlayer().getRotationSpeed());
        assertArrayEquals(new double[] { 1024, 1024 }, game.getPlayer().getCoords());
    }

    @Test
    public void testEnemyCreation() {
        TanksGameWorld game = TanksGameWorld.instance();
        VehicleStats.initStats();
        game.createEnemies(3, Vehicle.RT34);
        assertEquals(3, game.getEnemies().size());
        game.getEnemies().clear();
    }

    @Test
    public void testSpawnCoordsGeneration() {
        TanksGameWorld game = TanksGameWorld.instance();
        double[] coords = game.findSpawnCoords();
        double distance = Math.sqrt(coords[0] * coords[0] + coords[1] * coords[1]);
        assertTrue(distance <= 200, "Spawn coordinates should be within 200 radius");
    }

    @Test
    public void testDamageApplication() {
        TanksGameWorld game = TanksGameWorld.instance();
        Entity testEntity = new Entity();
        testEntity.setHp(500);
        game.getAttack(testEntity, 150);
        assertEquals(350, testEntity.getHp());
    }

    @Test
    public void testMovementSystem() {
        TanksGameWorld game = TanksGameWorld.instance();
        Entity entity = new Entity();
        entity.setCoords(new double[] { 10.0, 15.0 });
        entity.setSpeed(10);
        entity.setDirection(0); // 0 degrees = rightward movement

        game.getEnemies().add(entity);
        game.moveEntities();

        assertNotEquals(new double[] { 150, 100 }, entity.getCoords()[0]);
        assertEquals(10 * Math.cos(0), entity.getCoords()[0], 0.01);
    }

    @BeforeEach
    public void resetGameWorld() {
        TanksGameWorld.instance().getEnemies().clear();
        TanksGameWorld.instance().getBullets().clear();
    }

    @Test
    public void testBulletManagement() {
        TanksGameWorld game = TanksGameWorld.instance();
        game.getBullets().add(new Bullet(10, 0, new double[] { 0, 0 }));
        assertEquals(1, game.getBullets().size());
    }

    @Test
    public void testSpawnBankMechanics() {
        TanksGameWorld game = TanksGameWorld.instance();
        game.tick(); // Should increase spawnBank by difficulty (0.5)
        game.tick(); // Total spawnBank = 1.0
        game.clockReducer(); // Force spawn selector
    }

    @Test
    public void testFullEnemySpawnCycle() {
        TanksGameWorld game = TanksGameWorld.instance();
        game.setSpawnBank(100.0);
        VehicleStats.initStats();
        game.spawnSelector();
        assertTrue(game.getEnemies().size() > 0);
    }

    @Test
    public void testComplexMovement() {
        TanksGameWorld game = TanksGameWorld.instance();
        Entity testEntity = new Entity();
        testEntity.setCoords(new double[] { -10, 0 });
        testEntity.setSpeed(10);
        testEntity.setDirection(180);
        game.getEnemies().add(testEntity);

        game.moveEntities();
        assertArrayEquals(new double[] { -10, 0 }, testEntity.getCoords(), 0.01);
    }

    @Test
    public void testEdgeCaseSpawnCoordinates() {
        TanksGameWorld game = TanksGameWorld.instance();
        for (int i = 0; i < 1000; i++) {
            double[] coords = game.findSpawnCoords();
            double distance = Math.hypot(coords[0], coords[1]);
            assertTrue(distance <= 200 * 1.05, "Coordinate exceeded radius with distance: " + distance);
        }
    }

}
