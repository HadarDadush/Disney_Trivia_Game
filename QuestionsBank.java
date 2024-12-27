import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

// Class that manages the list of trivia questions, including loading, shuffling, and removing questions.
public class QuestionsBank {
	private List<Question> questions;

	public QuestionsBank() {
		this.questions = new ArrayList<>();
	}

	// Loads questions from a file.
	public void loadQuestions(String filePath) throws FileNotFoundException {
		File file = new File(filePath);
		if (!file.exists()) {
			throw new FileNotFoundException("File not found: " + filePath);
		}
		Scanner input = new Scanner(file);
		try {
			while (input.hasNext()) {
				String questionText = input.nextLine();
				List<String> answers = new ArrayList<>();
				for (int i = 0; i < 4; i++) {
					if (input.hasNextLine()) {
						answers.add(input.nextLine());
					} else {
						throw new NoSuchElementException("Not enough answers for question: " + questionText);
					}
				}
				Question question = new Question(questionText, answers);
				questions.add(question);
			}
		} catch (NoSuchElementException e) {
			System.out.println("Error reading question or answers: " + e.getMessage());
		} finally {
			input.close();
		}
	}

	// Returns the next question from the list.
	public Question getNextQuestion() {
		if (questions.isEmpty()) {
			return null;
		}
		return questions.remove(0);
	}

	// Shuffle the order of the questions.
	public void shuffleQuestions() {
		Collections.shuffle(questions);
	}

	// Clears the list of questions for a new game.
	public void clearQuestions() {
		questions.clear();
	}

	// Get the number of questions in the bank.
	public int getSize() {
		return questions.size();
	}

	// Removes a question from the list after it has been answered.
	public void removeQuestion(Question question) {
		questions.remove(question);
	}
}