package main.java;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;
import javax.swing.*;



/**
 * Created by shir.cohen on 12/9/2017.
 */
public class Sudoku extends JFrame {
    public static final int GRID_SIZE = 9;    // Size of the board
    public static final int SUBGRID_SIZE = 3; // Size of the sub-grid

    public static final int CELL_SIZE = 60;   // Cell width/height in pixels
    public static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE;
    public static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE ;

    public static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
    public static final Color OPEN_CELL_TEXT_YES = new Color(0, 255, 0);  // RGB
    public static final Color OPEN_CELL_TEXT_NO = Color.RED;
    public static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    public static final Color CLOSED_CELL_TEXT = Color.BLACK;
    public static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
    public boolean gameStarted = false;

    private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
    private JButton set;
    private JButton clear;


    int[][] puzzle = new int[GRID_SIZE][GRID_SIZE];

    Boolean[][] masks = new Boolean[GRID_SIZE][GRID_SIZE];


    public Sudoku() {
        BorderLayout layout = new BorderLayout(10,10);
        setLayout(layout);
        JPanel cp = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        InputListener listener = new InputListener();

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col] = new JTextField(); // Allocate element of array
                cp.add(tfCells[row][col]);            // ContentPane adds JTextField
                if (masks[row][col] == null) {
                    tfCells[row][col].setText("");     // set to empty string
                    tfCells[row][col].setEditable(true);
                    tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

                    // Add ActionEvent listener to process the input
                    tfCells[row][col].addActionListener(listener);   // For all editable rows and cols
                } else {
                    tfCells[row][col].setText(puzzle[row][col] + "");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                    tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
                }
                // Beautify all the cells
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
                tfCells[row][col].setFont(FONT_NUMBERS);
            }
        }

        cp.setPreferredSize(new Dimension(CANVAS_WIDTH, CANVAS_HEIGHT));

        JPanel buttonsPanel = new JPanel(new GridBagLayout());

        set = new JButton("Set");
        set.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameStarted = true;
            }
        });

        clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //something
            }
        });

        buttonsPanel.add(set);
        buttonsPanel.add(clear);
        add(cp, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // Handle window closing
        setTitle("Sudoku");
        setVisible(true);
    }

    public static void main(String[] args) {
        Sudoku frame = new Sudoku();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);

    }

    private class InputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // All the 9*9 JTextFileds invoke this handler. We need to determine
            // which JTextField (which row and column) is the source for this invocation.
            int rowSelected = -1;
            int colSelected = -1;

            // Get the source object that fired the event
            JTextField source = (JTextField) e.getSource();
            // Scan JTextFileds for all rows and columns, and match with the source object
            boolean found = false;
            for (int row = 0; row < GRID_SIZE && !found; ++row) {
                for (int col = 0; col < GRID_SIZE && !found; ++col) {
                    if (tfCells[row][col] == source) {
                        rowSelected = row;
                        colSelected = col;
                        found = true;  // break the inner/outer loops
                    }
                }
            }
            if (gameStarted){
                int input = Integer.parseInt(tfCells[rowSelected][colSelected].getText());
                if (input == puzzle[rowSelected][colSelected]) {
                    tfCells[rowSelected][colSelected].setBackground(Color.GREEN);
                    masks[rowSelected][colSelected] = false;
                } else {
                    tfCells[rowSelected][colSelected].setBackground(Color.RED);
                    masks[rowSelected][colSelected] = true;
                }
                boolean finish = true;
                for (int row = 0; row < GRID_SIZE && finish; ++row) {
                    for (int col = 0; col < GRID_SIZE && finish; ++col) {
                        if (masks[row][col]) {
                            finish = false;
                            break;
                        }
                    }
                }
                if (finish)
                    JOptionPane.showMessageDialog(null, "Congratulation!");
            } else{
                checkValidEntrance(rowSelected, colSelected);
            }

        }
    }

    public Boolean checkValidEntrance(int row, int col){
        int input = Integer.parseInt(tfCells[row][col].getText());
        if (input > 9 || input < 1)
        {
            JOptionPane.showMessageDialog(null, "please put only numbers between 1-9");
            tfCells[row][col].setText("");
        }
        else {
            int[] row_elements = new int[9];
            int[] column_elements = new int[9];
            System.arraycopy(puzzle[row], 0, row_elements, 0, 9);

            for (int i = 0; i < 9; i++) {
                if (i != col)
                    column_elements[i] = puzzle[i][col];
            }

            if (IntStream.of(row_elements).anyMatch(x -> x == input)){
                JOptionPane.showMessageDialog(null, "you should fill only once in a row");
                tfCells[row][col].setText("");

            } else if (IntStream.of(column_elements).anyMatch(x -> x == input))
            {
                JOptionPane.showMessageDialog(null, "you should fill only once in a column");
                tfCells[row][col].setText("");
            }
            else {
                puzzle[row][col] = input;
            }

        }
        return true;
    }

}
