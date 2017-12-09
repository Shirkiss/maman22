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

    private static final int CELL_SIZE = 60;   // Cell width/height in pixels
    private static final int CANVAS_WIDTH = CELL_SIZE * GRID_SIZE;
    private static final int CANVAS_HEIGHT = CELL_SIZE * GRID_SIZE;
    private static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
    private static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    private static final Color CLOSED_CELL_TEXT = Color.BLACK;
    private static final Font FONT_NUMBERS = new Font("Monospaced", Font.BOLD, 20);
    private boolean gameStarted = false;

    private JTextField[][] tfCells = new JTextField[GRID_SIZE][GRID_SIZE];
    private JButton set;


    private int[][] puzzle = new int[GRID_SIZE][GRID_SIZE];

    private boolean[][] masks = new boolean[GRID_SIZE][GRID_SIZE];


    private Sudoku(String title) {
        super(title);
        BorderLayout layout = new BorderLayout(10, 10);
        setLayout(layout);
        JPanel cp = new JPanel(new GridLayout(GRID_SIZE, GRID_SIZE));
        InputListener listener = new InputListener();

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col] = new JTextField(); // Allocate element of array
                cp.add(tfCells[row][col]);            // ContentPane adds JTextField

                tfCells[row][col].setText("");     // set to empty string
                tfCells[row][col].setEditable(true);
                tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);

                // Add ActionEvent listener to process the input
                tfCells[row][col].addActionListener(listener);

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
                setBoard();
                gameStarted = true;
                set.setEnabled(false);
            }
        });

        JButton clear = new JButton("Clear");
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
                set.setEnabled(true);
            }
        });

        buttonsPanel.add(set);
        buttonsPanel.add(clear);
        add(cp, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.SOUTH);
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
            if (gameStarted) {
                if (checkValidEntrance(rowSelected, colSelected)) {
                    tfCells[rowSelected][colSelected].setBackground(Color.GREEN);
                    masks[rowSelected][colSelected] = false;
                } else {
                    tfCells[rowSelected][colSelected].setBackground(Color.RED);
                    masks[rowSelected][colSelected] = true;
                }

                //check if game finish
                checkFinished();
            } else {
                checkValidEntrance(rowSelected, colSelected);
            }

        }
    }

    private Boolean checkValidEntrance(int row, int col) {
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
            int block = getBlock(row, col);

            //copy all element in a specific block except for the current cell

            int m = 0;
            for (int i = block / 3 * 3; i < block / 3 * 3 + 3; i++) {
                for (int j = block % 3 * 3; j < block % 3 * 3 + 3; j++) {
                    if (i != row || j != col) {
                        box_elements[m] = puzzle[i][j];
                        m++;
                    }
                }
            }

            if (IntStream.of(row_elements).anyMatch(x -> x == input)) {
                JOptionPane.showMessageDialog(null, "you should fill only once in a row");
                tfCells[row][col].setText("");
                return false;

            } else if (IntStream.of(column_elements).anyMatch(x -> x == input)) {
                JOptionPane.showMessageDialog(null, "you should fill only once in a column");
                tfCells[row][col].setText("");
                return false;

            } else if (IntStream.of(box_elements).anyMatch(x -> x == input)) {
                JOptionPane.showMessageDialog(null, "you should fill only once in a box");
                tfCells[row][col].setText("");
                return false;
            } else {
                puzzle[row][col] = input;
            }

        }
        return true;
    }

    private int getBlock(int row, int col) {
        int block_horiz;
        int block_ver;
        if (row / 3 < 1)
            block_ver = 0;
        else if (row / 3 < 2)
            block_ver = 1;
        else
            block_ver = 2;
        if (col / 3 < 1)
            block_horiz = 0;
        else if (col / 3 < 2)
            block_horiz = 1;
        else
            block_horiz = 2;
        return block_horiz + block_ver * 3;
    }

    private void setBoard() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (puzzle[row][col] != 0) {
                    tfCells[row][col].setText(puzzle[row][col] + "");
                    tfCells[row][col].setEditable(false);
                    tfCells[row][col].setBackground(CLOSED_CELL_BGCOLOR);
                    tfCells[row][col].setForeground(CLOSED_CELL_TEXT);
                }
            }
        }

    }

    private void clearBoard() {
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col].setText("");     // set to empty string
                tfCells[row][col].setEditable(true);
                tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
            }

        }
        puzzle = new int[GRID_SIZE][GRID_SIZE];
        masks = new boolean[GRID_SIZE][GRID_SIZE];

    }

    private void checkFinished(){
        //go over all masks elements and look if there is still false inside
        boolean finish = true;
        for (int row = 0; row < GRID_SIZE && finish; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                if (!masks[row][col]) {
                    finish = false;
                    break;
                }
            }
        }
        if (finish)
            JOptionPane.showMessageDialog(null, "Congratulation!");
    }
}


