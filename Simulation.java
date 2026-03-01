import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class Simulation {

    void tick(CarView frame, ArrayList<Vehicle> cars, Workshop<Volvo240> volvo240Workshop, ActionEvent e) {

        for (int i = 0; i < cars.size(); i++) {
            Vehicle vehicle = cars.get(i);
            vehicle.move();
            int x = (int) Math.round(vehicle.getX());
            int y = (int) Math.round(vehicle.getY());
            int maxX = frame.drawPanel.getWidth() - frame.drawPanel.getVehicleWidth(i);
            int maxY = frame.drawPanel.getHeight() - frame.drawPanel.getVehicleHeight(i);
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

            Rectangle workShopRectangle = new Rectangle(
                    frame.drawPanel.getWorkShopX(),
                    frame.drawPanel.getWorkShopY(),
                    frame.drawPanel.getWorkShopWidth(),
                    frame.drawPanel.getWorkShopHeight()
            );

            if (vehicle instanceof Volvo240) {
                Rectangle vehicleRectangle = new Rectangle(x, y, frame.drawPanel.getVehicleWidth(i), frame.drawPanel.getVehicleHeight(i));

                if (vehicleRectangle.intersects(workShopRectangle)) {
                    volvo240Workshop.load((Volvo240) vehicle);
                    cars.remove(i);
                    frame.drawPanel.removeVehicle(i);
                    i--;
                    continue;
                }
            }

            frame.drawPanel.moveit(x, y, i);
        }
        frame.drawPanel.repaint();
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
}