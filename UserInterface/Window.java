package UserInterface;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Window extends JFrame {

    private Screen screen;

    public Window(){
        super("Java Nous Courons");
        setSize(1280,1024);
        setLocation(150,0);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        screen = new Screen();
        add(screen);
        addKeyListener(screen);
    }

    public void startGame(){
        screen.startGame();
    }

    public static void main(String args[]){
        Window nw = new Window();
        nw.setVisible(true);
        nw.startGame();
    }
}
