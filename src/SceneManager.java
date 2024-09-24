import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SceneManager {

    private final Stage primaryStage;
    private final Map<String, Scene> scenes = new HashMap<>();
    private final Map<String, Object> controllers = new HashMap<>();

    // Constructor: Accepts the primary stage and initializes it
    public SceneManager(Stage primaryStage) {
        this.primaryStage = primaryStage;
    }

    // Method to load and store a scene
    public void loadScene(String name, String fxmlPath) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        scene.getStylesheets().add(getClass().getResource("main/resources/battleship-styles.css").toExternalForm());


        scenes.put(name, scene);
        controllers.put(name, loader.getController());
    }

    public double[] getMaxSceneSizes(){
        double[] sceneDimensions = {0,0};
        for(Scene scene: scenes.values()){
            double width = scene.getRoot().prefWidth(-1);
            double height = scene.getRoot().prefHeight(-1);

            sceneDimensions[0] = Math.max(sceneDimensions[0], width);
            sceneDimensions[1] = Math.max(sceneDimensions[1], height);
        }
        return sceneDimensions;
    }

    // Method to switch scenes
    public void switchTo(String name) {
        Scene scene = scenes.get(name);
        if (scene != null) {
            primaryStage.setScene(scene);
        } else {
            System.err.println("Scene " + name + " not found! Make sure it is loaded.");
        }
    }

    // Method to get the controller of a specific scene
    public Object getController(String name) {
        return controllers.get(name);
    }
}
