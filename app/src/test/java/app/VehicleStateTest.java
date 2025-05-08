package app;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import app.model.Vehicle;
import app.model.VehicleStats;

public class VehicleStateTest {
 @BeforeAll
public static void setup() {
    VehicleStats.initStats();
}

@Test
public void testStatsInitialization() {
    assertNotNull(VehicleStats.vehicleStats);
    assertFalse(VehicleStats.vehicleStats.isEmpty());
}

@Test
public void testSpecificVehicleStats() {
    VehicleStats.Stats tigerStats = VehicleStats.vehicleStats.get(Vehicle.GTIGER);
    
    assertAll("Tiger tank stats",
        () -> assertEquals(1000, tigerStats.hp()),
        () -> assertEquals(5, tigerStats.speed()),
        () -> assertEquals(250, tigerStats.damage()),
        () -> assertArrayEquals(new double[]{0.25, 0.05, 0}, tigerStats.hullArmor()),
        () -> assertEquals(80, tigerStats.spawnCost())
    );
}

@Test
public void testAllVehiclesHaveStats() {
    for (Vehicle vehicle : Vehicle.values()) {
        assertNotNull(VehicleStats.vehicleStats.get(vehicle),
            "Missing stats for vehicle: " + vehicle);
    }
}   
}
