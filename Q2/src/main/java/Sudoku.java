package main.java;

import javax.swing.*;
import javax.swing.border.Border;
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
    private static final Color OPEN_CELL_BGCOLOR = Color.YELLOW;
    private static final Color CLOSED_CELL_BGCOLOR = new Color(240, 240, 240); // RGB
    private static final Color CLOSED_CELL_TEXT = Color.BLACK;
    private boolean gameStarted = false;
    private static final Font FONT = new Font("Verdana", Font.BOLD, 20);
    private final JButton setButton;
    private JTextField[][] tfCells;
    private int[][] puzzle = new int[GRID_SIZE][GRID_SIZE];
    private boolean[][] masks = new boolean[GRID_SIZE][GRID_SIZE];


    private Sudoku(String title) {
        super(title);
        this.tfCells = new JTextField[GRID_SIZE][GRID_SIZE];

        InputListener listener = new InputListener();
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        Dimension fieldDimension = new Dimension(60, 60);

        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                tfCells[row][col] = new JTextField();
                tfCells[row][col].addActionListener(listener);
                tfCells[row][col].setText("");     // set to empty string
                tfCells[row][col].setEditable(true);
                tfCells[row][col].setBackground(OPEN_CELL_BGCOLOR);
                // Beautify all the cells
                tfCells[row][col].setBorder(border);
                tfCells[row][col].setFont(FONT);
                tfCells[row][col].setPreferredSize(fieldDimension);
                tfCells[row][col].setHorizontalAlignment(JTextField.CENTER);
            }
        }
        JPanel gridPanel = new JPanel();
        gridPanel.setLayout(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));

        JPanel[][] minisquarePanels = new JPanel[SUBGRID_SIZE][SUBGRID_SIZE];
        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);

        //create mini square Panels
        for (int row = 0; row < SUBGRID_SIZE; ++row) {
            for (int col = 0; col < SUBGRID_SIZE; ++col) {
                JPanel panel = new JPanel();
                panel.setLayout(new GridLayout(SUBGRID_SIZE, SUBGRID_SIZE));
                panel.setBorder(minisquareBorder);
                minisquarePanels[row][col] = panel;
                gridPanel.add(panel);
            }
        }

        //adding to each mini square Panels the relevant text fields
        for (int row = 0; row < GRID_SIZE; ++row) {
            for (int col = 0; col < GRID_SIZE; ++col) {
                int minisquareX = col / SUBGRID_SIZE;
                int minisquareY = row / SUBGRID_SIZE;

                minisquarePanels[minisquareY][minisquareX].add(tfCells[row][col]);
            }
        }

        gridPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        JPanel buttonPanel = new JPanel(new GridBagLayout());

        this.setButton = new JButton("Set");
        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setBoard();
                gameStarted = true;
                setButton.setEnabled(false);
            }
        });

        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearBoard();
                setButton.setEnabled(true);
            }
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

                //check if game finished
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
            int block = getBlockNumber(row, col);

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
                tfCells[row][col].setText("");
                return false;

            } else if (IntStream.of(column_elements).anyMatch(x -> x == input)) {
                JOptionPane.showMessageDialog(null, "You shouldn't fill the same number twice in a column");
                tfCells[row][col].setText("");
                return false;

            } else if (IntStream.of(box_elements).anyMatch(x -> x == input)) {
                JOptionPane.showMessageDialog(null, "You shouldn't fill the same number twice in a block");
                tfCells[row][col].setText("");
                return false;
            } else {
                puzzle[row][col] = input;
            }
        }
        return true;
    }

    private int getBlockNumber(int row, int col) {
        int block_horiz, block_ver;
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
        gameStarted = false;
    }

    private void checkFinished() {
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


