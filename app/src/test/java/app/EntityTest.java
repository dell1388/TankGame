package app;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

import app.model.Entity;

public class EntityTest {

    @Test 
    public void test_Creating_Entity_constructor_getting_setting_method(){

        double[] hullArmorTesting = {6.0, 7.0};
        double[] turretArmor = {8.0 , 9.0};
        Entity testingEntity = new Entity(1.0, 2.0, 3.0, 4.0, 5, 6.0, hullArmorTesting, turretArmor);

        assertEquals(1.0, testingEntity.getHp());
        testingEntity.setHp(2.0);
        assertNotEquals(1.0, testingEntity.getHp());

        assertEquals(2.0, testingEntity.getSpeed());
        testingEntity.setSpeed(0.0);
        assertNotEquals(2.0, testingEntity.getSpeed());

        assertEquals(3.0, testingEntity.getDamage());
        testingEntity.setDamage(5.0);
        assertEquals(5.0, testingEntity.getDamage());

        assertEquals(4.0, testingEntity.getDirection());
        testingEntity.setDirection(360.0);
        assertEquals(360.0, testingEntity.getDirection());

        assertEquals(5, testingEntity.getRotationSpeed());
        testingEntity.setRotationSpeed(50.0);
        assertEquals(50.0, testingEntity.getRotationSpeed());

        assertEquals(6.0, testingEntity.getReload());
        testingEntity.setReload(10);
        assertEquals(10, testingEntity.getReload());

        assertArrayEquals(hullArmorTesting, testingEntity.getHullArmor());
        assertArrayEquals(turretArmor, testingEntity.getTurretArmor());
    }

}
