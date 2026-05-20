package main;

import entities.bullets.Bullet;
import entities.ships.Ship;
import entities.ships.enemies.*;
import entities.ships.player.*;
import entities.upgrades.AmpUp;
import entities.upgrades.Shield;
import entities.upgrades.PowerUp;
import entities.upgrades.Shift;

import javax.imageio.ImageIO;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import javax.swing.Timer;

/**
 * A játék osztálya. Egyszerre csak egyszer inicializálható
 */
public class Game extends JPanel implements ActionListener, KeyListener , MouseListener {

    /**
     * A játék ablaka
     */
    private final JFrame window;

    /**
     * Alap paraméterek, felbontás és tickrate
     */
    private final int resX, resY, Hz;

    /**
     * Az utolsó tick ideje
     */
    private long t_last;

    /**
     * A játék indítási ideje
     */
    private final long t_start;

    /**
     * Az épp létező hajók listája
     */
    protected final LinkedList<Ship> ships = new LinkedList<>();

    /**
     * Az épp létező töltények listája
     */
    protected final LinkedList<Bullet> bullets = new LinkedList<>();

    /**
     * Az épp pályán levő fejlesztések listája
     */
    protected final LinkedList<PowerUp> powerUps = new LinkedList<>();

    /**
     * A háttérkép, azért van külön kezelve mert ez nem lehet BufferedImage
     */
    private final Image background;

    /**
     * A pontszám
     */
    private int score = 0;

    /**
     * Igaz, ha átváltottunk hardmode-ba
     */
    private boolean hardmode = false;

    /**
     * Igaz, ha meghaltunk
     */
    protected boolean over = false;

    /**
     * A játéko objektuma
     * Ez íródik felül ha egy fejlesztést felszedünk
     */
    public Player player;

    /**
     * A zene beolvasását kezelő Strean
     */
    public AudioInputStream stream;

    /**
     * A zene lejátszását kezelő objektum
     */
    public Clip clip;

    /**
     * A zene hangerőkezeléséhez szükséges objektum
     */
    public FloatControl fc;

    /**
     * A játék textúráit tartalmazó map
     */
    public HashMap<String, BufferedImage> textures = new HashMap<>();

    /**
     * A pontos tick frissítés hívásra használt osztály
     */
    public Timer timer;

    public Game(int x, int y, JFrame frame) throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        window = frame;
        resX = x;
        resY = y;
        Hz = 60;

        setDoubleBuffered(true);
        setPreferredSize(new Dimension(resX, resY));

        textures.put("player", ImageIO.read(new File("src/res/textures/player.png")));
        textures.put("ampedplayer", ImageIO.read(new File("src/res/textures/ampedplayer.png")));
        textures.put("shieldplayer", ImageIO.read(new File("src/res/textures/shieldplayer.png")));
        textures.put("shiftplayer", ImageIO.read(new File("src/res/textures/shiftplayer.png")));
        textures.put("amped", ImageIO.read(new File("src/res/textures/amped.png")));
        textures.put("shield", ImageIO.read(new File("src/res/textures/shield.png")));
        textures.put("shift", ImageIO.read(new File("src/res/textures/shift.png")));
        textures.put("e1", ImageIO.read(new File("src/res/textures/enemy1.png")));
        textures.put("e2", ImageIO.read(new File("src/res/textures/enemy2.png")));
        textures.put("e3", ImageIO.read(new File("src/res/textures/enemy3.png")));
        textures.put("ampedbg", ImageIO.read(new File("src/res/textures/ampedbg.png")));
        textures.put("shieldbg", ImageIO.read(new File("src/res/textures/shieldbg.png")));
        textures.put("shiftbg", ImageIO.read(new File("src/res/textures/shiftbg.png")));

