import java.util.Collections;

// Class that handles the game logic, including question management, answering, and scoring.
public class TriviaGame {

	private int score;
	private QuestionsBank questionBank;
	private Question currentQuestion;

	public TriviaGame() {
		this.score = 0;
		this.questionBank = new QuestionsBank();
	}

	// Starts a new game, loads questions and shuffles them.
	public void startNewGame(String filePath) throws Exception {
		questionBank.clearQuestions();
		questionBank.loadQuestions(filePath);
		questionBank.shuffleQuestions();
		score = 0;
		currentQuestion = null;
	}

	// Returns the details of the next question (question text and answers).
	public String[] getNextQuestionDetails() {
		if (hasNextQuestion()) {
			currentQuestion = questionBank.getNextQuestion();
			shuffleAnswers(currentQuestion);
			return new String[] { currentQuestion.getQuestion(), currentQuestion.getAnswers().get(0),
					currentQuestion.getAnswers().get(1), currentQuestion.getAnswers().get(2),
					currentQuestion.getAnswers().get(3) };
		}
		return null;
	}

	// Shuffles the order of the answers for the current question.
	public void shuffleAnswers(Question question) {
		Collections.shuffle(question.getAnswers());
	}

	// Checks if there are more questions available.
	public boolean hasNextQuestion() {
		return questionBank.getSize() > 0;
	}

	// Handles the player's answer and updates the score.
	public void handleAnswer(String userAnswer) {
		if (currentQuestion != null) {
			if (userAnswer.equals(currentQuestion.getCorrectAnswer())) {
				score += 10;
			} else {
				score -= 5;
			}
			questionBank.removeQuestion(currentQuestion);
		}
	}

	// Returns the current score of the player.
	public int getScore() {
		return score;
	}
}
