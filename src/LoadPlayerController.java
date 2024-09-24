import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;

public class LoadPlayerController {

    DatabaseManager databaseManager = DatabaseManager.getInstance();
    GameLogic gameLogic = GameLogic.getInstance();

    private int playerSlotToLoad;

    @FXML
    private Button backToStartButton;

    @FXML
    private ComboBox<String> playerProfiles;


    private SceneManager sceneManager;

    @FXML
    void backToStartScene() {// se till att det inte blir error om man trycker på tillbakaknappen men inte valt något i menyn.
        gameLogic.setPlayer(playerProfiles.getValue(), playerSlotToLoad);


        StartSceneController startSceneController = (StartSceneController) sceneManager.getController("StartScene");

        if (gameLogic.isPlayerSlotsFilled()){
            startSceneController.enableStartGameButton();

            GameSceneController gameSceneController = (GameSceneController) sceneManager.getController("GameScene");

            gameSceneController.setPlayerOneNameLabel(gameLogic.getPlayerOne().getName());
            gameSceneController.setPlayerTwoNameLabel(gameLogic.getPlayerTwo().getName());
            gameSceneController.setPlayerOneProfileImage(gameLogic.getPlayerOne().getImageName());
            gameSceneController.setPlayerTwoProfileImage(gameLogic.getPlayerTwo().getImageName());
        }

        if(playerSlotToLoad == 1 ){
            startSceneController.setPlayerOneImage(gameLogic.getPlayerOne().getImageName());
            startSceneController.setPlayerOneNameLabel(gameLogic.getPlayerOne().getName());
        } else if(playerSlotToLoad == 2){
            startSceneController.setPlayerTwoImage(gameLogic.getPlayerTwo().getImageName());
            startSceneController.setPlayerTwoNameLabel(gameLogic.getPlayerTwo().getName());
        }

        sceneManager.switchTo("StartScene");
        playerProfiles.setValue(null);
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void updatePlayerList(){
        ObservableList<String> playerNames = FXCollections.observableArrayList(databaseManager.getSavedPlayerNames());
        playerProfiles.setItems(playerNames);
    }

    public void setPlayerSlotToLoad(int playerSlotToLoad){
        this.playerSlotToLoad = playerSlotToLoad;
    }
}
