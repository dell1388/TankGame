package app.model;

import java.util.Objects;

public class Tank extends Entity {

    private double hp;
    private double speed;
    private double damage;
    private double rotationSpeed;
    private double reload;
    private double[] hullArmor;
    private double[] turretArmor;
    // The position for tank
    private double positionX;
    private double positionY;

    public Tank() {
    }

    public Tank(double hp, double speed, double damage, double rotationSpeed, double reload, double[] hullArmor,
            double[] turretArmor, double x, double y) {
        this.hp = hp;
        this.speed = speed;
        this.damage = damage;
        this.rotationSpeed = rotationSpeed;
        this.reload = reload;
        this.hullArmor = hullArmor;
        this.turretArmor = turretArmor;
        this.positionX = x;
        this.positionX = y;

    }

    public double getHp() {
        return this.hp;
    }

    public void setHp(int hp) {
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

    public double getRotationSpeed() {
        return this.rotationSpeed;
    }

    public void setRotationSpeed(int rotationSpeed) {
        this.rotationSpeed = rotationSpeed;
    }

    public double getReload() {
        return this.reload;
    }

    public void setReload(int reload) {
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

    public double getX() {
        return this.positionX;
    }

    public void setX(double x) {
        this.positionX = x;
    }

    public double getY() {
        return this.positionY;
    }

    public void setY(double y) {
        this.positionY = y;
    }

    public Tank hp(int hp) {
        setHp(hp);
        return this;
    }

    public Tank speed(double speed) {
        setSpeed(speed);
        return this;
    }

    public Tank damage(double damage) {
        setDamage(damage);
        return this;
    }

    public Tank rotationSpeed(int rotationSpeed) {
        setRotationSpeed(rotationSpeed);
        return this;
    }

    public Tank reload(int reload) {
        setReload(reload);
        return this;
    }

    public Tank hullArmor(double[] hullArmor) {
        setHullArmor(hullArmor);
        return this;
    }

    public Tank turretArmor(double[] turretArmor) {
        setTurretArmor(turretArmor);
        return this;
    }

    // @Override
    // public boolean equals(Object o) {
    // return EqualsBuilder.reflectionEquals(this, o);
    // }

    @Override
    public int hashCode() {
        return Objects.hash(hp, speed, damage, rotationSpeed, reload, hullArmor, turretArmor);
    }

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
