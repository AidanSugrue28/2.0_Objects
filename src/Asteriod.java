import java.awt.*;

public class Asteriod {

    public String name;
    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public boolean isAlive;
    public Rectangle hitbox;
    public boolean isCrashing;

    public Asteriod(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;

        dx = (int)(Math.random()*11) - 5;
        dy = (int)(Math.random()*11) - 5;

        if(dx == 0) dx = 1;
        if(dy == 0) dy = 1;

        width = 150;
        height = 150;
        isAlive = true;
        hitbox = new Rectangle(xpos, ypos, width, height);
        isCrashing = false;
    }

    public void move() {

        if (ypos > 700) {
            ypos = 0;
        }
        if (xpos > 1000) {
            xpos = 0;
        }
        if (xpos < 0) {
            xpos = 1000;
        }
        if (ypos < 0) {
            ypos = 700;
        }

        xpos = xpos + dx;
        ypos = ypos + dy;

        hitbox = new Rectangle(xpos, ypos, width, height);
    }
}