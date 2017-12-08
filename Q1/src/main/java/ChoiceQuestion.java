package main.java;
import javax.swing.*;


/**
 * Created by shir.cohen on 12/8/2017.
 */
public class ChoiceQuestion {

    private final String prompt;
    private final String correctAnswer;
    private final String[] options;
    private ButtonGroup buttonGroup;

    private String userResponse;

    public ChoiceQuestion(String prompt, String correctAnswer, String... options) {
        this.prompt = prompt;
        this.correctAnswer = correctAnswer;
        this.options = options;
    }

    public void setButtonGroup(ButtonGroup buttonGroup) {
        this.buttonGroup = buttonGroup;
    }

    public ButtonGroup getButtonGroup() {
        return this.buttonGroup;
    }

    public String getPrompt() {
        return prompt;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public String[] getOptions() {
        return options;
    }

    public String getUserResponse() {
        return userResponse;
    }

    public void setUserResponse(String response) {
        userResponse = response;
    }

    public boolean isCorrect() {
        return getCorrectAnswer().equals(getUserResponse());
    }
}
