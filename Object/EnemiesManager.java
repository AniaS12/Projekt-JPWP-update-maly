package Object;

import Other.Resource;
import UserInterface.Screen;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EnemiesManager {

    private List<Enemy> enemies;
    private Random random;

    private BufferedImage imageTree1, imageTree2, imageCloud;
    private MainCharacter mainCharacter;
    private Screen screen;

    public EnemiesManager(MainCharacter mainCharacter, Screen screen){
        this.screen = screen;
        this.mainCharacter = mainCharacter;
        enemies = new ArrayList<Enemy>();
        imageTree1 = Resource.getResourceImage("data/tree1.png");
        imageTree2 = Resource.getResourceImage("data/tree2.png");
        imageCloud = Resource.getResourceImage("data/cloud.png");
        random = new Random();

        enemies.add(getRandomTree());
        enemies.add(getRandomClouds());
    }

    public void update(){
        for(Enemy e:enemies){
            e.update();

            if (e.isOver() && !e.addScore()){
                screen.plusScore(20);
                e.setAddScore(true);
            }

            if (e.getBound().intersects(mainCharacter.getBound())){
                mainCharacter.setAlive(false);
            }
        }

        Enemy firstEnemy = enemies.get(0);

        if(firstEnemy.isOutOfScreen()){
            //enemies.clear();
            enemies.remove(firstEnemy);
            enemies.add(getRandomTree());
            enemies.add(getRandomClouds());
        }
    }

    public void draw(Graphics g){
        for (Enemy e:enemies){
            e.draw(g);
        }
    }

    public void reset(){
        enemies.clear();
        enemies.add(getRandomTree());
        enemies.add(getRandomClouds());
    }

    private Tree getRandomTree(){
        Tree tree;

        if(random.nextBoolean()){
            tree = new Tree(mainCharacter);
            tree.setX(1280);
            tree.setImage(imageTree1);
        }
        else {
            tree = new Tree(mainCharacter);
            tree.setX(1280);
            tree.setImage(imageTree2);
        }
        return tree;
    }

    private Clouds getRandomClouds(){
        Clouds clouds;
        clouds = new Clouds(mainCharacter);
        clouds.setX(300);
        clouds.setY(630);

        return clouds;
    }
}
