public class VehicleFactory {

    public static Vehicle createVehicle(VehicleType type) {
        return switch (type) {
            case SCANIA -> new Scania();
            case VOLVO240 -> new Volvo240();
            case SAAB95 -> new Saab95();
            case TRANSPORTER -> new CarTransporter();
        };
    }
    }
