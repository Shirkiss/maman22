package main.java;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/**
 * MiniSquarePanel.java
 * A 3*3 square container.
 *
 * @author Shir Cohen
 */
class MiniSquarePanel extends JPanel {

    MiniSquarePanel(int size){
        super();
        this.setLayout(new GridLayout(size, size));
        Border minisquareBorder = BorderFactory.createLineBorder(Color.BLACK, 1);
        this.setBorder(minisquareBorder);
    }
}
