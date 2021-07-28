package com.company;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.Random;


public class Panel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 75;
    final int x[] = new int[GAME_UNITS];
    final int y[] = new int[GAME_UNITS];
    int bodyParts = 6;
    int score;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;


    Panel(){
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        setApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }
    public void paintComponent(Graphics g){
        //Draws the game screen
        super.paintComponent(g);
        draw(g);

    }
    public void draw(Graphics g){

        if(running){
            //Game grid lines
            /*
            for(int i=0; i<SCREEN_HEIGHT/UNIT_SIZE;i++){
                g.drawLine(i*UNIT_SIZE, 0, i*UNIT_SIZE, SCREEN_HEIGHT);
                g.drawLine(0, i*UNIT_SIZE, SCREEN_WIDTH, i*UNIT_SIZE);
            }
            */
            //Draws the apples
            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            //Draws the snek
            for(int i=0; i<bodyParts; i++){
                //Head
                if(i == 0){
                    g.setColor(Color.green);
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                //Body
                else{
                    g.setColor(new Color(45,180,0));
                    //rainbow snek
                    g.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    g.fillRect(x[i], y[i], UNIT_SIZE, UNIT_SIZE);
                }
                g.setColor(Color.ORANGE);
                g.setFont(new Font("Arial", Font.BOLD, 30));
                FontMetrics metrics = getFontMetrics(g.getFont());
                //Game over at center of the screen
                g.drawString("Score: "+score, (SCREEN_WIDTH - metrics.stringWidth("Score: "+score)) /2, g.getFont().getSize());
            }
        }
        else{
            gameOver(g);
        }



    }
    public void setApple(){
        appleX = random.nextInt((int)(SCREEN_WIDTH / UNIT_SIZE)) * UNIT_SIZE;
        appleY = random.nextInt((int)(SCREEN_HEIGHT / UNIT_SIZE)) * UNIT_SIZE;
    }
    public void move(){
        for(int i=bodyParts; i>0; i--){
            x[i] = x[i-1];
            y[i] = y[i-1];
        }

        //Which direction the snake is moving
        switch (direction){
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
        }
    }
    public void checkPoints(){
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            score++;
            setApple();
        }
    }
    public void checkCollisions(){
        //Checks if the head collides with the body
        for (int i = bodyParts; i>0; i--){
            if((x[0] == x[i])&&(y[0] == y[i])){
                running = false;
            }
        }
        //Checks if the head collides with left border
        if(x[0]<0){
            running = false;
        }
        //Checks if the head collides with right border
        if(x[0]>SCREEN_WIDTH){
            running = false;
        }
        //Checks if the head collides with top border
        if(y[0]<0){
            running = false;
        }
        //Checks if the head collides with bottom border
        if(y[0]>SCREEN_HEIGHT){
            running = false;
        }

        //Stops the timer when game not running
        if(!running){
            timer.stop();
        }

    }
    public void gameOver(Graphics g){
        //Game over text
        g.setColor(Color.ORANGE);
        g.setFont(new Font("Arial", Font.BOLD, 75));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        //Game over at center of the screen
        g.drawString("Game Over!", (SCREEN_WIDTH - metrics1.stringWidth("Game Over!")) /2, SCREEN_HEIGHT/2);

        //Score text
        g.setFont(new Font("Arial", Font.BOLD, 30));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        //Game over at center of the screen
        g.drawString("Score: "+score, (SCREEN_WIDTH - metrics2.stringWidth("Score: "+score)) /2, g.getFont().getSize());
    }

    @Override
    public void actionPerformed(ActionEvent e){
        if(running){
            move();
            checkPoints();
            checkCollisions();
        }
        repaint();
    }
    public class MyKeyAdapter extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e){
            switch(e.getKeyCode()){
                case KeyEvent.VK_LEFT:
                case KeyEvent.VK_A:
                    if(direction != 'R'){
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                case KeyEvent.VK_D:
                    if(direction != 'L'){
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                case KeyEvent.VK_W:
                    if(direction != 'D'){
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                case KeyEvent.VK_S:
                    if(direction != 'U'){
                        direction = 'D';
                    }
                    break;
                case KeyEvent.VK_R:
                    if(!running){
                        startGame();
                    }
                    break;
            }
        }
    }
    
}
