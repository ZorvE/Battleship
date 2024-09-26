import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class GameSceneController {

    GameLogic gameLogic = GameLogic.getInstance();

    @FXML
    private Button endTurn;

    private SceneManager sceneManager;

    @FXML
    private Pane playerOneGameArea;

    @FXML
    private Label playerOneNameLabel;

    @FXML
    private ImageView playerOneProfileImage;

    @FXML
    private Pane playerTwoGameArea;

    @FXML
    private Label playerTwoNameLabel;

    @FXML
    private ImageView playerTwoProfileImage;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void paneIsClicked(MouseEvent event){


        if(event.getButton() == MouseButton.PRIMARY){

            if(!gameLogic.getActivePlayer().isShipPlacementActive()){
                gameLogic.shootShell(event);
            }
            else {
                gameLogic.placeShip(event);
            }
        }


        else if(event.getButton() == MouseButton.SECONDARY){ //se till så att "gammla placeringar" tas bort så att de inte kan bli träffade
            if(gameLogic.getActivePlayer().isShipPlacementActive()) {
                gameLogic.rotateShip(event);
            }
        }
    }

    @FXML
    void endTurn(ActionEvent event) {
        gameLogic.endTurn();
    }

    public void setPlayerOneNameLabel(String playerName){
        playerOneNameLabel.setText(playerName);
    }

    public void setPlayerTwoNameLabel(String playerName){
        playerTwoNameLabel.setText(playerName);
    }

    public void setPlayerOneProfileImage(String playerImage){
        try{
            playerOneProfileImage.setImage(new Image("file:" + playerImage));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void setPlayerTwoProfileImage(String playerImage){
        try{
            playerTwoProfileImage.setImage(new Image("file:" + playerImage));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Pane getPlayerOneGameArea(){
        return playerOneGameArea;
    }

    public Pane getPlayerTwoGameArea(){
        return playerTwoGameArea;
    }

    public void viewForActivePlayer(Player activePlayer, Player enemyPlayer){

        enemyPlayer.getPane().getStyleClass().clear();
        activePlayer.getPane().getStyleClass().clear();

        if (!enemyPlayer.getPane().getStyleClass().contains("background-pane")) {

            enemyPlayer.getPane().getStyleClass().add("background-pane");
        }
        if (!activePlayer.getPane().getStyleClass().contains("background-pane")) {
            activePlayer.getPane().getStyleClass().add("background-pane");
        }
        /*     Snygga till det ovan???     */
        if (activePlayer.isShipPlacementActive() || enemyPlayer.isShipPlacementActive()) {

            activePlayer.getPane().getStyleClass().add("place-ships-indicator");
            enemyPlayer.getPane().getStyleClass().add("unable-to-place-ships-indicator");
        }
        else {
            enemyPlayer.getPane().getStyleClass().add("attack-field-indicator");
        }

        for(Node node : activePlayer.getPane().getChildren()){
            if (node instanceof Ship){
                node.setVisible(true);
            }
            if(node instanceof Shell){
                ((Shell) node).friendlySide();
            }
        }

        for(Node node : enemyPlayer.getPane().getChildren()){
            if (node instanceof Ship){
                if(!((Ship) node).isShipHit()) {
                    node.setVisible(false);
                }
            }
            if(node instanceof Shell){
                ((Shell) node).enemySide();
            }
        }
    }

    @FXML
    void useTypeOneShell(ActionEvent event) {
        gameLogic.setShelltype(1);
    }

    @FXML
    void useTypeThreeShell(ActionEvent event) {
        gameLogic.setShelltype(2);
    }

    @FXML
    void useTypeTwoShell(ActionEvent event) {

        gameLogic.setShelltype(3);
    }
}
