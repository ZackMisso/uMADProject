
import java.util.ArrayList;
public class Enemy extends GameEntity{
	private int counter;
    private int xi;
    private int yi;
    //ArrayList<Integer> directions;
    private int dirX,dirY;
    private boolean checkupdate;

    public Enemy(GameThread param,int x,int y){
        super(param);
        dirX= dirY = -1;
        checkupdate=true;
        xi=x;
        yi=y;
    }
    
    public Enemy(GameThread param,int x,int y,boolean blah){
        super(param);
        checkupdate=false;
        xi=x;
        yi=y;
        dirX = dirY = -1;
    }

    public boolean update(){
        if(checkupdate){
            Enemy[][] enemies=getReference().getEnemies();
	    int[][] border = getReference().getBorderline();
            int start_x=border[yi][2];
            int end_x=border[yi][3];
            int start_y=border[xi][0];
            int end_y=border[xi][1];
            if(xi==start_x){
                dirX = 2;
            }
            if(xi==end_x){
                dirX = 3;
            }
            if(yi==start_y){
                dirY = 0;
            }
            if(yi==end_y){
                dirY = 1;
            }
            if(dirX!=-1) {
                int num=(int)(Math.random()*500);
                if(num > 496){ // change this number to make it harder
                    Enemy_Bullet temp=new Enemy_Bullet(getReference());
                    temp.setDirection(dirX);
                    temp.setXpos(getXpos()+12);
                    temp.setYpos(getYpos()+12);
                    getReference().getEnemyBullets().add(temp);
                }
            }
	    if(dirY!=-1) {
		int num=(int)(Math.random()*500);
                if(num > 497){ // change this number to make it harder
                    Enemy_Bullet temp=new Enemy_Bullet(getReference());
                    temp.setDirection(dirY);
                    temp.setXpos(getXpos()+12);
                    temp.setYpos(getYpos()+12);
                    getReference().getEnemyBullets().add(temp);
                }
	    }
            return checkForCollision();
        }
        return false;
    }
    
    public boolean checkForCollision(){
        ArrayList<Player_Bullet> pbullets=getReference().getPlayerBullets();
        for(int i=0;i<pbullets.size();i++){
            if(pbullets.get(i).getRect().intersects(getRect())){
                pbullets.remove(i);
                return true;
            }
        }
        return false;
    }
    
    public int getXI(){return xi;}
    public int getYI(){return yi;}
    public int getDirX(){return dirX;}
    public int getDirY(){return dirY;}
    public void setDirX(int param){dirX=param;}
    public void setDirY(int param){dirY=param;}
    public void setXI(int param){xi=param;}
    public void setYI(int param){yi=param;}
}