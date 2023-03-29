package Commands;

import javax.swing.JPanel;

public class PlayCommand implements Command {

    private JPanel menuPanel;


    public PlayCommand(JPanel menuPanel){
        this.menuPanel = menuPanel;
    }
    
    @Override
    public void execute() {
        System.out.println("Play");
        this.menuPanel.setVisible(false);
        
    }

    
}
