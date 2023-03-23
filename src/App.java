import javax.swing.JFrame;



class Game extends JFrame {

    private GameContainer gameContainer = new GameContainer();

    public Game() {
        setTitle("Game");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        add(gameContainer);
    }
}


public class App {
    public static void main(String[] args) throws Exception {
        Game game = new Game();


        game.setVisible(true);
    }
}
