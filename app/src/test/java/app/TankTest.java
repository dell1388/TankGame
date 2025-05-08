package app;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import app.model.Tank;

public class TankTest {
    @Test
public void testDefaultConstructor() {
    Tank tank = new Tank();
    assertEquals(0.0, tank.getHp());
    assertEquals(0.0, tank.getSpeed());
    assertEquals(0.0, tank.getDamage());
}

@Test
public void testParameterizedConstructor() {
    double[] hullArmor = {100, 80};
    double[] turretArmor = {120, 90};
    Tank tank = new Tank(1500, 10.5, 250, 35, 2.5, hullArmor, turretArmor, 500, 300);
    
    assertEquals(1500, tank.getHp());
    assertEquals(10.5, tank.getSpeed());
    assertEquals(250, tank.getDamage());
    assertEquals(35, tank.getRotationSpeed());
    assertEquals(2.5, tank.getReload());
    assertArrayEquals(hullArmor, tank.getHullArmor());
    assertArrayEquals(turretArmor, tank.getTurretArmor());
    assertEquals(300, tank.getX());
    assertEquals(0, tank.getY());
}

@Test
public void testFluentSetters() {
    Tank tank = new Tank()
        .hp(2000)
        .speed(15.0)
        .damage(300)
        .rotationSpeed(40)
        .reload(3)
        .hullArmor(new double[]{150, 100})
        .turretArmor(new double[]{200, 150});

    assertEquals(2000, tank.getHp());
    assertEquals(15.0, tank.getSpeed());
    assertEquals(300, tank.getDamage());
    assertEquals(40, tank.getRotationSpeed());
    assertEquals(3, tank.getReload());
    assertArrayEquals(new double[]{150, 100}, tank.getHullArmor());
    assertArrayEquals(new double[]{200, 150}, tank.getTurretArmor());
}

@Test
public void testPositionSetters() {
    Tank tank = new Tank();
    tank.setX(42.5);
    tank.setY(37.8);
    assertEquals(42.5, tank.getX());
    assertEquals(37.8, tank.getY());
}
    
}
