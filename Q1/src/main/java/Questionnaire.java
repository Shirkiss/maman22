package main.java;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import javax.swing.JFrame;
import java.nio.file.Paths;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class Questionnaire {

    public static void main(String[] args) {
        readQuestion();
    }


    // read records from file and display only records of appropriate type
    private static void readQuestion() {
        // open file and process contents
//      try (Scanner input = new Scanner(new File("exam.txt")))
        try (Scanner input = new Scanner(new File("exam.txt"))) {
            while (input.hasNext()) // more data to read
            {
                String question = input.next();
                List answers = new ArrayList<>(Arrays.asList(input.next(), input.next(), input.next(), input.next()));
                printQuestion(question, answers);
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

    } // end method readRecords

    private static void printQuestion(String question, List answers) {
        QuestionFrame radioButtonFrame = new QuestionFrame(question, answers);
        radioButtonFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        radioButtonFrame.setSize(300, 100);
        radioButtonFrame.setVisible(true);
    }


} // end class CreditInquiry