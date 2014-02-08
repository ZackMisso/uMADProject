
import java.awt.Graphics;
import java.awt.Color;
public class EndMenu {
    private final Game reference;
    private final Background back;
    
    public EndMenu(Game param){
        back=new Background();
        reference=param;
    }
    
    public boolean update(){
        back.update();
        //if(reference.getKeyListener().isKeyPressed(32))
        //    return true;
        return false;
    }
    
    public void paint(Graphics g){
        // Draw Back ground
        g.setColor(Color.WHITE);
        back.paint(g);
        // Draw G
        g.drawRect(40,40,100,40);
        g.drawRect(40,40,40,100);
        g.drawRect(40,80,100,40);
        g.drawRect(120,60,40,60);
        /*
        g.setColor(Color.WHITE);
        back.paint(g);
        // Draw B
        g.setColor(Color.CYAN);
        g.drawRect(40,40,50,200);
        g.drawRect(90,40,20,100);
        g.drawRect(90,40,20,100);
        // Draw L
        g.setColor(Color.RED);
        g.drawRect(100,40,50,200);
        g.drawRect(100,200,170,50);
        // Draw O
        g.setColor(Color.GREEN);
        g.drawRect(180,40,40,200);
        g.drawRect(260,40,40,200);
        g.drawRect(180,40,80,200);
        g.drawRect(180,200,80,200);
        // Draw C
        g.setColor(Color.WHITE);
        g.drawRect(310,40,50,200);
        g.drawRect(310,40,100,60);
        g.drawRect(310,190,100,60);
        // Draw S
        g.setColor(Color.YELLOW);
        g.drawRect(320,50,50,100);
        g.drawRect(320,50,100,40);
        g.drawRect(320,100,100,40);
        g.drawRect(440,100,50,100);
        g.drawRect(320,200,100,40);
        // WORDS
        g.setColor(Color.ORANGE);
        g.drawString("Press Space to Start", 380, 500);
        //g2.drawRect(p.getXpos(),p.getYpos(),p.getWidth(),p.getHeight());*/
    }
}
