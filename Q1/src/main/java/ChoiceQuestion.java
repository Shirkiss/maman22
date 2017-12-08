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

    protected ChoiceQuestion(String prompt, String correctAnswer, String... options) {
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    protected void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }

    protected ButtonGroup getButtonGroup() {
        return this.buttonGroup;
    }

    protected String getPrompt() {
        return prompt;
    }

    protected String getCorrectAnswer() {
        return correctAnswer;
    }

    protected String[] getOptions() {
        return options;
    }

    protected String getUserResponse() {
        return userResponse;
    }

    protected void setUserResponse(String response) {
        userResponse = response;
    }

    protected boolean isCorrect() {
        return getCorrectAnswer().equals(getUserResponse());
    }
}
