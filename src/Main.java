import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    private SceneManager sceneManager;

    @Override
    public void start(Stage primaryStage) throws Exception {
        sceneManager = new SceneManager(primaryStage);

        // Load scenes
        sceneManager.loadScene("StartScene", "StartScene.fxml");
        sceneManager.loadScene("GameScene", "GameScene.fxml");
        sceneManager.loadScene("LoadPlayerScene", "LoadPlayerScene.fxml");
        sceneManager.loadScene("PlayerCreationScene", "PlayerCreationScene.fxml");

        // Set the SceneManager in each controller

        GameLogic gameLogic = GameLogic.getInstance();
        gameLogic.setSceneManager(sceneManager);// ??

        StartSceneController startSceneController = (StartSceneController) sceneManager.getController("StartScene");
        startSceneController.setSceneManager(sceneManager);

        GameSceneController gameSceneController = (GameSceneController) sceneManager.getController("GameScene");
        gameSceneController.setSceneManager(sceneManager);

        LoadPlayerController loadPlayerController = (LoadPlayerController) sceneManager.getController("LoadPlayerScene");
        loadPlayerController.setSceneManager(sceneManager);

        PlayerCreationSceneController playerCreationSceneController = (PlayerCreationSceneController) sceneManager.getController("PlayerCreationScene");
        playerCreationSceneController.setSceneManager(sceneManager);


        //set stage size.
        double [] sceneDimensions = sceneManager.getMaxSceneSizes();

        primaryStage.setWidth(sceneDimensions[0]);
        primaryStage.setHeight(sceneDimensions[1]);

        // Start with Start Scene
        sceneManager.switchTo("StartScene");
        primaryStage.setTitle("Battleship example");
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
