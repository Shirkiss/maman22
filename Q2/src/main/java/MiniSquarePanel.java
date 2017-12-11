package main.java;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * Created by shir.cohen on 12/10/2017.
 */
class MiniSquarePanel extends JPanel {

    MiniSquarePanel(int size){
        super();
        this.setLayout(new GridLayout(size, size));
        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.setBorder(minisquareBorder);
    }
}
