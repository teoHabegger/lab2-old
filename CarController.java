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

    public static void main(String[] args) {
        CarController cc = new CarController();

        cc.sim.addVehicle(VehicleFactory.createVehicle(VehicleType.VOLVO240));
        cc.sim.addVehicle(VehicleFactory.createVehicle(VehicleType.SAAB95));
        cc.sim.addVehicle(VehicleFactory.createVehicle(VehicleType.SCANIA));

        cc.sim.getCars().get(0).setPosition(0, 0);
        cc.sim.getCars().get(1).setPosition(0, 100);
        cc.sim.getCars().get(2).setPosition(0, 200);

        cc.frame = new CarView("CarSim 1.0", cc.sim, cc);

        cc.timer.start();

        cc.sim.addObservers(cc.frame);
    }

    private class TimerListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // NEW: one call, all previous tick logic is inside Simulation
            sim.tick();
        }
    }

    // UI commands delegate to Simulation (logic moved there)
    void gas(int amount) { sim.gas(sim.getCars(), amount); }

    void brake(int amount) { sim.brake(sim.getCars(), amount); }

    void startEngine() { sim.startEngine(sim.getCars()); }

    void stopEngine() { sim.stopEngine(sim.getCars()); }

    void setTurboOn() { sim.setTurboOn(sim.getCars()); }

    void setTurboOff() { sim.setTurboOff(sim.getCars()); }

    void liftBed() { sim.liftBed(sim.getCars()); }

    void lowerBed() { sim.lowerBed(sim.getCars()); }

    void addCar() { sim.addCAr();}

    void removeCar() { sim.removeCar(); }
}