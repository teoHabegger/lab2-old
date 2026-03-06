import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private final int worldWidth = 800;
    private final int worldHeight = 560;

    private List<SimulationObserver> observers = new ArrayList<>();

    private final ArrayList<Vehicle> cars = new ArrayList<>();
    private final Workshop<Volvo240> volvo240Workshop = new Workshop<>(10, 0, 400);

    public ArrayList<Vehicle> getCars() { return cars; }

    public Workshop<Volvo240> getWorkshop() { return volvo240Workshop; }

    public void addVehicle(Vehicle v) { cars.add(v); }

    public void addObservers(SimulationObserver obs) {
        observers.add(obs);
    }

    public void updateObservers() {
        for (SimulationObserver obs : observers) {
            obs.simulationUpdated();
        }
    }

    void tick() {

        for (int i = 0; i < cars.size(); i++) {
            Vehicle vehicle = cars.get(i);
            vehicle.move();
            int x = (int) Math.round(vehicle.getX());
            int y = (int) Math.round(vehicle.getY());
            int maxX = worldWidth - vehicle.getWidth();
            int maxY = worldHeight - vehicle.getHeight();
            boolean hitWall = false;

            if (x < 0) {
                x = 0;
                hitWall = true;
            }
            else if (x > maxX) {
                x = maxX;
                hitWall = true;
            }
            if (y < 0) {
                y = 0;
                hitWall = true;
            }
            else if (y > maxY) {
                y = maxY;
                hitWall = true;
            }

            if (hitWall) {
                rotate(vehicle);
                vehicle.setPosition(x, y);
            }

            if (vehicle instanceof Volvo240) {
                boolean intersects =
                        x + vehicle.getWidth() > volvo240Workshop.getX() &&
                        x < volvo240Workshop.getX() + volvo240Workshop.getWidth() &&
                        y < volvo240Workshop.getY() + volvo240Workshop.getHeight() &&
                        y + vehicle.getHeight() > volvo240Workshop.getY();

                if (intersects && !volvo240Workshop.isFull()){
                    volvo240Workshop.load((Volvo240) vehicle);
                    cars.remove(i);
                    i--;
                    continue;
                }
            }
        }
        updateObservers();
    }

    void rotate (Vehicle vehicle) {
        vehicle.turnRight();
        vehicle.turnRight();
    }

    void gas(ArrayList<Vehicle> cars, int amount) {
        double gas = ((double) amount) / 100;
        for (Vehicle car : cars)
            car.gas(gas);
    }

    void brake (ArrayList<Vehicle> cars, int amount) {
        double brake = ((double) amount) / 100;
        for (Vehicle car : cars)
            car.brake(brake);
    }

    void startEngine(ArrayList<Vehicle> cars) {
        for (Vehicle car : cars)
            car.startEngine();
    }

    void stopEngine(ArrayList<Vehicle> cars) {
        for (Vehicle car : cars)
            car.stopEngine();
    }

    void setTurboOn(ArrayList<Vehicle> cars) {
        for (Vehicle v : cars){
            if (v instanceof HasTurbo t){
                t.setTurboOn();
            }
        }
    }

    void setTurboOff(ArrayList<Vehicle> cars) {
        for (Vehicle v : cars){
            if (v instanceof HasTurbo t){
                t.setTurboOff();
            }
        }
    }

    void liftBed(ArrayList<Vehicle> cars){
        for (Vehicle v : cars) {
            if (v instanceof HasLiftBed bed){
                bed.raiseBed(10);
            }
        }
    }

    void lowerBed(ArrayList<Vehicle> cars){
        for (Vehicle v : cars){
            if (v instanceof HasLiftBed bed){
                bed.lowerBed(10);
            }
        }
    }

    void addCAr(){
        if (cars.size() >= 10)
            return;
        Vehicle vehicle =
                VehicleFactory.createVehicle(VehicleType.values()[(int)(Math.random() * VehicleType.values().length)]);
        cars.add(vehicle);

        int x = (int) (Math.random() * (worldWidth - vehicle.getWidth()));
        int y = (int) (Math.random() * (worldHeight - vehicle.getHeight()));

        vehicle.setPosition(x, y);
    }

    void removeCar(){
        if (cars.isEmpty())
            return;
        cars.removeLast();
    }
}