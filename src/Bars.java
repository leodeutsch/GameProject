import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Bars extends Rectangle {

    int id; // 1 or 2 for player
    int velocityY;
    int speedY = 10;

    Bars(int x, int y, int BAR_WIDTH, int BAR_HEIGHT, int id) {
        super(x, y, BAR_WIDTH, BAR_HEIGHT);
        this.id = id;
    }

    public void keyPressed(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(-speedY);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(speedY);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(-speedY);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(speedY);
                    move();
                }
                break;
        }
    }
    public void keyReleased(KeyEvent e) {
        switch(id) {
            case 1:
                if(e.getKeyCode() == KeyEvent.VK_W) {
                    setYDirection(0);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_S) {
                    setYDirection(0);
                    move();
                }
                break;
            case 2:
                if(e.getKeyCode() == KeyEvent.VK_UP) {
                    setYDirection(0);
                    move();
                }
                if(e.getKeyCode() == KeyEvent.VK_DOWN) {
                    setYDirection(0);
                    move();
                }
                break;
        }
    }

    public void setYDirection(int yDirection) {
         velocityY = yDirection;
    }

    public void move() {
        y = y + velocityY;
    }
    public void draw(Graphics g) {
        if(id == 1) {
            g.setColor(Color.green);
        } else {
            g.setColor(Color.red);
        }

        g.fillRect(x, y, width, height);
    }
}
