import java.awt.AWTKeyStroke;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.KeyStroke;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;

import Builder.GameBuilder;
import Commands.Command;
import Commands.ControlsCommand;
import Commands.ExitCommand;
import Commands.PlayCommand;
import Game.GameContainer;



class Game extends JFrame {

    private GameContainer gameContainer;

    public Game() {
        setTitle("Game");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);  
        menu();     
    }

    public void buildMenu() {
        // Create menu panel with options
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(3, 1));

    
        // Asteroid count spinner
        JLabel asteroidLabel = new JLabel("Asteroid count:");
        SpinnerModel asteroidModel = new SpinnerNumberModel(10, 1, 100, 1); // Initial value, min, max, step
        JSpinner asteroidSpinner = new JSpinner(asteroidModel);
        asteroidSpinner.setPreferredSize(new Dimension(80, 30));
        JPanel asteroidPanel = new JPanel();
        asteroidPanel.add(asteroidLabel);
        asteroidPanel.add(asteroidSpinner);
        menuPanel.add(asteroidPanel);
    
        JPanel scrollPanel = new JPanel();
        menuPanel.add(scrollPanel);
    
        // Start game button
        JButton startGameButton = new JButton("Start game");
        startGameButton.addActionListener(e -> {
            gameContainer = new GameBuilder()
                    .setAsteroidCount((int) asteroidSpinner.getValue())
                    .build();
            add(gameContainer);
            menuPanel.setVisible(false);
        });
        startGameButton.setPreferredSize(new Dimension(100, 30));
        menuPanel.add(startGameButton);
        menuPanel.setPreferredSize(new Dimension(300, 200)); // set width to 300 and height to 200

        add(menuPanel);
    }
    
    

    public void menu(){
                // Create menu panel with options
                JPanel menuPanel = new JPanel();
                menuPanel.setLayout(new GridLayout(3, 1));
                Command playCommand = new PlayCommand(menuPanel);
                Command controlsCommand = new ControlsCommand();
                Command exitCommand = new ExitCommand();
                //Menu state?
                JButton playButton = new JButton("Play Game");
                playButton.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
         
                            playCommand.execute();
                            buildMenu();
                    }
                });
                playButton.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                            playCommand.execute();
                            buildMenu();

                    }
                });
                menuPanel.add(playButton);
        
                JButton controlsButton = new JButton("Controls");
                controlsButton.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        controlsCommand.execute();
                    }
                });
                controlsButton.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        controlsCommand.execute();
                    }
                });
                menuPanel.add(controlsButton);
        
                JButton exitButton = new JButton("Exit");
                exitButton.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent e) {
                        exitCommand.execute();
                    }
                });
                exitButton.addMouseListener(new MouseAdapter() {
                    public void mouseClicked(MouseEvent e) {
                        exitCommand.execute();
                    }
                });
                menuPanel.add(exitButton);

                Set<AWTKeyStroke> forwardKeys = new HashSet<AWTKeyStroke>(
                    menuPanel.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS));
                forwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
                menuPanel.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, forwardKeys);
                
                Set<AWTKeyStroke> backwardKeys = new HashSet<AWTKeyStroke>(
                    menuPanel.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS));
                backwardKeys.add(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
                menuPanel.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, backwardKeys);

                this.add(menuPanel);
    }
}




public class App {
    public static void main(String[] args) throws Exception {
        Game game = new Game();
        game.setVisible(true);
    }
}
