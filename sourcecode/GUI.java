/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package radarproject;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

/**
 * This class creates a Graphical user Interface to run the display.
 * @author probs
 */
public class GUI {
    private JFrame window;
    private Display mainPanel;
    private JPanel gameOverPanel, startPanel, topPanel, midUpPanel, midDownPanel, bottomPanel, midPanel;
    private int score;
    private JLabel gameOverLabel, scoreLabel, startLabel, startLabel2;
    private JTextField nameField;
    private JTextArea highScoreField;
    private JScrollPane scrollPane;
    private MouseMotionListener crosshairListener;
    private Timer gameOverTimer;
    private JButton startOverButton, quitButton, optionsButton, highScoreButton, 
            resetHighScoreButton, viewHighScoresButton, goBackButton, enterButton;
    private SoundPlayer gameOverSound;
    private String nameOfPlayer;

    /**
     * This constructor is the Graphical User Interface that runs the display, 
     * while checking the value of startMenu, to check to see if it needs to 
     * display the start menu.
     * and the start menu's
     * @param startMenu A boolean value to check for startMenu
     */
    public GUI(boolean startMenu){
        if(startMenu){
            startGameMenu();
        }
        else{
            // Create window
            window = new JFrame("Attack of The Triangles");
            window.setCursor( window.getToolkit().createCustomCursor(
                       new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                       new Point(),
                       null ) );
            window.setSize(700,720);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            // Create display
            score = 0;
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
        gameOverPanel.setLayout(new GridLayout(5,0,20,20));
        if(checkHighScore()){
            gameOverLabel = new JLabel("NEW HIGH SCORE!!!");
        }
        else{
            gameOverLabel = new JLabel("GAME OVER!!!");
        }
        topPanel = new JPanel();
        midUpPanel = new JPanel();
        midPanel = new JPanel();
        midDownPanel = new JPanel();
        bottomPanel = new JPanel();
        gameOverPanel.setBackground(Color.BLACK);
        topPanel.setBackground(Color.BLACK);
        midUpPanel.setBackground(Color.BLACK);
        midPanel.setBackground(Color.BLACK);
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
        midPanel.add(startOverButton);
        gameOverPanel.add(midPanel);
        highScoreButton = new JButton("Save Score");
        highScoreButton.addActionListener(new HighScoreButtonListener());
        highScoreButton.setFont(new Font("Arial", Font.BOLD, 70));
        highScoreButton.setBackground(Color.GREEN.darker());
        highScoreButton.setForeground(Color.BLACK);
        highScoreButton.setBorderPainted(false);
        midDownPanel.add(highScoreButton);
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

    private void startGameMenu(){
        window = new JFrame("Attack of The Triangles");
        window.setSize(700,720);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        // Edit this to start a game start screen
        startPanel = new JPanel();
        startPanel.setLayout(new GridLayout(5,0,20,20));
        startLabel = new JLabel("Attack of");
        startLabel2 = new JLabel("The Triangles");
        topPanel = new JPanel();
        midUpPanel = new JPanel();
        midPanel = new JPanel();
        midDownPanel = new JPanel();
        bottomPanel = new JPanel();
        startPanel.setBackground(Color.BLACK);
        topPanel.setBackground(Color.BLACK);
        midUpPanel.setBackground(Color.BLACK);
        midPanel.setBackground(Color.BLACK);
        midDownPanel.setBackground(Color.BLACK);
        bottomPanel.setBackground(Color.BLACK);
        topPanel.setLayout(new GridLayout(3,0));
        topPanel.add(new JLabel());
        startLabel.setFont(new Font("Arial",Font.BOLD,50));
        topPanel.add(startLabel);
        startLabel.setHorizontalAlignment(JLabel.CENTER);
        startLabel.setForeground(Color.RED.darker());
        topPanel.add(startLabel2);
        startLabel2.setFont(new Font("Arial", Font.BOLD, 50));
        startLabel2.setHorizontalAlignment(JLabel.CENTER);
        startLabel2.setForeground(Color.RED.darker());
        startPanel.add(topPanel);
        midUpPanel.setLayout(new GridLayout(3,0));
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
        midPanel.add(startOverButton);
        startPanel.add(midPanel);
        optionsButton = new JButton("Options");
        optionsButton.addActionListener(new OptionsListener());
        optionsButton.setFont(new Font("Arial", Font.BOLD, 70));
        optionsButton.setBackground(Color.GREEN.darker());
        optionsButton.setForeground(Color.BLACK);
        optionsButton.setBorderPainted(false);
        midDownPanel.add(optionsButton);
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
    private void openOptions(){
        window = new JFrame("Game Options");
        window.setSize(700,720);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setLocationRelativeTo(null);
        window.setResizable(false);
        startPanel = new JPanel();
        startPanel.setBackground(Color.BLACK);
        startPanel.setLayout(new GridLayout(4,0,20,20));
        startLabel = new JLabel("Game Options");
        startLabel.setForeground(Color.RED.darker());
        startLabel.setFont(new Font("Arial",Font.BOLD,50));
        startLabel.setHorizontalAlignment(JLabel.CENTER);
        topPanel = new JPanel();
        midUpPanel = new JPanel();
        midDownPanel = new JPanel();
        bottomPanel = new JPanel();
        topPanel.setBackground(Color.BLACK);
        topPanel.setLayout(new GridLayout(3,0,20,20));
        midUpPanel.setBackground(Color.BLACK);
        midDownPanel.setBackground(Color.BLACK);
        bottomPanel.setBackground(Color.BLACK);
        // Title
        topPanel.add(new JLabel());
        topPanel.add(startLabel);
        topPanel.add(new JLabel());
        startPanel.add(topPanel);
        // Reset High scores
        resetHighScoreButton = new JButton("Reset High Scores");
        resetHighScoreButton.addActionListener(new ResetHighScoreButtonListener());
        resetHighScoreButton.setFont(new Font("Arial", Font.BOLD, 70));
        resetHighScoreButton.setBackground(Color.GREEN.darker());
        resetHighScoreButton.setForeground(Color.BLACK);
        resetHighScoreButton.setBorderPainted(false);
        midUpPanel.add(resetHighScoreButton);
        startPanel.add(midUpPanel);
        // View High scores
        viewHighScoresButton = new JButton("View High Scores");
        viewHighScoresButton.addActionListener(new ViewHighScoreButtonListener());
        viewHighScoresButton.setFont(new Font("Arial", Font.BOLD, 70));
        viewHighScoresButton.setBackground(Color.GREEN.darker());
        viewHighScoresButton.setForeground(Color.BLACK);
        viewHighScoresButton.setBorderPainted(false);
        midDownPanel.add(viewHighScoresButton);
        startPanel.add(midDownPanel);
        // Go back
        goBackButton = new JButton("Go Back");
        goBackButton.addActionListener(new GoBackButtonListener());
        goBackButton.setFont(new Font("Arial", Font.BOLD, 70));
        goBackButton.setBackground(Color.GREEN.darker());
        goBackButton.setForeground(Color.BLACK);
        goBackButton.setBorderPainted(false);
        bottomPanel.add(goBackButton);
        startPanel.add(bottomPanel);
        window.add(startPanel);
        window.setVisible(true);
    }
    private boolean checkHighScore (){
        Scanner in = null;
        try{
            in = new Scanner(new File("highScores.score"));
        }catch(FileNotFoundException e){
            createNewScores();
            try{
                in = new Scanner(new File("highScores.score"));
            }
            catch(Exception ex){}
        }
        long temp=-1;
        while(in.hasNext()){
            if(in.next().equals("$$&&$$")){
                long val = in.nextLong();
                if(val>temp){
                    temp = val;
                }
            }
        }
        in.close();
        return score>temp;
    }
    private void createNewScores(){
        new File("highScores.score");
        saveHighScore();
    }
    private void saveHighScore(){
        FileWriter fw = null;
            try{
                fw = new FileWriter("highScores.score", true);
                BufferedWriter out = new BufferedWriter(fw);
                StringBuilder encrypted = new StringBuilder();
                encrypted.append(nameOfPlayer);
                encrypted.append(" - ");
                encrypted.append(score);
                encrypted = encrypt(encrypted);
                out.write(encrypted.toString()+"\n");
                out.close();
                fw.close();
            }
            catch(FileNotFoundException ex){
                createNewScores();
            }
            catch(IOException ex){}
            window = new JFrame("Score Saved!");
            window.setSize(700,720);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            startPanel = new JPanel();
            startPanel.setBackground(Color.BLACK);
            startPanel.setLayout(new GridLayout(3,0,20,20));
            startLabel = new JLabel("Score has been saved!");
            startLabel.setForeground(Color.RED.darker());
            startLabel.setFont(new Font("Arial",Font.BOLD,50));
            startLabel.setHorizontalAlignment(JLabel.CENTER);
            topPanel = new JPanel();
            midUpPanel = new JPanel();
            midDownPanel = new JPanel();
            bottomPanel = new JPanel();
            topPanel.setBackground(Color.BLACK);
            topPanel.setLayout(new GridLayout(3,0,20,20));
            midUpPanel.setBackground(Color.BLACK);
            midDownPanel.setBackground(Color.BLACK);
            bottomPanel.setBackground(Color.BLACK);
            // Title
            topPanel.add(new JLabel());
            topPanel.add(startLabel);
            topPanel.add(new JLabel());
            startPanel.add(topPanel);
            // View High scores
            viewHighScoresButton = new JButton("View High Scores");
            viewHighScoresButton.addActionListener(new ViewHighScoreButtonListener());
            viewHighScoresButton.setFont(new Font("Arial", Font.BOLD, 70));
            viewHighScoresButton.setBackground(Color.GREEN.darker());
            viewHighScoresButton.setForeground(Color.BLACK);
            viewHighScoresButton.setBorderPainted(false);
            midDownPanel.add(viewHighScoresButton);
            startPanel.add(midDownPanel);
            // Go back
            goBackButton = new JButton("Go Back");
            goBackButton.addActionListener(new GoBackButtonListener());
            goBackButton.setFont(new Font("Arial", Font.BOLD, 70));
            goBackButton.setBackground(Color.GREEN.darker());
            goBackButton.setForeground(Color.BLACK);
            goBackButton.setBorderPainted(false);
            bottomPanel.add(goBackButton);
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
            //if (true){
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
    private class OptionsListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            window.dispose();
            openOptions();
        }
        
    }
    private class HighScoreButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            window.dispose();
            window = new JFrame("Save Score");
            window.setSize(700,720);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            startPanel = new JPanel();
            startPanel.setBackground(Color.BLACK);
            startPanel.setLayout(new GridLayout(4,0,0,0));
            topPanel = new JPanel();
            topPanel.setBackground(Color.BLACK);
            startPanel.add(topPanel);
            midPanel = new JPanel();
            midPanel.setBackground(Color.BLACK);
            startLabel = new JLabel("Please enter your name:");
            startLabel.setFont(new Font("Arial", Font.BOLD, 40));
            startLabel.setForeground(Color.GREEN.darker());
            midPanel.add(startLabel);
            startPanel.add(midPanel);
            bottomPanel = new JPanel();
            bottomPanel.setBackground(Color.BLACK);
            nameField = new JTextField(40);
            nameField.setBackground(Color.GREEN.darker());
            nameField.setForeground(Color.RED.darker());
            nameField.setBorder(null);
            enterButton = new JButton("Enter");
            enterButton.addActionListener(new EnterButtonListener());
            enterButton.setFont(new Font("Arial", Font.BOLD, 20));
            enterButton.setBackground(Color.GREEN.darker());
            enterButton.setForeground(Color.BLACK);
            enterButton.setBorderPainted(false);
            bottomPanel.add(nameField);
            bottomPanel.add(enterButton);
            startPanel.add(bottomPanel);
            window.add(startPanel);
            window.setVisible(true);
        }
        
    }
    private class GoBackButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            window.dispose();
            startGameMenu();
        }
        
    }
    private class ResetHighScoreButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            startLabel.setText("Scores reset!");
            try{
                new FileWriter(new File("highScores.score"));
            }
            catch(IOException ex){}
        }
        
    }
    private class ViewHighScoreButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            window.dispose();
            window = new JFrame("High Scores:");
            window.setSize(700,720);
            window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            window.setLocationRelativeTo(null);
            window.setResizable(false);
            startPanel = new JPanel();
            startPanel.setBackground(Color.BLACK);
            startPanel.setLayout(new GridLayout(4,0,20,20));
            startLabel = new JLabel("Game Options");
            startLabel.setForeground(Color.RED.darker());
            startLabel.setFont(new Font("Arial",Font.BOLD,50));
            startLabel.setHorizontalAlignment(JLabel.CENTER);
            topPanel = new JPanel();
            midPanel = new JPanel();
            bottomPanel = new JPanel();
            topPanel.setBackground(Color.BLACK);
            topPanel.setLayout(new GridLayout(3,0,20,20));
            midPanel.setBackground(Color.BLACK);
            midDownPanel.setBackground(Color.BLACK);
            bottomPanel.setBackground(Color.BLACK);
            // Title
            topPanel.add(new JLabel());
            topPanel.add(startLabel);
            topPanel.add(new JLabel());
            startPanel.add(topPanel);
            // Score viewing
            Scanner in = null;
            StringBuilder sb = new StringBuilder();
            try{
                in = new Scanner(new File("highScores.score"));
                while(in.hasNext()){
                    sb.append(in.nextLine());
                    sb.append("\n");
                }
            }
            catch(FileNotFoundException ex){}
            sb = decrypt(sb);
            highScoreField = new JTextArea(sb.toString(), 5, 20);
            highScoreField.setLineWrap(true);
            highScoreField.setBackground(Color.BLACK);
            highScoreField.setForeground(Color.WHITE);
            highScoreField.setFont(new Font("Arial", Font.PLAIN, 25));
            highScoreField.setEditable(false);
            scrollPane = new JScrollPane(highScoreField);
            scrollPane.setBorder(null);
            JScrollBar scrollBar = new JScrollBar();
            scrollBar.setBackground(Color.GRAY.darker().darker());
            scrollBar.setBorder(null);
            scrollPane.setVerticalScrollBar(scrollBar);
            scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
            midPanel.add(scrollPane);
            startPanel.add(midPanel);
            // Go back
            goBackButton = new JButton("Go Back");
            goBackButton.addActionListener(new GoBackButtonListener());
            goBackButton.setFont(new Font("Arial", Font.BOLD, 70));
            goBackButton.setBackground(Color.GREEN.darker());
            goBackButton.setForeground(Color.BLACK);
            goBackButton.setBorderPainted(false);
            bottomPanel.add(goBackButton);
            startPanel.add(bottomPanel);
            window.add(startPanel);
            window.setVisible(true);
        }
        
    }
    private StringBuilder decrypt(StringBuilder encrypted){
        StringBuilder decrypted = new StringBuilder();
        Scanner scan = new Scanner(encrypted.toString());
        while(scan.hasNext()){
            String data = scan.nextLine();
            for (int i = 0; i < data.length(); i++) {
                switch (data.charAt(i)){
                    case '0': break;
                    case '1': break;
                    case '2': break;
                    case '3': break;
                    case '4': break;
                    case '5': break;
                    case '6': break;
                    case '7': break;
                    case '8': break;
                    case '9': break;
                    default: data = data.replace(data.charAt(i), ' ');
                        break;
                }
            }
            Scanner numScan = new Scanner(data);
            long lastNumber = 0;
            while(numScan.hasNext()){
                long number = 0;
                number = numScan.nextLong();
                decrypted.append((char)(number - lastNumber));
                lastNumber = number;
            }
        }
        return decrypted;
    }
    private StringBuilder encrypt(StringBuilder decrypted){
        StringBuilder encrypted = new StringBuilder();
        long sum = 0;
        for (int i = 0; i < decrypted.toString().length(); i++) {
            sum += (int)decrypted.toString().charAt(i);
            encrypted.append(sum);
            Random rand = new Random();
            encrypted.append((char)(rand.nextInt(68)+58));
        }
        encrypted.append(sum+10);
        return encrypted;
    }
    private class EnterButtonListener implements ActionListener{

        @Override
        public void actionPerformed(ActionEvent e) {
            if(!nameField.getText().equals("") && !nameField.getText().contains("$$&&$$")){
                nameOfPlayer = nameField.getText();
                window.dispose();
                saveHighScore();
            }
            else{
                startLabel.setText("Invalid name: Please enter a name");
                startLabel.setForeground(Color.RED.darker());
                nameField.setText("");
            }
        }
        
    }
    
    private class SoundPlayer{
        private Clip clip;
        private URL fileResource;
        private AudioInputStream audioStream;
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
}
