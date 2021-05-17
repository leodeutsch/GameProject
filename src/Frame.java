import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Frame extends JFrame{

    Panel panel;

    Frame() {
        panel = new Panel();

        this.add(panel);
        this.setResizable(false);
        this.setBackground(Color.black);
        this.setTitle("Ping-Pong Game");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
        this.setLocation(100, 100);
        this.pack();
    }
}
