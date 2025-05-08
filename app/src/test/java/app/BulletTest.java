package app;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import app.model.Bullet;

public class BulletTest {
    @Test
    public void testBulletMovement() {
    Bullet bullet = new Bullet(10, 45, new double[]{0, 0});
    bullet.move();
    
    double expectedX = 10 * Math.cos(Math.toRadians(45));
    double expectedY = 10 * Math.sin(Math.toRadians(45));
    assertArrayEquals(new double[]{expectedX, expectedY}, bullet.getCoords(), 0.01);
    }

    @Test
    public void testBulletAdjustment() {
        Bullet bullet = new Bullet(0, 0, new double[]{100, 200});
        bullet.adjust(new double[]{-50, 30});
        assertArrayEquals(new double[]{50, 230}, bullet.getCoords(), 0.01);
    }

    @Test
    public void testDirectionWrap() {
        Bullet bullet = new Bullet(5, 720, new double[]{0,0});
        assertEquals(720, bullet.getDirection());
        bullet.setDirection(-90);
        assertEquals(-90, bullet.getDirection());
    }

    @Test
    public void testBulletConstructorAndGetters() {
    Bullet bullet = new Bullet(15, 90, new double[]{100, 200});
    
    assertAll("Bullet properties",
        () -> assertEquals(15, bullet.getSpeed()),
        () -> assertEquals(90, bullet.getDirection()),
        () -> assertArrayEquals(new double[]{100, 200}, bullet.getCoords())
    );
    }

    @Test
    public void testExtremeDirectionMovement() {
    Bullet bullet = new Bullet(10, 360, new double[]{0, 0});
    bullet.move();
    assertArrayEquals(new double[]{10, 0}, bullet.getCoords(), 0.01);
    }

    @Test
    public void testNegativeCoordinateAdjustment() {
    Bullet bullet = new Bullet(0, 0, new double[]{50, 75});
    bullet.adjust(new double[]{-30, -25});
    assertArrayEquals(new double[]{20, 50}, bullet.getCoords(), 0.01);
    }

    @Test
    public void testSetters() {
    Bullet bullet = new Bullet(0, 0, new double[]{0, 0});
    bullet.setSpeed(20);
    bullet.setDirection(180);
    bullet.setCoords(new double[]{-10, 5});
    
    assertAll("Updated bullet properties",
        () -> assertEquals(20, bullet.getSpeed()),
        () -> assertEquals(180, bullet.getDirection()),
        () -> assertArrayEquals(new double[]{-10, 5}, bullet.getCoords())
    );
}
}
