import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Panel extends JPanel implements Runnable{

    static final int SCREEN_WIDTH = 1000;
    static final int SCREEN_HEIGHT = (int)(SCREEN_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT);
    static final int BALL_DIAMETER = 15;
    static final int BAR_WIDTH = 10;
    static final int BAR_HEIGHT = 100;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Bars bar1;
    Bars bar2;
    Ball ball;
    Scoreboard score;

    Panel() {
        // set panel configs and creates the thread to run
        newBars();
        newBall();

        score = new Scoreboard(SCREEN_WIDTH, SCREEN_HEIGHT);

        this.setFocusable(true);
        this.addKeyListener(new ActionListener());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        //random
        ball = new Ball((SCREEN_WIDTH/2)-(BALL_DIAMETER/2), (SCREEN_HEIGHT/2)-(BALL_DIAMETER/2), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newBars() {
        // set bars config and creat it
        bar1 = new Bars(5, (SCREEN_HEIGHT/2)-(BAR_HEIGHT/2), BAR_WIDTH, BAR_HEIGHT, 1);
        bar2 = new Bars(SCREEN_WIDTH - (BAR_WIDTH + 5), (SCREEN_HEIGHT/2)-(BAR_HEIGHT/2), BAR_WIDTH, BAR_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();

        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        // draw bars on screen
        bar1.draw(g);
        bar2.draw(g);
        ball.draw(g);
        score.draw(g);
    }

    public void move() {
        // makes the movement smooth by calling it in every refresh
        bar1.move();
        bar2.move();
        ball.move();
    }

    public void checkCollision() {
        // limits the screen for the ball
        if(ball.y <= 0) ball.setYDirection(-ball.ySpeed);
        if(ball.y >= SCREEN_HEIGHT-BALL_DIAMETER) ball.setYDirection(-ball.ySpeed);

        // limits the screen for the bars
        if(bar1.y <= 0) bar1.y = 0;
        if(bar1.y >= SCREEN_HEIGHT-BAR_HEIGHT) bar1.y = SCREEN_HEIGHT-BAR_HEIGHT;

        if(bar2.y <= 0) bar2.y = 0;
        if(bar2.y >= SCREEN_HEIGHT-BAR_HEIGHT) bar2.y = SCREEN_HEIGHT-BAR_HEIGHT;

        // ball bounce when hit the bar
        if(ball.intersects(bar1)) {
            ball.xSpeed = Math.abs(ball.xSpeed);
            ball.xSpeed++; // for difficulty

            if(ball.ySpeed > 0) ball.ySpeed++;
            else ball.ySpeed--; // for difficulty

            ball.setXDirection(ball.xSpeed);
            ball.setYDirection(ball.ySpeed);
        }
        if(ball.intersects(bar2)) {
            ball.xSpeed = Math.abs(ball.xSpeed);
            ball.xSpeed++; // for difficulty

            if(ball.ySpeed > 0) ball.ySpeed++;
            else ball.ySpeed--; // for difficulty

            ball.setXDirection(-ball.xSpeed);
            ball.setYDirection(ball.ySpeed);
        }

        // give point + new ball and reset bar
        if(ball.x <= 0) {
            score.player2++;
            newBars();
            newBall();
        }
        if(ball.x >= SCREEN_WIDTH-BALL_DIAMETER) {
            score.player1++;
            newBars();
            newBall();
        }
    }

    public void run() {
        // game loop
        long lastLoopTime = System.nanoTime();
        double target = 60.0;
        double optimalTime = 1000000000 / target;
        double delta = 0;

        while (true) {
            long now = System.nanoTime();
            delta += (now - lastLoopTime)/optimalTime;
            lastLoopTime = now;

            if(delta >= 1) {
                move();
                checkCollision();
                repaint();
                delta--;
            }
        }
    }

    public class ActionListener extends KeyAdapter {
        // listen to keypress
        public void keyPressed(KeyEvent e) {
            bar1.keyPressed(e);
            bar2.keyPressed(e);
        }
        public void keyReleased(KeyEvent e) {
            bar1.keyReleased(e);
            bar2.keyReleased(e);
        }
    }
}
