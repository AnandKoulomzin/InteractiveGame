import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class Cubefield implements Runnable, KeyListener{

    final int WIDTH = 1000;
    final int HEIGHT = 700;
    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;
    public Arrow user;
    public Image arrowPic;
    public Image squaresPic;
    public Square[] squares;

    public static void main(String[] args) {
        Cubefield myApp = new Cubefield();
        new Thread(myApp).start();
    }

    public Cubefield() {
        setUpGraphics();

        canvas.addKeyListener(this);

        arrowPic = Toolkit.getDefaultToolkit().getImage("Arrow.png");
        squaresPic = Toolkit.getDefaultToolkit().getImage("redsquare.png");

        user = new Arrow(500, 600, 5, 5, arrowPic);
        squares = new Square[5];
        for(int x = 0;x < 5; x++){
            squares[x] = new Square (x*100+100,400,3,3, squaresPic);
        }

    }

    public void moveThings () {
        user.move();
    }

    public void checkIntersections () {
        
    }

    public void run () {
        while (true) {
            moveThings();           //move all the game objects
            checkIntersections();   // check character crashes
            render();               // paint the graphics
            pause(20);         // sleep for 20 ms
        }
    }

    public void render () {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        g.drawImage(user.pic, user.xpos, user.ypos, user.width, user.height, null);
        for (int x=0; x<5; x++) {
            g.drawImage(squares[x].pic, squares[x].xpos, squares[x].ypos, squares[x].width, squares[x].height, null);
        }

        g.dispose();
        bufferStrategy.show();


    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 68) {
            user.right=true;
        }

        if (keyCode == 65) {
            user.left=true;
        }
    }
    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();

        if (keyCode == 68) {
            user.right=false;
        }

        if (keyCode == 65) {
            user.left=false;
        }
    }

    public void keyTyped(KeyEvent event) {

    }

    public void setUpGraphics() {
        frame = new JFrame("Cubefield");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
        System.out.println("DONE graphic setup");

    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }
}
