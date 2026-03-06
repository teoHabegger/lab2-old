import  java.awt.*;
import java.util.ArrayDeque;
import java.util.Deque;

public class CarTransporter extends Truck {

    private  int maxCars;
    private Deque<Car> loadedCars;
    private RampState rampState;

    public CarTransporter() {
        super(2, 70, Color.gray, "lab2.CarTransporter", 5);
        maxCars = 10;
        loadedCars = new ArrayDeque<Car>();
        rampState = new StateRampUp();
    }

    public  void setPlatformRaised() {
        rampState.raiseRamp(this);
    }

    public void setPlatformDown() {
        rampState.lowerRamp(this);
    }

    public void setRampState(RampState rampState){
        this.rampState = rampState;
    }

    @Override
    public  void move() {
        if (!rampState.isPlatformUp())
            throw new IllegalStateException("Truck can't move if the platform is off the ground");
        super.move();

        syncCarPos();
    }

    public void syncCarPos() {
        for (Car c : loadedCars) {
            c.setPosition(this.getX(), this.getY());
        }
    }


    @Override
    public void gas(double amount) {
        if (!rampState.isPlatformUp())
            throw new IllegalStateException("Truck can't change speeds if the platform is off the ground");
        super.gas(amount);
    }

    public  void loadCar(Car car) {

        if (car == null)
            throw new IllegalStateException("Car can't be null");
        if (getCurrentSpeed() != 0)
            throw  new IllegalStateException("Car transporter has to be stationary to load a car");
        if (loadedCars.size() >= maxCars)
            throw new IllegalStateException("Max amount of cars has been reached");
        if (car.getSize() >= 5)
            throw new IllegalStateException("Vehicle is too big");
        if (rampState.isPlatformUp())
            throw new IllegalStateException("Can't load vehicles if platform is raised");

        double distance = Math.abs(this.getX() - car.getX()) + Math.abs(this.getY() - car.getY());

        if (distance > 2.0)
            throw new IllegalStateException("Car is too far away");
        loadedCars.push(car);
        car.setPosition(this.getX(), this.getY());

    }

    public Car unloadCar() {
        if (rampState.isPlatformUp())
            throw new IllegalStateException("Can't unload vehicles if platform is raised");
        if (getCurrentSpeed() != 0)
            throw new IllegalStateException("Car transporter has to be stationary to unload a car");
        if (loadedCars.isEmpty())
            throw new IllegalStateException("There are no cars in the transport car");
        Car car = loadedCars.pop();
        car.setPosition(this.getX(), this.getY() - 1);
        return car;
    }
}