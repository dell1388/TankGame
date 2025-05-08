package app.model;

public class Bullet {
    private double speed;
    private double direction;
    private double[] coords;

    public double[] getCoords() {
        return coords;
    }

    public void setCoords(double[] coords) {
        this.coords = coords;
    }

    public Bullet(double s, double d, double[] c) {
        speed = s;
        direction = d;
        coords = c;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public void setDirection(double direction) {
        this.direction = direction;
    }

    public void move() {
        // setDirection(Math.toDegrees(Math.atan2(coords[0], -coords[1])) + 90);
        coords[0] += speed * Math.cos(direction * Math.PI / 180.0);
        coords[1] += speed * Math.sin(direction * Math.PI / 180.0);

    }

    public void adjust(double[] delta) {
        coords[0] += delta[0];
        coords[1] += delta[1];
    }
}
