package main.java;
import javax.swing.*;

/**
 * ChoiceQuestion.java
 * Purpose: Create a choice question object
 *
 * @author Shir Cohen
 */
class ChoiceQuestion {

    private final String prompt;
    private final String correctAnswer;
    private final String[] options;
    private ButtonGroup buttonGroup;

    private String userResponse;

    ChoiceQuestion(String prompt, String correctAnswer, String... options) {
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }

    ButtonGroup getButtonGroup() {
        return this.buttonGroup;
    }

    String getPrompt() {
        return prompt;
    }

    String getCorrectAnswer() {
        return correctAnswer;
    }

    String[] getOptions() {
        return options;
    }

    String getUserResponse() {
        return userResponse;
    }

    void setUserResponse(String response) {
        userResponse = response;
    }

    boolean isCorrect(String answer) {
        return getCorrectAnswer().equals(answer);
    }
}
