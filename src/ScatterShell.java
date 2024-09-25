import java.util.ArrayList;
import java.util.Random;

public class ScatterShell extends ArrayList<Shell> {

    private double hitRadius = 55.0;  // Radius within which the shots are scattered
    private int numberOfShots = 6;    // Number of shots to fire
    private static final Random random = new Random();  // Static Random instance

    public ScatterShell(double x, double y) {
        // Generate shells with random offsets
        for (int i = 0; i < numberOfShots; i++) {
            // Generate random positions within the hit radius
            double randomX = (random.nextDouble() * hitRadius) - (hitRadius / 2.0);
            double randomY = (random.nextDouble() * hitRadius) - (hitRadius / 2.0);

            // Create new NormalShell and add it to the list
            NormalShell shell = new NormalShell(x + randomX, y + randomY);
            add(shell);
        }
    }
}
