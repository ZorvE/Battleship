import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class PlayerCreationSceneController {//ska kunna ta bort spelare också?? se till att det inte går att skapa "tom" spelare


    DatabaseManager databaseManager = DatabaseManager.getInstance();

    @FXML
    private Button createPlayerProfileButton;

    @FXML
    private RadioButton imageChoiceFour;

    @FXML
    private RadioButton imageChoiceOne;

    @FXML
    private RadioButton imageChoiceThree;

    @FXML
    private RadioButton imageChoiceTwo;

    @FXML
    private ImageView playerImageFour;

    @FXML
    private ImageView playerImageOne;

    @FXML
    private ImageView playerImageThree;

    @FXML
    private ImageView playerImageTwo;

    @FXML
    private TextField playerNameField;

    private Image imageOne;
    private Image imageTwo;
    private Image imageThree;
    private Image imageFour;

    private String imageOneName = "char1.jpg";
    private String imageTwoName = "char2.jpg";
    private String imageThreeName = "char3.jpg";
    private String imageFourName = "char4.jpg";

    private ToggleGroup toggleGroup = new ToggleGroup();;

    @FXML
    void createPlayer(ActionEvent event) {//se till att det finns en namn i textfältet

        databaseManager.savePlayer(playerNameField.getText(), chosenImage(), 0);

        sceneManager.switchTo("StartScene");
        playerNameField.setText(null);
        toggleGroup.selectToggle(null);

    }

    private String chosenImage(){

        String imageFileName ="";
        RadioButton selectedButton = (RadioButton) toggleGroup.getSelectedToggle();
        if(selectedButton == imageChoiceOne) {
            imageFileName = imageOneName;
        } else if (selectedButton ==imageChoiceTwo) {
            imageFileName = imageTwoName;
        }
        else if (selectedButton == imageChoiceThree) {
            imageFileName = imageThreeName;
        }
        else if (selectedButton == imageChoiceFour) {
            imageFileName = imageFourName;
        }
        else {
            System.out.println("No image chosen!");//Göra till popup varnings ruta?
        }

        return imageFileName;
    }

    @FXML
    private void initialize() {
        imageChoiceOne.setToggleGroup(toggleGroup);
        imageChoiceTwo.setToggleGroup(toggleGroup);
        imageChoiceThree.setToggleGroup(toggleGroup);
        imageChoiceFour.setToggleGroup(toggleGroup);

        try {
            imageOne = new Image("file:" + imageOneName);
            imageTwo = new Image("file:" + imageTwoName);
            imageThree = new Image("file:" + imageThreeName);
            imageFour = new Image("file:" + imageFourName);
        } catch (Exception e) {
            e.printStackTrace();
        }

        playerImageOne.setImage(imageOne);
        playerImageTwo.setImage(imageTwo);
        playerImageThree.setImage(imageThree);
        playerImageFour.setImage(imageFour);
    }

    private SceneManager sceneManager;

    public void setSceneManager(SceneManager sceneManager) {
        this.sceneManager = sceneManager;
    }
}
