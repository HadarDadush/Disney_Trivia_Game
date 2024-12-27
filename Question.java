import java.util.List;

// Class representing a trivia question with its answers and correct answer.
public class Question {
	private String question;
	private List<String> answers;
	private String correctAnswer;

	// Initializes the question and answers; assumes the first answer is correct.
	public Question(String question, List<String> answers) {
		this.question = question;
		this.answers = answers;

		// First answer is the correct one in the file.
		this.correctAnswer = answers.get(0);
	}

	public String getQuestion() {
		return question;
	}

	public List<String> getAnswers() {
		return answers;
	}

	public String getCorrectAnswer() {
		return correctAnswer;
	}
}
