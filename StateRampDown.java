public class StateRampDown implements RampState {

    @Override
    public void raiseRamp(CarTransporter carTransporter) {
        carTransporter.setRampState(new StateRampUp());
    }

    @Override
    public void lowerRamp(CarTransporter carTransporter) {
        // Ramp is already down
    }

    @Override
    public boolean isPlatformUp() {
        return false;
    }
}
