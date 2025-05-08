package app.model;

import java.util.*;

public class VehicleStats {

        public record Stats(double hp, double speed, double damage, double rotationSpeed, double reload,
                        double[] hullArmor,
                        double[] turretArmor, double spawnCost, String complete, String hullPath, String turretPath) {
        }

        public static Map<Vehicle, Stats> vehicleStats = new EnumMap<>(Vehicle.class);

        public static void initStats() {

                vehicleStats.put(Vehicle.BCHURCHILL,
                                new Stats(1200, 2, 100, 15, 2, new double[] { 0.35, 0.05, 0 },
                                                new double[] { 0.35, 0.1, 0 }, 80, 
                                                "/app/images/Tanks/Churchill/ww2_top_view_complete7.png",
                                                "/app/images/Tanks/Churchill/ww2_top_view_hull7.png",
                                                "/app/images/Tanks/Churchill/ww2_top_view_turret7.png"));
                vehicleStats.put(Vehicle.BCRUSADER,
                                new Stats(300, 8, 60, 40, 2, new double[] { 0.05, 0.05, 0 },
                                                new double[] { 0.1, 0.05, 0 }, 30,
                                                "/app/images/Tanks/Crusader/ww2_top_view_complete8.png",
                                                "/app/images/Tanks/Crusader/ww2_top_view_hull8.png",
                                                "/app/images/Tanks/Crusader/ww2_top_view_turret8.png"));
                vehicleStats.put(Vehicle.BMATILDA,
                                new Stats(500, 3, 50, 20, 1, new double[] { 0.2, 0.1, 0 },
                                                new double[] { 0.25, 0.2, 0.1 }, 60,
                                                "/app/images/Tanks/Matilda/ww2_top_view_complete9.png",
                                                "/app/images/Tanks/Matilda/ww2_top_view_hull9.png",
                                                "/app/images/Tanks/Matilda/ww2_top_view_turret9.png"));
                vehicleStats.put(Vehicle.GTIGER,
                                new Stats(1000, 5, 250, 25, 5, new double[] { 0.25, 0.05, 0 },
                                                new double[] { 0.4, 0.05, 0 }, 80,
                                                "/app/images/Tanks/Tiger/ww2_top_view_complete3.png",
                                                "/app/images/Tanks/Tiger/ww2_top_view_hull3.png",
                                                "/app/images/Tanks/Tiger/ww2_top_view_turret3.png"));
                vehicleStats.put(Vehicle.GPANTHER,
                                new Stats(700, 6, 200, 30, 4, new double[] { 0.35, 0.05, 0 },
                                                new double[] { 0.15, 0.2, 0.1 }, 60,
                                                "/app/images/Tanks/Panther/ww2_top_view_complete1.png",
                                                "/app/images/Tanks/Panther/ww2_top_view_hull1.png",
                                                "/app/images/Tanks/Panther/ww2_top_view_turret1.png"));
                vehicleStats.put(Vehicle.GPANZER4,
                                new Stats(500, 5, 150, 30, 1, new double[] { 0.1, 0, 0 }, 
                                                new double[] { 0.1, 0, 0 }, 40,
                                                "/app/images/Tanks/Panzer 4/ww2_top_view_complete2.png",
                                                "/app/images/Tanks/Panzer 4/ww2_top_view_hull2.png",
                                                "/app/images/Tanks/Panzer 4/ww2_top_view_turret2.png"));
                vehicleStats.put(Vehicle.IM13,
                                new Stats(500, 5, 70, 30, 1, new double[] { 0.1, 0, 0 }, 
                                                new double[] { 0.15, 0, 0 }, 30,
                                                "/app/images/Tanks/M13/ww2_top_view_complete13.png",
                                                "/app/images/Tanks/M13/ww2_top_view_hull13.png",
                                                "/app/images/Tanks/M13/ww2_top_view_turret13.png"));
                vehicleStats.put(Vehicle.JTYPE95,
                                new Stats(900, 4, 200, 20, 5, new double[] { 0.15, 0.05, 0 },
                                                new double[] { 0.15, 0.05, 0 }, 60,
                                                "/app/images/Tanks/type95/ww2_top_view_complete14.png",
                                                "/app/images/Tanks/type95/ww2_top_view_hull14.png",
                                                "/app/images/Tanks/type95/ww2_top_view_turret14.png"));
                vehicleStats.put(Vehicle.RKV1,
                                new Stats(1000, 4, 150, 20, 2.5, new double[] { 0.35, 0.15, 0.1 },
                                                new double[] { 0.25, 0.1, 0.1 }, 80,
                                                "/app/images/Tanks/KV-1/ww2_top_view_complete5.png",
                                                "/app/images/Tanks/KV-1/ww2_top_view_hull5.png",
                                                "/app/images/Tanks/KV-1/ww2_top_view_turret5.png"));
                vehicleStats.put(Vehicle.RT26,
                                new Stats(200, 4, 200, 35, 4, new double[] { 0, 0, 0 }, 
                                                new double[] { 0, 0, 0 }, 30,
                                                "/app/images/Tanks/T-26/ww2_top_view_complete6.png",
                                                "/app/images/Tanks/T-26/ww2_top_view_hull6.png",
                                                "/app/images/Tanks/T-26/ww2_top_view_turret6.png"));
                vehicleStats.put(Vehicle.RT34,
                                new Stats(400, 4, 150, 30, 2, new double[] { 0.25, 0.05, 0 },
                                                new double[] { 0.15, 0.1, 0.1 }, 60,
                                                "/app/images/Tanks/T-34/ww2_top_view_complete4.png",
                                                "/app/images/Tanks/T-34/ww2_top_view_hull4.png",
                                                "/app/images/Tanks/T-34/ww2_top_view_turret4.png"));
                vehicleStats.put(Vehicle.ULEE,
                                new Stats(600, 5, 60, 30, 1, new double[] { 0.1, 0, 0 }, 
                                                new double[] { 0.1, 0, 0 }, 40,
                                                "/app/images/Tanks/Lee/ww2_top_view_complete11.png",
                                                "/app/images/Tanks/Lee/ww2_top_view_hull11.png",
                                                "/app/images/Tanks/Lee/ww2_top_view_turret11.png"));
                vehicleStats.put(Vehicle.USHERMAN,
                                new Stats(700, 6, 100, 30, 1, new double[] { 0.2, 0.05, 0 },
                                                new double[] { 0.15, 0.05, 0 }, 60,
                                                "/app/images/Tanks/Sherman/ww2_top_view_complete10.png",
                                                "/app/images/Tanks/Sherman/ww2_top_view_hull10.png",
                                                "/app/images/Tanks/Sherman/ww2_top_view_turret10.png"));
                vehicleStats.put(Vehicle.USTUART,
                                new Stats(300, 9, 50, 45, 1.5, new double[] { 0, 0, 0 }, 
                                                new double[] { 0, 0, 0 }, 30,
                                                "/app/images/Tanks/Stuart/ww2_top_view_complete12.png",
                                                "/app/images/Tanks/Stuart/ww2_top_view_hull12.png",
                                                "/app/images/Tanks/Stuart/ww2_top_view_turret12.png"));
        }
}
