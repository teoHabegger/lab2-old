import java.awt.*;

public  class Scania extends Truck implements HasLiftBed {

    private double platformAngle;

    public Scania() {
        super(2, 80, Color.white, "lab2.Scania", 6);
        platformAngle = 0;
    }

    @Override
    public void raiseBed(double amount) {

        if (amount < 0) {
            throw new IllegalArgumentException("No negative numbers allowed as input");
        }

        if (getCurrentSpeed() != 0) return;

        if (platformAngle + amount > 70)
            throw new IllegalArgumentException("The angle of the platform can't go above 70");
        platformAngle =+ amount;
    }

    @Override
    public void lowerBed(double amount) {
        if (amount < 0) {
            throw new IllegalArgumentException("No negative numbers allowed as input");
        }
        if (getCurrentSpeed() != 0) return;

        if (platformAngle - amount < 0)
            throw new IllegalArgumentException("The angle of the platform can't go below 0");
        platformAngle =- amount;
    }


    public double getPlatformAngle() {
        return platformAngle;
    }

    @Override
    public void move() {
        if (platformAngle > 0) return;
        super.move();
    }

    @Override
    public void gas(double amount) {
        if (platformAngle > 0) return;
        super.gas(amount);
    }
}