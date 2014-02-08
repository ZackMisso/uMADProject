
import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.awt.Color;
import java.util.Arrays;

public class GameThread extends JPanel implements Runnable {

    private Game game;
    private MainMenu menu;
    private EndMenu end;
    private Background background;
    private Enemy[][] enemies;
    private int[][] border;
    private ArrayList<Enemy_Bullet> ebullets;
    private ArrayList<Player_Bullet> pbullets;
    private ArrayList<Player> players;
    private boolean radial;
    private boolean mainmenu;
    private boolean endmenu;
    private static int WIN_WIDTH = 800;
    private static int WIN_HEIGHT = 800;

    public GameThread(Game param, boolean radial) {
        game = param;
        this.radial = radial;
        initialize();
        //System.out.println(enemies.size());
        //System.out.println(enemies.get(0).size());
    }

    private void initialize() {
        mainmenu = true;
        endmenu = false;
        end = new EndMenu(game);
        menu = new MainMenu(game);
        enemies = new Enemy[12][12];
        border = new int[12][4];
        ebullets = new ArrayList<>();
        pbullets = new ArrayList<>();
        players = new ArrayList<>();
        background = new Background();
        players.add(new Player(this, 20, 40, 49, 87, 50)); // first player (top-left)
        players.add(new Player(this, 750, 40, 44, 47, 46)); // second player (top-right)
        players.add(new Player(this, 20, 740, 90, 67, 88)); // third player (bottom-left)
        players.add(new Player(this, 750, 740, 61, 80, 45)); // fourth player (bottom-right)
        players.get(0).setDirection(3); // right
        players.get(1).setDirection(1); // down
        players.get(2).setDirection(0); // left
        players.get(3).setDirection(2); // up

        for (int i = 0; i < 12; i++) {
            for (int f = 0; f < 12; f++) {
                Enemy e = new Enemy(this, f, i);
                e.setYpos(i * 40 + 160);
                e.setXpos(f * 40 + 160);
                e.setWidth(32);
                e.setHeight(32);
                enemies[i][f] = e;
            }
        }
        for (int i = 0; i < 12; i++) {
            if (!radial) {
                border[i] = new int[]{0, 11, 0, 11};
            }
            if (radial) {
                border[i] = new int[]{0, 11, -1, -1};
            }
            //top, bottom, leftmost, rightmost
        }
    }

