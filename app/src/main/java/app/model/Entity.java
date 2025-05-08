package app.model;

import java.util.Objects;

public class Entity {

    private Vehicle type;
    private double hp;
    private double speed;
    private double damage;
    private double direction;
    private double turretDirection; // assigned absolutely, not relative to hull
    private double rotationSpeed;
    private double reload;
    private double[] hullArmor;
    private double[] turretArmor;
    private double[] coords;
    private boolean reloading;
    private int ticksToReload;
    private final double tps = 25;

    public Entity() {
    }

    public Entity(double hp, double speed, double damage, double direction, int rotationSpeed, double reload,
            double[] hullArmor, double[] turretArmor) {
        this.hp = hp;
        this.speed = speed;
        this.damage = damage;
        this.direction = direction;
        this.rotationSpeed = rotationSpeed;
        this.reload = reload;
        this.hullArmor = hullArmor;
        this.turretArmor = turretArmor;
    }

    public Entity(Vehicle v, double[] coords) {
        type = v;
        hp = VehicleStats.vehicleStats.get(v).hp();
        damage = VehicleStats.vehicleStats.get(v).damage();
        speed = VehicleStats.vehicleStats.get(v).speed();
        reload = VehicleStats.vehicleStats.get(v).reload();
        rotationSpeed = VehicleStats.vehicleStats.get(v).rotationSpeed();
        hullArmor = VehicleStats.vehicleStats.get(v).hullArmor();
        turretArmor = VehicleStats.vehicleStats.get(v).turretArmor();
        this.coords = coords;
        direction = Math.toDegrees(Math.atan2(-coords[1], -coords[0]));
        // System.out.println(v + " at: " + coords[0] + ", " + coords[1] + " pointing "
        // + direction);
    }

    public double getHp() {
        return this.hp;
    }

    public void setHp(double hp) {
        this.hp = hp;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getDamage() {
        return this.damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public double getDirection() {
        return this.direction;
    }

    public void setDirection(double direction) {
        this.direction = direction;
        if (this.direction > 360)
            this.direction -= 360;
        if (this.direction < 0)
            this.direction += 360;
    }

    public double getRotationSpeed() {
        return this.rotationSpeed;
    }

    public void setRotationSpeed(double rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getReload() {
        return this.reload;
    }

    public void setReload(double reload) {
        this.reload = reload;
    }

    public double[] getHullArmor() {
        return this.hullArmor;
    }

    public void setHullArmor(double[] hullArmor) {
        this.hullArmor = hullArmor;
    }

    public double[] getTurretArmor() {
        return this.turretArmor;
    }

    public void setTurretArmor(double[] turretArmor) {
        this.turretArmor = turretArmor;
    }

    public Entity hp(int hp) {
        setHp(hp);
        return this;
    }

    public Entity speed(double speed) {
        setSpeed(speed);
        return this;
    }

    public Entity damage(double damage) {
        setDamage(damage);
        return this;
    }

    public Entity rotationSpeed(int rotationSpeed) {
        setRotationSpeed(rotationSpeed);
        return this;
    }

    public Entity reload(int reload) {
        setReload(reload);
        return this;
    }

    public Entity hullArmor(double[] hullArmor) {
        setHullArmor(hullArmor);
        return this;
    }

    public Entity turretArmor(double[] turretArmor) {
        setTurretArmor(turretArmor);
        return this;
    }

    public double getTurretDirection() {
        return turretDirection;
    }

    public void setTurretDirection(double turretDirection) {
        this.turretDirection = turretDirection;
    }

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    public Vehicle getType() {
        return type;
    }

    public void setType(Vehicle type) {
        this.type = type;
    }

    public boolean isReloading() {
        return reloading;
    }

    public int getTicksToReload() {
        return ticksToReload;
    }

    public boolean tickReload() {
        ticksToReload--;
        if (ticksToReload <= 0) {
            reloading = false;
        }
        return reloading;
    }

    public void fire() {
        ticksToReload = (int) (reload * tps);
        reloading = true;
    }

    public void setReloading(boolean reloading) {
        this.reloading = reloading;
    }

    public void setTicksToReload(int ticksToReload) {
        this.ticksToReload = ticksToReload;
    }
    // @Override
    // public boolean equals(Object o) {
    // return EqualsBuilder.reflectionEquals(this, o);
    // }

    public void move() {
        setDirection(Math.toDegrees(Math.atan2(coords[0], -coords[1])) + 90);
        if (Math.hypot(coords[0], coords[1]) > 100) {
            coords[0] += 0.25 * speed * Math.cos(direction * Math.PI / 180.0);
            coords[1] += 0.25 * speed * Math.sin(direction * Math.PI / 180.0);

        } else
            return;
    }

    public void adjust(double[] delta) {
        coords[0] += delta[0];
        coords[1] += delta[1];
    }

    public void adjust(double x, double y) {
        coords[0] += x;
        coords[1] += y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hp, speed, damage, rotationSpeed, reload, hullArmor, turretArmor);
    }

    // public static Entity omsktransmash(Country c){}

    @Override
    public String toString() {
        return "{" +
                " hp='" + getHp() + "'" +
                ", speed='" + getSpeed() + "'" +
                ", damage='" + getDamage() + "'" +
                ", rotationSpeed='" + getRotationSpeed() + "'" +
                ", reload='" + getReload() + "'" +
                ", hullArmor='" + getHullArmor() + "'" +
                ", turretArmor='" + getTurretArmor() + "'" +
                "}";
    }

}
