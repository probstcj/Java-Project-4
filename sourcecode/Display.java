/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 * This class extends the JPanel class and creates the main screen of the game.
 * @author probs
 */
public class Display extends JPanel{
    private ImageIcon radar;
    private Sweeper sweeper, secondSweeper;
    private Color sweeperColor;
    private Timer EnemyMovementTimer, EnemySpawnTimer, sweeperTimer, bulletTimer, powerupTimer,
            extendedSweeperTimer, fasterShootingTimer, secondSweeperTimer, freezeEnemiesTimer, 
            showAllEnemiesTimer, doubleDamageTimer, textTimer, textPowerupTimer;
    private ArrayList<Enemy> enemy;
    private ArrayList<Bullet> bullets;
    private ArrayList<Powerup> powerup;
    private Random rand;
    private boolean isMoving=false, isExtended = false, isFaster = false, isSecond=false, 
            isFrozen=false, isShown=false, isVisible=false, isDoubleDamage=false, 
            isPowerup = false, powerOn=false, gameOver=false;
    private int crossX=0, crossY=0, bulletDamage=10, currentPowerup, score=0, level=1, textPowerup=0;
    private You you;
    private SoundPlayer shooting, hurt, explosion, powerupSound;
    private MusicPlayer backgroundMusic;
    
    /**
     * This constructor creates a Display object for the main game. Initializes 
     * all values and objects for the rest of the game.
     */
    public Display(){
        super();
        radar = new ImageIcon(getClass().getClassLoader().getResource("background.png"));
        rand = new Random();
        setBackground(Color.BLACK);
        sweeper = new Sweeper();
        sweeper.setDTheta(.02);
        // Create color for sweeper
        sweeperColor = new Color(0,255,0,50);
        enemy = new ArrayList<>();
        sweeperTimer = new Timer(30, new SweeperListener());
        EnemyMovementTimer = new Timer(40,new MovementListener());
        EnemySpawnTimer = new Timer(4000, new EnemyListener());
        bulletTimer = new Timer(100, new BulletTimerListener());
        powerupTimer = new Timer(60000, new PowerupTimerListener());
        extendedSweeperTimer = new Timer(1, new ExtendedSweeperTimerListener());
        fasterShootingTimer = new Timer(1, new FasterShootingTimerListener());
        secondSweeperTimer = new Timer(1, new SecondSweeperTimerListener());
        freezeEnemiesTimer = new Timer(1, new FreezeEnemiesTimerListener());
        showAllEnemiesTimer = new Timer(1, new ShowAllEnemiesTimerListener());
        doubleDamageTimer = new Timer(1, new DoubleDamageTimerListener());
        textTimer = new Timer(1, new TextTimerListener());
        textPowerupTimer = new Timer(1000, new PowerupTextTimerListener());
        EnemySpawnTimer.start();
        powerupTimer.start();
        EnemyMovementTimer.start();
        sweeperTimer.start();
        you = new You();
        bullets = new ArrayList<>();
        powerup = new ArrayList<>();
        try{
            shooting = new SoundPlayer("sf_laser_18.wav");
            hurt = new SoundPlayer("classic_hurt.wav");
            explosion = new SoundPlayer("explosion.wav");
            powerupSound = new SoundPlayer("powerup.wav");
            backgroundMusic = new MusicPlayer("backgroundMusic.wav");
        }
        catch(Exception e){}
        addMouseListener(new BulletListener());
    }
    