        background = ImageIO.read(new File("src/res/textures/bg.png")).getScaledInstance(resX, resY, Image.SCALE_DEFAULT);
        player = new DefaultPlayer(0.5, 0.5, textures.get("player"));
        ships.add(player);
        stream = AudioSystem.getAudioInputStream(new File("src/res/sounds/bgmusic.wav"));
        clip = AudioSystem.getClip();
        clip.open(stream);
        fc = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);

        if(Main.getsound())
            clip.start();


        timer = new Timer(1000/Hz, this);
        t_start = System.currentTimeMillis();
        t_last = t_start;
        timer.start();

    }

    /**
     * Ez a függvény hívódik meg a Timer által 16 ms-ként.
     * Ez a függvény hívja meg az update() és repaint függvényt()
     * Valamit a hardmode-ba váltást is kezeli
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(!hardmode && t_last-t_start > 40000) {
            System.out.println("NYOMOD");
            ships.add(new Corvette());
            ships.add(new Corvette());
            ships.add(new Corvette());
            ships.add(new Corvette());
            ships.add(new Destroyer());
            ships.add(new Destroyer());
            ships.add(new Destroyer());
            hardmode = true;
        }
        try {
            update();

            repaint();
            //System.out.println( 1000/(System.currentTimeMillis()-t_last)+ "FPS");
            t_last = System.currentTimeMillis();
        } catch (RuntimeException gameover){
            over = true;
            timer.stop();
            fc.setValue(-16.0f);

            Graphics g = getGraphics();
            g.drawImage(background, 0, 0, this);
            g.setColor(Color.WHITE);
            g.drawString("SCORE: " + score, 0, 20);
            g.setFont(new Font("Font", Font.PLAIN,150));
            g.setColor(Color.LIGHT_GRAY);
            g.drawString("GAME OVER", resX/2-425, resY/2+50);
            g.setFont(new Font("Font", Font.PLAIN,50));
            g.drawString("Press Esc to quit", resX/2-200, resY/2+200);
        }
    }

    /**
     * A minden tickenként lefutó, mindent frissítő függvény.
     * Ugyanitt történik az új ellenfelek és fejlesztések hozzásadása.
     */
    private void update(){
        score += 1;
        int rand = (int)(Math.random()*1000);
        
        if(hardmode) {
            score += 1;
            if (rand < 20)
                ships.add(new Fighter());
            else if (rand < 30)
                ships.add(new Corvette());
            else if (rand < 35)
                ships.add(new Destroyer());
        }
        else{
            if (rand < 7)
                ships.add(new Fighter());
            else if (rand < 10)
                ships.add(new Corvette());
        }
        for(Bullet bullet : bullets){
            bullet.update();
        }

        Iterator<Ship> shipi = ships.iterator();
        while(shipi.hasNext()){
            Ship ship = shipi.next();
            ship.update();
            Iterator<Bullet> bulleti = bullets.iterator();
            while(bulleti.hasNext()) {
                Bullet bullet = bulleti.next();
                if(bullet == null)
                    break;
                if (ship.hitcheck(bullet)) {
                    try {
                        if(ship.getHp()<=0) {
                            if(rand < 50)
                                powerUps.add(new Shield(ship.getPosX(), ship.getPosY()));
                            else if(rand < 100)
                                powerUps.add(new AmpUp(ship.getPosX(), ship.getPosY()));
                            else if(rand < 150)
                                powerUps.add(new Shift(ship.getPosX(), ship.getPosY()));
                            score += 300;
                            shipi.remove();
                        }
                        bulleti.remove();
                        score += 50;
                    } catch (IllegalStateException e){
                        //eddig ezt nem sikerult reprodukalni
                    }
                }
            }
        }

        Iterator <PowerUp> upgi = powerUps.iterator();
        while(upgi.hasNext()) {
            PowerUp powerUp = upgi.next();
            powerUp.update();
            if(powerUp.hitcheck()){
                ships.remove(player);
                player = powerUp.pickup();
                ships.add(player);
                upgi.remove();
                score += 50;
            }
        }

        bullets.removeIf(bullet -> t_last-bullet.getTime() > 3000);
        powerUps.removeIf(powerUp -> t_last- powerUp.getTime() > 10000);
    }

    /**
     * Kirajzolja a képernyőre az összes komponenst
     * @param g A JPanel Graphicse
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(background, 0, 0, this);
        for(Ship ship : ships){
            ship.draw(g, this);
        }
        for(Bullet bullet : bullets){
            bullet.draw(g, this);
        }
        for(PowerUp powerUp : powerUps){
            powerUp.draw(g, this);
        }
        g.setColor(Color.WHITE);
        g.drawString("SCORE: " + score, 0, 20);
    }

    @Override
    @Deprecated
    public void keyTyped(KeyEvent e) {}

    /**
     * A billentyűk lenyomásával foglalkozó függvény
     * @param e A lenyomott billentyű adatai
     */
    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.pressedW();
            case KeyEvent.VK_A -> player.pressedA();
            case KeyEvent.VK_S -> player.pressedS();
            case KeyEvent.VK_D -> player.pressedD();
            case KeyEvent.VK_SPACE -> player.pressedSpace();
        }
    }

    /**
     * A felengedett billentyűket figyeli.
     * Ebben a függvényben záródik be az ablak az
     * Esc gomb megnymásával miután meghaltunk.
     * @param e the event to be processed
     */
    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> player.releasedW();
            case KeyEvent.VK_A -> player.releasedA();
            case KeyEvent.VK_S -> player.releasedS();
            case KeyEvent.VK_D -> player.releasedD();
            case KeyEvent.VK_SPACE -> player.releasedSpace();
            case KeyEvent.VK_ESCAPE -> {
                if(over){
                    clip.stop();
                    window.dispose();
                    Main.launcher.addScore(score);
                    Main.launchwindow.setVisible(true);
                }}
        }
    }

    @Override
    @Deprecated
    public void mouseClicked(MouseEvent e) {}

    /**
     * Ha egy egérgomb lenyomódik, ez meghívódik
     * @param e Az egér adatai (pozíció, lenyomott gomb)
     */
    @Override
    public void mousePressed(MouseEvent e) {
        switch (e.getButton()){
            case MouseEvent.BUTTON1 -> player.fire(e.getX()/(double)getResX(), (e.getY()-28)/(double)getResX());
            case MouseEvent.BUTTON3 -> player.special();
        }
    }


    @Override
    @Deprecated
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    @Deprecated
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    @Deprecated
    public void mouseExited(MouseEvent e) {

    }

    public LinkedList<Ship> getShips(){
        return ships;
    }

    public void addBullet(Bullet b){
        bullets.add(b);
    }

    public int getResX() {
        return resX;
    }
}
