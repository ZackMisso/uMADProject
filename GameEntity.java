
import java.awt.Rectangle;
public abstract class GameEntity{
    private final GameThread reference;
    private final Rectangle rigidBody;
    
    public GameEntity(GameThread param){
        reference=param;
        rigidBody=new Rectangle(0,0,0,0);
    }
    
    
    public abstract boolean update();
    
    public int getXpos(){
        return (int)rigidBody.getX();
    }
    
    public int getXcenter() {
	return (int)rigidBody.getCenterX();
    }
    
    public int getYcenter() {
	return (int)rigidBody.getCenterY();
    }
    
    public int getYpos(){
        return (int)rigidBody.getY();
    }
    
    public int getWidth(){
        return (int)rigidBody.getWidth();
    }
    
    public int getHeight(){
        return (int)rigidBody.getHeight();
    }
    
    public void setXpos(int param){
        rigidBody.setLocation(param,(int)rigidBody.getY());
    }
    
    public void setYpos(int param){
        rigidBody.setLocation((int)rigidBody.getX(),param);
    }
    
    public void setWidth(int param){
        rigidBody.setSize(param,(int)rigidBody.getHeight());
    }
    
    public void setHeight(int param){
        rigidBody.setSize((int)rigidBody.getWidth(),param);
    }

    public GameThread getReference(){return reference;}
    public Rectangle getRect(){return rigidBody;}
}
