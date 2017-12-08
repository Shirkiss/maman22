package main.java;

/**
 * Created by shir.cohen on 12/8/2017.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class QuestionPanel extends JPanel {

    protected ChoiceQuestion question;
    private JPanel correctPanel;


    protected ButtonGroup buttonGroup = null;
    private CardLayout cardLayout;


    protected QuestionPanel(ChoiceQuestion question) {
        this.question = question;

        setLayout(new BorderLayout());

        JLabel prompt = new JLabel("<html><b>" + question.getPrompt() + "</b></html>");
        prompt.setHorizontalAlignment(JLabel.LEFT);
        add(prompt, BorderLayout.NORTH);

        cardLayout = new CardLayout();
        correctPanel = new JPanel(cardLayout);

        JLabel correct = new JLabel(""+ question.isCorrect());
        correct.setHorizontalAlignment(JLabel.LEFT);
        correctPanel.add(correct, "correct");


        JPanel guesses = new JPanel(new GridBagLayout());
        guesses.setBorder(new EmptyBorder(10,10,10,10));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weightx = 1;
        gbc.anchor = GridBagConstraints.WEST;

        List<String> options = new ArrayList<>(Arrays.asList(question.getOptions()));
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

    protected ButtonGroup getButtonGroup() {
        return buttonGroup;
    }

    protected ChoiceQuestion getQuestion() {
        return question;
    }

    protected class ActionHandler implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            getQuestion().setUserResponse(e.getActionCommand());
        }

    }

}

