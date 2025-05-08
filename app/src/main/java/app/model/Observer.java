package app.model;

public interface Observer {
    
    void updatePlayerPosition(double x, double y, double direction);
    void fire();
    void currentBlood();
}
