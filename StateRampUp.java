public class StateRampUp implements RampState {

    @Override
    public void raiseRamp(CarTransporter carTransporter) {
        // Ramp is already up
    }

    @Override
    public void lowerRamp(CarTransporter carTransporter) {
        if(carTransporter.getCurrentSpeed() != 0){
            throw new IllegalStateException("Vehicle speed must be 0 to lower ramp");
        }
        carTransporter.setRampState(new StateRampDown());
    }

    @Override
    public boolean isPlatformUp() {
        return true;
    }
}
