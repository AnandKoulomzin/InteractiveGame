import java.awt.Graphics2D;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.*;

import java.awt.event.*;

public class Cubefield implements Runnable, KeyListener{

    final int WIDTH = 1000;
    final int HEIGHT = 700;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    public static void main(String[] args) {
        Cubefield myApp = new Cubefield();
        new Thread(myApp).start();
    }

    public Cubefield() {
        setUpGraphics();

        canvas.addKeyListener(this);
    }

    public void moveThings () {

    }

    public void checkIntersections () {
        
    }

    public void run () {

    }

    public void render () {


    }

    public void keyPressed(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();
        System.out.println("Key Pressed: " + key + "  Code: " + keyCode);

        if (keyCode == 68) {

        }
        if (keyCode == 83) {

        }
        if (keyCode == 65) {

        }
        if(keyCode == 87){

        }
    }
    public void keyReleased(KeyEvent event) {
        char key = event.getKeyChar();
        int keyCode = event.getKeyCode();

        if (keyCode == 68) {

        }
        if (keyCode == 83) {

        }
        if (keyCode == 65) {

        }
        if(keyCode == 87){

        }

    }

    public void keyTyped(KeyEvent event) {

    }

    public void setUpGraphics() {

    }

    public void pause(int time) {

    }


}
