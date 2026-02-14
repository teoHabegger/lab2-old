import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
* This class represents the Controller part in the MVC pattern.
* Its responsibilities are to listen to the View and responds in an appropriate manner by
* modifying the model state and the updating the view.
 */

public class CarController {
    // member fields:

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;
    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;
    // A list of cars, modify if needed
     ArrayList<Vehicle> cars = new ArrayList<>();

    final Workshop<Volvo240> volvo240Workshop = new Workshop<>(10);

    //methods:

    public static void main(String[] args) {
        // Instance of this class
        CarController cc = new CarController();

        cc.cars.add(new Volvo240());
        cc.cars.add(new Saab95());
        cc.cars.add(new Scania());

        cc.cars.get(0).setPosition(0, 0);
        cc.cars.get(1).setPosition(0, 100);
        cc.cars.get(2).setPosition(0, 200);

        // Start a new view and send a reference of self
        cc.frame = new CarView("CarSim 1.0", cc);

        // Start the timer
        cc.timer.start();
    }

    /* Each step the TimerListener moves all the cars in the list and tells the
    * view to update its images. Change this method to your needs.
    * */
    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
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
    }

    void rotate (Vehicle vehicle) {
        vehicle.turnRight();
        vehicle.turnRight();
    }

    // Calls the gas method for each car once
    void gas(int amount) {
        double gas = ((double) amount) / 100;
        for (Vehicle car : cars)
            car.gas(gas);
    }

    void brake (int amount) {
        double brake = ((double) amount) / 100;
        for (Vehicle car : cars)
            car.brake(brake);
    }

    void startEngine() {
        for (Vehicle car : cars)
            car.startEngine();
    }

    void stopEngine() {
        for (Vehicle car : cars)
            car.stopEngine();
    }
}
