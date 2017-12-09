package main.java;
import javax.swing.*;


/**
 * Created by shir.cohen on 12/8/2017.
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

    boolean isCorrect() {
        return getCorrectAnswer().equals(getUserResponse());
    }
}
