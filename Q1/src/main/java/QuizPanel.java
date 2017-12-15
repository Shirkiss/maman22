package main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

/**
 * QuizPanel.java
 * Purpose: Create a panel with questions from exam.txt file
 *
 * @author Shir Cohen
 */
class QuizPanel extends JPanel {

    private List<ChoiceQuestion> quiz;
    private JButton finish;
    private JButton startOver;
    private CardLayout cardLayout;
    private JPanel QuestionsPanel;


    QuizPanel(List<ChoiceQuestion> quiz) {
        this.quiz = quiz;
        cardLayout = new CardLayout();
        QuestionsPanel = new JPanel(cardLayout);
        QuestionsPanel.setBorder(new EmptyBorder(40, 45, 40, 45));

        JButton start = new JButton("Start");
        start.addActionListener(e -> {
            showQuestions();
            finish.setEnabled(true);
        });

        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.add(start);
        QuestionsPanel.add(mainPanel, "start");

        cardLayout.show(QuestionsPanel, "Start");

        setLayout(new BorderLayout());
        add(QuestionsPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        finish = new JButton("Finish");
        startOver = new JButton("Start Over");
        buttonPanel.add(finish);
        buttonPanel.add(startOver);
        finish.setEnabled(false);
        startOver.setEnabled(false);

        add(buttonPanel, BorderLayout.SOUTH);

        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                finish();
            }
        });

        startOver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuestions();
                finish.setEnabled(true);
                startOver.setEnabled(false);
            }
        });

    }

    private void finish() {
        // store the selected answer for each question
        try {
            int totalCorrect = 0;
            for (ChoiceQuestion currentQObject : quiz) {
                if (currentQObject != null) {
                    currentQObject.setUserResponse(getSelected(currentQObject.getButtonGroup()));
                    if (currentQObject.isCorrect())
                        totalCorrect++;
                }
            }

            // Show Corrects any way....
            System.out.println("Total correct responses: " + totalCorrect);
            QuestionsPanel.add(new JLabel("<html>Your total correct answers: <br>" + totalCorrect + "/" + quiz.size() + "</html>"), "last");
            cardLayout.show(QuestionsPanel, "last");
            finish.setEnabled(false);
            startOver.setEnabled(true);


        } catch (NullPointerException e) {
            JOptionPane.showMessageDialog(null, "Please answer all questions");
        }

    }

    private void showQuestions() {
        JPanel allQuestionPanel = new JPanel(new GridLayout(quiz.size(), 2));
        JScrollPane scrollPane = new JScrollPane(allQuestionPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        for (ChoiceQuestion question : quiz) {
            QuestionPanel questionPanel = new QuestionPanel(question);
            question.setButtonGroup(questionPanel.getButtonGroup());
            allQuestionPanel.add(questionPanel);
        }
        QuestionsPanel.add(scrollPane, "questions");
        cardLayout.show(QuestionsPanel, "questions");
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