    // the update Loop
    public void update() {
        try {
            if (!mainmenu) {
                for (int i = 0; i < enemies.length; i++) {
                    for (int f = 0; f < enemies[i].length; f++) {
                        if (enemies[i][f] != null) {
                            if (enemies[i][f].update()) {
                                int xpos = f,
                                        ypos = i;
                                enemies[i][f] = null;

                                if (xpos == border[i][2]) {//leftmost
                                    for (int k = 11; k >= 0; k--) {
                                        if (enemies[i][k] != null) {
                                            border[i][2] = k;
                                        }
                                    }
                                }

                                if (xpos == border[i][3]) {//rightmost
                                    for (int k = 0; k < 12; k++) {
                                        if (enemies[i][k] != null) {
                                            border[i][3] = k;
                                        }
                                    }
                                }
                                if (ypos == border[f][0]) {//top
                                    for (int k = 11; k >= 0; k--) {
                                        if (enemies[k][f] != null) {
                                            border[f][0] = k;
                                        }
                                    }
                                }

                                if (ypos == border[f][1]) {//bottom
                                    for (int k = 0; k < 12; k++) {
                                        if (enemies[k][f] != null) {
                                            border[f][1] = k;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

                /*for(int i=0;i<enemies.size();i++)
                 if(enemies.get(i).update())
                 enemies.remove(i--);*/
                for (Player_Bullet obj : pbullets) {
                    obj.update();
                }
                for (Enemy_Bullet obj : ebullets) {
                    obj.update();
                }
                for (Player p : players) {
                    if (p.update() || checkBoard(enemies)) {
                        initialize();
                    }
                }
            } else {
                if (mainmenu) {
                    if (menu.update()) {
                        mainmenu = false;
                    }
                }
                if (endmenu) {
                    end.update();
                    System.out.println("dsafas");
                }
            }
        } catch (Exception e) {
            System.err.println(e);
            e.printStackTrace();
        }
        // ^ happens if there is an eror in the thread (Will probably never happen)
    }

    public boolean checkBoard(Enemy[][] param) {
        for (int i = 0; i < param.length; i++) {
            for (int j = 0; j < param[i].length; j++) {
                if (param[i][j] != null) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    // the draw Loop
    public void paint(Graphics g) {
        if (radial) {
            radialPaint(g);
        } else {
            cartesianPaint(g);
        }
    }

    private void cartesianPaint(Graphics g) {

        BufferedImage backBuffer = new BufferedImage(800, 800, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = backBuffer.getGraphics();
        if (!mainmenu) {
            // draw enemies
            g2.setColor(Color.RED);
            for (int i = 0; i < enemies.length; i++) {
                for (int f = 0; f < enemies[i].length; f++) {
                    Enemy obj = enemies[i][f];
                    if (obj != null) {
                        g2.drawRect(obj.getXpos(), obj.getYpos(), obj.getWidth(), obj.getHeight());
                    }
                }
            }
            //for(Enemy obj : enemies)
            //        g2.drawRect(obj.getXpos(),obj.getYpos(),obj.getWidth(),obj.getHeight());
            // draw player's bullets
            g2.setColor(Color.YELLOW);
            for (Player_Bullet obj : pbullets) {
                g2.drawRect(obj.getXpos(), obj.getYpos(), obj.getWidth(), obj.getHeight());
            }
            // draw enemy bullets
            g2.setColor(Color.ORANGE);
            for (Enemy_Bullet obj : ebullets) {
                g2.drawRect(obj.getXpos(), obj.getYpos(), obj.getWidth(), obj.getHeight());
            }
            // draw player
            g2.setColor(Color.CYAN);
            Player p = players.get(0);
            g2.drawRect(p.getXpos(), p.getYpos(), p.getWidth(), p.getHeight());
            g2.setColor(Color.GREEN);
            p = players.get(1);
            g2.drawRect(p.getXpos(), p.getYpos(), p.getWidth(), p.getHeight());
            g2.setColor(Color.PINK);
            p = players.get(2);
            g2.drawRect(p.getXpos(), p.getYpos(), p.getWidth(), p.getHeight());
            g2.setColor(Color.WHITE);
            p = players.get(3);
            g2.drawRect(p.getXpos(), p.getYpos(), p.getWidth(), p.getHeight());

	    //for(Player p:players)
            //g2.drawRect(p.getXpos(),p.getYpos(),p.getWidth(),p.getHeight());
        } else {
            menu.paint(g2);
        }
		// draw your images onto the buffer
        // the below two lines should ALWAYS go last
        g.drawImage(backBuffer, 0, 0, game.getWindow());
        g2.dispose();
    }

    private void radialPaint(Graphics g) {
        int stretch = 350;
        BufferedImage backBuffer = new BufferedImage(1600, 1600, BufferedImage.TYPE_INT_RGB);
        Graphics g2 = backBuffer.getGraphics();
        // draw enemies
        g2.setColor(Color.RED);
        for (int i = 0; i < enemies.length; i++) {
            for (int f = 0; f < enemies[i].length; f++) {
                Enemy obj = enemies[i][f];
                if (obj != null) {
                    double theta = Math.PI * (obj.getXcenter()) / 400.0,
                            radius = stretch * (obj.getYcenter()) / 800.0;
                    //g2.drawRect(obj.getXcenter(),obj.getYcenter(),obj.getWidth(),obj.getHeight());
                    g2.fillOval((int) (400 + radius * Math.cos(theta)), (int) (400 + radius * Math.sin(theta)), obj.getHeight() / 2, obj.getWidth() / 2);

                }
            }
        }
        // draw player's bullets
        g2.setColor(Color.YELLOW);
        for (Player_Bullet obj : pbullets) {
            double theta = Math.PI * (obj.getXcenter()) / 400.0,
                    radius = stretch * (obj.getYcenter()) / 800.0;
            //g2.drawRect(obj.getXcenter(),obj.getYcenter(),obj.getWidth(),obj.getHeight());
            g2.drawOval((int) (400 + radius * Math.cos(theta)), (int) (400 + radius * Math.sin(theta)), obj.getHeight() / 2, obj.getHeight() / 2);
        }
        // draw enemy bullets
        g2.setColor(Color.ORANGE);
        for (Enemy_Bullet obj : ebullets) {
            double theta = Math.PI * (obj.getXcenter()) / 400.0,
                    radius = stretch * (obj.getYcenter()) / 800.0;
            //g2.drawRect(obj.getXcenter(),obj.getYcenter(),obj.getWidth(),obj.getHeight());
            g2.drawOval((int) (400 + radius * Math.cos(theta)), (int) (400 + radius * Math.sin(theta)), obj.getHeight() / 2, obj.getHeight() / 2);
        }
        // draw player
        g2.setColor(Color.BLUE);
        for (Player p : players) {
            double theta = Math.PI * (p.getXcenter()) / 400.0,
                    radius = stretch * (p.getYcenter()) / 800.0;
            //g2.drawRect(obj.getXcenter(),obj.getYcenter(),obj.getWidth(),obj.getHeight());
            g2.fillOval((int) (400 + radius * Math.cos(theta)), (int) (400 + radius * Math.sin(theta)), p.getHeight() / 2, p.getWidth() / 2);

        }
	    // draw your images onto the buffer
        // the below two lines should ALWAYS go last
        g.drawImage(backBuffer, 0, 0, game.getWindow());
        g2.dispose();
    }

    // the Main Loop (you can ignore this)
    public void run() {
        boolean running = true;
        long lastTime = System.nanoTime();
        double nsPerTick = 1000000000D / 60D;
        int ticks = 0;
        int frames = 0;
        long lastTimer = System.currentTimeMillis();
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;
            boolean shouldRender = false;
            while (delta >= 1) {
                ticks++;
                update();
                delta -= 1;
                shouldRender = true;
            }
            if (shouldRender) {
                frames++;
                paint(game.getWindow().getGraphics());
            }
            if (System.currentTimeMillis() - lastTimer >= 1000) {
                lastTimer += 1000;
				// Uncomment the code below to print out the games ticks and frame rate
                //System.out.println("FPS :: "+ticks+" ticks, "+frames+" frames");
                frames = 0;
                ticks = 0;
            }
        }
        repaint();
    }

    public void flipView() {
        System.out.println(radial = !radial);
    }

    // getter methods
    public Game getReference() {
        return game;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Enemy[][] getEnemies() {
        return enemies;
    }

    public int[][] getBorderline() {
        return border;
    }

    public ArrayList<Player_Bullet> getPlayerBullets() {
        return pbullets;
    }

    public ArrayList<Enemy_Bullet> getEnemyBullets() {
        return ebullets;
    }

    public Game getGame() {
        return game;
    }
}
