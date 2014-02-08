
public class Star extends GameEntity{
    private int life;
    
    public Star(GameThread param,int x,int y){
        super(param);
        setXpos(x);
        setYpos(y);
        setWidth(4);
        setHeight(4);
        life=(int)(Math.random()*80);
    }
    
    @Override
    public boolean update(){
        life--;
        if(life<0)
            return true;
        return false;
    }
}
