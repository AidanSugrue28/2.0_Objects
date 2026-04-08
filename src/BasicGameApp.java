import java.awt.Graphics2D;
import java.awt.event.*;
import java.awt.image.BufferStrategy;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class BasicGameApp implements Runnable, KeyListener, MouseListener {

    final int WIDTH = 1000;
    final int HEIGHT = 700;

    public JFrame frame;
    public Canvas canvas;
    public JPanel panel;
    public BufferStrategy bufferStrategy;

    public Image astroPic;
    public Image backgroundPic;

    public Image asteriodPic;
    public Asteriod asteriod1;
    public Asteriod asteriod2;
    public Rectangle startHitbox;
    public boolean startGame;
    public Asteriod[] asteriods;

    private Astronaut astro;

    public static void main(String[] args) {
        BasicGameApp ex = new BasicGameApp();
        new Thread(ex).start();
    }

    public BasicGameApp() {

        setUpGraphics();

        astroPic = Toolkit.getDefaultToolkit().getImage("astronaut.png");
        backgroundPic = Toolkit.getDefaultToolkit().getImage("Stars.jpg");
        asteriodPic = Toolkit.getDefaultToolkit().getImage("Asteroid_Vesta-1.jpeg");

        astro = new Astronaut(100, 100);

        asteriod1 = new Asteriod(300, 200);
        asteriod2 = new Asteriod(100, 500);

        asteriod1.dx = -asteriod1.dx;
        asteriod1.dy = -asteriod1.dy;

        startHitbox = new Rectangle(100, 100, 100, 100);

        asteriods = new Asteriod[5];

        for (int i = 0; i < asteriods.length; i++) {
            asteriods[i] = new Asteriod(200, (int) (Math.random() * 700));
        }
    }

    public void run() {
        while (true) {
            moveThings();
            render();
            pause(20);
        }
    }

    public void moveThings() {
        if (startGame == true)
            astro.move();

        asteriod1.move();
        asteriod2.move();

        for (int o = 0; o < asteriods.length; o++) {
            asteriods[o].move();
        }

        crashing();
    }

    public void crashing() {
        if (asteriod1.hitbox.intersects(asteriod2.hitbox) && asteriod2.isCrashing == false) {
            System.out.println("ASTERIOD CRASH!");
            asteriod2.width = asteriod2.width + 100;
            asteriod2.height = asteriod2.height + 100;
            asteriod2.isCrashing = true;
        }

        if (!asteriod1.hitbox.intersects(asteriod2.hitbox)) {
            asteriod2.isCrashing = false;
        }

        for (int x=0; x< asteriods.length; x++){
            if (asteriods[x].hitbox.intersects(astro.hitbox)){
                System.out.println("astro crash");
            }
        }
    }

    public void pause(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
        }
    }

    private void setUpGraphics() {
        frame = new JFrame("Application Template");

        panel = (JPanel) frame.getContentPane();
        panel.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        panel.setLayout(null);

        canvas = new Canvas();
        canvas.setBounds(0, 0, WIDTH, HEIGHT);
        canvas.setIgnoreRepaint(true);

        canvas.addKeyListener(this);
        canvas.addMouseListener(this);

        panel.add(canvas);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);

        canvas.createBufferStrategy(2);
        bufferStrategy = canvas.getBufferStrategy();
        canvas.requestFocus();
    }

    private void render() {
        Graphics2D g = (Graphics2D) bufferStrategy.getDrawGraphics();

        g.clearRect(0, 0, WIDTH, HEIGHT);

        if (startGame == true) {
            g.drawImage(backgroundPic, 0, 0, WIDTH, HEIGHT, null);

            g.drawImage(asteriodPic, asteriod1.xpos, asteriod1.ypos, asteriod1.width, asteriod1.height, null);
            g.drawImage(asteriodPic, asteriod2.xpos, asteriod2.ypos, asteriod2.width, asteriod2.height, null);

            g.drawImage(astroPic, astro.xpos, astro.ypos, astro.width, astro.height, null);

            for (int b = 0; b < asteriods.length; b++) {
                g.drawImage(asteriodPic, asteriods[b].xpos, asteriods[b].ypos, asteriods[b].width, asteriods[b].height, null);
            }
        }

        g.setColor(Color.green);
        g.fillRect(100, 100, 100, 100);

        g.dispose();
        bufferStrategy.show();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) astro.isNorth = true;
        if (key == KeyEvent.VK_DOWN) astro.isSouth = true;
        if (key == KeyEvent.VK_RIGHT) astro.isEast = true;
        if (key == KeyEvent.VK_LEFT) astro.isWest = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();

        if (key == KeyEvent.VK_UP) astro.isNorth = false;
        if (key == KeyEvent.VK_DOWN) astro.isSouth = false;
        if (key == KeyEvent.VK_RIGHT) astro.isEast = false;
        if (key == KeyEvent.VK_LEFT) astro.isWest = false;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void mouseClicked(MouseEvent e) {}

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.println(e.getPoint());
        Rectangle pointHitbox = new Rectangle(e.getX(), e.getY(), 1, 1);

        if (startHitbox.intersects(pointHitbox)) {
            System.out.println("start game");
            startGame = true;
        }

        astro.xpos = e.getX() - astro.width / 2;
        astro.ypos = e.getY() - astro.height / 2;
    }

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.println("mouse entered the screen");
    }

    @Override
    public void mouseExited(MouseEvent e) {}
}