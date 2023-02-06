/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject2.pkg0;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

/**
 *
 * @author probs
 */
public class GUI {
    private JFrame window;
    private Display mainPanel;
    private JPanel gameOverPanel, startPanel, topPanel, midUpPanel, midDownPanel, bottomPanel;
    private int score;
    private JLabel gameOverLabel, scoreLabel, startLabel;
    private MouseMotionListener crosshairListener;
    private Timer gameOverTimer;
    private JButton startOverButton, quitButton;
    private SoundPlayer gameOverSound;
    public GUI(boolean startMenu){
        if(startMenu){
            startGameMenu();
        }
        else{
            // Create window
            window = new JFrame("Radar game");
            window.setCursor( window.getToolkit().createCustomCursor(
                       new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                       new Point(),
                       null ) );
            window.setSize(700,720);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            // Create display
            mainPanel = new Display();
            window.add(mainPanel);
            // Set visibility
            window.setVisible(true);
            crosshairListener = new CrosshairListener();
            mainPanel.addMouseMotionListener(crosshairListener);
            gameOverTimer = new Timer(1, new GameOverTimerListener());
            gameOverTimer.start();
        }
    }
    private void gameOver(){
        try{
            gameOverSound = new SoundPlayer("gameOver.wav");
        }
        catch(Exception ex){}
        window = new JFrame("GAME OVER");
        window.setCursor(0);
        window.setSize(700,720);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        gameOverPanel = new JPanel();
        gameOverPanel.setLayout(new GridLayout(4,0,20,20));
        gameOverLabel = new JLabel("GAME OVER!!!");
        topPanel = new JPanel();
        midUpPanel = new JPanel();
        midDownPanel = new JPanel();
        bottomPanel = new JPanel();
        gameOverPanel.setBackground(Color.BLACK);
        topPanel.setBackground(Color.BLACK);
        midUpPanel.setBackground(Color.BLACK);
        midDownPanel.setBackground(Color.BLACK);
        bottomPanel.setBackground(Color.BLACK);
        topPanel.setLayout(new GridLayout(3,0));
        topPanel.add(new JLabel());
        gameOverLabel.setFont(new Font("Arial",Font.BOLD,56));
        topPanel.add(gameOverLabel);
        gameOverLabel.setHorizontalAlignment(JLabel.CENTER);
        gameOverLabel.setForeground(Color.RED.darker());
        topPanel.add(new JLabel());
        gameOverPanel.add(topPanel);
        midUpPanel.setLayout(new GridLayout(3,0));
        // Later put in actual score
        scoreLabel = new JLabel("Score: " + score);
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 48));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setForeground(Color.GREEN.darker());
        midUpPanel.add(new JLabel());
        midUpPanel.add(scoreLabel);
        midUpPanel.add(new JLabel());
        gameOverPanel.add(midUpPanel);
        startOverButton = new JButton("Start Over");
        startOverButton.addActionListener(new StartOverListener());
        startOverButton.setFont(new Font("Arial", Font.BOLD, 70));
        startOverButton.setBackground(Color.GREEN.darker());
        startOverButton.setForeground(Color.BLACK);
        startOverButton.setBorderPainted(false);
        midDownPanel.add(startOverButton);
        gameOverPanel.add(midDownPanel);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new QuitListener());
        quitButton.setFont(new Font("Arial", Font.BOLD, 70));
        quitButton.setBackground(Color.GREEN.darker());
        quitButton.setForeground(Color.BLACK);
        quitButton.setBorderPainted(false);
        bottomPanel.add(quitButton);
        gameOverPanel.add(bottomPanel);
        window.add(gameOverPanel);
        gameOverSound.play();
        window.setVisible(true);
        
    }
    public void startGameMenu(){
        
        window = new JFrame("Radar game");
        window.setSize(700,720);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        // Edit this to start a game start screen
        startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(4,0,20,20));
        startLabel = new JLabel("Radar-mania");
        topPanel = new JPanel();
        midUpPanel = new JPanel();
        midDownPanel = new JPanel();
        bottomPanel = new JPanel();
        startPanel.setBackground(Color.BLACK);
        topPanel.setBackground(Color.BLACK);
        midUpPanel.setBackground(Color.BLACK);
        midDownPanel.setBackground(Color.BLACK);
        bottomPanel.setBackground(Color.BLACK);
        topPanel.setLayout(new GridLayout(3,0));
        topPanel.add(new JLabel());
        startLabel.setFont(new Font("Arial",Font.BOLD,56));
        topPanel.add(startLabel);
        startLabel.setHorizontalAlignment(JLabel.CENTER);
        startLabel.setForeground(Color.RED.darker());
        topPanel.add(new JLabel());
        startPanel.add(topPanel);
        midUpPanel.setLayout(new GridLayout(3,0));
        // Later put in actual score
        scoreLabel = new JLabel("by: Caleb Probst");
        scoreLabel.setFont(new Font("Arial", Font.PLAIN, 36));
        scoreLabel.setHorizontalAlignment(JLabel.CENTER);
        scoreLabel.setForeground(Color.GREEN.darker());
        midUpPanel.add(new JLabel());
        midUpPanel.add(scoreLabel);
        midUpPanel.add(new JLabel());
        startPanel.add(midUpPanel);
        startOverButton = new JButton("Start");
        startOverButton.addActionListener(new StartOverListener());
        startOverButton.setFont(new Font("Arial", Font.BOLD, 70));
        startOverButton.setBackground(Color.GREEN.darker());
        startOverButton.setForeground(Color.BLACK);
        startOverButton.setBorderPainted(false);
        midDownPanel.add(startOverButton);
        startPanel.add(midDownPanel);
        quitButton = new JButton("Quit");
        quitButton.addActionListener(new QuitListener());
        quitButton.setFont(new Font("Arial", Font.BOLD, 70));
        quitButton.setBackground(Color.GREEN.darker());
        quitButton.setForeground(Color.BLACK);
        quitButton.setBorderPainted(false);
        bottomPanel.add(quitButton);
        startPanel.add(bottomPanel);
        window.add(startPanel);
        window.setVisible(true);
    }
    private class CrosshairListener implements MouseMotionListener{

        @Override
        public void mouseDragged(MouseEvent e) {
            mainPanel.setIsMoving(true);
            mainPanel.setCrossX(e.getX());
            mainPanel.setCrossY(e.getY());
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mainPanel.setIsMoving(true);
            mainPanel.setCrossX(e.getX());
            mainPanel.setCrossY(e.getY());
        }
        
    }
    private class GameOverTimerListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(mainPanel.getGameOver()){
                score = mainPanel.getScore();
                window.dispose();
                gameOver();
                gameOverTimer.stop();
            }
        }
        
    }
    private class StartOverListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            window.dispose();
            new GUI(false);
        }
        
    }
    private class QuitListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            window.dispose();
            System.exit(0);
        }
        
    }
    private class SoundPlayer{
        private Clip clip;
        private AudioInputStream audioStream;
        public SoundPlayer(String path) throws 
                UnsupportedAudioFileException, 
                IOException, 
                LineUnavailableException{
            audioStream = AudioSystem.getAudioInputStream(new File(path).getAbsoluteFile());
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
}
