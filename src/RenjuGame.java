import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class RenjuGame extends JFrame{

    // set default values for the size of game units and their number
    static int DEFAULT_UNIT_SIZE = 40;
    static int DEFAULT_UNIT_NUMBER = 15;

    public static CardLayout layout = new CardLayout();

    // making the panels for mainPanel
    public static JPanel mainPanel = new JPanel();
    public static MenuPanel menuPanel = new MenuPanel();
    public static GamePanel gamePanel = new GamePanel();
    public static LoaderPanel loaderPanel = new LoaderPanel();
    public static SettingsPanel settingsPanel = new SettingsPanel();

    public RenjuGame(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        setSize(SCREEN_WIDTH, SCREEN_HEIGHT);

        // set mainpanel layout
        mainPanel.setLayout(layout);

        // adding the panels to the mainpanel
        mainPanel.add(menuPanel, "MENU");
        mainPanel.add(gamePanel, "GAME");
        mainPanel.add(loaderPanel, "LOADER");
        mainPanel.add(settingsPanel, "SETTINGS");

        add(mainPanel);
        setTitle("Renju");
        ImageIcon logo = new ImageIcon("./images/icon.png");
        setIconImage(logo.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public static void main(String[] args){

        new RenjuGame();

    }
}