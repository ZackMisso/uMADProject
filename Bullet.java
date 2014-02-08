
public abstract class Bullet extends GameEntity{
    private int direction;
    
    public Bullet(GameThread param){
        super(param);
        direction=-1;
        setWidth(8);
        setHeight(8);
    }
    
    @Override
    public boolean update(){
        if(direction==0){ // UP
            setYpos(getYpos()-5);
        }
        else if(direction==1){ // DOWN
            setYpos(getYpos()+5);
        }
        else if(direction==2){ // LEFT
            setXpos(getXpos()-5);
        }
        else if(direction==3){ // RIGHT
            setXpos(getXpos()+5);
        }
        return checkBounds();
    }
    
    // this class checks if the bullet is out of the bounds of the screen
    public boolean checkBounds(){
        if(getXpos()>810||getXpos()<-10||getYpos()>810||getYpos()<-10)
            return true;
        return false;
    }
    
    public int getDirection(){return direction;}
    
    public void setDirection(int param){direction=param;}
}
