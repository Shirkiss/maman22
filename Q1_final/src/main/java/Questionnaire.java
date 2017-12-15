package main.java;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Questionnaire.java
 * Purpose: Create a frame with questions in it
 *
 * @author Shir Cohen
 */
public class Questionnaire {

    public static void main(String[] args) {
        List<ChoiceQuestion> quiz;

        quiz = getQuestions("C:\\Users\\shir.cohen\\Documents\\GitHub\\maman13\\Q1_final\\src\\main\\resources\\exam.txt");

        QuizFrame frame = new QuizFrame(quiz);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * Get a file path and create a list of ChoiceQuestion extracted from the file
     *
     * @param path A pathname string for the questions file
     * @return A list of ChoiceQuestion extracted from the file
     */
    private static List<ChoiceQuestion> getQuestions(String path)
    {
        List<ChoiceQuestion> questions = new ArrayList<>();
        try (Scanner input = new Scanner(new File(path))) {
            while (input.hasNext()) // more data to read
            {
                String question = input.nextLine();
                String correctAnswer = input.nextLine();
                questions.add(new ChoiceQuestion(question, correctAnswer, correctAnswer, input.nextLine(),input.nextLine(),input.nextLine()));
            }
            input.close();
        }      catch (NoSuchElementException elementException)
        {
            System.err.println("File improperly formed. Terminating.");
        }
        catch (IllegalStateException stateException)
        {
            System.err.println("Error reading from file. Terminating.");
        }
        catch ( IOException e) {
            System.err.println("Error processing file. Terminating.");
            System.exit(1);
        }
        return questions;
    }

}