import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

// This panel represents the animated part of the view with the car images.

public class DrawPanel extends JPanel{

    ArrayList<BufferedImage> vehicleImages;
    ArrayList<Point> vehiclePoints;

    BufferedImage volvoWorkshopImage;
    Point volvoWorkshopPoint = new Point(0,400);

    private Simulation sim;

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y, Simulation sim) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);
        this.sim = sim;

        vehicleImages = new ArrayList<>();

        try {
            BufferedImage volvoImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Volvo240.jpg"));
            BufferedImage saabImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Saab95.jpg"));
            BufferedImage scaniaImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/Scania.jpg"));
            volvoWorkshopImage = ImageIO.read(DrawPanel.class.getResourceAsStream("pics/VolvoBrand.jpg"));

            vehicleImages.add(volvoImage);
            vehicleImages.add(saabImage);
            vehicleImages.add(scaniaImage);

        } catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        ArrayList<Vehicle> cars = sim.getCars();

        for (int i = 0; i < cars.size(); i++) {
            Vehicle vehicle = sim.getCars().get(i);
            BufferedImage currentImage;

            if (vehicle instanceof Volvo240) currentImage = vehicleImages.get(0);
            else if (vehicle instanceof Saab95) currentImage = vehicleImages.get(1);
            else if (vehicle instanceof Scania) currentImage = vehicleImages.get(2);
            else continue;

            g.drawImage(
                    currentImage, (int) vehicle.getX(), (int) vehicle.getY(),
                    vehicle.getWidth(), vehicle.getHeight(), null
            );
        }

        Workshop<Volvo240> workshop = sim.getWorkshop();

        g.drawImage(
                volvoWorkshopImage, workshop.getX(), workshop.getY(),
                workshop.getWidth(), workshop.getHeight(), null);
    }
}
