package main.java;

/**
 * Created by shir.cohen on 12/6/2017.
 */

import javax.swing.*;
import java.awt.*;
import java.util.Collections;
import java.util.List;

public class QuestionFrame extends JFrame {
    private JLabel question; // used to display font changes
    private JRadioButton correctAnswer; // selects plain text
    private JRadioButton firstQuestionButton; // selects plain text
    private JRadioButton secondQuestionButton; // selects bold text
    private JRadioButton thirdQuestionButton; // selects italic text
    private JRadioButton fourthQuestionButton; // bold and italic
    private ButtonGroup radioGroup; // buttongroup to hold radio buttons

    // RadioButtonFrame constructor adds JRadioButtons to JFrame
    public QuestionFrame(String question, List<String> answers) {
        super("Question");
        setLayout(new FlowLayout());

        correctAnswer = new JRadioButton(answers.get(0), false);
        Collections.shuffle(answers);

        // create radio buttons
        firstQuestionButton = new JRadioButton(answers.get(0), false);
        secondQuestionButton = new JRadioButton(answers.get(1), false);
        thirdQuestionButton = new JRadioButton(answers.get(2), false);
        fourthQuestionButton = new JRadioButton(answers.get(3), false);
        add(firstQuestionButton); // add plain button to JFrame
        add(secondQuestionButton); // add bold button to JFrame
        add(thirdQuestionButton); // add italic button to JFrame
        add(fourthQuestionButton); // add bold and italic button

        // create logical relationship between JRadioButtons
        radioGroup = new ButtonGroup(); // create ButtonGroup
        radioGroup.add(firstQuestionButton); // add plain to group
        radioGroup.add(secondQuestionButton); // add bold to group
        radioGroup.add(thirdQuestionButton); // add italic to group
        radioGroup.add(fourthQuestionButton); // add bold and italic

    }

}

