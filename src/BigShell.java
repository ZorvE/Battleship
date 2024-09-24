import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class BigShell extends Shell {

    public BigShell(double x, double y) {

        super(x, y, 35);

        setFill(new ImagePattern(new Image("file:explosion.gif")));
    }

    public void friendlySide(){
        setOpacity(0.60);
    }

    public void enemySide(){
        setOpacity(1.0);
    }
}
