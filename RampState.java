public interface RampState {
    void raiseRamp(CarTransporter carTransporter);
    void lowerRamp(CarTransporter carTransporter);
    boolean isPlatformUp();
}
