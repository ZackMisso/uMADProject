
import java.util.ArrayList;
import java.awt.Graphics;
public class Background{
    public ArrayList<Star> stars;
    
    public Background(){
        stars=new ArrayList<Star>();
        for(int i=0;i<50;i++){
            int x=(int)(Math.random()*800);
            int y=(int)(Math.random()*800);
            stars.add(new Star(null,x,y));
        }
    }
    
    public boolean update(){
        for(int i=stars.size()-1;i>-1;i--){
            if(stars.get(i).update()){
                stars.remove(i);
                int x=(int)(Math.random()*800);
                int y=(int)(Math.random()*800);
                stars.add(new Star(null,x,y));
            }
        }
        return false;
    }
    
    public void paint(Graphics g){
        for(Star obj:stars)
            g.drawRect(obj.getXpos(),obj.getYpos(),obj.getWidth(),obj.getHeight());
    }
}
