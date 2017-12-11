package main.java;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;


/**
 * Created by shir.cohen on 12/10/2017.
 */
public class SudokuTextField extends JTextField {
    private static final Font FONT = new Font("Verdana", Font.BOLD, 20);
    private Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
    private Dimension fieldDimension = new Dimension(60, 60);
    private int blockNumber, row, column, value;

    public SudokuTextField(){
        super();
        this.setText("");    // set to empty string
        this.setEditable(true);
        // Beautify all the cells
        this.setBorder(border);
        this.setFont(FONT);
        this.setPreferredSize(fieldDimension);
        this.setHorizontalAlignment(JTextField.CENTER);
    }

    public void setBlock(){
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

    public void setRow(int rowNumber){
        this.row = rowNumber;
    }

    public void setColumn(int columnNumber){
        this.column = columnNumber;
    }

    public int getRow(){
        return row;
    }

    public int getColumn(){
        return column;
    }

    public int getBlock(){
        return blockNumber;
    }

    public void setInteger(int input){
        this.value = input;
    }
    public int getInteger(){
        return value;
    }

    public void setColor(Color color){
        this.setBackground(color);
    }

}
