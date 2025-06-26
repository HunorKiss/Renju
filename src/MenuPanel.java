import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MenuPanel extends JPanel{

    private BufferedImage backgroundImage;
    public static MyButton newGameButton = new MyButton("New Game");
    public static MyButton loadGameButton = new MyButton("Load Game");
    public static MyButton settingsButton = new MyButton("Settings");
    public static MyButton exitButton = new MyButton("Exit");

    public MenuPanel(){

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int SCREEN_WIDTH = (int) screenSize.getWidth();
        int SCREEN_HEIGHT = (int) screenSize.getHeight();
        this.setPreferredSize( new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT) );
        this.setFocusable(true);

        // set background
        try {
             backgroundImage = ImageIO.read(new File("images/background.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageIcon renju = new ImageIcon("images/renju.jpg");
        JLabel imageLabel = new JLabel(renju);

        // set layout of MenuPanel
        setLayout(new GridLayout(1, 2));
        setButtons();

        this.add(imageLabel);
        // panel to contain the buttons
        JLabel buttonLabel = new JLabel();
        buttonLabel.setPreferredSize( new Dimension(SCREEN_WIDTH/2, SCREEN_HEIGHT) );
        buttonLabel.setBackground( new Color(0, 0, 0, 0) );
        this.add(buttonLabel);

        buttonLabel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20,20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        buttonLabel.add(newGameButton, gbc);
        gbc.gridy = 1;
        buttonLabel.add(loadGameButton, gbc);
        gbc.gridy = 2;
        buttonLabel.add(settingsButton, gbc);
        gbc.gridy = 3;
        buttonLabel.add(exitButton, gbc);

    }

    private void setButtons() {

        // MENU board -> new game
        newGameButton.addActionListener( event -> {
            RenjuGame.layout.show(RenjuGame.mainPanel, "GAME");
            RenjuGame.gamePanel.startGame( null );
        });

        // MENU board -> load game
        loadGameButton.addActionListener( event -> {
            RenjuGame.layout.show(RenjuGame.mainPanel, "LOADER");
            RenjuGame.loaderPanel.makeModel();
        });

        // MENU board -> settings
        settingsButton.addActionListener( event -> RenjuGame.layout.show(RenjuGame.mainPanel, "SETTINGS"));

        //MENU board -> exit
        exitButton.addActionListener( event -> System.exit(0));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
