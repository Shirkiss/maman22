package main.java;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Questionnaire {

    public static void main(String[] args) {
        List<ChoiceQuestion> quiz;

        quiz = getQuestions("C:\\Users\\shir.cohen\\Documents\\GitHub\\maman13\\Q1_final\\src\\main\\resources\\exam.txt");

        QuizFrame frame = new QuizFrame(quiz);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(450,quiz.size()*140 + 200);
        frame.setVisible(true);
    }


    // read records from file and display only records of appropriate type

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

} // end class Questionnaire