package main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;

/**
 * Created by shir.cohen on 12/8/2017.
 */
public class QuestionPanel extends JPanel {

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
