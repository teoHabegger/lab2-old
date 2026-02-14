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

    // TODO: Make this general for all cars
    void moveit(int x, int y, int index){
        vehiclePoints.get(index).x = x;
        vehiclePoints.get(index).y = y;
    }

    // Initializes the panel and reads the images
    public DrawPanel(int x, int y) {
        this.setDoubleBuffered(true);
        this.setPreferredSize(new Dimension(x, y));
        this.setBackground(Color.green);

        vehicleImages = new ArrayList<>();
        vehiclePoints = new ArrayList<>();

        // Print an error message in case file is not found with a try/catch block
        try {
            // You can remove the "pics" part if running outside of IntelliJ and
            // everything is in the same main folder.
            // volvoImage = ImageIO.read(new File("Volvo240.jpg"));

            // Rememember to rightclick src New -> Package -> name: pics -> MOVE *.jpg to pics.
            // if you are starting in IntelliJ.
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

        vehiclePoints.add(new Point(0, 0));
        vehiclePoints.add(new Point(0, 100));
        vehiclePoints.add(new Point(0, 200));
    }

    // This method is called each time the panel updates/refreshes/repaints itself
    // TODO: Change to suit your needs.
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (int i = 0; i < vehicleImages.size(); i++) {
            Point point = vehiclePoints.get(i);
            g.drawImage(vehicleImages.get(i), point.x, point.y, null);
        }
        g.drawImage(volvoWorkshopImage, volvoWorkshopPoint.x, volvoWorkshopPoint.y, null);
    }

    public int getVehicleWidth(int index) {
        return vehicleImages.get(index).getWidth();
    }

    public int getVehicleHeight(int index) {
        return vehicleImages.get(index).getHeight();
    }

    public int getWorkShopX() { return volvoWorkshopPoint.x; }
    public int getWorkShopY() { return volvoWorkshopPoint.y; }

    public int getWorkShopWidth() { return volvoWorkshopImage.getWidth(); }
    public int getWorkShopHeight() { return volvoWorkshopImage.getHeight(); }

    public void removeVehicle (int index) {
        vehicleImages.remove(index);
        vehiclePoints.remove(index);
    }

}
