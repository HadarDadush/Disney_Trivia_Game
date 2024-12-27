import javafx.application.Application;
import javafx.stage.Stage;

// Main class for launching the trivia game application.
public class Main extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		Initializer initializer = new Initializer(primaryStage);

		// Show the welcome alert and allow file selection.
		if (!initializer.initializeGame()) {
			return;
		}

		// Set the game scene and start.
		primaryStage.setScene(initializer.getScene());
		primaryStage.setTitle("Trivia Game");

		primaryStage.setOnCloseRequest(event -> {

			// Prevent immediate window closure.
			event.consume();
			initializer.showExitAlert(primaryStage);
		});

		primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}