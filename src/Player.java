import javafx.scene.layout.Pane;

import java.util.ArrayList;

public class Player {


    private String playerName;

    private String playerImage;

    private int playerHighScore;

    private ArrayList<Ship> ships = new ArrayList<>();

    private ArrayList<Shell> shells = new ArrayList<>();

    private boolean isPlayerOkToShot = true;

    private boolean isShipPlacementActive = true;

    private Pane playerPane;

    private int maxAllowedOfShipsToOwn = 5;


    public void setPlayerName(String playerName){
        this.playerName = playerName;
    }

    public void setPlayerImage(String playerImage){
        this.playerImage = playerImage;
    }

    public void setHighScore(int playerHighScore){
        this.playerHighScore = playerHighScore;
    }

    public String getName(){
        return playerName;
    }

    public String getImageName(){
        return playerImage;
    }

    public boolean isShipPlacementActive(){
        return isShipPlacementActive;
    }

    public void shipPlacementFinished(){
        isShipPlacementActive = false;
    }

    public boolean isShipLimitReached(){
        if(ships.size() >= maxAllowedOfShipsToOwn){
            return true;
        }
        return false;
    }

    public void setMaxNumberOfShips(int numberOfShips){
        maxAllowedOfShipsToOwn = numberOfShips;
    }

    public Pane getPane(){
        return playerPane;
    }

    public void setPlayerPane(Pane pane){
        this.playerPane = pane;
    }

    public ArrayList<Ship> getShips(){
        return ships;
    }

    public ArrayList<Shell> getShells(){
        return shells;
    }

    public void addShell(Shell shell){
        shells.add(shell);
    }

    public void addShell(ArrayList<Shell> shells){//ny
        shells.addAll(shells);
    }

    public void addShip(Ship ship){
        ships.add(ship);
    }

    public void removeShip(Ship ship){
        ships.remove(ship);
    }

    public int getScore(){
        return playerHighScore;
    }

    public void setScore(int score){
        this.playerHighScore = score;
    }

    public boolean isPlayerOkToShot(){
        return isPlayerOkToShot;
    }
}
