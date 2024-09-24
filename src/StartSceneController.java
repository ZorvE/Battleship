import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class StartSceneController {
    private SceneManager sceneManager;

    @FXML
    private Button createPlayerButton;

    @FXML
    private Button loadPlayerOneButton;

    @FXML
    private Button loadPlayerTwoButton;

    @FXML
    private ImageView playerOneImage;

    @FXML
    private Label playerOneLabel;

    @FXML
    private Label playerOneName;

    @FXML
    private ImageView playerTwoImage;

    @FXML
    private Label playerTwoLabel;

    @FXML
    private Label playerTwoName;

    @FXML
    private Button startGameButton;

    GameLogic gameLogic = GameLogic.getInstance();

    public void initialize() {
        try {
            Image defaultPlayerImage = new Image("file:char_default.jpg");

            playerOneImage.setImage(defaultPlayerImage);
            playerTwoImage.setImage(defaultPlayerImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        startGameButton.setDisable(true);
    }

    @FXML
    void goToCreatePlayerScene() {
        sceneManager.switchTo("PlayerCreationScene");
    }

    @FXML
    void goToGameScene() {
        gameLogic.startGame();
        sceneManager.switchTo("GameScene");
    }

    @FXML
    void goToLoadPlayerScene(ActionEvent event) {
        LoadPlayerController loadPlayerController = (LoadPlayerController) sceneManager.getController("LoadPlayerScene");

        if(event.getSource() == loadPlayerOneButton){
            loadPlayerController.setPlayerSlotToLoad(1);
        } else if (event.getSource() == loadPlayerTwoButton) {
            loadPlayerController.setPlayerSlotToLoad(2);
        }

        loadPlayerController.updatePlayerList();
        sceneManager.switchTo("LoadPlayerScene");
    }

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void enableStartGameButton(){
        startGameButton.setDisable(false);
    }

    public void setPlayerOneNameLabel(String playerName){
        playerOneName.setText(playerName);
    }

    public void setPlayerTwoNameLabel(String playerName){
        playerTwoName.setText(playerName);
    }

    public void setPlayerOneImage(String playerImage){
        playerOneImage.setImage(new Image("file:" + playerImage));
    }

    public void setPlayerTwoImage(String playerImage){
        playerTwoImage.setImage(new Image("file:" + playerImage));
    }

}
