import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class SnakeGUI extends JPanel implements ActionListener, KeyListener {

    // represents colors
    final static Color backgroundColour = Color.white;
    final static Color frameColor = Color.blue;
    final static Color snakeColor = Color.black;
    final static Color pointColor = Color.green;

    Timer timer = new Timer(75, this);

    // id in positions last element
    static int lastID = 0;

    // current destination
    static destination des;

    // position first element
    static int firstX = 25;
    static int firstY = 25;

    // position of point
    static Integer pointX;
    static Integer pointY;


     // represents the positions of a single element
    private class Pos {
        int x; int y;
        Pos(int a, int b){ x = a; y = b; }
    }


     // stores all positions
    static ArrayList<Pos> positions = new ArrayList<Pos>();

    private enum destination {
        UP, DOWN, RIGHT, LEFT;}

    SnakeGUI() {
        positions.add(new Pos(25, 25));
        des = destination.UP;
    }


    // check collision if it happens return true
    boolean collision(){
        for(int i=0; i<positions.size(); i++)
            for(int j=0; j<positions.size(); j++)
                if(i!=j && positions.get(i).x==positions.get(j).x && positions.get(i).y==positions.get(j).y)
                    return true;
        return false;
    }

    // restart game function
    void restart() {
        positions = new ArrayList<Pos>();
        positions.add(new Pos(25, 25));
        des = destination.UP;
        firstX = 25;
        firstY = 25;
        pointX = null;
        pointY = null;
        lastID = 0;
    }



    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        Dimension d = getSize();
        int sideLen = d.width / 50;

        g.setColor(backgroundColour);                                               // fill panel
        g.fillRect(0, 0, d.width - 1, d.height - 1);     // *
        g.setColor(frameColor);                                           // draw panel frame
        g.drawRect(0, 0, d.width - 1, d.height - 1);     // *

        // adding new element depending on current direction
        switch(des) {
            case UP: {
                positions.add(new Pos(firstX, firstY - 1));
                firstY -= 1;
                break; }
            case DOWN: {
                positions.add(new Pos(firstX, firstY + 1));
                firstY += 1;
                break; }
            case RIGHT: {
                positions.add(new Pos(firstX + 1, firstY));
                firstX += 1;
                break; }
            case LEFT: {
                positions.add(new Pos(firstX - 1, firstY));
                firstX -= 1;
                break; }
        }

        // drwa the point
        if(pointX == null) {
            Random draw = new Random();
            pointX = draw.nextInt(50);
            pointY = draw.nextInt(50);
        }
        g.setColor(pointColor);
        g.fillRect(pointX *sideLen, pointY *sideLen, sideLen, sideLen);


        // if don't get a point
        if(!(pointX == firstX && pointY == firstY))
            positions.remove(lastID);
        // if get a point
        else {
            pointX = null;
            pointY = null;
        }

        // repaint all elements and check if they are off the map
        g.setColor(snakeColor);
        for(Pos i : positions) {
            g.fillRect(i.x * sideLen, i.y * sideLen, sideLen, sideLen);
            // if they are off the map -> restart game
            if(i.y==-1 || i.y==51 || i.x==-1 || i.x==53)
                restart();
        }

        // collision check
        if(collision())
            restart();

        timer.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        repaint();
    }


    @Override
    public void keyPressed(KeyEvent e) {
        // direction
        switch (e.getKeyCode()) {
            case 38: {
                if(!des.equals(destination.DOWN))
                    des = destination.UP;
                break; }
            case 40: {
                if(!des.equals(destination.UP))
                    des = destination.DOWN;
                break; }
            case 39: {
                if(!des.equals(destination.LEFT))
                    des = destination.RIGHT;
                break; }
            case 37: {
                if(!des.equals(destination.RIGHT))
                    des = destination.LEFT;
                break; }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }




    public static void main(String[] args) {

        JFrame frame = new JFrame("SNAKE APP");
        Dimension dimension = new Dimension(50 * 15, 50 * 15);
        SnakeGUI b = new SnakeGUI();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(dimension);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        frame.add(b);
        frame.addKeyListener(b);


    }


}


