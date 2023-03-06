import java.awt.*;

public class Arrow {
    public int xpos;
    public int ypos;
    public int width;
    public int height;
    public boolean isAlive;
    public int dx;
    public int dy;
    public Rectangle rec;
    public Image pic;
    public boolean right;
    public boolean down;
    public boolean left;
    public boolean up;
    public int levelCounter;
    public int arrowSpeed;

    public Arrow(int pXpos, int pYpos, int dxParameter, int dyParameter, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
        width = 80;
        height = 80;
        dx = dxParameter;
        dy = dyParameter;
        pic = picParameter;
        isAlive = true;
        rec = new Rectangle(xpos, ypos, width, height);

    }


    public void move() {

        xpos = xpos + dx;

        if(right == true) {
            dx = 14;
        } else if (left == true){
            dx = -14;
        } else {
            dx=0;
        }

        if(down == true) {
            dy = 10;
        } else if (up == true){
            dy = -10;
        } else {
            dy=0;
        }

        if(xpos>1000-width){
            xpos = 1000-width;
        }

        if(xpos < 0) {
            xpos = 0;
        }

        if(ypos>700-height){
            ypos = 700-height;
        }

        if(ypos < 0) {
            ypos = 0;
        }

        rec = new Rectangle(xpos, ypos, width, height);

    }

}
