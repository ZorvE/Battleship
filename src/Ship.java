import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class Ship extends Rectangle {

    private boolean isHit = false;

    private String name = "Boat name";

    Image image = new Image("file:ship.gif");

    public Ship (double x, double y){

        super(x, y, 100, 20);

        centralizePlacement();

        setFill(Color.GRAY);

        ImagePattern imgPattern = new ImagePattern(image, x, y, getWidth(), getHeight(), false);
        setFill(imgPattern);

    }

    public void hit(){
        isHit = true;

        setFill(Color.LIGHTGOLDENRODYELLOW); // eld eller brinnande .png (gif?) ska in här. (Still bild)
    }

    public boolean isShipHit() {
        return isHit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void centralizePlacement(){
        setLayoutX(-(getWidth()/2));
        setLayoutY(-(getHeight()/2));
    }
}
