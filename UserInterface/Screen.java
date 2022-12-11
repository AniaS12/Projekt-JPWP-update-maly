package UserInterface;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.net.MalformedURLException;
import java.net.URL;

import Object.*;
import Other.Resource;

public class Screen extends JPanel implements Runnable, KeyListener {

    public static final int GAME_FIRST_STATE = 0;
    public static final int GAME_PLAY_STATE = 1;
    public static final int GAME_DEATH_STATE = 2;
    public static final float GRAVITY = 0.3f;   //szybkosc spadania
    public static final float GROUNDY = 700;    //poziom ziemi

    private MainCharacter mainCharacter;
    private Thread thread;
    private Land land;
    private EnemiesManager enemiesManager;
    private int score;

    private int gameState = GAME_FIRST_STATE;

    private BufferedImage imageGameOver;

    private AudioClip scoreUpSound;

    public Screen(){
        thread = new Thread(this);
        mainCharacter = new MainCharacter();
        mainCharacter.setX(50);
        mainCharacter.setY(650);
        land = new Land();
        enemiesManager = new EnemiesManager(mainCharacter, this);
        imageGameOver = Resource.getResourceImage("data/gameover.png");
        try {
            scoreUpSound = Applet.newAudioClip(new URL("file","","data/scoreup.wav"));
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void startGame(){
        thread.start();
    }

    @Override
    public void run() {                 //poruszanie się świata
        while(true){
            try {
                update();
                repaint();
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update(){
        switch (gameState){
            case GAME_PLAY_STATE:
                mainCharacter.update();
                land.update();
                enemiesManager.update();

                if(!mainCharacter.getAlive()){
                    gameState = GAME_DEATH_STATE;
                    mainCharacter.state = 4;
                }
                break;
        }
    }

    public void plusScore(int score){
        this.score += score;
        scoreUpSound.play();
    }

    @Override
    public void paint(Graphics g){
        super.paint(g);                 //zmiana otoczenia, przesuwa się

        g.setColor(Color.decode("#f7f7f7"));
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.BLACK);
        //g.drawLine(0, (int)GROUNDY, getWidth(),(int)GROUNDY);

        switch (gameState){
            case GAME_FIRST_STATE:
                mainCharacter.draw(g);
                break;
            case GAME_PLAY_STATE:
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g.drawString("Droga: " + String.valueOf(score), 1000, 20);
                break;
            case GAME_DEATH_STATE:
                land.draw(g);
                mainCharacter.draw(g);
                enemiesManager.draw(g);
                g.drawImage(imageGameOver, 540, 600, null);
                break;
        }
    }

    private void resetGame(){
        mainCharacter.setAlive(true);
        mainCharacter.setX(50);
        mainCharacter.setY(650);
        enemiesManager.reset();
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP)
        {
            mainCharacter.jump();       //skok
        }
        else if (e.getKeyCode() == KeyEvent.VK_DOWN)
        {
            mainCharacter.duck(true);       //kucanie
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()){
            case KeyEvent.VK_SPACE:
                if (gameState == GAME_FIRST_STATE){
                    gameState = GAME_PLAY_STATE;
                }
                else if (gameState == GAME_DEATH_STATE){
                    resetGame();
                    gameState = GAME_PLAY_STATE;
                }
                break;
            case KeyEvent.VK_DOWN:
                    mainCharacter.duck(false);       //kucanie
                break;
        }
    }
}
