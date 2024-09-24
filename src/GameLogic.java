import javafx.geometry.Bounds;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;

public class GameLogic {
    private static GameLogic gameLogic;

    private SceneManager sceneManager;

    DatabaseManager databaseManager = DatabaseManager.getInstance();

    private int playersTurn;

    private int maxShipsPerPlayer = 5;

    private Player activePlayer, enemyPlayer, playerOne, playerTwo;

    private boolean isGameOver = false;

    private int activeShellType = 0;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }

    public void randomizePlayerToStartGame(){

        playersTurn = new Random().nextInt(2) + 1;

        GameSceneController gameSceneController = (GameSceneController) sceneManager.getController("GameScene");

        if (playersTurn == 1) {
            activePlayer = playerOne;
            enemyPlayer = playerTwo;
            gameSceneController.viewForActivePlayer(activePlayer, enemyPlayer);
        } else if (playersTurn == 2) {
            activePlayer = playerTwo;
            enemyPlayer = playerOne;
            gameSceneController.viewForActivePlayer(activePlayer, enemyPlayer);
        }
    }

    public void startGame(){
        randomizePlayerToStartGame();
    }

    public static GameLogic getInstance() {
        if (gameLogic == null) {
            gameLogic = new GameLogic();
        }
        return gameLogic;
    }

    public void placeShip(MouseEvent event) {// change name of method?

        // Check if the click source is the PlayerPane
        if (event.getSource() == activePlayer.getPane()) {
            Pane clickedPane = (Pane) event.getSource();

            boolean clickedOnExistingShip = false;

            if(activePlayer.isShipPlacementActive()) {
                //remove ships
                for (Ship ship : activePlayer.getShips()) {
                    if (ship.contains(event.getX(), event.getY())) {
                        clickedPane.getChildren().remove(ship);
                        activePlayer.removeShip(ship);
                        clickedOnExistingShip = true;
                        break;
                    }
                }

                if(checkShipPlacement(new Ship(event.getX(), event.getY()), event)){
                   //place ship if possible
                    if(!activePlayer.isShipLimitReached()) {
                        if (!clickedOnExistingShip) {
                            activePlayer.addShip(new Ship(event.getX(), event.getY()));
                        }
                    }
                }
                else {
                    System.out.println("can not place ship here!");
                }
            }

            // Repaint ships
            clickedPane.getChildren().clear();
            for (Ship ship : activePlayer.getShips()) {
                clickedPane.getChildren().add(ship);
            }

            // Repaint shells
            for (Shell shell : enemyPlayer.getShells()) {
                clickedPane.getChildren().add(shell);
            }
        }
    }

    public void rotateShip(MouseEvent event){ //skepp vill ej rotera ibland när flera skepp är utplacerade. (??)
        for (Ship ship : activePlayer.getShips()) {
            if (ship.contains(event.getX(), event.getY())) {
                ship.setRotate(ship.getRotate() + 45);
                break;
            }
        }
    }

    public boolean checkShipPlacement(Ship ship, MouseEvent event){//olika skeppa kan sedan föras in här.

        double shipWidthConstraint = ship.getWidth()/2;
        double shipHeightConstraint = ship.getHeight()/2;

        Pane clickedPane = (Pane) event.getSource();

        if(event.getX() > shipWidthConstraint && event.getY() > shipHeightConstraint && event.getX() < clickedPane.getWidth() - shipWidthConstraint && event.getY() < clickedPane.getHeight() - shipHeightConstraint){
            return true;
        }
        return false;
    }

    public void shootShell(MouseEvent event){

        if(!activePlayer.isShipPlacementActive()) {

            if (event.getSource() == enemyPlayer.getPane()) {

                Pane clickedPane = (Pane) event.getSource();

                if(activeShellType == 1) {
                    NormalShell newShell = new NormalShell(event.getX(), event.getY()); //fixa till "normal"
                    activePlayer.addShell(newShell);
                    clickedPane.getChildren().add(newShell);
                }

                else if(activeShellType == 2) {
                    ScatterShell newScatterShell = new ScatterShell(event.getX(), event.getY());

                    activePlayer.addShell(newScatterShell);

                    for (Shell shell : newScatterShell) {//strul med detta?? runtime error-ish??
                        clickedPane.getChildren().add(shell);
                    }

                    /*for (Ship ship : activePlayer.getShips()) {
                        clickedPane.getChildren().add(ship);
                    }

                    // Repaint shells
                    for (Shell shell : enemyPlayer.getShells()) {
                        clickedPane.getChildren().add(shell);
                    }*/
                }
                else if(activeShellType == 3){
                    BigShell newBigShell = new BigShell(event.getX(), event.getY());
                    activePlayer.addShell(newBigShell);
                    clickedPane.getChildren().add(newBigShell);
                }


                ArrayList<Bounds> shellBounds = new ArrayList<>();
                ArrayList<Bounds> shipBounds = new ArrayList<>();

                for (Shell shot : activePlayer.getShells()) {
                    shellBounds.add(shot.getBoundsInParent());
                }

                if (activePlayer == playerOne) {
                    for (Ship ship : playerTwo.getShips()) {
                        shipBounds.add(ship.getBoundsInParent());
                    }
                } else {
                    for (Ship ship : playerOne.getShips()) {
                        shipBounds.add(ship.getBoundsInParent());
                    }
                }

                for (Bounds boundsShot : shellBounds) {
                    for (Bounds boundsShip : shipBounds) {
                        if (boundsShot.intersects(boundsShip)) {
                            int hitShipIndex = shipBounds.indexOf(boundsShip);
                            if (activePlayer == playerOne) {
                                playerTwo.getShips().get(hitShipIndex).hit();
                                playerTwo.getShips().get(hitShipIndex).setVisible(true);
                            }
                            if (activePlayer == playerTwo) {
                                playerOne.getShips().get(hitShipIndex).hit();
                                playerOne.getShips().get(hitShipIndex).setVisible(true);
                            }
                        }
                    }
                }
            }
        }

        checkIfPlayerWon();
    }

    public void endTurn(){

        GameSceneController gameSceneController = (GameSceneController) sceneManager.getController("GameScene");

        if (activePlayer == playerOne) {

            if(activePlayer.isShipPlacementActive()) {//göra egen funktion av detta.
                if (activePlayer.isShipLimitReached()) {
                    activePlayer.shipPlacementFinished();
                }
            }

            activePlayer = playerTwo;
            enemyPlayer = playerOne;
            gameSceneController.viewForActivePlayer(activePlayer, enemyPlayer);


        } else if (activePlayer == playerTwo) {
            if(activePlayer.isShipPlacementActive()) {//göra egen funktion av detta.
                if (activePlayer.isShipLimitReached()) {
                    activePlayer.shipPlacementFinished();
                }
            }

            activePlayer = playerOne;
            enemyPlayer = playerTwo;
            gameSceneController.viewForActivePlayer(activePlayer, enemyPlayer);
        }
    }

    private void swapPlayers(){// för ovan.

    }

    public void setPlayer(String playerName, int playerSlot){

        GameSceneController gameSceneController = (GameSceneController) sceneManager.getController("GameScene");

        if(playerSlot == 1) {
            playerOne = databaseManager.loadPlayer(playerName);
            playerOne.setPlayerPane(gameSceneController.getPlayerOneGameArea());
        }

        if(playerSlot == 2) {
            playerTwo = databaseManager.loadPlayer(playerName);
            playerTwo.setPlayerPane(gameSceneController.getPlayerTwoGameArea());
        }
    }

    public void endGame(){ //se till så att den gammla datan sparas över av den nya
        if(!isGameOver){
            databaseManager.endGameSave(activePlayer.getName(), activePlayer.getImageName(), activePlayer.getScore()+3);
            databaseManager.endGameSave(enemyPlayer.getName(), enemyPlayer.getImageName(), enemyPlayer.getScore()+1);
            isGameOver = true;
        }
    }

    private void checkIfPlayerWon(){
        int numberOfShipsHit = 0;
        for(Ship ship : enemyPlayer.getShips()){
            if(ship.isShipHit()){
                numberOfShipsHit++;
            }
        }
        if(numberOfShipsHit >= maxShipsPerPlayer){
            endGame();
        }
    }

    public boolean isPlayerSlotsFilled(){
        return playerOne != null && playerTwo != null;
    }

    public Player getPlayerOne(){
        return playerOne;
    }

    public Player getPlayerTwo(){
        return playerTwo;
    }

    public Player getActivePlayer(){
        return activePlayer;
    }

    public void setShelltype(int shellTypeNumber){
        if(shellTypeNumber <= 3 && shellTypeNumber >= 1) {
            this.activeShellType = shellTypeNumber;
        }
        else {
            System.out.println("Needs to be a number 1, 2 or 3");
        }
    }
}