    /**
     * This method overrides the JPanel method. This runs the motion of the 
     * game.
     * @param g The graphics object given by the JPanel (turns into a Graphics2D object)
     */
    @Override
    public void paintComponent (Graphics g){
        try{
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(radar.getImage(), 0, 0,700,700,null);
            // Radar image has a radius of 215 pixels        
            g2.setColor(sweeperColor);
            sweeper.rotate();
            g2.fill(sweeper);
            g2.setColor(Color.WHITE);
            if(secondSweeper != null){
                secondSweeper.setDTheta(.02);
                sweeperColor = new Color(0,255,0,50);
                g2.setColor(sweeperColor);
                g2.fill(secondSweeper);
                g2.setColor(Color.WHITE);
                secondSweeper.rotate();
            }
            boolean intersect = false;
            for (int i = 0; i < enemy.size(); i++) {
                g2.setColor(enemy.get(i).getColor());
                if(isVisible || sweeper.intersects(enemy.get(i)) || (secondSweeper!=null && secondSweeper.intersects(enemy.get(i)))){
                    g2.fill(enemy.get(i));
                }
                if(you.intersects(enemy.get(i))){
                    if(you.getHealth()-enemy.get(i).getHealth()<0){
                        gameOver = true;
                        backgroundMusic.stop();
                    }
                    else{
                        hurt.play();
                        you.setHealth(you.getHealth()-enemy.get(i).getHealth());
                        enemy.remove(i);
                    }
                }
                else{
                    g2.setColor(you.getColor());
                    g2.fill(you);
                    g2.setColor(Color.WHITE);
                }
                if(isMoving && !intersect){
                    intersect = new Crosshair(crossX,crossY).intersects(enemy.get(i));
                } 
            }
            for (int i = 0; i < powerup.size(); i++) {
                g2.setColor(powerup.get(i).getColor());
                if(isVisible || sweeper.intersects(powerup.get(i)) || (secondSweeper!=null && secondSweeper.intersects(enemy.get(i)))){
                    g2.fill(powerup.get(i));
                }
                if(you.intersects(powerup.get(i))){
                    powerup.remove(i);
                }
                else{
                    g2.setColor(you.getColor());
                    g2.fill(you);
                    g2.setColor(Color.WHITE);
                }
                if(isMoving && !intersect){
                    intersect = new Crosshair(crossX,crossY).intersects(powerup.get(i));
                } 
            }
            if (intersect){
                g2.setColor(Color.CYAN);
            }
            else{
                g2.setColor(Color.ORANGE);
            }
            g2.setStroke(new BasicStroke(2));
            g2.draw(new Crosshair(crossX,crossY));
            g2.setColor(Color.WHITE);
            for (int i = 0; i < bullets.size(); i++) {
                g2.fill(bullets.get(i));
                bullets.get(i).move();
                for (int j = 0; j < enemy.size(); j++) {
                    if(!bullets.isEmpty()                                 &&
                       bullets.get(i).getCenterX()<enemy.get(j).getMaxX() && 
                       bullets.get(i).getCenterX()>enemy.get(j).getMinX() &&
                       bullets.get(i).getCenterY()<enemy.get(j).getMaxY() &&
                       bullets.get(i).getCenterY()>enemy.get(j).getMinY()){
                        if(enemy.get(j).getHealth()>0){
                            enemy.get(j).setHealth(enemy.get(j).getHealth()-bullets.get(i).getDamage());
                        }
                        else{
                            score += enemy.get(j).getOrigHealth();
                            explosion.play();
                            enemy.remove(j);
                        }
                        bullets.remove(i);
                    }
                }
                for (int j = 0; j < powerup.size(); j++) {
                    if(!bullets.isEmpty()                                 &&
                       bullets.get(i).getCenterX()<powerup.get(j).getMaxX() && 
                       bullets.get(i).getCenterX()>powerup.get(j).getMinX() &&
                       bullets.get(i).getCenterY()<powerup.get(j).getMaxY() &&
                       bullets.get(i).getCenterY()>powerup.get(j).getMinY()){
                        if(powerup.get(j).getHealth()>0){
                            powerup.get(j).setHealth(powerup.get(j).getHealth()-bullets.get(i).getDamage());
                        }
                        else{
                            // Activate powerups
                            powerupSound.play();
                            switch(powerup.get(j).getPowerup()){
                                case 1: 
                                    // Nuke
                                    enemy.clear();
                                    // Print out a NUKE string or something for effects
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 1;
                                    isPowerup = true;
                                    break;
                                case 2:
                                    // Extended Sweeper
                                    extendedSweeperTimer.start();
                                    extendedSweeperTimer.setDelay(30000);
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 2;
                                    isPowerup = true;
                                    isExtended = true;
                                    break;
                                case 3:
                                    // Faster shooting
                                    fasterShootingTimer.start();
                                    fasterShootingTimer.setDelay(30000);
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 3;
                                    isPowerup = true;
                                    isFaster = true;
                                    break;
                                case 4:
                                    // Add a second sweeper
                                    secondSweeperTimer.start();
                                    secondSweeperTimer.setDelay(30000);
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 4;
                                    isPowerup = true;
                                    isSecond = true;
                                    break;
                                case 5:
                                    // Freeze all enemies
                                    freezeEnemiesTimer.start();
                                    freezeEnemiesTimer.setDelay(30000);
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 5;
                                    isPowerup = true;
                                    isFrozen = true;
                                    break;
                                case 6: 
                                    // Show all enemies
                                    showAllEnemiesTimer.start();
                                    showAllEnemiesTimer.setDelay(30000);
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 6;
                                    isPowerup = true;
                                    isShown = true;
                                    break;
                                case 7:
                                    // Double damage
                                    doubleDamageTimer.start();
                                    doubleDamageTimer.setDelay(30000);
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 7;
                                    isPowerup = true;
                                    isDoubleDamage = true;
                                    break;
                                case 8:
                                    // Full health
                                    you.setHealth(400);
                                    textTimer.start();
                                    textTimer.setDelay(5000);
                                    currentPowerup = 8;
                                    isPowerup = true;
                            }
                            powerup.remove(j);
                        }
                        bullets.remove(i);
                    }
                }
                if(!bullets.isEmpty()            &&
                   (bullets.get(i).getCenterX()<0 || 
                   bullets.get(i).getCenterY()<0 || 
                   bullets.get(i).getCenterX()>700 || 
                   bullets.get(i).getCenterY()>700)){
                    bullets.remove(i);
                }
            }
            if(powerOn && currentPowerup!=0){
                switch(currentPowerup){
                    case 1:
                        Font temp = g2.getFont();
                        g2.setFont(new Font("Arial", 60, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("NUKE!!!", 240, 60);
                        g2.setFont(temp);
                        g2.setColor(Color.WHITE);
                        break;
                    case 2:
                        temp = g2.getFont();
                        g2.setFont(new Font("Arial", 60, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("Extended", 240, 60);
                        g2.drawString("Sweeper", 240,120);
                        g2.setFont(temp);
                        g2.setColor(Color.WHITE);
                        textPowerupTimer.start();
                        break;
                    case 3:
                        temp = g2.getFont();
                        g2.setFont(new Font("Arial", 60, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("Faster", 250, 60);
                        g2.drawString("Shooting", 230,120);
                        g2.setFont(temp);
                        g2.setColor(Color.WHITE);
                        textPowerupTimer.start();
                        break;
                    case 4:
                        temp = g2.getFont();
                        g2.setFont(new Font("Arial", 60, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("Second", 240, 60);
                        g2.drawString("Sweeper", 240,120);
                        g2.setFont(temp);
                        g2.setColor(Color.WHITE);
                        textPowerupTimer.start();
                        break;
                    case 5:
                        temp = g2.getFont();
                        g2.setFont(new Font("Arial", 60, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("Freezing", 230, 60);
                        g2.drawString("Enemies", 230,120);
                        g2.setFont(temp);
                        g2.setColor(Color.WHITE);
                        textPowerupTimer.start();
                        break;
                    case 6:
                        temp = g2.getFont();
                        g2.setFont(new Font("Arial", 60, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("Showing", 230, 60);
                        g2.drawString("Enemies", 230,120);
                        g2.setFont(temp);
                        textPowerupTimer.start();
                        break;
                    case 7:
                        temp = g2.getFont();
                        g2.setFont(new Font("Arial", Font.PLAIN, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("Double", 240, 60);
                        g2.drawString("Damage", 230,120);
                        g2.setFont(temp);
                        textPowerupTimer.start();
                        break;
                    case 8:
                        temp = g2.getFont();
                        g2.setFont(new Font("Arial", Font.PLAIN, 60));
                        g2.setColor(Color.RED);
                        g2.drawString("Full Health", 220, 60);
                        g2.setFont(temp);
                        textPowerupTimer.start();
                        break;
                }
            }
            g2.setFont(new Font("Arial", Font.PLAIN, 30));
            g2.setColor(Color.WHITE);
            g2.drawString("Health:",0,30);
            g2.drawRect(5, 35, 200, 20);
            g2.setColor(Color.RED.darker());
            g2.fillRect(5, 35, you.getHealth()/2, 20);
            g2.setColor(Color.WHITE);
            g2.drawString("Score:", 575, 30);
            Integer scoreInt = score;
            g2.drawString(""+score, 670-17*scoreInt.toString().length(), 70);
            g2.drawString("Level: " + level, 10, 670);
            if(textPowerup!=0 && textPowerup!=30){
                Integer textInt = textPowerup;
                g2.drawString(""+textPowerup, 675-17*textInt.toString().length(), 660);
            }
            level = (int)(score/5000)+1;
            if((int)(900*Math.log(level))<4000){
                EnemySpawnTimer.setDelay(4000-(int)(300*(Math.log(level))));
            }
            else{
                EnemySpawnTimer.setDelay(1);
            }
        }
        catch(IndexOutOfBoundsException e){}
    }

    /**
     * This method returns if the mouse is moving
     * @return Returns true if the mouse is moving, and false if it is not
     */
    public boolean getIsMoving(){
        return isMoving;
    }

    /**
     * This method sets the value of isMoving to show the mouse is moving
     * @param isMoving User-given value to show if the mouse is moving
     */
    public void setIsMoving(boolean isMoving){
        this.isMoving = isMoving;
    }

    /**
     * This method sets the x value of the crosshair
     * @param x The user-given x value of the crosshair
     */
    public void setCrossX(int x){
        crossX = x;
    }

    /**
     * This method sets the y value of the crosshair
     * @param y The user-given y value of the crosshair
     */
    public void setCrossY(int y){
        crossY = y;
    }

    /**
     * This method returns the boolean value of gameOver, to show if the game has ended
     * @return Returns true if the game is over, and false if the game is not over.
     */
    public boolean getGameOver(){
        return gameOver;
    }

    /**
     * This method gets the current score of the game.
     * @return Returns the score of the game
     */
    public int getScore(){
        return score;
    }
    private class MovementListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            for (int i = 0; i < enemy.size(); i++) {
                enemy.get(i).move();
            }
            for (int i = 0; i < powerup.size(); i++) {
                powerup.get(i).move();
            }
        }
        
    }
    private class EnemyListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            enemy.add(new Enemy(rand.nextFloat()+rand.nextInt(7),rand.nextInt(300)+50));
        }
        
    }
    private class PowerupTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            powerup.add(new Powerup(rand.nextFloat()+rand.nextInt(7),100, rand.nextInt(7)+1));
        }
        
    }
    private class SweeperListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }
        
    }
    private class BulletListener implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
        }

        @Override
        public void mousePressed(MouseEvent e) {
            bulletTimer.start();
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            bulletTimer.stop();
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
        
    }
    private class BulletTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            bullets.add(new Bullet(crossX, crossY, bulletDamage));
            shooting.play();
        }
        
    }
    private class ExtendedSweeperTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isExtended){
                sweeper.setDTheta(sweeper.getDTheta()*2);
                isExtended = !isExtended;
            }
            else{
                sweeper.setDTheta(sweeper.getDTheta()/2);
                isExtended = !isExtended;
                extendedSweeperTimer.setDelay(1);
                extendedSweeperTimer.stop();
            }
        }
    
    }
    private class FasterShootingTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isFaster){
                bulletTimer.setDelay(bulletTimer.getDelay()/4);
                isFaster = !isFaster;
            }
            else{
                bulletTimer.setDelay(bulletTimer.getDelay()*4);
                isFaster = !isFaster;
                fasterShootingTimer.setDelay(1);
                fasterShootingTimer.stop();
            }
        }
        
    }
    private class SecondSweeperTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isSecond){
                secondSweeper = new Sweeper();
                isSecond = !isSecond;
            }
            else{
                secondSweeper = null;
                isSecond = !isSecond;
                secondSweeperTimer.setDelay(1);
                secondSweeperTimer.stop();
            }
        }
        
    }
    private class FreezeEnemiesTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isFrozen){
                EnemyMovementTimer.stop();
                isFrozen = !isFrozen;
            }
            else{
                EnemyMovementTimer.start();
                isFrozen = !isFrozen;
                freezeEnemiesTimer.setDelay(1);
                freezeEnemiesTimer.stop();
            }
        }
        
    }
    private class ShowAllEnemiesTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isShown){
                isVisible = true;
                isShown = !isShown;
            }
            else{
                isVisible = false;
                isShown = !isShown;
                showAllEnemiesTimer.setDelay(1);
                showAllEnemiesTimer.stop();
            }
        }
        
    }
    private class DoubleDamageTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isDoubleDamage){
                bulletDamage*=2;
                isDoubleDamage = !isDoubleDamage;
            }
            else{
                bulletDamage/=2;
                isDoubleDamage = !isDoubleDamage;
                doubleDamageTimer.setDelay(1);
                doubleDamageTimer.stop();
            }
        }
        
    }
    private class TextTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(isPowerup){
                powerOn = true;
                isPowerup = !isPowerup;
            }
            else{
                powerOn = false;
                isPowerup = !isPowerup;
                textTimer.setDelay(1);
                textTimer.stop();
            }
        }
        
    }
    private class PowerupTextTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(textPowerup == 0){
                textPowerup = 30;
                textPowerupTimer.stop();
            }
            else{
                textPowerup--;
            }
        }
        
    }
    private class SoundPlayer{
        private Clip clip;
        private AudioInputStream audioStream;
        private URL fileResource;
        public SoundPlayer(String path) throws 
                UnsupportedAudioFileException, 
                IOException, 
                LineUnavailableException{
            fileResource = getClass().getClassLoader().getResource(path);
            audioStream = AudioSystem.getAudioInputStream(fileResource);
            clip = AudioSystem.getClip();
            clip.open(audioStream);
        }
        public void play(){
            if(clip.isRunning()){
                clip.stop();
            }
            clip.setFramePosition(0);
            clip.start();
        }
    }
    private class MusicPlayer{
        private Clip clip;
        private AudioInputStream audioStream;
        private URL fileResource;
        public MusicPlayer(String path)throws 
                UnsupportedAudioFileException, 
                IOException, 
                LineUnavailableException{
                fileResource = getClass().getClassLoader().getResource(path);
                audioStream = AudioSystem.getAudioInputStream(fileResource);
                clip = AudioSystem.getClip();
                clip.open(audioStream);
                clip.start();
                clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
        public void stop(){
            clip.stop();
        }
    }
}
