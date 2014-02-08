
import java.awt.event.KeyEvent;
import java.util.ArrayList;
public class Player extends GameEntity{
    private int leftUpKey;
    private int rightDownKey;
    private int shootKey;
    private int shoot_counter;
    private int direction; 
    double health;
    private static long lastFlip = 0;
    // what direction the player is facing
    // 0 - up
    // 1 - down
    // 2 - left
    // 3 - right

    public Player(GameThread param, int xPos, int yPos, int leftUp, int rightDown, int shoot) {
        super(param);
        setXpos(xPos);
        setYpos(yPos);
        setWidth(32);
        setHeight(32);
        health = 3.0;
        
        leftUpKey = leftUp;
        rightDownKey = rightDown;
        shootKey = shoot;
        shoot_counter = 0;
        direction=0;
    }

    @Override
    public boolean update(){
        shoot_counter++;
        KeyboardListener kl = getReference().getReference().getKeyListener();
        
        if(kl.isKeyPressed(leftUpKey)){
            // move up
            if (leftUpKey == 49 || leftUpKey == 61) {
            	setYpos((getYpos()-6 + 800) % 800);
            }

            // move left
            if (leftUpKey == 90 || leftUpKey == 44) {
            	setXpos((getXpos()-6 + 800) % 800);
            }
            
        }
        
        if(kl.isKeyPressed(rightDownKey)){
            // move down
            if (rightDownKey == 87 || rightDownKey == 80) {
            	setYpos((getYpos()+6 + 800) % 800);
            }

            // move right
            if (rightDownKey == 67 || rightDownKey == 47) {
            	setXpos((getXpos()+6 + 800) % 800);
            }
            
        }
        if (kl.isKeyPressed(shootKey)) {
	    	if(kl.isKeyPressed(50) || kl.isKeyPressed(45) || kl.isKeyPressed(88) || kl.isKeyPressed(46)){
	            // shoot
	            if(shoot_counter>50){
	                Player_Bullet temp=new Player_Bullet(getReference());
	                temp.setDirection(direction);
	                //temp.setWidth(12);
	                //temp.setHeight(12);
	                temp.setXpos(getXcenter());
	                temp.setYpos(getYcenter());
	                getReference().getPlayerBullets().add(temp);
	                shoot_counter=0;
	            }
	        }
    	}
	
	if (kl.isKeyPressed(KeyEvent.VK_SHIFT)) {
		if(lastFlip == 0 || System.currentTimeMillis() - lastFlip >=500) {
		    super.getReference().flipView();
		    lastFlip = System.currentTimeMillis();
		}
	}
        return checkForCollision();
    }
    
    public boolean checkForCollision(){
        ArrayList<Enemy_Bullet> ebullets=getReference().getEnemyBullets();
        for(int i=0;i<ebullets.size();i++){
            if(ebullets.get(i).getRect().intersects(getRect())){
                ebullets.remove(i);
                if(--health == 0)
                    return true;
                setHeight((int)(32 * (health/3)));
                setWidth((int)(32 * (health/3)));
            }
        }
        return false;
    }
    
    public int getDirection(){return direction;}
    
    public void setDirection(int param){direction=param;}
}

// 1 - 49 up
// w - 87 down
// 2 - 50 shoot

// = - 61 up
// p - 80 down
// - - 45 shoot

// z - 90 left
// c - 67 right
// x - 88 shoot

// , - 44 left
// / - 47 right
// . - 46 shoot