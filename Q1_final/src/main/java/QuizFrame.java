package main.java;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Enumeration;
import java.util.List;

/**
 * QuizFrame.java
 * Purpose: Create a frame with questions from exam.txt file
 *
 * @author Shir Cohen
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
        //creating the main panel that other panels will be added to
        quizPanel = new JPanel(cardLayout);
        quizPanel.setPreferredSize(new Dimension(500, 700));
        quizPanel.setBorder(new EmptyBorder(40, 45, 40, 45));


        //creating the start panel with GridBagLayout layout
        GridLayout startLayout = new GridLayout(2,1,0,15);
        JPanel startPanel = new JPanel(startLayout);
        startPanel.setBorder(new EmptyBorder(100, 100, 400, 100));

        JLabel startLabel = new JLabel("<html><b>Welcome to QUIZ TIME!</b></html>");

        //adding start button
        JButton start = new JButton("Start");
        start.addActionListener(e -> showQuestions());

        startPanel.add(startLabel);
        startPanel.add(start);
        quizPanel.add(startPanel, "start");
        cardLayout.show(quizPanel, "Start");

        //adding the quiz panel to the frame
        add(quizPanel);
    }

    /**
     * Create question panel for each question and display it
     */
    private void showQuestions() {
        questionsPanel = new JPanel(new GridLayout(quiz.size() + 1, 1));
        //create scroll panel and put the questions panel inside
        JScrollPane scrollPane = new JScrollPane(questionsPanel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        //create question panel to each question and add it to the questionsPanel
        for (ChoiceQuestion question : quiz) {
            QuestionPanel questionPanel = new QuestionPanel(question);
            question.setButtonGroup(questionPanel.getButtonGroup());
            questionsPanel.add(questionPanel);
        }

        quizPanel.add(scrollPane, "questions");
        cardLayout.show(quizPanel, "questions");

        JPanel finishPanel = new JPanel(new GridBagLayout());

        finish = new JButton("Finish");
        finish.addActionListener(e -> showResults());

        startOver = new JButton("Start Over");
        startOver.addActionListener(e -> showQuestions());
        startOver.setEnabled(false);

        finishPanel.add(finish);
        finishPanel.add(startOver);
        questionsPanel.add(finishPanel);
    }

    /**
     * Mark the correct results on the frame and display a score message to the user
     */
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
            JOptionPane.showMessageDialog(null, "<html>Your managed to answer correctly "
                    + totalCorrect + "/" + quiz.size() + ".<br> Your score is: " + String.format("%%%.1f", totalCorrect * 1.0 / quiz.size() * 100));
            startOver.setEnabled(true);
            finish.setEnabled(false);
    }

    /**
     * Get the selected radio button from a specific button group
     *
     * @param buttonGroup The button group
     * @return The selected radio button from the button group
     */
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
