import java.util.ArrayList;
import java.util.Random;

public class ScatterShell extends ArrayList<Shell> {

    private double hitRadius = 55;

    private int numberOfShots = 6;

    private Random random;

    public ScatterShell(double x, double y){

        random = new Random();

        for(int i = 0; i <= numberOfShots; i++) {

            double randomX = random.nextDouble(hitRadius + 1) - hitRadius / 2;
            double randomY = random.nextDouble(hitRadius + 1) - hitRadius / 2;

            NormalShell shell = new NormalShell( x + randomX, y + randomY);
            add(shell);
        }
    }
}
