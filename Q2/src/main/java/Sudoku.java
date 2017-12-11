package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.stream.IntStream;


/**
 * Created by shir.cohen on 12/9/2017.
 */
public class Sudoku extends JFrame {
    private static final int GRID_SIZE = 9;    // Size of the board
    private static final int SUBGRID_SIZE = 3; // Size of the sub-grid
    private static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    private static final Color ODD_BLOCK = new Color(176, 176, 176); // RGB
    private static final Color EVEN_BLOCK = new Color(114, 114, 114); // RGB
    private boolean gameStarted = false;
    private final JButton setButton;
    private JTextField[][] tfCells;
    private int[][] puzzle = new int[GRID_SIZE][GRID_SIZE];
    private int leftCounter = GRID_SIZE * GRID_SIZE;


    private Sudoku(String title) {
        super(title);
        this.tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
        InputListener listener = new InputListener();
        JPanel gridPanel = new JPanel();

        // create 9 miniSquarePanel and put inside each 9 SudokuTextField.
        for (int block = 0; block < GRID_SIZE; ++block) {
            JPanel miniSquarePanel = new MiniSquarePanel(SUBGRID_SIZE);
            Color color = (block % 2 == 0) ? ODD_BLOCK : EVEN_BLOCK;
            for (int block_row = block / 3 * 3; block_row < block / 3 * 3 + 3; block_row++) {
                for (int block_col = block % 3 * 3; block_col < block % 3 * 3 + 3; block_col++) {
                    SudokuTextField field = new SudokuTextField();
                    field.addActionListener(listener);
                    field.setRow(block_row);
                    field.setColor(color);
                    field.setColumn(block_col);
                    field.setBlock();
                    tfCells[block_row][block_col] = field;
                    miniSquarePanel.add(field);
                }
            }
            gridPanel.add(miniSquarePanel);
        }

        gridPanel.setLayout(new GridLayout(0, SUBGRID_SIZE));
        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        // Create set button
        this.setButton = new JButton("Set");
        setButton.addActionListener(e -> {
            setBoard();
            gameStarted = true;
            setButton.setEnabled(false);
        });

        // Create clear button
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e -> {
            clearBoard();
            setButton.setEnabled(true);
        });

        buttonPanel.add(setButton);
        buttonPanel.add(clearButton);
        this.setLayout(new BorderLayout());
        this.add(gridPanel, BorderLayout.NORTH);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        Sudoku frame = new Sudoku("Sudoku");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private class InputListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            // Get the source object that fired the event
            int sourceRow = ((SudokuTextField) e.getSource()).getRow();
            int sourceColumn = ((SudokuTextField) e.getSource()).getColumn();

            // Check if entrance is valid
            if (checkValidEntrance(sourceRow, sourceColumn)) {
                // If the field was empty, decrease the leftCounter
                if (puzzle[sourceRow][sourceColumn] == 0)
                    leftCounter--;
                if (gameStarted)
                    tfCells[sourceRow][sourceColumn].setBackground(Color.GREEN);
                // Check if the game finished
                if (leftCounter == 0)
                    JOptionPane.showMessageDialog(null, "Congratulation!");
                puzzle[sourceRow][sourceColumn] = ((SudokuTextField) tfCells[sourceRow][sourceColumn]).getInteger();
            } else {
                // If the field was not empty and was replaced with invalid input increase the leftCounter
                if (puzzle[sourceRow][sourceColumn] != 0)
                    leftCounter++;
                tfCells[sourceRow][sourceColumn].setText("");
                puzzle[sourceRow][sourceColumn] = 0;
                if (gameStarted)
                    tfCells[sourceRow][sourceColumn].setBackground(Color.RED);
            }
        }
    }

    private Boolean checkValidEntrance(int row, int col) {
        try {
            int input = Integer.parseInt(tfCells[row][col].getText());
            if (input > 9 || input < 1) {
                JOptionPane.showMessageDialog(null, "please put only numbers between 1-9");
                //clear cell content
                tfCells[row][col].setText("");
            } else {
                int[] row_elements = new int[9];
                int[] column_elements = new int[9];
                int[] box_elements = new int[9];

                //copy all element in a specific row except for the current cell
                System.arraycopy(puzzle[row], 0, row_elements, 0, 9);
                row_elements[col] = 0;

                //copy all element in a specific column except for the current cell
                for (int column_row = 0; column_row < 9; column_row++) {
                    if (column_row != row)
                        column_elements[column_row] = puzzle[column_row][col];
                }
                //find the block that the cell is in
                int block = ((SudokuTextField) tfCells[row][col]).getBlock();

                //copy all element in a specific block except for the current cell
                int index = 0;
                for (int block_row = block / 3 * 3; block_row < block / 3 * 3 + 3; block_row++) {
                    for (int block_col = block % 3 * 3; block_col < block % 3 * 3 + 3; block_col++) {
                        if (block_row != row || block_col != col) {
                            box_elements[index] = puzzle[block_row][block_col];
                            index++;
                        }
                    }
                }
                if (IntStream.of(row_elements).anyMatch(x -> x == input)) {
                    JOptionPane.showMessageDialog(null, "You shouldn't fill the same number twice in a row");
                    return false;

                } else if (IntStream.of(column_elements).anyMatch(x -> x == input)) {
                    JOptionPane.showMessageDialog(null, "You shouldn't fill the same number twice in a column");
                    return false;

                } else if (IntStream.of(box_elements).anyMatch(x -> x == input)) {
                    JOptionPane.showMessageDialog(null, "You shouldn't fill the same number twice in a block");
                    return false;
                } else {
                    ((SudokuTextField) tfCells[row][col]).setInteger(input);
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Please enter only numbers!");
            return false;
        }
        return true;
    }

    private void setBoard() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (puzzle[row][col] != 0) {
                    tfCells[row][col].setText(puzzle[row][col] + "");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                }
            }
        }
    }

    private void clearBoard() {
        for (int block = 0; block < GRID_SIZE; ++block) {
            Color color = (block % 2 == 0) ? ODD_BLOCK : EVEN_BLOCK;
            for (int block_row = block / 3 * 3; block_row < block / 3 * 3 + 3; block_row++) {
                for (int block_col = block % 3 * 3; block_col < block % 3 * 3 + 3; block_col++) {
                    tfCells[block_row][block_col].setText("");     // set to empty string
                    tfCells[block_row][block_col].setEditable(true);
                    ((SudokuTextField) tfCells[block_row][block_col]).setColor(color);
                }
            }
        }
        puzzle = new int[GRID_SIZE][GRID_SIZE];
        leftCounter = GRID_SIZE * GRID_SIZE;
        gameStarted = false;
    }
}


