package main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * QuestionPanel.java
 * Purpose: Create a panel specific for choice questions
 *
 * @author Shir Cohen
 */
class QuestionPanel extends JPanel {

    private ChoiceQuestion question;
    private ButtonGroup buttonGroup = null;

    QuestionPanel(ChoiceQuestion question) {
        this.question = question;

        setLayout(new BorderLayout());

        JLabel prompt = new JLabel("<html><b>" + question.getPrompt() + "</b></html>");
        prompt.setHorizontalAlignment(JLabel.LEFT);
        add(prompt, BorderLayout.NORTH);


        JPanel guesses = new JPanel(new GridBagLayout());
        guesses.setBorder(new EmptyBorder(10, 10, 10, 10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;

        java.util.List<String> options = new ArrayList<>(Arrays.asList(question.getOptions()));
        Collections.shuffle(options);

        ButtonGroup bg = new ButtonGroup();
        for (String option : options) {
            JRadioButton btn = new JRadioButton(option);
            btn.setName(option);
            bg.add(btn);

            guesses.add(btn, gbc);
        }
        this.buttonGroup = bg;

        add(guesses);
    }

    ButtonGroup getButtonGroup() {
        return buttonGroup;
    }
    ChoiceQuestion getQuestion() {
        return question;
    }
}
