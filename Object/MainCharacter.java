package Object;

import Other.Animation;
import Other.Resource;

import java.awt.*;
import java.awt.image.BufferedImage;

import static UserInterface.Screen.*;

public class MainCharacter {

    public int state = 1;

    private float x = 0;
    private float y = 40;
    private float speedY = 0;
    private Animation characterRun;
    private BufferedImage jumpingImage;
    private BufferedImage downRunImage;
    private BufferedImage deathImage;
    private Rectangle rectangle;
    private boolean Alive = true;

    public MainCharacter(){
        characterRun = new Animation(200);
        characterRun.addFrame(Resource.getResourceImage("data/duobieganie1.png"));
        characterRun.addFrame(Resource.getResourceImage("data/duobieganie2.png"));
        jumpingImage = Resource.getResourceImage("data/duogora.png");
        downRunImage = Resource.getResourceImage("data/duodol.png");
        deathImage = Resource.getResourceImage("data/duoprzegrana.png");
        rectangle = new Rectangle();
    }

    public void update(){
        characterRun.update();      //duo porusza się podczas biegania
        if(y >= GROUNDY - characterRun.getFrame().getHeight()){     //linie do skoku
            speedY = 0;
            y = GROUNDY - characterRun.getFrame().getHeight();

            if(state != 3){
                state = 1;
                rectangle.x = (int)x;
                rectangle.y = (int)y;
                rectangle.width = characterRun.getFrame().getWidth();
                rectangle.height = characterRun.getFrame().getHeight();
            }
        }
        else {
            speedY+=GRAVITY;
            y+=speedY;
            rectangle.x = (int)x;
            rectangle.y = (int)y;
            rectangle.width = jumpingImage.getWidth();
            rectangle.height = jumpingImage.getHeight();
        }
    }

    public Rectangle getBound(){
        return rectangle;
    }

    public void draw(Graphics g){
        switch (state){
            case 1:
                g.drawImage(characterRun.getFrame(), (int)x, (int)y, null);
                break;
            case 2:
                g.drawImage(jumpingImage, (int)x, (int)y, null);
                break;
            case 3:
                g.drawImage(downRunImage, (int)x, (int)y + 15, null);
                break;
            case 4:
                g.drawImage(deathImage, (int)x, (int)y, null);
                break;
        }
    }

    public void jump(){
        if(y >= GROUNDY - characterRun.getFrame().getHeight()){
            speedY = -10;                //skok, strzałka w górę
            y += speedY;
            state = 2;
        }
    }

    public void duck(boolean isDown){   //kucanie, strzałka w dół
        if(state == 2){
            return;
        }
        if(isDown){
            state = 3;
            rectangle.x = (int)x;
            rectangle.y = (int)y + 15;
            rectangle.width = downRunImage.getWidth();
            rectangle.height = downRunImage.getHeight();
        }
        else {
            state = 1;
        }
    }

    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getSpeedY() {
        return speedY;
    }

    public void setSpeedY(float speedY) {
        this.speedY = speedY;
    }

    public void setAlive(boolean alive){
        Alive = alive;
    }

    public boolean getAlive(){
        return Alive;
    }
}
