import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseManager {

    private static DatabaseManager databaseManager;

    private final String saveFile = "save.txt"; // SQL för laddning/sparning mellan spel. csv för buffring av spalre data. json för buffring av placering på skepp och skott.

    public static DatabaseManager getInstance() {
        if (databaseManager == null) {
            databaseManager = new DatabaseManager();
        }
        return databaseManager;
    }
    public Player loadPlayer(String playerName){
        try {
            FileReader fileReader = new FileReader(saveFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(";");

                if (tokens[0].equals(playerName)) {
                    bufferedReader.close();
                    fileReader.close();
                    Player foundPlayer = new Player();

                    foundPlayer.setPlayerName(tokens[0]);
                    foundPlayer.setPlayerImage(tokens[1]);
                    foundPlayer.setScore(Integer.parseInt(tokens[2]));

                    return foundPlayer;
                }
            }

            bufferedReader.close();
            fileReader.close();
        } catch (FileNotFoundException e) {
            System.err.println("The save file is not found!");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
        return null;
    }

    public void savePlayer(String playerName, String playerImage, int playerHighScore){

        try {
            FileWriter fileWriter = new FileWriter(saveFile,true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(playerName).append(";").append(playerImage).append(";").append(playerHighScore).append("\n");

            bufferedWriter.write(stringBuilder.toString());

            bufferedWriter.close();

        } catch (IOException e) {
            System.err.println("Can not save game data " + e.getMessage());
        }
    }

    public List<String> getSavedPlayerNames(){
        try {
            FileReader fileReader = new FileReader(saveFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> options = new ArrayList<>();

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] tokens = line.split(";");
                options.add(tokens[0]);
            }
            bufferedReader.close();
            fileReader.close();

            return options;

        } catch (FileNotFoundException e) {
            System.err.println("The save file is not found!");
        } catch (IOException e) {
            System.err.println();
        }
        return null;
    }

    //behöver ha med båda spelarna annars
    public void endGameSave(String playerName, String playerImage, int playerHighScore){
        try {
            // Reading current saved players from the file
            FileReader fileReader = new FileReader(saveFile);
            BufferedReader bufferedReader = new BufferedReader(fileReader);

            List<String> savedPlayers = new ArrayList<>();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                savedPlayers.add(line);  // Save each player line
            }
            bufferedReader.close(); // Close the reader after reading

            // Prepare the updated list of players
            ArrayList<String> playersToSave = new ArrayList<>();

            boolean playerFound = false; // Track if the player is already in the file

            for (String originalPlayer : savedPlayers) {
                String[] tokens = originalPlayer.split(";");
                if (tokens[0].equals(playerName)) {
                    // If the player is found, update the information
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(playerName).append(";").append(playerImage).append(";").append(playerHighScore).append("\n");
                    playersToSave.add(stringBuilder.toString());
                    playerFound = true;
                } else {
                    // Add other players without modification
                    playersToSave.add(originalPlayer + "\n");
                }
            }

            // Write all players back to the file
            FileWriter newFileWriter = new FileWriter(saveFile); // Overwrite mode
            BufferedWriter newBufferedWriter = new BufferedWriter(newFileWriter);

            for (String player : playersToSave) {
                newBufferedWriter.write(player); // Write each player info
            }

            // Close the writer after writing all players
            newBufferedWriter.close();
        } catch (FileNotFoundException e) {
            System.err.println("The save file is not found!");
        } catch (IOException e) {
            System.err.println("Error reading the file: " + e.getMessage());
        }
    }
}
