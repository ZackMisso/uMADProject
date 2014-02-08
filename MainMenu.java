
import java.awt.Graphics;
import java.awt.Color;
public class MainMenu {
    private final Game reference;
    private final Background back;
    
    public MainMenu(Game param){
        back=new Background();
        reference=param;
    }
    
    public boolean update(){
        back.update();
        if(reference.getKeyListener().isKeyPressed(32))
            return true;
        return false;
    }
    
    public void paint(Graphics g){
        g.setColor(Color.WHITE);
        back.paint(g);
        // Draw B
        g.setColor(Color.CYAN);
        g.drawRect(140,140,50,200);
        g.drawRect(190,140,40,100);
        g.drawRect(190,240,40,100);
        // Draw L
        g.setColor(Color.RED);
        g.drawRect(240,140,50,200);
        g.drawRect(240,280,100,60);
        // Draw O
        g.setColor(Color.GREEN);
        g.drawRect(350,140,40,200);
        g.drawRect(430,140,40,200);
        g.drawRect(350,140,80,40);
        g.drawRect(350,300,80,40);
        // Draw C
        g.setColor(Color.WHITE);
        g.drawRect(480,140,50,200);
        g.drawRect(480,140,100,60);
        g.drawRect(480,280,100,60);
        // Draw S
        g.setColor(Color.YELLOW);
        g.drawRect(590,140,50,110);
        g.drawRect(590,140,100,40);
        g.drawRect(590,210,100,40);
        g.drawRect(640,210,50,90);
        g.drawRect(590,300,100,40);
        // WORDS
        g.setColor(Color.ORANGE);
        g.drawString("Press Space to Start", 350, 500);
        //g2.drawRect(p.getXpos(),p.getYpos(),p.getWidth(),p.getHeight());
    }
}
