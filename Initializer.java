import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.FileChooser;
import javafx.application.Platform;
import java.io.File;
import java.util.Optional;

// Class responsible for initializing the game.
public class Initializer {

	private Stage primaryStage;
	private TriviaGameController controller;
	private Scene scene;

	public Initializer(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	public boolean initializeGame() {

		// Show the welcome alert to the user.
		if (!showWelcomeAlert()) {
			return false;
		}

		// Load the FXML and set the scene.
		try {
			File fxmlFile = new File("bin/TriviaGame.fxml");
			if (!fxmlFile.exists()) {
				throw new RuntimeException("FXML file not found!");
			}

			FXMLLoader loader = new FXMLLoader(fxmlFile.toURI().toURL());
			scene = new Scene(loader.load());
			controller = loader.getController();
			controller.setInitializer(this);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		// Allow the user to choose the trivia file.
		File selectedFile = chooseFile();
		if (selectedFile != null) {
			controller.setFilePath(selectedFile.getAbsolutePath());
			return true;
		}

		// If no file is selected, show error message.
		showErrorAlert();
		return false;
	}

	private boolean showWelcomeAlert() {
		Alert alert = new Alert(Alert.AlertType.NONE);
		alert.setTitle(" ");
		alert.setHeaderText("Welcome to the Trivia Game!");
		Label contentLabel = new Label(
				"Are you ready for a trivia challenge?\n" + "\n"
						+ "Please upload the text file of the questions bank.");
		contentLabel.setStyle("-fx-font-size: 15px; -fx-font-weight: normal; -fx-alignment: center;");
		alert.getDialogPane().setContent(contentLabel);

		ButtonType okButton = new ButtonType("Start");
		alert.getButtonTypes().setAll(okButton);

		Optional<ButtonType> result = alert.showAndWait();
		return result.isPresent();
	}

	private File chooseFile() {
		FileChooser fileChooser = new FileChooser();
		fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));
		return fileChooser.showOpenDialog(primaryStage);
	}

	private void showErrorAlert() {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error");
		alert.setHeaderText("No file selected!");
		alert.setContentText("Please select a valid trivia file to play.");
		alert.showAndWait();
	}

	public Scene getScene() {
		return scene;
	}

	public void showExitAlert(Stage primaryStage) {
		int currentScore = controller.getScore();
		Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
		alert.setTitle("Exit");
		alert.setHeaderText("Are you sure you want to exit?");
		alert.setContentText("Your current score is: " + currentScore + "\n"
				+ "\nIf you exit, the game will be closed.");

		ButtonType startNewGameButton = new ButtonType("Start New Game");
		ButtonType exitButton = new ButtonType("Exit", ButtonType.CANCEL.getButtonData());
		alert.getButtonTypes().setAll(startNewGameButton, exitButton);

		alert.showAndWait().ifPresent(response -> {
			if (response == startNewGameButton) {
				controller.startNewGame(); 
			} else if (response == exitButton) {
				Platform.exit();
			}
		});
	}

	// Prompts user for a new file when restarting the game.
	public String promptNewFileForGame() throws Exception {
		File selectedFile = chooseFile();
		if (selectedFile != null) {
			controller.setFilePath(selectedFile.getAbsolutePath());
			return selectedFile.getAbsolutePath();
		} else {
			throw new Exception("No file selected for new game.");
		}
	}
}