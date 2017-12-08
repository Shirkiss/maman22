package main.java;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.*;

public class Questionnaire {

    public static void main(String[] args) {
        QuizMain();
    }


    // read records from file and display only records of appropriate type
    private static void QuizMain() {
        // open file and process contents
//      try (Scanner input = new Scanner(new File("exam.txt")))
        List<ChoiceQuestion> quiz = new ArrayList<>();
        try (Scanner input = new Scanner(new File("C:\\Users\\shir.cohen\\Documents\\GitHub\\maman13\\Q1\\src\\main\\resources\\exam.txt"))) {
            while (input.hasNext()) // more data to read
            {
                String question = input.nextLine();
//                List answers = new ArrayList<>(Arrays.asList(input.nextLine(), input.nextLine(), input.nextLine(), input.nextLine()));
                String correctAnswer = input.nextLine();
                quiz.add(new ChoiceQuestion(question, correctAnswer, correctAnswer, input.nextLine(),input.nextLine(),input.nextLine()));
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
            System.err.println("Error processing file. Terminating." + e);
            System.exit(1);
        }

        JFrame frame = new JFrame("QUIZ TIME!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new QuizPanel(quiz));
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);


    } // end method readRecords


} // end class CreditInquiry