import java.awt.*;
public class Square {
    public int xpos;                //the x position
    public int ypos;                //the y position
    public int width;
    public int height;
    public boolean isAlive;            //a boolean to denote if the hero is alive or dead.
    public int dx;                    //the speed of the hero in the x direction
    public int dy = -15;                    //the speed of the hero in the y direction
    public Rectangle rec;
    public Image pic;
    public boolean first;
    public boolean hasChosen;
    public int levelCounter;

    public Square(int pXpos, int pYpos, Image picParameter) {

        xpos = pXpos;
        ypos = pYpos;
//        dx = dxParameter;
//        dy = dyParameter;
        pic = picParameter;
        width = 50;
        height = 50;
        dx = 0;
        dy = -9;

        isAlive = false;
        rec = new Rectangle(xpos, ypos, width, height);

    }



    public void move() {
        ypos = ypos - dy;


//        if (ypos + height > 700) {
//            ypos=-10;
//            System.out.println("changing isAlive to false");
//            isAlive = false;
//            hasChosen=false;
//        }

//        if(ypos==5 && isAlive == true){
//            isAlive=false;
//        }

        rec = new Rectangle(xpos, ypos, width, height);

    }
}
