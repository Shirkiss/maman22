package main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Enumeration;
import java.util.List;

/**
 * Created by shir.cohen on 12/8/2017.
 */
class QuizFrame extends JFrame {

    private CardLayout cardLayout;
    private JPanel quizPanel;
    private JPanel questionsPanel;
    private List<ChoiceQuestion> quiz;
    private JButton finish;
    private JButton startOver;


    QuizFrame(java.util.List<ChoiceQuestion> quiz) {
        super("QUIZ TIME!");
        this.quiz = quiz;
        setLayout(new FlowLayout());

        cardLayout = new CardLayout();
        //creating the main panel that other panel will be added to
        quizPanel = new JPanel(cardLayout);
        quizPanel.setBorder(new EmptyBorder(40, 45, 40, 45));

        //creating the start panel with GridBagLayout layout
        GridLayout startLayout = new GridLayout(2,1,0,15);
        JPanel startPanel = new JPanel(startLayout);
        JLabel startLabel = new JLabel("<html><b>Welcome to QUIZ TIME!</b></html>");


        JButton start = new JButton("Start");
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuestions();
            }
        });

        startPanel.add(startLabel);
        startPanel.add(start);
        quizPanel.add(startPanel, "start");
        cardLayout.show(quizPanel, "Start");

        //adding the quiz panel to the frame
        add(quizPanel);
    }

    private void showQuestions() {
        questionsPanel = new JPanel(new GridLayout(quiz.size() + 1, 1));

        for (ChoiceQuestion question : quiz) {
            QuestionPanel questionPanel = new QuestionPanel(question);
            question.setButtonGroup(questionPanel.getButtonGroup());
            questionsPanel.add(questionPanel);
        }

        JPanel finishPanel = new JPanel(new GridBagLayout());

        finish = new JButton("Finish");
        finish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showResults();
            }
        });

        startOver = new JButton("Start Over");
        startOver.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showQuestions();
            }
        });
        startOver.setEnabled(false);


        finishPanel.add(finish);
        finishPanel.add(startOver);
        questionsPanel.add(finishPanel);

        quizPanel.add(questionsPanel, "questions");
        cardLayout.show(quizPanel, "questions");
    }

    private void showResults() {
            // sum correct answers
            int totalCorrect = 0;
            for (Component currentPanel : questionsPanel.getComponents()) {
                if (currentPanel instanceof QuestionPanel) {
                    // mark correct answers in green and incorrect on red
                    if (((QuestionPanel) currentPanel).getQuestion().isCorrect(getSelected(((QuestionPanel) currentPanel).getQuestion().getButtonGroup()))) {
                        currentPanel.setBackground(Color.green);
                        totalCorrect++;
                    } else {
                        currentPanel.setBackground(Color.red);
                    }
                }
            }
            // Show Corrects
            System.out.println("Total correct responses: " + totalCorrect);
            JOptionPane.showMessageDialog(null, "<html>Your managed to answer correctly "
                    + totalCorrect + "/" + quiz.size() + ".<br> Your score is: " + String.format("%%%.1f", totalCorrect * 1.0 / quiz.size() * 100));
            startOver.setEnabled(true);
            finish.setEnabled(false);



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
        return selectedRadio != null ? selectedRadio.getText() : null;
    }
}
