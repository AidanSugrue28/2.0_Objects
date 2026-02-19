import java.awt.*;

public class Astronaut {

    public int xpos;
    public int ypos;
    public int dx;
    public int dy;
    public int width;
    public int height;
    public Rectangle hitbox;

    // movement flags
    public boolean isNorth;
    public boolean isSouth;
    public boolean isEast;
    public boolean isWest;

    public Astronaut(int pXpos, int pYpos) {
        xpos = pXpos;
        ypos = pYpos;
        width = 100;
        height = 100;
        dx = 0;
        dy = 0;
        hitbox = new Rectangle(xpos, ypos, width, height);
    }

    public void move() {

        int speed = 5;

        // vertical movement
        if (isNorth && !isSouth) {
            dy = -speed;
        } else if (isSouth && !isNorth) {
            dy = speed;
        } else {
            dy = 0;
        }

        // horizontal movement
        if (isEast && !isWest) {
            dx = speed;
        } else if (isWest && !isEast) {
            dx = -speed;
        } else {
            dx = 0;
        }

        xpos += dx;
        ypos += dy;

        // screen boundaries
        if (xpos < 0) xpos = 0;
        if (ypos < 0) ypos = 0;
        if (xpos > 1000 - width) xpos = 1000 - width;
        if (ypos > 700 - height) ypos = 700 - height;

        hitbox = new Rectangle(xpos, ypos, width, height);
    }
}