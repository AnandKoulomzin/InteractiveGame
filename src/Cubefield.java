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
    public boolean isAlive;
    public int timer;
    public boolean second;
    public boolean gameStart=false;
    public boolean hasChosen = false;
    public int levelCounter = 0;
    public boolean finished  = false;

    public void endScreen () {
    }

    public static void main(String[] args) {
        Cubefield myApp = new Cubefield();
        new Thread(myApp).start();


    }

    public Cubefield() {
        setUpGraphics();

        canvas.addKeyListener(this);

        arrowPic = Toolkit.getDefaultToolkit().getImage("Arrow.png");
        squaresPic = Toolkit.getDefaultToolkit().getImage("orangesquare.png");

        user = new Arrow(500, 600, 5, 5, arrowPic);
        squares = new Square[10];

        makeSquares();
        chooseSquares2();

    }

    public void makeSquares() {
        for(int x = 0; x < squares.length; x++){
            squares[x] = new Square (x*100+35,40, squaresPic);
        }
        levelCounter++;
        System.out.println("level" + levelCounter);
    }



    public void chooseSquares(){

//        if (hasChosen == false) {
            for (int x = 0; x < squares.length; x++) {
          //      System.out.println("squares[x].isAlive is " + squares[x].isAlive);
                if (squares[x].isAlive == false) {
                    System.out.println("choosing square?");
                    double r = Math.random();
                    if (r < 0.7) {
                        System.out.println("test");
                        squares[x].isAlive = true;
                    }
                }
            }
//        }
//        hasChosen = true;
//        System.out.println("has chosen is: " + hasChosen);
    }

    public void chooseSquares2() {
        int squareCounter = 0;
        for (int x = 0; x < squares.length; x++) {
            double r = Math.random();
            if (r < 0.9 && squareCounter < 9) {
             //   System.out.println("test");
                squares[x].isAlive = true;
                squareCounter++;
            }
        }
    }

    public void moveThings () {
        user.move();

        for (int x = 0; x < squares.length; x++){
            if( squares[x].isAlive == true) {
                squares[x].move();
            }

            if (squares[x].ypos > 700) {
//                hasChosen = false;
//                System.out.println("has chosen is: " + hasChosen);
                makeSquares();
                chooseSquares2();
                break;
            }

        }
    }



    public void checkIntersections () {
        for (int x = 0; x < 10; x++) {
            if (user.rec.intersects(squares[x].rec)) {
                user.isAlive = false;
            }
        }
    }

    public void run () {

            while (true) {
                if (gameStart == true && finished==false) {
             //       chooseSquares();
                    moveThings();
                    checkIntersections();
                }
                render();
                pause(20);
            }
    }

    public void render () {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();
        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (gameStart == false){
            //draw start screen
            g.setColor(Color.WHITE);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.BLACK);
            g.drawString("Press enter to start", 300,250);
        }

        else {

            if (user.isAlive == true) {
                g.drawImage(user.pic, user.xpos, user.ypos, user.width, user.height, null);
            }

            for (int x = 0; x < 10; x++) {
                if (squares[x].isAlive == true) {
                    g.drawImage(squares[x].pic, squares[x].xpos, squares[x].ypos, squares[x].width, squares[x].height, null);
                }
            }
        }

        if (user.isAlive == false) {
            g.setColor(Color.WHITE);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.BLACK);
            g.drawString("you lose L. Score:" + levelCounter, 500,400);
            finished=true;


        }

        bufferStrategy.show();
        g.dispose();

    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
       // System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 10 && gameStart == false) {  //return key is starting the game
            gameStart=true;
        }

        if (keyCode == 68) {  // d makes the arrow go right
            user.right=true;
        }

        if (keyCode == 65) {  // a makes the arrow go left
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
