import java.awt.*;
public class Square {
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public int dx;                    //the speed of the hero in the x direction
    public int dy;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;

    public Square(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        dx = dxParameter;
        dy = dyParameter;
        width = 50;
        height = 50;
        dx = 5;
        dy = -5;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);


    }
    public void move() {
        xpos = xpos + dx;
        ypos = ypos + dy;

        if (xpos > 1000 - width || xpos < 0) {
            dx = -dx;
        }

        if (ypos < 0 || ypos + height > 700) {
            dy = -dy;
        }

        rec = new Rectangle(xpos, ypos, width, height);

    }
}
