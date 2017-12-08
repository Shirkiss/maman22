package main.java;

/**
 * Created by shir.cohen on 12/8/2017.
 */

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;


public class QuizPanel extends JPanel {

    private List<ChoiceQuestion> quiz;

    private JButton next;
    private CardLayout cardLayout;
    private int currentQuestion;
    private JPanel QuestionsPanel;


    public QuizPanel(List<ChoiceQuestion> quiz) {
        this.quiz = quiz;
        cardLayout = new CardLayout();
        QuestionsPanel = new JPanel(cardLayout);
        QuestionsPanel.setBorder(new EmptyBorder(40, 45, 40, 45));

        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                currentQuestion = -1;
                nextQuestion();
            }
        });


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.add(start);
        QuestionsPanel.add(mainPanel, "start");


        for (int index = 0; index < quiz.size(); index++) {
            ChoiceQuestion question = quiz.get(index);
            QuestionPanel questionPanel = new QuestionPanel(question);
            question.setButtonGroup(questionPanel.getButtonGroup());
            QuestionsPanel.add(questionPanel, Integer.toString(index));
        }
//        QuestionsPanel.add(new JLabel("You have finished the Quiz"), "last");
        currentQuestion = 0;
        cardLayout.show(QuestionsPanel, "Start");

        setLayout(new BorderLayout());
        add(QuestionsPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        next = new JButton("Next");
        buttonPanel.add(next);
        next.setEnabled(false);

        add(buttonPanel, BorderLayout.SOUTH);

        next.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextQuestion();
            }
        });

    }

    protected void nextQuestion() {
        // store the selected answer for each question
        if (currentQuestion >= 0 && currentQuestion < quiz.size()) {
            ChoiceQuestion currentQObject = quiz.get(currentQuestion);
            if (currentQObject != null) {
                currentQObject.setUserResponse(getSelected(currentQObject.getButtonGroup()));
            }
        }
        currentQuestion++;
        if (currentQuestion >= quiz.size()) {
            //Show however many correct after last question.
            // Just iterate over quiz list to check if answers are correct:
            int totalCorrect = 0;
            for (ChoiceQuestion q : quiz) {
                if (q.isCorrect()) {
                    totalCorrect++;
                }
            }

            // Show Corrects any way....
            System.out.println("Total correct responses: " + totalCorrect);
            QuestionsPanel.add(new JLabel("<html>Your total correct answers: <br>" + totalCorrect + "/" + quiz.size() + "</html>"), "last");
            cardLayout.show(QuestionsPanel, "last");
            next.setEnabled(false);


        } else {
            cardLayout.show(QuestionsPanel, Integer.toString(currentQuestion));
            next.setText("Next");
            next.setEnabled(true);
        }
    }

    private String getSelected(ButtonGroup buttonGroup) {
        JRadioButton selectedRadio = null;
        Enumeration e = buttonGroup.getElements();
        while (e.hasMoreElements()) {
            JRadioButton radioOption = (JRadioButton) e.nextElement();
            if (radioOption.isSelected()) {
                selectedRadio = radioOption;
                break;
            }
        }
        return selectedRadio.getText();
    }
}

