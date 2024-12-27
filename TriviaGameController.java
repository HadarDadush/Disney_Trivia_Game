import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.application.Platform;

// Class that manages the game interface.
public class TriviaGameController {

	@FXML
	private Label questionLabel;
	@FXML
	private Button answer1Button;
	@FXML
	private Button answer2Button;
	@FXML
	private Button answer3Button;
	@FXML
	private Button answer4Button;
	@FXML
	private Label scoreLabel;

	private TriviaGame game;
	private Initializer initializer;

	public TriviaGameController() {
		game = new TriviaGame();
	}

	// Sets the file path for the trivia questions and starts a new game.
	public void setFilePath(String filePath) {
		try {
			game.startNewGame(filePath);
			loadNextQuestion();
		} catch (Exception e) {
			showError("Error starting new game.", e.getMessage());
		}
	}

	// Loads the next question and updates the UI with it.
	public void loadNextQuestion() {
		String[] questionDetails = game.getNextQuestionDetails();
		if (questionDetails != null) {
			questionLabel.setText(questionDetails[0]);
			answer1Button.setText(questionDetails[1]);
			answer2Button.setText(questionDetails[2]);
			answer3Button.setText(questionDetails[3]);
			answer4Button.setText(questionDetails[4]);
		} else {
			showGameOverAlert();
		}
	}

	// Handles the first answer button click.
	@FXML
	private void handleAnswer1() {
		handleAnswer(answer1Button.getText());
	}

	// Handles the second answer button click.
	@FXML
	private void handleAnswer2() {
		handleAnswer(answer2Button.getText());
	}

	// Handles the third answer button click.
	@FXML
	private void handleAnswer3() {
		handleAnswer(answer3Button.getText());
	}

	// Handles the fourth answer button click.
	@FXML
	private void handleAnswer4() {
		handleAnswer(answer4Button.getText());
	}

	// Processes the user's answer and updates the score.
	private void handleAnswer(String answer) {
		game.handleAnswer(answer);
		updateScore();
		loadNextQuestion();
	}

	// Updates the score label on the UI.
	private void updateScore() {
		scoreLabel.setText("Score: " + game.getScore());
	}

	// Shows the game over alert with the final score and options.
	public void showGameOverAlert() {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		alert.setTitle("Game Over");
		alert.setHeaderText("The game has ended!");
		alert.setContentText("Your final score is: " + game.getScore());

		ButtonType startNewGameButton = new ButtonType("Start New Game");
		ButtonType exitButton = new ButtonType("Exit", ButtonType.CANCEL.getButtonData());

		alert.getButtonTypes().setAll(startNewGameButton, exitButton);

		alert.showAndWait().ifPresent(response -> {
			if (response == startNewGameButton) {
				startNewGame();
			} else if (response == exitButton) {
				Platform.exit();
			}
		});
	}

	// Starts a new game by prompting the user to choose a new file.
	public void startNewGame() {
		try {
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setTitle("Choose a File");
			alert.setHeaderText("Choose a trivia file to start the game.");
			alert.setContentText("Please upload the text file of the questions bank.");
			alert.showAndWait();
			String filePath = initializer.promptNewFileForGame();
			game.startNewGame(filePath);
			updateScore();
			loadNextQuestion();

		} catch (Exception e) {
			showError("Error", "No file selected! The game will now close.");
			Platform.exit();
		}
	}

	// Shows an error alert with a header and content.
	private void showError(String header, String content) {
		Alert errorAlert = new Alert(Alert.AlertType.ERROR);
		errorAlert.setTitle("Error");
		errorAlert.setHeaderText(header);
		errorAlert.setContentText(content);
		errorAlert.showAndWait();
	}

	// Sets the initializer object to be used later.
	public void setInitializer(Initializer initializer) {
		this.initializer = initializer;
	}

	// Returns the current score of the player.
	public int getScore() {
		return game.getScore();
	}
}
