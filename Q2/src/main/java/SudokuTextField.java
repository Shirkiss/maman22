package main.java;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


/**
 * MiniSquarePanel.java
 * A Sudoku text field
 *
 * @author Shir Cohen
 */
class SudokuTextField extends JTextField {
    private static final Font FONT = new Font("Verdana", Font.BOLD, 20);
    private int blockNumber, row, column, value;

    SudokuTextField() {
        super();
        this.clearText();    // set to empty string
        this.setEditable(true);
        // Beautify all the cells
        Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.setBorder(border);
        this.setFont(FONT);
        Dimension fieldDimension = new Dimension(60, 60);
        this.setPreferredSize(fieldDimension);
        this.setHorizontalAlignment(JTextField.CENTER);
    }

    /**
     * Get the block number of a Sudoku text field
     *
     * @param row the row of the field that need to be checked
     * @param column the column of the field that need to be checked.
     * @return the block number that the field is in
     */
    void setBlock(int row, int column) {
        this.row = row;
        this.column = column;
        int block_horiz, block_ver;
        if (this.row / 3 < 1)
            block_ver = 0;
        else if (this.row / 3 < 2)
            block_ver = 1;
        else
            block_ver = 2;
        if (this.column / 3 < 1)
            block_horiz = 0;
        else if (this.column / 3 < 2)
            block_horiz = 1;
        else
            block_horiz = 2;
        this.blockNumber = block_horiz + block_ver * 3;
    }

    void setRow(int rowNumber) {
        this.row = rowNumber;
    }

    void setColumn(int columnNumber) {
        this.column = columnNumber;
    }

    int getRow() {
        return row;
    }

    int getColumn() {
        return column;
    }

    int getBlock() {
        return blockNumber;
    }

    void setInteger(int input) {
        this.value = input;
    }

    int getInteger() {
        return value;
    }

    void clearText() {
        this.setText("");
    }

    void setColor(Color color) {
        this.setBackground(color);
    }

}
