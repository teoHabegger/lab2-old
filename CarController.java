import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
 * This class represents the Controller part in the MVC pattern.
 */

public class CarController {

    // The delay (ms) corresponds to 20 updates a sec (hz)
    private final int delay = 50;

    // NEW: simulation holds the moved logic
    private final Simulation sim = new Simulation();

    // The timer is started with a listener (see below) that executes the statements
    // each step between delays.
    private Timer timer = new Timer(delay, new TimerListener());

    // The frame that represents this instance View of the MVC pattern
    CarView frame;

    ArrayList<Vehicle> cars = new ArrayList<>();

    final Workshop<Volvo240> volvo240Workshop = new Workshop<>(10);

    public static void main(String[] args) {
        CarController cc = new CarController();

        cc.cars.add(new Volvo240());
        cc.cars.add(new Saab95());
        cc.cars.add(new Scania());

        cc.cars.get(0).setPosition(0, 0);
        cc.cars.get(1).setPosition(0, 100);
        cc.cars.get(2).setPosition(0, 200);

        cc.frame = new CarView("CarSim 1.0", cc);

        cc.timer.start();
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // NEW: one call, all previous tick logic is inside Simulation
            sim.tick(frame, cars, volvo240Workshop, e);
        }
    }

    // UI commands delegate to Simulation (logic moved there)
    void gas(int amount) { sim.gas(cars, amount); }

    void brake(int amount) { sim.brake(cars, amount); }

    void startEngine() { sim.startEngine(cars); }

    void stopEngine() { sim.stopEngine(cars); }

    void setTurboOn() { sim.setTurboOn(cars); }

    void setTurboOff() { sim.setTurboOff(cars); }

    void liftBed() { sim.liftBed(cars); }

    void lowerBed() { sim.lowerBed(cars); }
}