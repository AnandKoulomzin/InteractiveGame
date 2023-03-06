import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.sound.sampled.Line;
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
    public boolean gameStart = false;
    public boolean hasChosen = false;
    public int levelCounter = 0;
    public boolean finished  = false;
    public int stopTimer;
    public boolean stopping=false;
    public int stopCounter = 0;


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
        for (int x = 0; x < squares.length; x++) {
            squares[x] = new Square(x * 100 + 35, 40, squaresPic);
        }
        levelCounter++;
        System.out.println("level " + levelCounter);
        for (int x = 0; x < squares.length; x++) {
            squares[x].dy=squares[x].dy-levelCounter/2;
        }
        user.ypos=user.ypos-levelCounter*2;
        System.out.println(user.ypos);
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

    public void setStopTimer () {
        if (stopping == true) {
            stopTimer++;
        }
        if (stopTimer > 30) {
            stopping = false;
            stopTimer = 0;
        }
    }

    public void moveThings () {
        user.move();

        for (int x = 0; x < squares.length; x++){
            if( squares[x].isAlive == true) {

                if (stopping == false) {
                    squares[x].move();
                }
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
                    setStopTimer();
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


                for (int x = 0; x < 10; x++) {
                    if (squares[x].isAlive == true) {
                        g.drawImage(squares[x].pic, squares[x].xpos, squares[x].ypos, squares[x].width, squares[x].height, null);
                    }
                }
            }
        }

        g.drawString("Score: " + levelCounter, 20, 20);
        g.drawString("Stops used: " + stopCounter + "/3", 900, 20);

        if (user.isAlive == false) {
            g.setColor(Color.WHITE);
            g.fillRect(0,0,WIDTH,HEIGHT);
            g.setColor(Color.BLACK);
            g.setFont(new Font("TimesRoman", Font.PLAIN , 25));
            g.drawString("you lose. L", 350,240);
            g.setFont(new Font("TimesRoman", Font.BOLD , 25));
            g.drawString("Score: " + levelCounter, 350, 280);
            g.setFont(new Font("TimesRoman", Font.PLAIN , 25));
            g.drawString("hold the space bar to play again", 350, 320);
            finished=true;

        }

        bufferStrategy.show();
        g.dispose();

    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        // System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 10 && gameStart == false) {  //enter starts the game
            gameStart = true;
        }

        if (keyCode == 68) {  // d makes the arrow go right
            user.right = true;
        }

        if (keyCode == 65) {  // a makes the arrow go left
            user.left = true;
        }
        if (keyCode == 32) {
            finished = false;
            user.isAlive = true;
            user.ypos = 600;
            levelCounter = 0;
            user.xpos = 500;
        }
        if (keyCode == 16 && stopCounter < 3) {
            stopping = true;
            stopCounter++;
            System.out.println(stopCounter);
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
        if (keyCode == 32) {
            finished=false;
            user.isAlive=true;
            levelCounter=0;
            user.xpos=500;
            stopCounter=0;
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
